package com.example.pokedex_demo.entities;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Type Model")
public class Type {

    @ApiModelProperty(notes = "Name of the Type", name = "typeName", required = true, value = "Water")
    private String typeName;
    @ApiModelProperty(notes = "ImageURL of the Type", name = "url", required = true, value = "....")
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
