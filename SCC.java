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

package scc;

/**
 *
 * @author gumdelli
 */

/**
 *
 * Program to check Strongly connected components for graphs
 */
public class SCC {
    public static int clock = 1;
    public static int a=0,b=0;
    public static int pre[] = new int[12];
    public static int post[] = new int[12];
    public static int visited[] = new int[12];
    public static int stack[]  = new int[25];
    public static int stack1[]  = new int[1000];
    public static int[][] graph = {{0,1,0,0,0,1,0,1,0,0,0,0},
                                   {0,0,1,0,1,0,0,0,0,0,0,0},
                                   {0,0,0,1,0,0,0,0,0,0,0,0},
                                   {0,0,0,0,0,0,0,0,0,0,0,0},
                                   {0,0,0,0,0,0,0,0,0,0,0,0},
                                   {0,0,0,0,0,0,1,0,0,0,0,0},
                                   {0,0,0,0,0,0,0,0,0,1,0,0},
                                   {0,0,0,0,0,0,1,0,0,0,0,0},
                                   {0,0,0,0,0,0,0,0,0,0,0,0},
                                   {0,0,0,0,0,0,0,0,1,0,0,0},
                                   {0,0,0,0,0,0,0,0,0,0,0,1},
                                   {0,0,0,0,0,0,1,0,0,0,0,0}
                            };

    public static int[][] transpose = new int[12][12];
    
    public static void previsit(int v)
    {
        pre[v] = clock;
        clock = clock + 1;
    }


    public static void postvisit(int v)
    {
        post[v] = clock;
        clock = clock + 1;
    }

    public static void dfs(int[][] g, int mtype){
        for(int i =0; i<12;i++)
        {
            visited[i] = 0;
        }
        
        if(mtype == 1){
            for(int i =0; i<12;i++)
            {
               for(int j=0;j<12;j++){
                if(visited[j]==0&&graph[i][j]==1){
                    explore(j,1);
                }
                }
            }
        }
        else{
            for(int i =0; i<12;i++)
            {
               for(int j=0;j<12;j++){
                if(visited[j]==0&&transpose[i][j]==1){
                    explore(j,0);
                }
                }
            }
        }
    }
    public static void explore(int vertex, int mtype){//mtype indicates the regular graph or the transpose of the graph
        int j;
        if(mtype ==1)
        {
            previsit(vertex);
            System.out.println("Node a  Visited : " + (vertex+1));
            stack[a] = vertex+1;//pushing the dfs forest of graph into stack
            a++;
            for(j=0;j<12;j++){
            if(visited[j]==0&&graph[vertex][j]==1){
                explore(j,1);
            }
            }
            visited[vertex]=1;
            postvisit(vertex);
            stack[a] = -1;//pushing -1 into stack to indicate the dfs forest
            a++;
        }
        else{//dfs-visit method for the transpose matrix
//            previsit(vertex);
           // System.out.println("call here"+vertex);
           
            for(int i=0;i<11;i++) //for loop to perform the dfs in decreasing order of finishing times
            {
           int flag = 0;
           int max = post[i];
           for(j=0;j<11;j++)
           {
               if(max<post[j])
               {
                   max = post[j];
                   flag = j;
               }
           }
           post[flag] = 0;//replacing the max element in post array by 0 to get the next max element
            vertex = flag;
            //System.out.println("Node Visited : " + (vertex+1));
            stack1[b] = vertex+1;//pushing the dfs forest on transpose of graph into stack1
            b++;
            //for(j=0;j<12;j++){
            if(visited[j]==0&&transpose[vertex][j]==1){
                explore(vertex,0);//calls the dfs-visit recursively
            }
            visited[vertex]=1;
            stack1[b] = -1;//pushing -1 into stack1 to indicate the dfs forest
            b++;
        }

        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      
       dfs(graph,1);
   
       
       for(int i=0;i<11;i++)//transposing the graph adjacency matrix and store it into transpose matrix
       {
           for(int j=0;j<11;j++)
           {
               transpose[i][j]=graph[j][i];
           }
       }
      
      
       dfs(transpose,0);//dfs call to perform dfs on the transpose of the graph
            for(int i=0;i<stack.length;i++){   System.out.println("{ "+ stack[i] + "}"+stack1[i]);}


       
       
    }

}

