package org.xzy.utils.uuid;

import java.util.UUID;

/**
 * Created by RuzzZZ on 2017/2/7.
 */
// TODO: 2017/2/7
public class UUIDUtil {

    /**
     * 产生UUID,不带'-'符号
     *
     * @return
     */
    public static String generateUUIDWithoutConnector() {
        String s = UUID.randomUUID().toString();
        //去掉“-”符号
        return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
    }

    /**
     * 调用java.util的工具类，生成uuid
     *
     * @return
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
