package com.ylqq.document.util;

import lombok.ToString;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * @author ylqq
 */
@ToString
public class Layui extends HashMap<String, Object> implements Serializable {
    public static Layui data(String msg,Integer count, List<?> data) {
        Layui r = new Layui();
        r.put("code", 0);
        r.put("msg", msg);
        r.put("count", count);
        r.put("data", data);
        return r;
    }
}

