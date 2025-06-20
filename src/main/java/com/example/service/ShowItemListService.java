package com.example.service;

import com.example.domain.Item;
import com.example.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品関連機能の業務処理を行うサービスクラス.
 */
@Service
@Transactional
public class ShowItemListService {

    @Autowired
    private ItemRepository itemRepository;

    /**
     * 商品一覧を取得します.
     *
     * @return 商品一覧
     */
    public List<Item> searchAll() {
        return itemRepository.findAll();
    }

    /**
     * 商品を名前で部分検索します.
     *
     * @param name 検索文字列
     * @return 条件に合致する商品一覧
     */
    public List<Item> findByName(String name) {
        return itemRepository.findByName(name);
    }

    /**
     * 主キーで商品を検索します.
     *
     * @param itemId 商品ID
     * @return 商品情報
     */
    public Item showItemDetail(Integer itemId) {
        return itemRepository.findById(itemId);
    }
}
