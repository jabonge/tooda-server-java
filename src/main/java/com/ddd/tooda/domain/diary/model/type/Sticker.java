package com.ddd.tooda.domain.diary.model.type;

public enum Sticker {
    SAD("SAD"),
    OMG("OMG"),
    ANGRY("ANGRY"),
    THINKING("THINKING"),
    CHICKEN("CHICKEN"),
    PENCIL("PENCIL");

    private String name;

    Sticker(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
