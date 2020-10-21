package com.ylqq.document.proxyTest;

public class OrderServiceImpl implements OrderService{
    @Override
    public void test1() {
        System.out.println("==执行test1==");
        test2();
    }

    @Override
    public void test2() {
        System.out.println("==执行test2==");
    }
}
