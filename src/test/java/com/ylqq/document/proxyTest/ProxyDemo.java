package com.ylqq.document.proxyTest;


import org.junit.jupiter.api.Test;

public class ProxyDemo {
    @Test
    public void test(){
        OrderService orderService=new OrderServiceImpl();

        //proxy代理OrderService类，invoke为方法进入口
        OrderProxy proxy=new OrderProxy(orderService);

        //调用代理
        orderService= (OrderService) proxy.getProxy();
        orderService.test1();
        //orderService.test2();
    }
}
