package pack;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String args[]) {

        Foo f = new Foo();

        Thread threadA = new Thread() {
            public void run() {
                f.first();
            }
        };

        threadA.start();

        Thread threadB = new Thread() {
            public void run() {
                f.second();
            }
        };

        threadB.start();

        Thread threadC = new Thread() {
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

    volatile boolean isFirstComplete = false;
    volatile boolean isSecondComplete = false;
    
    public void first() {
        System.out.println("first complete");
        isFirstComplete = true;

    }
    public void second() {
        while (!isFirstComplete) {}
        System.out.println("second complete");
        isSecondComplete = true;
    }
    public void third() {
        while (!isSecondComplete) {}
        System.out.println("third complete");
    }
}