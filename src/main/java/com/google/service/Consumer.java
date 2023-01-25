package com.google.service;

import java.util.Vector;

public class Consumer implements Runnable {
    private final Vector sharedQueue;

    public Consumer(Vector sharedQueue) {
        this.sharedQueue = sharedQueue;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("consumed : " + consume() + ", sharedqueue's size is : " + sharedQueue.size());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private int consume() {
        while (sharedQueue.isEmpty()) {
            synchronized (sharedQueue) {
                System.out.println("consumer sharedqueue is empty");
                try {
                    sharedQueue.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        synchronized (sharedQueue) {
            sharedQueue.notify();
            return (Integer) sharedQueue.remove(0);
        }
    }
}
