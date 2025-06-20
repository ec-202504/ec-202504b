package com.example.service;

import com.example.domain.Item;
import com.example.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 商品詳細画面を表示する機能の業務処理を行うサービスクラス.
 */
@Service
@Transactional
public class ShowItemDetailService {

    @Autowired
    private ItemRepository itemRepository;
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
