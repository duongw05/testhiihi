package com.language.service.common.utils.excel;


public @interface DataConditionRequired {
    String nameField();

    String[] value();
}