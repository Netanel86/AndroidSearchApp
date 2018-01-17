package com.freelance.netanel.androidsearchapp.domain.json;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Netanel on 09/12/2017.
 */

 public abstract class TypeOfT<T> {
    private final Class<? super T> rawType;
    private final Type type;

    @SuppressWarnings("unchecked")
    protected TypeOfT() {

        type = getTypeParameterFromSuperclass();

        rawType = (Class<? super T>) ((ParameterizedType)type).getRawType();
    }

    private Type getTypeParameterFromSuperclass() {
        Type superclass = this.getClass().getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parametrized = (ParameterizedType) superclass;
        return parametrized.getActualTypeArguments()[0];
    }

    public Type getType() {
        return type;
    }

    public Class<?> getRawType() {
        return rawType;
    }
}
