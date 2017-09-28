package pack;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
Экземпляр класса передается 3 потокам: ThreadA вызывает first(), ThreadB вызывает second(), ThreadC вызывает third().

Разработайте механизм, гарантирующий, что метод first() будет вызван перед second(), а метод second() будет вызван перед third().

public class Foo {
    public Foo() {
    }
    public void first() {
    }
    public void second() {
    }
    public void third() {
    }
}
*/

public class Main {

    public static void main(String args[]) {

        FooWrapper f = new FooWrapper(new Foo()); 
        
        Thread threadA = new Thread() {
            public void run() {
                f.first();
            }
        };

       

        Thread threadB = new Thread() {
            public void run() {
                f.second();
            }
        };

        

        Thread threadC = new Thread() {
            public void run() {
               
                f.third();
            }
        };

        threadC.start();
        threadB.start();
        threadA.start();
    }


}

class FooWrapper{
    
    private Foo foo;
    public FooWrapper(Foo foo){
        this.foo = foo;
    }
    
    volatile boolean isFirstComplete = false;
    volatile boolean isSecondComplete = false;
    
    public void first(){
        foo.first();
        isFirstComplete = true;
    }
    
    public void second(){
        while (!isFirstComplete) {}
        foo.second();
        isSecondComplete = true;
    }
    
    public void third(){
        while (!isSecondComplete) {}
        foo.third();
    }
}

class Foo {
    public Foo() {
        
    }
    
    public void first() {
        System.out.println("first complete");
    }
    public void second() {
        System.out.println("second complete");
    }
    public void third() {
        System.out.println("third complete");
    }
}