package pack;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

// Производитель потребитель
// Вывод на экран значений не синхронизирован
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