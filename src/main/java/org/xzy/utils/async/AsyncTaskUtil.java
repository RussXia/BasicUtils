package org.xzy.utils.async;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by RuzzZZ on 2017/2/7.
 */
@Slf4j
public class AsyncTaskUtil implements Runnable {
    Object inner_invoker;

    Method inner_method;

    List<?> inner_paraArray;

    public AsyncTaskUtil(Object invoker, Method method, List<?> list) {
        inner_invoker = invoker;
        inner_method = method;
        inner_paraArray = list;

    }

    @Override
    public void run() {
        try {
            for (Object obj : inner_paraArray) {
                inner_method.invoke(inner_invoker, obj);
            }
        } catch (Exception e) {
            log.error("AsynExecutor" + "\t" + inner_method.getClass() + "\t"
                    + inner_method.getName() + "\t" + "error:" + e.getStackTrace());
        }
    }
}
