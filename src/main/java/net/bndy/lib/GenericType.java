package net.bndy.lib;

import java.lang.reflect.ParameterizedType;

public class GenericType<T> {

    protected Class<T> clazz;

    public GenericType() {
        this.clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public Class<T> getClazz() {
        return clazz;
    }
}
