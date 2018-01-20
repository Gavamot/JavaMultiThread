package threads;
 
class Data {
    private int state=1;

    public int getState() { return state; }

    public void Tic(){
        System.out.print("Tic-");
        state=2;
    }
    public void Tak(){
        System.out.println("Tak");
        state=1;
    }
}

class Worker extends Thread{

    private int id;
    private Data data;
    
    Worker(int id, Data data) {
        this.id = id;
        this.data = data;
        this.start();
    }
    
    @Override
    public void run(){
        for (int i = 0; i < 10; i++){ 
            synchronized(data){
                if(id == 2 && data.getState() == 1 
                  || id == 1 &&  data.getState() == 2 ){ 
                    try {
                        data.wait();
                    }catch (InterruptedException ex) {

                    }
                }
                if(id==1) {
                    data.Tic();
                }
                else{ 
                    data.Tak();
                }
                data.notify();
             }
        }
    } 
}

public class Main {

    public static void main(String[] args) throws Exception {
        
        Data    d = new Data();
        Worker w1=new Worker(1, d);
        Worker w2=new Worker(2, d);

        w2.join();
        System.out.println("end of mian...");
    }
}
