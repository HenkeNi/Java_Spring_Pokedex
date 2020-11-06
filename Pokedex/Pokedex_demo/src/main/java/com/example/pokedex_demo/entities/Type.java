package com.example.pokedex_demo.entities;

public class Type {

    private String typeName;
    private String url;

    public Type() {

    }

    public Type(String typeName, String url) {
        this.typeName = typeName;
        this.url = url;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
