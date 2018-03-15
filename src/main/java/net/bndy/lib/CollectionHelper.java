package net.bndy.lib;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Collection Helper
 */
public class CollectionHelper {

    /**
     * Checks whether the collection is null or empty.
     * @param source The collection
     * @return <code>true</code> if null or empty, otherwise false
     */
    public static boolean isNullOrEmpty(Collection<?> source) {
        if (source == null || source.size() == 0)
            return true;
        return false;
    }

    /**
     * Gets first element in source.
     * @param source The collection
     * @param <T> the type of element in collection.
     * @return the first element
     */
    public static <T> T first(Collection<T> source) {
        if (source == null)
            return null;

        Optional<T> first = source.stream().findFirst();
        if (first.isPresent()) {
            return first.get();
        }
        return null;
    }

    /**
     * Gets the element matched the condition.
     * @param source the collection
     * @param predicate the condition
     * @param <T> the type of element in source
     * @return the first element matched
     */
    public static <T> T first(Collection<T> source, Predicate<T> predicate) {
        return first(filter(source, predicate));
    }

    /**
     * Checks whether a collection contains the element by conditions.
     *
     * @param source The collection
     * @param predicate The condition
     * @param <T>   T generic type
     * @return  <code>true</code> if contains, otherwise false.
     */
    public static <T> boolean contains(Collection<T> source, Predicate<? super T> predicate) {
        if (source == null)
            return false;
        return source.stream().anyMatch(predicate);
    }

    /**
     * Gets the index of matched first element.
     * @param source    The collection
     * @param predicate The condition
     * @param <T>   The generic type
     * @return The index matched
     */
    public static <T> int indexOf(Collection<T> source, Predicate<? super T> predicate) {
        if (source == null || source.size() == 0)
            return -1;

        int index = -1;
        for (T t : source) {
            index++;
            if (predicate.test(t)) {
                return index;
            }
        }

        return -1;
    }

    /**
     * Gets all matched elements.
     * @param source The collection source
     * @param predicate The condition
     * @param <T> The type of element
     * @return The matched elements
     */
    public static <T> List<T> filter(Collection<T> source, Predicate<T> predicate) {
        if (source == null)
            return new ArrayList<>();
        return source.stream().filter(predicate).collect(Collectors.toList());
    }

    /**
     * Converts a collection typed <code>T</code> to another collection typed <code>R</code>
     * @param source The source typed T
     * @param mapper The mapper for T to R
     * @param <T> The source element type
     * @param <R> The destination element type
     * @return A collection typed R
     */
    public static <T, R> List<R> convert(Collection<T> source, Function<T, R> mapper) {
        if (source == null)
            return new ArrayList<>();
        return source.stream().map(mapper).collect(Collectors.toList());
    }


    /**
     * Converts a Map object to entity.
     * @param mappings the Map
     * @param targetClass the destination class
     * @param <T> the result type
     * @return an instance typed T
     * @throws InstantiationException if instantiation
     * @throws IllegalAccessException if illegal access
     * @throws ClassNotFoundException if targetClass not found
     */
    public static <T> T convertMap2(Map<String, Object> mappings, Class<T> targetClass) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        T t = (T) ReflectionHelper.newInstance(targetClass);
        for(String key: mappings.keySet()) {
            ReflectionHelper.setFieldValue(t, key, mappings.get(key));
        }
        return t;
    }

    /**
     * Array to List
     * @param source the source
     * @param <T> the element type
     * @return a list typed T
     */
    public static <T> List<T> array2List(T[] source) {
        return Arrays.asList(source);
    }

    /**
     * List to Array
     * @param source the source
     * @param clazz the element class
     * @param <T> the element type
     * @return an array typedd T
     */
    public static <T> T[] list2Array(List<T> source, Class<T> clazz) {
        T[] result = (T[]) Array.newInstance(clazz, source.size());
        return source.toArray(result);
    }
}
