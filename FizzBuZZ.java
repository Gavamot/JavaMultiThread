/*
Реализуйте потоки производителя (Producer) и потребителя (Consumer), совместно пользующихся буфером фиксированного размера.

Первый поток должен помещать числа в буфер в бесконечном цикле, а второй — бесконечно извлекать их оттуда. Порядок добавления и извлечения чисел не имеет значения. Данные производителя не должны теряться: либо считаться потребителем, либо остаться в буфере.

Решение по организации ожидания чтения, в случае пустого буфера, или записи, в случае заполненного буфера, остается за вами.
*/

package com.company;
import java.util.concurrent.CyclicBarrier;

public class Main {

    public static void main(String args[]){
        FizzBuZZ fizzBuZZ = new FizzBuZZ();
        fizzBuZZ.Start();
    }
}

interface Check{
    void check(int i);
}

class FizzBuZZ
{
    public FizzBuZZ(){}

    private volatile int i = 1;
    private static final int COUNT_CHECK = 3;
    private CyclicBarrier Start;
    private final static int N = 120;

    public void Start(){
        Start = new CyclicBarrier(COUNT_CHECK, new ThreadI());
        threadA.start();
        threadB.start();
        threadC.start();
    }
    private void awaitBarrier(CyclicBarrier start){
        try{
            start.await();
        }catch (Exception e){}
    }

    Thread threadA = new ThreadChecker( (int i)-> { if (i % 3 == 0) System.out.print("[Fizz]"); });
    Thread threadB = new ThreadChecker( (int i)-> { if (i % 5 == 0) System.out.print("[Buzz]"); });
    Thread threadC = new ThreadChecker( (int i)-> { if (i % 3 == 0 && i % 5 == 0) System.out.print("[FizzBuzz]"); });

    class ThreadChecker extends Thread {
        private Check checkMethod;
        public ThreadChecker(Check checkMethod){
            this.checkMethod = checkMethod;
        }
        @Override
        public void run() {
            do{
                checkMethod.check(i);
                awaitBarrier(Start);
            }while (i <= N);
        }
    }

    public class ThreadI implements Runnable {
        public ThreadI() {}
        @Override
        public void run() {
            System.out.print("[" + i + "]");
            i++;
        }
    }
}