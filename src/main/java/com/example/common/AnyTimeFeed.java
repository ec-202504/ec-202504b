// EarthquakeAndVolcanoFeed.java
package com.example.common;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.net.URL;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 地震、火山、気象情報を取得するクラス.
 */

@Component
public class AnyTimeFeed {

    @Autowired
    private ServletContext application;

    /** フィードのリンク */
    private static final String FEED_URL = "https://www.data.jma.go.jp/developer/xml/feed/extra.xml";

    /**
     * 1分に1回、随時フィードを取得する.
     */
    @PostConstruct
    @Scheduled(fixedRate = 1 * 60 * 1000)
    public void fetchFeed() {
        try {
            List<AnyTimeFeedReport> anyTimeFeedsReports = new ArrayList<>();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();

            try (InputStream stream = new URL(FEED_URL).openStream()) {
                Document doc = builder.parse(stream);
                NodeList entries = doc.getElementsByTagNameNS("http://www.w3.org/2005/Atom", "entry");

                for (int i = 0; i < entries.getLength(); i++) {
                    Element entry = (Element) entries.item(i);
                    String title = getText(entry, "title");
                    String updated = getText(entry, "updated");
                    //時間フォーマット
                    ZonedDateTime dtUtc = ZonedDateTime.parse(updated);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    ZonedDateTime dtUtcToJst = dtUtc.withZoneSameInstant(ZoneId.of("Asia/Tokyo"));
                    updated = dtUtcToJst.format(formatter);

                    String content = getText(entry, "content");
                    Element linkEl = (Element) entry.getElementsByTagNameNS("http://www.w3.org/2005/Atom", "link").item(0);
                    String xmlUrl = linkEl.getAttribute("href");

                    List<String> detailHeadlines = fetchDetailHeadlines(builder, xmlUrl, title);

                    anyTimeFeedsReports.add(new AnyTimeFeedReport(title, updated, xmlUrl, content, detailHeadlines));
                }
            }

            application.setAttribute("anyTimeFeedsReports", anyTimeFeedsReports);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * タグからその内容を取得する
     *
     * @param parent 親タグ
     * @param tagName タグの名前空間（別名）
     * @return
     */
    private String getText(Element parent, String tagName) {
        NodeList list = parent.getElementsByTagNameNS("http://www.w3.org/2005/Atom", tagName);
        return list.getLength() > 0 ? list.item(0).getTextContent() : "";
    }

    /**
     *臨時情報の詳細を取得する.
     *
     * @param builder XMLをJavaのデータ構造に変換するビルダ
     * @param url URL
     * @param title titleタグ
     * @return　フィード詳細内容
     */
    private List<String> fetchDetailHeadlines(DocumentBuilder builder, String url, String title) {
        List<String> headlines = new ArrayList<>();
        try (InputStream stream = new URL(url).openStream()) {
            Document detailDoc = builder.parse(stream);

            NodeList headNodes = detailDoc.getElementsByTagNameNS("http://xml.kishou.go.jp/jmaxml1/informationBasis1/", "Head");
            if (headNodes.getLength() > 0) {
                Element head = (Element) headNodes.item(0);
                NodeList texts = head.getElementsByTagName("Title");
                if (texts.getLength() > 0) {
                    headlines.add("[見出し] " + texts.item(0).getTextContent());
                }

                NodeList reportDateTime = head.getElementsByTagName("ReportDateTime");
                if (reportDateTime.getLength() > 0) {
                    OffsetDateTime dtJst = OffsetDateTime.parse(reportDateTime.item(0).getTextContent());
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    String formatted = dtJst.atZoneSameInstant(ZoneId.of("Asia/Tokyo")).format(formatter);
                    headlines.add("[通知時刻] " + formatted);
                }
            }

            NodeList headLineNodes = detailDoc.getElementsByTagNameNS("http://xml.kishou.go.jp/jmaxml1/informationBasis1/", "Headline");
            if (headNodes.getLength() > 0) {
                Element headline = (Element) headNodes.item(0);
                NodeList texts = headline.getElementsByTagName("Text");
                if (texts.getLength() > 0) {
                    headlines.add("[随時情報] " + texts.item(0).getTextContent());
                }
            }

            NodeList commentTexts = detailDoc.getElementsByTagNameNS(
                    "http://xml.kishou.go.jp/jmaxml1/body/meteorology1/",
                    "Text"
            );

            for (int i = 0; i < commentTexts.getLength(); i++) {
                Element textEl = (Element) commentTexts.item(i);
                String type = textEl.getAttribute("type");
                if ("本文".equals(type)) {
                    addIfExists(textEl.getParentNode() instanceof Element ? (Element) textEl.getParentNode() : textEl, "Text", "[コメント]", headlines);
                }
            }

            }
         catch (Exception e) {
            e.printStackTrace();
        }
        return headlines;
    }

    /**
     * 或るタグに対して、そのタグが存在すれば詳細内容として追加する.
     *
     * @param parent 親タグ
     * @param tag タグ
     * @param label ラベル
     * @param headlines 詳細内容
     */
    private void addIfExists(Element parent, String tag, String label, List<String> headlines) {
        NodeList list = parent.getElementsByTagName(tag);
        if (list.getLength() > 0) {
            String text = list.item(0).getTextContent().trim();
            if (!text.isEmpty()) {
                String formatted = text.replaceAll("\n", "<br/>");  // ← 改行を <br/> に
                headlines.add(label + " " + formatted);
            }
        }
    }


    /**
     * 随時情報用のレポートクラス.
     */
    public static class AnyTimeFeedReport {
        private final String title;
        private final String updated;
        private final String link;
        private final String summary;
        private final List<String> detailHeadline;

        public AnyTimeFeedReport(String title, String updated, String link, String summary, List<String> detailHeadline) {
            this.title = title;
            this.updated = updated;
            this.link = link;
            this.summary = summary;
            this.detailHeadline = detailHeadline;
        }

        public String getTitle() { return title; }
        public String getUpdated() { return updated; }
        public String getLink() { return link; }
        public String getSummary() { return summary; }
        public List<String> getDetailHeadline() { return detailHeadline; }
    }


}
