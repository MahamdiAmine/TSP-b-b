package com;

// Java program to solve TSP using B&B.

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import static Utils.helper.*;

/***
 solving the TSP using branch and bound .
 ***/
public class TSP_BranchAndBound {
    // the path to the data
    private String data_path;
    //the no nodes witch is the noLines in the data text file
    private int N;//noNodes in the graph
    private int[] final_path;//stores the path of the salesman.
    //keeps track of the already visited nodes in a particular path
    private boolean visited[];
    //Stores the final minimum weight of shortest tour.
    private int final_weight_res;
    //the graph is represented with the Adjacency matrix as a text file.
    private int adj[][];


    private void TSPRec(int adj[][], int curr_bound, int curr_weight,
                        int level, int curr_path[]) {
        //If the all nodes are  covered all
        if (level == N) {
            // making sure that we have an hamiltonian cycle
            if (adj[curr_path[level - 1]][curr_path[0]] != 0) {
                int curr_res = curr_weight + adj[curr_path[level - 1]][curr_path[0]];
                if (curr_res < final_weight_res) {
                    copyToFinal(curr_path, final_path, N);
                    final_weight_res = curr_res;
                }
            }
            return;
        }
        for (int i = 0; i < N; i++) {
            if (adj[curr_path[level - 1]][i] != 0 && visited[i] == false) {
                int temp = curr_bound;
                curr_weight += adj[curr_path[level - 1]][i];

                // different computation of curr_bound for level 2 from the other levels
                int sub_value;
                if (level == 1)
                    sub_value = firstMin(adj, curr_path[level - 1], N);
                else
                    sub_value = secondMin(adj, curr_path[level - 1], N);
                curr_bound -= ((sub_value + firstMin(adj, i, N)) / 2);
                // compare the actual lower bound for the current node with the
                // final_weight_res
                if (curr_bound + curr_weight < final_weight_res) {
                    curr_path[level] = i;
                    visited[i] = true;
                    // pass to the next level
                    TSPRec(adj, curr_bound, curr_weight, level + 1, curr_path);
                }
                // TODO clean the code !!!
                // Resetting the values for curr_weight,curr_bound,visited
                curr_weight -= adj[curr_path[level - 1]][i];
                curr_bound = temp;
                Arrays.fill(visited, false);
                for (int j = 0; j <= level - 1; j++)
                    visited[curr_path[j]] = true;
            }
        }
    }


    //the constructor
    public TSP_BranchAndBound( int adj[][]) {
        this.N = adj.length;//noNodes
        this.final_path = new int[N + 1];
        this.visited = new boolean[N];
        this.final_weight_res = Integer.MAX_VALUE;
        this.adj = adj;
        int curr_path[] = new int[N + 1];
        int curr_bound = initial_bound(adj, N);
        Arrays.fill(curr_path, -1);
        Arrays.fill(visited, false);
        visited[0] = true;
        //start from the node 0
        curr_path[0] = 0;
        //make a rec call to TSPRec with the level 1.
        TSPRec(adj, curr_bound, 0, 1, curr_path);
    }

    public int getFinal_weight_res() {
        return final_weight_res;
    }

    public int[] getFinal_path() {
        return final_path;
    }

    public static void main(String[] args) {
        //Read the Adjacency matrix for the given graph
        int adj_matrix[][] = new int[0][0];
        String data_path = "./src/data/data.txt";
        String data_path2 = "./src/data/data01230-66.txt";
        String data_path3 = "./src/data/data4.txt";
        try {
            //read the matrix:
            System.out.println("The Matrix :");
            adj_matrix = readFromFile(data_path3);
            System.out.println(Arrays.deepToString(adj_matrix));
        } catch (Exception e) {
            print_exception(e);
        }
        //calculating..
        System.out.println("[*]Calculating ...");
        TSP_BranchAndBound t = new TSP_BranchAndBound(adj_matrix);
        System.out.println("     Done.");
        //Print the results :
        System.out.println("[*]Results :");
        System.out.printf("     Minimum cost : %d", t.getFinal_weight_res());
        System.out.printf("     Path Taken : " + Arrays.toString(t.getFinal_path()));
    }
}
