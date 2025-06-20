package com.example.form;

import java.util.List;

/**
 * 質問回答時に使用するフォーム.
 */
public class QuestionnaireForm {
    /** 対象性別 */
    private String targetGender;
    /** 幼児がいるか */
    private String existInfant;
    /** 高齢者がいるか */
    private String existSenior;
    /** ペットがいるか */
    private Integer existPets;
    /** 近くに特定の地形があるか */
    private List<Integer> terrainList;

    public String getTargetGender() {
        return targetGender;
    }

    public void setTargetGender(String targetGender) {
        this.targetGender = targetGender;
    }

    public String getExistInfant() {
        return existInfant;
    }

    public void setExistInfant(String existInfant) {
        this.existInfant = existInfant;
    }

    public String getExistSenior() {
        return existSenior;
    }

    public void setExistSenior(String existSenior) {
        this.existSenior = existSenior;
    }

    public Integer getExistPets() {
        return existPets;
    }

    public void setExistPets(Integer existPets) {
        this.existPets = existPets;
    }

    public List<Integer> getTerrainList() {
        return terrainList;
    }

    public void setTerrainList(List<Integer> terrainList) {
        this.terrainList = terrainList;
    }

    @Override
    public String toString() {
        return "QuestionnaireForm{" +
                "targetGender='" + targetGender + '\'' +
                ", existInfant='" + existInfant + '\'' +
                ", existSenior='" + existSenior + '\'' +
                ", existPets=" + existPets +
                ", terrainList=" + terrainList +
                '}';
    }
}
