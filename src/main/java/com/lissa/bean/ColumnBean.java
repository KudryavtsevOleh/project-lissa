package com.lissa.bean;

import com.sun.istack.internal.NotNull;
import lombok.*;

@Getter
public class ColumnBean {

    @Setter
    private boolean isPrimaryKey = false;
    @Setter
    private boolean isAutoIncrement;
    @NotNull
    private String name;
    @NotNull
    private String type;

    public ColumnBean(String name, String type) {
        this.name = name;
        this.type = type;
    }
}
