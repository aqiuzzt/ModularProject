package com.hdh.common.thread;


public interface ThreadDelegate {

    long start();

    boolean cancel(long key);
}
