/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.lib;

import java.lang.annotation.Annotation;

/**
 * Utils for Annotation
 */
public class AnnotationHelper {

    /**
     * Get annotation of public field.
     *
     * @param annotationClass   the annotation class annotated by @Retention(RetentionPolicy.RUNTIME)
     * @param source    the source class
     * @param fieldName the public field name
     * @param <T>       the annotation type
     * @param <TSource> the source type
     * @return          the instance typed {@code T}
     * @throws NoSuchFieldException if the field does not exist, or not public
     */
    public static <T extends Annotation, TSource> T getFieldAnnotation(Class<T> annotationClass, Class<TSource> source, String fieldName) throws NoSuchFieldException {
        return source.getField(fieldName).getAnnotation(annotationClass);
    }

    /**
     * Get annotation of class.
     *
     * @param annotationClass   the annotation class annotated by @Retention(RetentionPolicy.RUNTIME)
     * @param source    the source class
     * @param <T>       the annotation type
     * @param <TSource> the source type
     * @return          the instance typed {@code T}
     * @throws NoSuchFieldException if the field does not exist, or not public
     */
    public static <T extends Annotation, TSource> T getClassAnnotation(Class<T> annotationClass, Class<TSource> source) throws SecurityException {
        return source.getAnnotation(annotationClass);
    }
}
