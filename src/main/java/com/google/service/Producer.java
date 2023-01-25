package com.google.service;

import java.util.Vector;

public class Producer implements Runnable {
    private final Vector sharedQueue;
    private final int SIZE;

    public Producer(Vector sharedQueue, int size) {
        this.sharedQueue = sharedQueue;
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
        while (sharedQueue.size() == SIZE) {
            synchronized (sharedQueue) {
                System.out.println("producer sharedqueue is full");
                try {
                    sharedQueue.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        synchronized (sharedQueue) {
            sharedQueue.add(i);
            System.out.println("produced : " + i + ", sharedqueue's size is : " + sharedQueue.size());
            sharedQueue.notify();
        }
    }
}
