package com.hibit2.hibit2.profile.domain;

import lombok.Getter;

@Getter
public enum PersonalityType {

    TYPE_1(1, "지적한"),
    TYPE_2(2, "차분한"),
    TYPE_3(3, "유머있는"),
    TYPE_4(4, "낙천적인"),
    TYPE_5(5, "내향적인"),
    TYPE_6(6, "외향적인"),
    TYPE_7(7, "감성적인"),
    TYPE_8(8, "상냥한"),
    TYPE_9(9, "귀여운"),
    TYPE_10(10, "열정적인"),
    TYPE_11(11, "듬직한"),
    TYPE_12(12, "개성있는"),
    TYPE_13(13, "친절한"),
    TYPE_14(14, "사려 깊은"),
    TYPE_15(15, "창의적인"),
    TYPE_16(16, "책임감이 강한"),
    TYPE_17(17, "에너제틱한"),
    TYPE_18(18, "겸손한"),
    TYPE_19(19, "카리스마 있는"),
    TYPE_20(20, "적응력이 좋은"),
    TYPE_21(21, "섬세한"),
    TYPE_22(22, "야심적인"),
    TYPE_23(23, "계획적인"),
    TYPE_24(24, "진취적인");


    private final int index;
    private final String contents;

    PersonalityType(int index, String contents) {
        this.index = index;
        this.contents = contents;
    }

}
