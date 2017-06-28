package cn.lspush.spruce.utility;

import android.support.annotation.NonNull;

import java.util.AbstractList;
import java.util.LinkedHashMap;

/**
 *
 */
public class FilterList<E extends FilterList.Key> extends AbstractList<E> {
    private final LinkedHashMap<String, E> map = new LinkedHashMap<>();

    @Override
    public E get(int index) {
        //noinspection unchecked
        return ((E) map.values().toArray()[index]);
    }

    @Override
    public int size() {
        return map.values().size();
    }

    @Override
    public E set(int index, @NonNull E element) {
        String key = getKey(index);
        return map.put(key, element);
    }

    @Override
    public void add(int index, E element) {
        map.put(element.key(), element);
    }

    @Override
    public E remove(int index) {
        String key = getKey(index);
        return map.remove(key);
    }

    private String getKey(int index) {
        return (String) map.keySet().toArray()[index];
    }

    public interface Key {
        String key();
    }
}
