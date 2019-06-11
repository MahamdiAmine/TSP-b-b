import com.TSP_BranchAndBound;

import java.util.Arrays;

import static Utils.helper.*;

class main {
    public static void main(String[] args) {
        //Read the Adjacency matrix for the given graph
        double adj_matrix[][];
        String data_path1 = "./src/data/wi29.tsp";
        String data_path2 = "./src/data/berlin52.tsp";
        String data_path3 = "./src/data/data4.txt";
        try {
            //read the matrix:
            adj_matrix = generateAdjMatrix(data_path1);
            //adj_matrix = readFromFile(data_path3);
            if (adj_matrix != null) {
                System.out.println("The Matrix :");
                System.out.println(Arrays.deepToString(adj_matrix));
                System.out.println("[*]Calculating ...");
                TSP_BranchAndBound t = new TSP_BranchAndBound(adj_matrix);
                System.out.println("     Done.");
                System.out.println("[*]Results :");
                System.out.println("==================================================:");
                System.out.println("   Minimum cost           : " + t.getFinal_weight_res());
                System.out.println("   Path Taken             : " + Arrays.toString(t.getFinal_path()));
                System.out.println("   Time Elapsed in (ms)   : " + t.getTimeElapsed());
            } else {
                System.out.println("Unrecognized file format!");
            }

        } catch (Exception e) {
            print_exception(e);
        }
    }
}