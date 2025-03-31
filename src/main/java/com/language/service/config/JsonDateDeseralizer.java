package com.language.service.config;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class JsonDateDeseralizer extends JsonDeserializer<Date> {
    private static final List<SimpleDateFormat> DATE_FORMATS = Arrays.asList(
            new SimpleDateFormat("dd/MM/yyyy"),
            new SimpleDateFormat("MM/yyyy"),
            new SimpleDateFormat("yyyy")
    );

    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String dateStr = p.getText().trim();
        if (dateStr.isEmpty()) {
            return null;
        }

        for (SimpleDateFormat format : DATE_FORMATS) {
            try {
                return format.parse(dateStr);
            } catch (ParseException ignored) {
                // Nếu lỗi, thử định dạng tiếp theo
            }
        }

        throw new IOException("Invalid date format: " + dateStr);
    }
}
