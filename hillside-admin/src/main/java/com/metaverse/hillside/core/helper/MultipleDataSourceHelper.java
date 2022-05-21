package com.metaverse.hillside.core.helper;

import com.metaverse.hillside.common.constants.SpecifyDataSourceEnum;

/**
 * 不同请求，不同线程，数据源的隔离类
 */
public class MultipleDataSourceHelper {

    /**
     * ThreadLocal数据隔离，但是并非数据的复制，而是在每一个线程中创建一个新的数据对象，然后每一个线程使用的是不一样的
     */
    private final static ThreadLocal<SpecifyDataSourceEnum> HANDLER_DATA_SOURCE_NAMES = new ThreadLocal<>();

    public static void putDataSource(SpecifyDataSourceEnum specifyDataSourceEnum) {
        HANDLER_DATA_SOURCE_NAMES.set(specifyDataSourceEnum);
    }

    public static SpecifyDataSourceEnum getDataSource() {
        return HANDLER_DATA_SOURCE_NAMES.get();
    }

    public static void clearReset() {
        HANDLER_DATA_SOURCE_NAMES.remove();
    }

}
