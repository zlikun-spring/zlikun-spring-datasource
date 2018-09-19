package com.zlikun.spring.ds;

/**
 * @author zlikun
 * @date 2018-09-19 20:21
 */
public class DynamicDataSourceHolder {

    private static final String DEFAULT_DS_NAME = "master";
    private static final ThreadLocal<String> holder = new ThreadLocal<>();

    /**
     * 设置数据源名称
     * @param dsName
     */
    public static void setDataSourceKey(String dsName) {
        holder.set(dsName);
    }

    /**
     * 获取数据源名称
     * @return
     */
    public static String getDataSourceKey() {
        String dsName = holder.get();
        return dsName != null ? dsName : DEFAULT_DS_NAME;
    }

    /**
     * 判断当前数据源是master数据源
     *
     * @return
     */
    public static boolean isMaster() {
        String key = holder.get();
        return key != null && key.equals(DEFAULT_DS_NAME);
    }

    /**
     * 清除数据源设定
     */
    public static void clearDataSourceKey() {
        holder.remove();
    }

}
