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
package com.company;
import java.util.concurrent.Semaphore;

public class Main {

    public static void main(String args[]) {
        FooWrapper f = new FooWrapper(new Foo());
        Thread threadA = new Thread(()->f.first());
        Thread threadB = new Thread(()->f.second());
        Thread threadC = new Thread(()->f.third());
        threadC.start();
        threadB.start();
        threadA.start();
    }
}

class FooWrapper{
    private Foo foo;
    private Semaphore semF, semS;

    public FooWrapper(Foo foo){
        this.foo = foo;
        semF =  new Semaphore(1);
        semS = new Semaphore(1);
        acquire(semF);
        acquire(semS);
    }

    private void acquire(Semaphore sem){
        try{
            sem.acquire();
        }catch (InterruptedException e){}
    }

    public void first(){
        foo.first();
        System.out.println("first complete");
        semF.release();
    }

    public void second(){
        acquire(semF);
        foo.second();
        System.out.println("second complete");
        semS.release();
    }

    public void third(){
        acquire(semS);
        foo.third();
        System.out.println("third complete");
    }
}

class Foo {
    public Foo() {}
    public void first() {}
    public void second() {}
    public void third() {}
}