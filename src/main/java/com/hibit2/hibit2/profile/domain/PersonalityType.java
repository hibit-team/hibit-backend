package com.hibit2.hibit2.profile.domain;

public enum PersonalityType {

    TYPE_1("지적한"),
    TYPE_2("차분한"),
    TYPE_3("유머있는"),
    TYPE_4("낙천적인"),
    TYPE_5("내향적인"),
    TYPE_6("외향적인"),
    TYPE_7("감성적인"),
    TYPE_8("상냥한"),
    TYPE_9("귀여운"),
    TYPE_10( "열정적인"),
    TYPE_11("듬직한"),
    TYPE_12("개성있는"),
    TYPE_13("친절한"),
    TYPE_14("사려 깊은"),
    TYPE_15("창의적인"),
    TYPE_16("책임감이 강한"),
    TYPE_17("에너제틱한"),
    TYPE_18("겸손한"),
    TYPE_19("카리스마 있는"),
    TYPE_20("적응력이 좋은"),
    TYPE_21("섬세한"),
    TYPE_22("야심적인"),
    TYPE_23("계획적인"),
    TYPE_24("진취적인");

    private final String contents;

    PersonalityType(String contents) {
        this.contents = contents;
    }
}