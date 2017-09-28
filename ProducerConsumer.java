package com.company;

import java.util.Random;
import java.util.concurrent.SynchronousQueue;

// Производитель потребитель
// Вывод на экран значений не синхронизирован
public class Main {

    public static void main(String args[]) {

        final SynchronousQueue<Integer> queue = new SynchronousQueue<>();
        final int BUFFER_SIZE = 100;
        final int RANDOM_BOUND = 100;

        Thread producer = new Thread("PRODUCER") {
            public void run() {
                Random rnd = new Random();
                int event = -1;
                while(true) {
                    if(event == -1) event = rnd.nextInt(RANDOM_BOUND);

                    if(queue.size() < BUFFER_SIZE) {
                        try {
                            System.out.printf("[%s] produce : %s %n", Thread.currentThread().getName(), event);
                            queue.put(event);
                            sleep(event);
                            event = -1;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        };

        producer.start(); // starting publisher thread

        Thread consumer = new Thread("CONSUMER") {
            public void run() {
                try {
                    while (true) {
                        int event = queue.take(); // thread will block here
                        System.out.printf("[%s] consumed event : %s %n", Thread.currentThread().getName(), event);
                        sleep(event);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        consumer.start(); // starting consumer thread
    }
}

