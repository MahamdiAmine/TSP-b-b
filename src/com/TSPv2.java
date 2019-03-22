package com;

// Java program to solve TSP using B&B.

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import static Utils.helper.*;

/***
 solving the TSP using branch and bound .
 ***/
public class TSPv2 {
    //the graph is represented with the Adjacency matrix as a text file.
    private String data_path;
    private int N;//noNodes in the graph
    private int[] final_path;
    private boolean visited[];
    private int final_weight_res;
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


    private int[] TSP() {
        int curr_path[] = new int[N + 1];
        int curr_bound = initial_bound(adj, N);
        Arrays.fill(curr_path, -1);
        Arrays.fill(visited, false);
        visited[0] = true;
        //start from the node 0
        curr_path[0] = 0;
        //make a rec call to TSPRec with the level 1.
        TSPRec(adj, curr_bound, 0, 1, curr_path);
        return final_path;
    }

    public TSPv2(int N, int adj[][]) {
        this.N = N;
        this.final_path = new int[N + 1];
        this.visited = new boolean[N];
        this.final_weight_res = Integer.MAX_VALUE;
        this.adj = adj;
    }

    public int getFinal_weight_res() {
        return final_weight_res;
    }

    public int[] getFinal_path() {
        return final_path;
    }

    public static void main(String[] args) {
        //Read the Adjacency matrix for the given graph
        int adj[][] = new int[0][0];
        String data_path = "./src/data/data.txt";
        try {
            //read the matrix:
            System.out.println("The Matrix :");
            adj = readFromFile(data_path, 5);
            System.out.println(Arrays.deepToString(adj));
        } catch (FileNotFoundException e) {
            print_exception(e);
        }
        TSPv2 t = new TSPv2(5, adj);

        //calculating..
        System.out.println("[*]Calculating ...");
        t.TSP();
        System.out.println("     Done.");
        //Print the results :
        System.out.println("[*]Results :");
        System.out.printf("     Minimum cost : %d", t.getFinal_weight_res());
        System.out.printf("     Path Taken : " + Arrays.toString(t.getFinal_path()));
    }
}
