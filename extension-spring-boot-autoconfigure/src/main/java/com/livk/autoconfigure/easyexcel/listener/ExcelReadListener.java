package com.livk.autoconfigure.easyexcel.listener;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;

import java.io.InputStream;
import java.util.Collection;

/**
 * <p>
 * ExcelReadListener
 * </p>
 *
 * @param <T> the type parameter
 * @author livk
 */
public interface ExcelReadListener<T> extends ReadListener<T> {

    /**
     * 获取数据集合
     *
     * @return collection collection data
     */
    Collection<T> getCollectionData();

    @Override
    default void doAfterAllAnalysed(AnalysisContext context) {

    }

    /**
     * 解析数据
     *
     * @param inputStream 数据流
     * @param targetClass 数据类型
     * @return excel read listener
     */
    default ExcelReadListener<T> parse(InputStream inputStream, Class<?> targetClass) {
        EasyExcel.read(inputStream, targetClass, this).sheet().doRead();
        return this;
    }

}