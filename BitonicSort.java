/*
	Please read the README file for more information about this project
    Copyright (C) 2013 "Srinivas Prasad Gumdelli"

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.

 */

package bitonicsort;
import java.util.Random;
/**
 *
 * @author gumdelli
 */
class Bitonic
{
	void bitonic(int[] a, int l, int cnt, boolean ascend)
	{
		if(cnt < 2)
			return;
		else if((cnt & -cnt) != cnt) // handle non-power of 2
		{
			int n = cnt;
			while((n & -n) != n)
				n &= (n - 1);
			bitonic(a, l, cnt - n, true);
			bitonic(a, l + (cnt - n), n, false);
			bitonicMerge(a, l + cnt - n - n, n + n, ascend);
			return;
		}
		int k = cnt / 2;
		bitonic(a, l, k, true);
		bitonic(a, l + k, k, false);
		bitonicMerge(a, l, cnt, ascend);
	}

	void bitonicMerge(int[] a, int l, int cnt, boolean ascend)
	{
		if(cnt < 2)
			return;
		else if(((l + cnt) < 0) || (l > a.length))
			return;

		int k = cnt / 2;
		for(int i = l; i < (l + k); i++)
			if((i < 0) || ((i + k) > a.length))
				continue;
			else if(ascend == (a[i] > a[i + k]))
				swap(a, i, i + k);

		bitonicMerge(a, l, k, ascend);
		bitonicMerge(a, l + k, k, ascend);
	}

	void swap(int[] a, int i, int j)
	{
		int t = a[i];
		a[i] = a[j];
		a[j] = t;
	}

	void sort(int[] a)
	{
		bitonic(a, 0, a.length, true);
	}
}


public class BitonicSort {


    public static int maxsize = 1000;
    public static int a[]=new int[maxsize];
    public static Random rand;
    public static final int seed = 10000;
    // sorting direction:
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    rand = new Random(seed);
    for(int i=0; i<a.length; i++) {
            a[i] = rand.nextInt(seed);
        }
    Bitonic s = new Bitonic();
    for(int i=0; i<a.length; i++) {
    System.out.print(a[i] + " ");
    }
    System.out.println();
    System.out.println();
    s.sort(a);
    for(int i=0; i<a.length; i++) {
    System.out.print(a[i] + " ");
    }


    }

}
