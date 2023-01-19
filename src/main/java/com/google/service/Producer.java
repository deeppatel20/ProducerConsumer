package com.google.service;

import java.util.Vector;

public class Producer implements Runnable {
    private final Vector sharedqueue;
    private final int SIZE;

    public Producer(Vector sharedqueue, int size) {
        this.sharedqueue = sharedqueue;
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
        while (sharedqueue.size() == SIZE) {
            synchronized (sharedqueue) {
                System.out.println("producer sharedqueue is full");
                try {
                    sharedqueue.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        synchronized (sharedqueue) {
            sharedqueue.add(i);
            System.out.println("produced : " + i + ", sharedqueue's size is : " + sharedqueue.size());
            sharedqueue.notify();
        }
    }
}
