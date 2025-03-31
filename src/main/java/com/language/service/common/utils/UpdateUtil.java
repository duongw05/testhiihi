package com.language.service.common.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

/**
 * Role: used for null assignment in entity objects
 */
public class UpdateUtil {

    /**
     * Copy the attributes of the null value from the target entity class to the source entity class
     *
     * @param src
     * @param target
     */
    public static void copyNonNullProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullProperties(src));
    }

    /**
     * Find out the empty properties and return them
     *
     * @param src
     * @return
     */
    private static String[] getNullProperties(Object src) {
        BeanWrapper srcBean = new BeanWrapperImpl(src);
        PropertyDescriptor[] pds = srcBean.getPropertyDescriptors();
        Set<String> emptyName = new HashSet<>();
        for (PropertyDescriptor p : pds) {
            Object srcValue = srcBean.getPropertyValue(p.getName());
            if (srcValue == null) emptyName.add(p.getName());
        }
        String[] result = new String[emptyName.size()];
        return emptyName.toArray(result);
    }
}
