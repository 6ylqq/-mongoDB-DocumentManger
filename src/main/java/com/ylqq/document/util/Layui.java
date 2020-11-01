package com.ylqq.document.util;

import java.util.HashMap;
import java.util.List;

/**
 * @author ylqq
 */
public class Layui extends HashMap<String, Object>{
    public static Layui data(String msg,Integer count, List<?> data) {
        Layui r = new Layui();
        r.put("code", 0);
        r.put("msg", msg);
        r.put("count", count);
        r.put("data", data);
        return r;
    }
}

