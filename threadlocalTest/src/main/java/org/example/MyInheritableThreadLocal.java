package org.example;

import com.alibaba.fastjson.JSONObject;

/**
 * @Author: heiden
 * @Date: 2021/7/15 17:13
 * @Project: qrcode-utils
 */
public class MyInheritableThreadLocal<T> extends InheritableThreadLocal<T>{
    protected T childValue(T parentValue) {
        String s = JSONObject.toJSONString(parentValue);
        return (T)JSONObject.parseObject(s,parentValue.getClass());
    }

}
