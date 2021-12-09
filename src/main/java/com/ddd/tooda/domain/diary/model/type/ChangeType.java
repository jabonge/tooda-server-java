package com.ddd.tooda.domain.diary.model.type;

public enum ChangeType {

    RISE("RISE"),
    EVEN("EVEN"),
    FALL("FALL");

    private String name;

    ChangeType(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
