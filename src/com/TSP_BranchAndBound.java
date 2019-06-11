package com;

// Java program to solve TSP using B&B.

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import static Utils.helper.*;

/***
 solving the TSP using branch and bound .
 ***/
public class TSP_BranchAndBound {

    //the no nodes witch is the noLines in the data text file
    private int N;//noNodes in the graph
    private int[] final_path;//stores the path of the salesman.
    //keeps track of the already visited nodes in a particular path
    private boolean visited[];
    //Stores the final minimum weight of shortest tour.
    private double final_weight_res;
    //the graph is represented with the Adjacency matrix as a text file.
    private double adj[][];
    private long timeElapsed = 0;


    private void TSPRec(double adj[][], double curr_bound, double curr_weight,
                        int level, int curr_path[]) {
        //If the all nodes are  covered all
        if (level == N) {
            // making sure that we have an hamiltonian cycle
            if (adj[curr_path[level - 1]][curr_path[0]] != 0) {
                double curr_res = curr_weight + adj[curr_path[level - 1]][curr_path[0]];
                if (curr_res < final_weight_res) {
                    copyToFinal(curr_path, final_path, N);
                    final_weight_res = curr_res;
                }
            }
            return;
        }
        for (int i = 0; i < N; i++) {
            if (adj[curr_path[level - 1]][i] != 0 && !visited[i]) {
                double temp = curr_bound;
                curr_weight += adj[curr_path[level - 1]][i];
                // different computation of curr_bound for level 2 from the other levels
                double sub_value;
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
    public TSP_BranchAndBound(double[][] adj) {
        this.N = adj.length;//noNodes
        this.final_path = new int[N + 1];
        this.visited = new boolean[N];
        this.final_weight_res = Double.MAX_VALUE;
        this.adj = adj;
        int curr_path[] = new int[N + 1];
        double curr_bound = initial_bound(adj, N);
        Arrays.fill(curr_path, -1);
        Arrays.fill(visited, false);
        visited[0] = true;
        //start from the node 0
        curr_path[0] = 0;
        //make a rec call to TSPRec with the level 1.
        Instant start = Instant.now();
        TSPRec(adj, curr_bound, 0.0, 1, curr_path);
        Instant finish = Instant.now();
        this.timeElapsed = Duration.between(start, finish).toMillis();  //in millis
    }

    public double getFinal_weight_res() {
        return final_weight_res;
    }

    public int[] getFinal_path() {
        return final_path;
    }

    public long getTimeElapsed() {
        return this.timeElapsed;
    }

}
