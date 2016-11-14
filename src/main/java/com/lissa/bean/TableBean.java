package com.lissa.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
public class TableBean {

    @Setter
    private String tableName;
    private List<ColumnBean> columns;

}
