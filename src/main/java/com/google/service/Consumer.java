package com.google.service;

import java.util.Vector;

public class Consumer implements Runnable {
    private final Vector sharedqueue;

    public Consumer(Vector sharedqueue) {
        this.sharedqueue = sharedqueue;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("consumed : " + consume() + ", sharedqueue's size is : " + sharedqueue.size());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private int consume() {
        while (sharedqueue.isEmpty()) {
            synchronized (sharedqueue) {
                System.out.println("consumer sharedqueue is empty");
                try {
                    sharedqueue.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        synchronized (sharedqueue) {
            sharedqueue.notify();
            return (Integer) sharedqueue.remove(0);
        }
    }
}
