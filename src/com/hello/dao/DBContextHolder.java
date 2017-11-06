package com.hello.dao;

/**
 * 数据源切换器
 * 
 * @author mengchuang
 *
 */
public abstract class DBContextHolder {

    public static final String READ = "read";
    public static final String WRITE = "write";


    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<String>();

    /**
     * 切换至读库
     */
    public static void switchToRead() {
        CONTEXT_HOLDER.set(READ);
    }

    /**
     * 切换至写库
     */
    public static void switchToWrite() {
        CONTEXT_HOLDER.set(WRITE);
    }

    /**
     * 获取当前的操作类型
     * 
     * @return
     */
    public static String getCurrent() {
        return CONTEXT_HOLDER.get();
    }

    /**
     * 清除
     */
    public static void clear() {
        CONTEXT_HOLDER.remove();
    }
}
