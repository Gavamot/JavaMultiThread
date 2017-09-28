package pack;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


/*
Реализуйте потоки производителя (Producer) и потребителя (Consumer), совместно пользующихся буфером фиксированного размера.

Первый поток должен помещать числа в буфер в бесконечном цикле, а второй — бесконечно извлекать их оттуда. Порядок добавления и извлечения чисел не имеет значения. Данные производителя не должны теряться: либо считаться потребителем, либо остаться в буфере.

Решение по организации ожидания чтения, в случае пустого буфера, или записи, в случае заполненного буфера, остается за вами.
*/

public class Main {

    public static void main(String args[]) {

        final Queue<Integer> queue = new LinkedList<>();
        final int BUFFER_SIZE = 10;
        final int RANDOM_BOUND = 100;
        
        Thread producer = new Thread("PRODUCER") {
            public void run() {
                Random rnd = new Random();
                int event = -1;
                while(true) {
                    if(event == -1) event = rnd.nextInt(RANDOM_BOUND);
                    synchronized(queue){
                        if(queue.size() < BUFFER_SIZE) {
                            System.out.printf("[%s] produce : %s %n", Thread.currentThread().getName(), event);
                            queue.add(event);
                            event = -1;
                        }
                    }
                }
            }
        };

        producer.start(); // starting publisher thread

        Thread consumer = new Thread("CONSUMER") {
            public void run() {
                while (true) {
                    synchronized(queue){
                        if(queue.size() > 0){
                            int event = queue.remove(); // thread will block here
                            System.out.printf("[%s] consumed : %s %n", Thread.currentThread().getName(), event);
                        }
                   }
                }
            }
        };

        consumer.start(); // starting consumer thread
    }
}