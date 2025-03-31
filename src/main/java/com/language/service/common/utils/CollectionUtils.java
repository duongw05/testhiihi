package com.language.service.common.utils;

import java.util.List;

public class CollectionUtils {
    public static String ListLongToString(List<Long> listLong) {
        String character = "";
        if (listLong == null || listLong.isEmpty()) return character;
        for (Long l : listLong) {
            if (character.isEmpty()) {
                character = l.toString();
            } else {
                character = character + "," + l;
            }

        }
        return character;
    }

    public static String ListStringToStr(List<String> listLong) {
        if (listLong == null || listLong.isEmpty()) return null;
        String character = "";
        for (String l : listLong) {
            if (character.isEmpty()) {
                character = l.trim();
            } else {
                character = character.trim() + ", " + l;
            }

        }
        // remove last character (,)
        if (character.endsWith(",")) {
            character = character.substring(0, character.length() - 1);
        }
        return character;
    }

    public static String ListStringToStr2(List<String> listLong) {
        if (listLong == null || listLong.isEmpty()) return null;
        String character = "";
        for (String l : listLong) {
            if (character.isEmpty()) {
                character = "'" + l.trim() + "'";
            } else {
                character = character.trim() + ", '" + l + "'";
            }

        }
        // remove last character (,)
        if (character.endsWith(",")) {
            character = character.substring(0, character.length() - 1);
        }
        return character;
    }
}
