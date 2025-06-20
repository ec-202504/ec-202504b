package com.example.service;

import com.example.domain.Item;
import com.example.form.QuestionnaireForm;
import com.example.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品おすすめ機能の業務処理を行うサービスクラス.
 */
@Service
@Transactional
public class RecommendService {

    @Autowired
    private ItemRepository itemRepository;

    /** ペットがいるかを結びつけるためのmap */
    private final Map<Integer, Boolean> PET_OPTION_MAP;

    {
        PET_OPTION_MAP = new HashMap<>();
        PET_OPTION_MAP.put(0, true);
        PET_OPTION_MAP.put(1, false);
    }

    /** 災害種別を結びつけるためのmap */
    private final Map<Integer, String> DISASTER_TYPE_MAP;

    {
        DISASTER_TYPE_MAP = new HashMap<>();
        DISASTER_TYPE_MAP.put(0, "地震");
        DISASTER_TYPE_MAP.put(1, "水害");
        DISASTER_TYPE_MAP.put(2, "火災");
        DISASTER_TYPE_MAP.put(3, "停電");
        DISASTER_TYPE_MAP.put(4, "台風");
    }

    public List<Item> recommend(QuestionnaireForm form) {
        String targetGender = form.getTargetGender();
        String infant = form.getExistInfant();
        String senior = form.getExistSenior();
        Boolean pet = PET_OPTION_MAP.get(form.getExistPets());
        String disaster;

        if (form.getTerrainList().contains(0)) {
            disaster = DISASTER_TYPE_MAP.get(1);
        } else {
            disaster = DISASTER_TYPE_MAP.get(0);
        }

        return itemRepository.recommend(infant, senior, targetGender, pet,
                disaster);
    }
}
