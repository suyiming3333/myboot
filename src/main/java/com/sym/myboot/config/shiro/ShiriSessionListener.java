package com.sym.myboot.config.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

import java.util.concurrent.atomic.AtomicInteger;

public class ShiriSessionListener implements SessionListener {

    /*
     * 线程安全自增变量 统计在线人数
     */
    private final AtomicInteger sessionCount = new AtomicInteger(0);

    @Override
    public void onStart(Session session) {
        sessionCount.incrementAndGet();
    }

    @Override
    public void onStop(Session session) {
        sessionCount.decrementAndGet();
    }

    @Override
    public void onExpiration(Session session) {
        sessionCount.decrementAndGet();
    }

    public AtomicInteger getSessionCount(){
        return sessionCount;
    }
}
