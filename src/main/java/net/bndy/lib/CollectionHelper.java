package net.bndy.lib;

import java.util.Collection;
import java.util.List;
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
     * Checks whether a collection contains the element by conditions.
     *
     * @param source The collection
     * @param predicate The condition
     * @param <T>   T generic type
     * @return  <code>true</code> if contains, otherwise false.
     */
    public static <T> boolean contains(Collection<T> source, Predicate<? super T> predicate) {
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
        return source.stream().map(mapper).collect(Collectors.toList());
    }
}
