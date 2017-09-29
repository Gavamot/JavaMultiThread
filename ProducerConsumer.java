/*
Реализуйте потоки производителя (Producer) и потребителя (Consumer), совместно пользующихся буфером фиксированного размера.

Первый поток должен помещать числа в буфер в бесконечном цикле, а второй — бесконечно извлекать их оттуда. Порядок добавления и извлечения чисел не имеет значения. Данные производителя не должны теряться: либо считаться потребителем, либо остаться в буфере.

Решение по организации ожидания чтения, в случае пустого буфера, или записи, в случае заполненного буфера, остается за вами.
*/

package com.company;

import java.util.ArrayDeque;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Main {

    private static void semaphoreWait(Semaphore sem) {
        try {
            sem.acquire(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) throws InterruptedException {
        final int BUFFER_SIZE = 10;
        final int RANDOM_BOUND = 100;
        final ArrayDeque<Integer> queue = new ArrayDeque<>();

        Semaphore semP = new Semaphore(BUFFER_SIZE);
        Semaphore semC = new Semaphore(BUFFER_SIZE);
        semC.acquire(10);

        Thread producer = new Thread("PRODUCER") {
            public void run() {
                Random rnd = new Random();
                int event = -1;
                while(true) {
                    semaphoreWait(semP);
                    if(event == -1) event = rnd.nextInt(RANDOM_BOUND);
                    System.out.printf("[%s] produce : %s %n", Thread.currentThread().getName(), event);
                    queue.add(event);
                    event = -1;
                    semC.release(1);
                }
            }
        };

        producer.start();

        Thread consumer = new Thread("CONSUMER") {
            public void run() {
                while (true) {
                    semaphoreWait(semC);
                    int event = queue.remove();
                    System.out.printf("[%s] consumed : %s %n", Thread.currentThread().getName(), event);
                    semP.release(1);
                }
            }
        };

        consumer.start();
    }
}