package pack;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String args[]) {

       FizzBuZZ fizzBuZZ = new FizzBuZZ();
       fizzBuZZ.Start();
    }
    
}

class FizzBuZZ
{
    public FizzBuZZ(){}
  
    private volatile int i = 1;
    private volatile int a = 0;
    private volatile int b = 0;
    private volatile int c = 0;
    private final static int N = 100;

    public void Start(){
        threadA.start();
        threadB.start();
        threadC.start();
        threadI.start();
    }
    
    Thread threadA = new Thread() {
        public void run() {
            while(!isInterrupted()){
                if(a < i){
                    if (i % 3 == 0) System.out.print("[Fizz]");
                    a++;
                }
            }
        }
    };
        
    Thread threadB = new Thread() {
        public void run() {
            while(!isInterrupted()){
                if(b < i){
                    if (i % 5 == 0) System.out.print("[Buzz]");
                    b++;
                }
            }
        }
    };
        
    Thread threadC = new Thread() {
        public void run() {
            while(!isInterrupted()){
                if(c < i){
                    if (i % 3 == 0 && i % 5 == 0)  System.out.print("[FizzBuzz]");
                    c++;
                }
            }
        }
    };
        
    Thread threadI = new Thread() {
        public void run() {
            while(i <= N) {
                if(i == a && i == b && i == c){
                    System.out.print("[" + i + "]");
                    i++;
                }
            }
            threadA.interrupt();
            threadB.interrupt();
            threadC.interrupt();
        }
    };
}