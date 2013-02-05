package sortparallel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Arrays;
/*author : Srinivas Prasad Gumdelli
This program does sorting in two seperate threads, in parallel and merges both of the results.
The running time is compared to various other sorting methods
*/

public class SortThreads implements Runnable {
    private static final int L = 100000000;
    private static int[] nums;
    private static Random rand;
    private static final int seed = 10000000;
    

    public void run() {
        /* sort upper half */
        Arrays.sort(nums, nums.length/2, nums.length);
    }

    public static void main(String args[]) throws InterruptedException, FileNotFoundException {
        /* allocate array and random number generator */
        import java.util.Scanner;
        PrintWriter out = new PrintWriter (new File ("/home/gumdelli/output.txt"));
        int i;
        System.out.println();
        nums = new int[L];
        rand = new Random(seed);

        /* populate the array */
        for(i=0; i<nums.length; i++) {
            nums[i] = rand.nextInt(seed);
        }
        long st = System.nanoTime();
        Thread t = new Thread(new Main());
        t.start();

        /* sort lower half */
        Arrays.sort(nums, 0, nums.length/2);

        t.join();

        /* merge */
        int j = 0;
        int k = nums.length/2;
        int[] tmp = new int[nums.length];
        for (i=0; i<tmp.length; i++){
            if (j < nums.length/2) {
                if (k < nums.length) {
                    if (nums[j] < nums[k]) {
                        tmp[i] = nums[j++];
                    } else {
                        tmp[i] = nums[k++];
                    }
                } else {
                    /* reached end of second half, copy first*/
                    tmp[i] = nums[j++];
                }
            } else {
                /* reached end of 1st half, copy 2nd*/
                tmp[i] = nums[k++];
            }
        }
        nums = tmp;
    
        
        long et = System.nanoTime();
        long at = et-st;
        System.out.println(nums.length + " " + nums[500]);
//        for(i=0;i<nums.length;i++)
//        {
////            System.out.println(i+" : " +Main.nums[i]+"  ");
//            out.println(nums[i] + " ");
//        }
        System.out.println();
       System.out.println("parallel time : " + at);
        out.close();
       }
}
