package com.language.service.common;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateAdapter extends XmlAdapter<String, Date> {

    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    private final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    @Override
    public Date unmarshal(String dateString) throws ParseException {
        return dateFormat.parse(dateString);
    }

    @Override
    public String marshal(Date date) {
        return dateFormat.format(date);
    }
}
