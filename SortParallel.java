/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sort_algo;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gumdelli
 */

/**
 * This program uses threads that are deployed parallelly to each processor and uses the default sort method 
 * present in Java, to compare the running time with serial sorting(Serial Merge sort)
 * 
 */

 class threads implements Runnable{
     Main obj;
     PrintWriter obj1;

    public threads(int a,int b, Main m)
    {   obj = m;
        obj.l = a;
        obj.r = b;
        obj.func = true;
    }
    public threads(int[] a,int[] b, Main m)
    {
        obj = m;
        obj.x = a;
        obj.y = b;
        obj.func = false;
    }

    void getobj(Main o)
     {
        obj = o;
    }

    public void run()
    {

        if(obj.func)
        {
            Arrays.sort(Main.sorted, obj.l, obj.r);
        }
        else
        {
            Main.counter = 0;
            Main.merge(obj.x,obj.y);
        }
    }

    void getobj1(PrintWriter out) {
    obj1 = out;
    }

}
public class SortParallel{

    public static int[] sorted = new int [1000000];
    public static int counter = 0;
    public int l,r;
    public boolean func;
    public int[] x;
    public int[] y;

    public static int[] swap(int[] array,int r)
    {
        for(int i=0;i<r-1;i++)
        {
            array[i] = array[i+1];
        }
        return array;
    }

public static int[] merge(int[] arr1, int[] arr2) {
        int[] result = new int[arr1.length+arr2.length];

        int j1 = 0; /* index into arr1 */
        int j2 = 0; /* index into arr2 */
        for(int i=0; i< result.length; i++) {
            if (j1 < arr1.length) {
                if (j2 < arr2.length) {
                    if (arr1[j1]<(arr2[j2])) {
                        result[i] = arr1[j1++];
                    } else {
                        result[i] = arr2[j2++];
                    }
                } else {
                    /* reached end of second chunk, copy first*/
                    result[i] = arr1[j1++];
                }
            } else {
                /* reached end of 1st chunk, copy 2nd*/
                result[i] = arr2[j2++];
            }
        }

        return result;
    }


    public static void main(String[] args) throws FileNotFoundException{
        int i=0;
        Random r = new Random(1000000);
        

        PrintWriter out = new PrintWriter (new File ("/home/gumdelli/output.txt"));
        while (i<1000000)
         {
           sorted[i] = r.nextInt(1000000);
           i++;
         }

         long st = System.nanoTime();

        Arrays.sort(Main.sorted, 0, 250000);
        Main m2 = new Main();
        threads t2 = new threads(250000,500000,m2);
        t2.getobj(m2);
        try {
            Process p = Runtime.getRuntime().exec("taskset -c 2 ");// system call to set this thread to processor no. 2
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        new Thread(t2).start();

        Main m3 = new Main();
        threads t3 = new threads(500000,750000,m3);
        t3.getobj(m3);
        try {
            Process p = Runtime.getRuntime().exec("taskset -c 3 ");// system call to set this thread to processor no. 3
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        new Thread(t3).start();
        
        
        

        Main m4 = new Main();
        threads t4 = new threads(750000,1000000,m4);
        t4.getobj(m4);
        try {
            Process p = Runtime.getRuntime().exec("taskset -c 4 ");// system call to set this thread to processor no. 4
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        new Thread(t4).start();



        try {
            new Thread(t2).join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }



        Main m5 = new Main();
        threads t5 = new threads(Arrays.copyOfRange(sorted, 0, 250000),Arrays.copyOfRange(sorted,250000, 500000),m5);
        t5.getobj(m5);
        try {
            Process p = Runtime.getRuntime().exec("taskset -c 2 ");// system call to set this thread to processor no. 2
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        new Thread(t5).start();

 

        try {
            new Thread(t3).join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }



        Main m6 = new Main();
        threads t6 = new threads(Arrays.copyOfRange(sorted, 500000, 750000),Arrays.copyOfRange(sorted,750000, 1000000),m6);
        t6.getobj(m6);
        try {
            Process p = Runtime.getRuntime().exec("taskset -c 3 ");// system call to set this thread to processor no. 3
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        new Thread(t6).start();
        try {
            new Thread(t4).join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            new Thread(t5).join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            new Thread(t6).join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }


        Main m7 = new Main();
        threads t7 = new threads(Arrays.copyOfRange(sorted, 0, 500000),Arrays.copyOfRange(sorted,500000, 1000000),m7);
        t7.getobj(m7);
        try {
            Process p = Runtime.getRuntime().exec("taskset -c 4 ");// system call to set this thread to processor no. 4
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        new Thread(t7).start();
      long et = System.nanoTime();
      long at = et-st;
       System.out.println(sorted.length + " " + sorted[500000]);
        for(i=0;i<sorted.length;i++)
        {
            out.println(sorted[i] + " ");
        }

        System.out.println("parallel time : " + at);
    }
}
