/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package threads;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gumdelli
 */
/**
 *
 * This is a parallel implementation of OE transposition sort
 */

class ParallelOESort implements Runnable{


    static class thread1 implements Runnable{
       public void run(){
           do{
               if(Main.thr1){waiter1=true;
for (int j=0; j<Cols/2; j++) {
            try {
                for (int i = 0; i < Rows; i++) {
                    sortPart1(a, i * Cols, (i + 1) * Cols, 1, i % 2 == 0 ? true : false);
                }
                //apause(h);
                for (int i = 0; i < Rows; i++) {
                    sortPart2(a, i * Cols, (i + 1) * Cols, 1, i % 2 == 0 ? true : false);
                    //apause(h);
                }
                //apause(h);
            } catch (Exception ex) {
                System.out.println(ex);
            }
	    }waiter1=false;
}
        }while(done);
           }
    }

    static class thread2 implements Runnable{
    public void run(){
        do{
            if(thr2)
            {
                waiter2=true;
        for (int j=0; j<Rows/2; j++) {
                try {
                    for (int i = 0; i < Cols; i++) {
                        sortPart1(a, i, Rows * Cols + i, Cols, true);
                    }
                    //apause(h);
                    for (int i = 0; i < Cols; i++) {
                        sortPart2(a, i, Rows * Cols + i, Cols, true);
                        //apause(h);
                    }
                    //apause(h);
                } catch (Exception ex) {
                    System.out.println(ex);
                }
	    }
    waiter2=false;
        }
        }while(done1);
    }
    }

    static class thread3 implements Runnable{
        public void run(){
            do{
            if(thr3)
            {waiter3=true;
            try {
                for (int i = 0; i < Rows; i++) {
                    sortPart1(a, i * Cols, (i + 1) * Cols, 1, true);
                }
                for (int i = 0; i < Rows; i++) {
                    sortPart2(a, i * Cols, (i + 1) * Cols, 1, true);
                }
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }

        }waiter3=false;
            }while(done2);


    }}

    private static int Log, Rows, Cols;
    public static int maxsize = 100000000;
    public static int a[]=new int[maxsize];
    public static Random rand;
    public static final int seed = 100000000;
    public static boolean done = true;
    public static boolean done1 = true;
    public static boolean done2 = true;
    public static boolean thr1 = false;
    public static boolean thr2 = false;
    public static boolean thr3 = false;
    public static boolean waiter1,waiter2,waiter3 = false;
     public void run() {
        
    }

    public static void waiter(int arg)  {
	if(arg=='1'){
       while(waiter1){while(waiter1){}}
        }
        else if(arg == '2'){
            while(waiter2){while(waiter2){}}
        }
        else if(arg=='3'){
            while(waiter3){while(waiter3){}}
        }
    }

    private static void sortPart1(int a[], int Lo, int Hi, int Nx, boolean Up) throws Exception {
	    for (int j = Lo; j+Nx<Hi; j+=2*Nx)
		if ((Up && a[j] > a[j+Nx]) || !Up && a[j] < a[j+Nx]) {
		    int T = a[j];
		    a[j] = a[j+Nx];
		    a[j+Nx] = T;
		}
    }

    private static void sortPart2(int a[], int Lo, int Hi, int Nx, boolean Up) throws Exception {
	    for (int j = Lo+Nx; j+Nx<Hi; j+=2*Nx)
		if ((Up && a[j] > a[j+Nx]) || !Up && a[j] < a[j+Nx]) {
		    int T = a[j];
		    a[j] = a[j+Nx];
		    a[j+Nx] = T;
		}
    }

    
    
    public static void main(String[] args) throws Exception{
    rand = new Random(seed);
    for(int i=0; i<a.length; i++) {
            a[i] = rand.nextInt(seed);
    }
    Main obj = new Main();
   //(new Thread(new thread1())).start();
    thread1 t1 = new thread1();
    thread2 t2 = new thread2();
    thread3 t3 = new thread3();
    try {
            Process p = Runtime.getRuntime().exec("taskset -c 2 ");//// system call to set this thread to processor no. 2
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    (new Thread(t1)).start();
    try {
            Process p = Runtime.getRuntime().exec("taskset -c 3 ");// system call to set this thread to processor no. 3
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    (new Thread(t2)).start();
    try {
            Process p = Runtime.getRuntime().exec("taskset -c 4 ");// system call to set this thread to processor no. 4
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    (new Thread(t3)).start();
        
        
//    for(int i=0; i<a.length; i++) {
//    System.out.print(a[i] + " ");
//    }
	System.out.println();
	System.out.println();
    
    int pow=1, div=1; int j,k;
	int h[];

	for(int i=1; i*i<=a.length; i++)
	    if (a.length % i == 0) div = i;
	Rows = div; Cols = a.length / div;
	for(Log=0; pow<=Rows; Log++)
	    pow = pow * 2;

	h = new int[Rows];
	for (int i=0; i<Rows; i++)
	    h[i]=i*Cols;
        long st = System.nanoTime();
	for (k=0; k<Log; k++) {
            thr1=true;//thread1 start
            Thread.sleep(1);//waiter(1);//wait till thread1 is done....waiter method
            //start thread2
            thr2=true;           
            Thread.sleep(1);//waiter(2);//wait till thread2 is done....waiter method
            //stop thread1 and thread2	    
	}
        
        done=false;
        done1=false;
        //kill thread1 and thread2
	for (j=0; j<Cols/2; j++) {
	    thr3=true;//thread 3 start
            Thread.sleep(1);//waiter(3);
	}
        
        long et = System.nanoTime();
        done2=false;
        //kill thread3
        long ext = k*2*1*1000000;
        ext = ext + j*1*1000000/2;
        long at = et-st-ext;
	for (int i=0; i<Rows; i++)
	    h[i]=-1;

        
//    for(int i=0; i<a.length; i++) {
//    System.out.print(a[i] + " ");
//    }
        System.out.println();
        System.out.println("Time :" + at);
        System.exit(1);
}


}
