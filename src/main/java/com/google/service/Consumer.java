package com.google.service;

import java.util.Vector;

public class Consumer implements Runnable {
    private final Vector SHAREDQUEUE;

    public Consumer(Vector sharedqueue) {
        this.SHAREDQUEUE = sharedqueue;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("consumed : " + consume() + ", sharedqueue's size is : " + SHAREDQUEUE.size());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private int consume() {
        while (SHAREDQUEUE.isEmpty()) {
            synchronized (SHAREDQUEUE) {
                System.out.println("consumer sharedqueue is empty");
                try {
                    SHAREDQUEUE.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        synchronized (SHAREDQUEUE) {
            SHAREDQUEUE.notify();
            return (Integer) SHAREDQUEUE.remove(0);
        }
    }
}
