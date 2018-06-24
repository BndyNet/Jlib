/**
 * Copyright (c) 2018 BNDY-NET. All Rights Reserved.
 * Created at 2018/6/24 8:43 PM
 * http://bndy.net
 */
package net.bndy.lib;

import java.util.EnumSet;
import java.util.Set;

/**
 * @author Bendy Zhang 
 * @version 1.0
 */
public class EnumHelper {

    /**
     * Converts name to Enum instance.
     * @deprecated use EnumType.valueOf(name) instead.
     *
     * @param name the enum name
     * @param enumClazz the class of enum
     * @param <T>  the enum type
     * @return an enum instance
     */
    @Deprecated
    public static <T extends Enum<T>> T convert(String name, Class<T> enumClazz) {
        Set<T> values = EnumSet.allOf(enumClazz);
        for(T t: values) {
            if (t.name().equalsIgnoreCase(name)) {
                return t;
            }
        }
        return null;
    }
}
