package com.google.service;

import java.util.Vector;

public class Producer implements Runnable {
    private final Vector SHAREDQUEUE;
    private final int SIZE;

    public Producer(Vector sharedqueue, int size) {
        this.SHAREDQUEUE = sharedqueue;
        this.SIZE = size;
    }

    @Override
    public void run() {
        while (true) {
            for (int i = 1; i <= SIZE; i++) {
                produce(i);
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void produce(int i) {
        while (SHAREDQUEUE.size() == SIZE) {
            synchronized (SHAREDQUEUE) {
                System.out.println("producer sharedqueue is full");
                try {
                    SHAREDQUEUE.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        synchronized (SHAREDQUEUE) {
            SHAREDQUEUE.add(i);
            System.out.println("produced : " + i + ", sharedqueue's size is : " + SHAREDQUEUE.size());
            SHAREDQUEUE.notify();
        }
    }
}
