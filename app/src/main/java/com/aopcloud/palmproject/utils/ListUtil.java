package com.aopcloud.palmproject.utils;

import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.utils
 * @File : ListUtil.java
 * @Date : 2020/6/1 2020/6/1
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class ListUtil {

    /**
     * is null or its size is 0
     * <p/>
     * <pre>
     * isEmpty(null)   =   true;
     * isEmpty({})     =   true;
     * isEmpty({1})    =   false;
     * </pre>
     *
     * @param <V>
     * @param sourceList
     * @return if list is null or its size is 0, return true, else return false.
     */
    public static <V> boolean isEmpty(List<V> sourceList) {
        return (sourceList == null || sourceList.size() == 0);
    }
}
