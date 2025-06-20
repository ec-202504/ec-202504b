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
public class EarthquakeAndVolcanoFeed {

    @Autowired
    private ServletContext application;

    /** フィードのリンク */
    private static final String FEED_URL = "https://www.data.jma.go.jp/developer/xml/feed/eqvol.xml";

    /**
     * 10分に1回、地震、火山の定時フィードを取得する.
     */
    @PostConstruct
    @Scheduled(fixedRate = 10 * 60 * 1000)
    public void fetchFeed() {
        try {
            List<VolcanoReport> volcanoReports = new ArrayList<>();
            List<EarthquakeReport> earthquakeReports = new ArrayList<>();

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

                    if (title.contains("降灰予報")) {
                        volcanoReports.add(new VolcanoReport(title, updated, xmlUrl, content, detailHeadlines));
                    } else if (title.contains("震源・震度")) {
                        earthquakeReports.add(new EarthquakeReport(title, updated, xmlUrl, content, detailHeadlines));
                    }
                }
            }

            application.setAttribute("volcanoReports", volcanoReports);
            application.setAttribute("earthquakeReports", earthquakeReports);

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
     *地震、降灰情報の詳細を取得する.
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

            NodeList headlineNodes = detailDoc.getElementsByTagNameNS("http://xml.kishou.go.jp/jmaxml1/informationBasis1/", "Headline");
            if (headlineNodes.getLength() > 0) {
                Element head = (Element) headlineNodes.item(0);
                NodeList texts = head.getElementsByTagName("Text");
                if (texts.getLength() > 0) {
                    headlines.add("[見出し] " + texts.item(0).getTextContent());
                }
            }

            if (title.contains("降灰予報")) {
                NodeList areaNames = detailDoc.getElementsByTagNameNS("http://xml.kishou.go.jp/jmaxml1/informationBasis1/", "VolcanoInfoContent");
                for (int i = 0; i < areaNames.getLength(); i++) {
                    Element area = (Element) areaNames.item(i);
                    NodeList names = area.getElementsByTagName("Name");
                    if (names.getLength() > 0) {
                        headlines.add("[対象地域] " + names.item(0).getTextContent());
                    }
                }

                NodeList volcanoInfoContents = detailDoc.getElementsByTagName("VolcanoInfoContent");
                if (volcanoInfoContents.getLength() > 0) {
                    Element content = (Element) volcanoInfoContents.item(0);
                    addIfExists(content, "VolcanoHeadline", "[火山ヘッドライン]", headlines);
                    addIfExists(content, "VolcanoActivity", "[火山活動]", headlines);
                    addIfExists(content, "VolcanoPrevention", "[予防情報]", headlines);
                }
            } else if (title.contains("震源・震度")) {
                NodeList origins = detailDoc.getElementsByTagName("OriginTime");
                if (origins.getLength() > 0) {
                    //時間フォーマット
                    OffsetDateTime dtJst = OffsetDateTime.parse(origins.item(0).getTextContent());
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    String formatted = dtJst.atZoneSameInstant(ZoneId.of("Asia/Tokyo")).format(formatter);
                    headlines.add("[発生時刻] " + formatted);
                }

                NodeList areaNames = detailDoc.getElementsByTagName("Area");
                for (int i = 0; i < areaNames.getLength(); i++) {
                    Element area = (Element) areaNames.item(i);
                    NodeList names = area.getElementsByTagName("Name");
                    if (names.getLength() > 0) {
                        headlines.add("[震源地] " + names.item(0).getTextContent());
                    }

                    NodeList coords = area.getElementsByTagNameNS("http://xml.kishou.go.jp/jmaxml1/elementBasis1/", "Coordinate");
                    if (coords.getLength() > 0) {
                        String desc = ((Element) coords.item(0)).getAttribute("description");
                        headlines.add("[座標・深さ] " + desc);
                    }
                }

                NodeList mags = detailDoc.getElementsByTagNameNS("http://xml.kishou.go.jp/jmaxml1/elementBasis1/", "Magnitude");
                if (mags.getLength() > 0) {
                    String desc = ((Element) mags.item(0)).getAttribute("description");
                    headlines.add("[マグニチュード] " + desc);
                }

                NodeList forecastComments = detailDoc.getElementsByTagName("ForecastComment");
                if (forecastComments.getLength() > 0) {
                    Element content = (Element) forecastComments.item(0);
                    addIfExists(content, "Text", "[固定付加文]", headlines);
                }
            }
        } catch (Exception e) {
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
     * 火山情報用のレポートクラス.
     */
    public static class VolcanoReport {
        private final String title;
        private final String updated;
        private final String link;
        private final String summary;
        private final List<String> detailHeadline;

        public VolcanoReport(String title, String updated, String link, String summary, List<String> detailHeadline) {
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

    /**
     * 地震情報用のレポートクラス.
     */
    public static class EarthquakeReport {
        private final String title;
        private final String updated;
        private final String link;
        private final String summary;
        private final List<String> detailHeadline;

        public EarthquakeReport(String title, String updated, String link, String summary, List<String> detailHeadline) {
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
