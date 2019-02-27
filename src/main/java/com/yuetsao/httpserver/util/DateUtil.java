package com.yuetsao.httpserver.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 */
public class DateUtil {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
    private DateUtil() {}

    /**
     * 获取当前系统时间
     * 格式【yyyy-MM-dd HH：mm：ss SSS】
     * @return
     */
    public static String getCurrentTime() {
        return dateFormat.format(new Date());
    }
}
