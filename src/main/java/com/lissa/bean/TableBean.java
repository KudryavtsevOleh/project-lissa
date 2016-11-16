package com.lissa.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
public class TableBean {

    @Setter
    private String tableName;
    private List<ColumnBean> columns;

    public TableBean() {
        columns = new ArrayList<>();
    }
}
