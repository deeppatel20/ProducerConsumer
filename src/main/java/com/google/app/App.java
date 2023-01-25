package com.google.app;

import com.google.service.Consumer;
import com.google.service.Producer;

import java.util.Vector;

public class App {
    public static void main(String[] args) {
        System.out.println("Producer Consumer project");
        Vector sharedQueue = new Vector();
        Producer producer = new Producer(sharedQueue, 10);
        Consumer consumer = new Consumer(sharedQueue);
        Thread t1 = new Thread(producer, "Producer");
        Thread t2 = new Thread(consumer, "Consumer");
        t1.start();
        t2.start();
    }
}