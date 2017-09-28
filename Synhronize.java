package com.company;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static void main(String args[]) {

        Foo f = new Foo();

        Thread threadA = new Thread(ThreadA) {
            public void run() {
                f.first();
            }
        };

        threadA.start();

        Thread threadB = new Thread(ThreadB) {
            public void run() {
                try{
                    sleep(1000);
                }catch (InterruptedException e){

                }
                f.second();
            }
        };

        threadB.start();

        Thread threadC = new Thread(ThreadC) {
            public void run() {
                f.third();
            }
        };

        threadC.start();
    }


}

class Foo {
    public Foo() {

    }

    final Object second = new Object();
    final Object third = new Object();

    public void first() {
        System.out.println(first);
        second.notify();

    }
    public void second() {
        synchronized (second) {
            try {
                second.wait();
            }catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println(second);
            third.notify();
        }

    }
    public void third() {

        synchronized (third) {
            try {
                third.wait();
            }catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println(third);
        }

    }
}

