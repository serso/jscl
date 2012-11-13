package jscl.util;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ArrayUtils {

    private ArrayUtils() {
    }

    public static Object[] concat(Object o1[], Object o2[], Object res[]) {
        System.arraycopy(o1, 0, res, 0, o1.length);
        System.arraycopy(o2, 0, res, o1.length, o2.length);
        return res;
    }

    public static <T> T[] toArray(@NotNull List<T> list, T res[]) {
        int n = list.size();

        for (int i = 0; i < n; i++) {
            res[i] = list.get(i);
        }

        return res;
    }

    public static int[] toArray(@NotNull List<Integer> list, @NotNull int result[]) {
        int n = list.size();

        for (int i = 0; i < n; i++) {
            result[i] = list.get(i);
        }

        return result;
    }

    public static <T> List<T> toList(@NotNull Collection<T> collection) {
        return new ArrayList<T>(collection);
    }

    public static String toString(@NotNull Object array[]) {
        final StringBuilder result = new StringBuilder();
        result.append("{");
        for (int i = 0; i < array.length; i++) {
            result.append(array[i]).append(i < array.length - 1 ? ", " : "");
        }
        result.append("}");
        return result.toString();
    }

    private static final int BINARY_SEARCH_THRESHOLD = 5000;

    public static <T extends Comparable<T>> int binarySearch(@NotNull List<T> list, @NotNull T key) {
        if (list instanceof RandomAccess || list.size() < BINARY_SEARCH_THRESHOLD)
            return indexedBinarySearch(list, key);
        else
            return iteratorBinarySearch(list, key);
    }

    private static <T extends Comparable<T>> int indexedBinarySearch(@NotNull List<T> list, @NotNull T key) {
        int low = 0;
        int high = list.size() - 1;

        while (low <= high) {
            int mid = (low + high) >> 1;
            T midVal = list.get(mid);
            int cmp = midVal.compareTo(key);

            if (cmp < 0)
                low = mid + 1;
            else if (cmp > 0)
                high = mid - 1;
            else
                return mid; // key found
        }
        return -(low + 1);  // key not found
    }

    private static <T extends Comparable<T>> int iteratorBinarySearch(@NotNull List<T> list, @NotNull T key) {
        int low = 0;
        int high = list.size() - 1;
        final ListIterator<T> it = list.listIterator();

        while (low <= high) {
            int mid = (low + high) >> 1;
            T midVal = get(it, mid);
            int cmp = midVal.compareTo(key);

            if (cmp < 0)
                low = mid + 1;
            else if (cmp > 0)
                high = mid - 1;
            else
                return mid; // key found
        }
        return -(low + 1);  // key not found
    }

    @NotNull
    private static <T> T get(@NotNull ListIterator<T> it, int index) {
        T result;

        int pos = it.nextIndex();
        if (pos <= index) {
            do {
                result = it.next();
            } while (pos++ < index);
        } else {
            do {
                result = it.previous();
            } while (--pos > index);
        }

        return result;
    }
}
