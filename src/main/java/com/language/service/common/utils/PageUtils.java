package com.language.service.common.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import com.language.service.common.utils.DataUtils;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

public class PageUtils {
    public static <T> Page<T> setSerialNumbers(Page<T> page, Pageable pageable, BiConsumer<T, String> setSerialNumberFunction) {
        List<T> content = page.getContent();
        AtomicInteger count = new AtomicInteger(pageable.getPageNumber() * pageable.getPageSize() + 1);

        content.forEach(item -> setSerialNumberFunction.accept(item, DataUtils.safeToString(count.getAndIncrement())));

        return new PageImpl<>(content, pageable, page.getTotalElements());
    }

    public static <T> List<T> setSerialNumbersList(List<T> list, Pageable pageable, BiConsumer<T, String> setSerialNumberFunction) {
        AtomicInteger count = new AtomicInteger(pageable.getPageNumber() * pageable.getPageSize() + 1);

        list.forEach(item -> setSerialNumberFunction.accept(item, DataUtils.safeToString(count.getAndIncrement())));

        return list;
    }
}
