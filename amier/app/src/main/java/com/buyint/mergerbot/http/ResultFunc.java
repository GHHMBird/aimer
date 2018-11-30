package com.buyint.mergerbot.http;

import com.buyint.mergerbot.dto.ResultBean;

import io.reactivex.functions.Function;
import kotlin.jvm.functions.Function1;

/**
 * created by licheng  on date 2018/8/14
 */
public class ResultFunc<T> implements Function<ResultBean<T>, T> {

    @Override
    public T apply(ResultBean<T> bean) throws Exception {

        if (bean.getMessage() != null) {
            throw new IllegalStateException(bean.getMessage());
        }

        if (bean.getData() == null) {
            throw new IllegalStateException(bean.getMessage());
        }

        return bean.getData();
    }
}
