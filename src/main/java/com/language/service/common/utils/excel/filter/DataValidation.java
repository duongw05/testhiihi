package com.language.service.common.utils.excel.filter;

public @interface DataValidation {
    public String code();

    public String value();

    public int length() default -1;

    public String regex() default "";

    public String errorMessage() default "";
}