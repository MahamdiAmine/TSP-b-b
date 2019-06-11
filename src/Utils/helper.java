package Utils;

import tsp.TSPCoordinate;
import tsp.TSPException;
import tsp.TSPFileParser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/***
 * Utils to make coding clean nd simple
 ***/
public class helper {
    //read 2D matrix from text file , the values are separated by space .
    public static double[][] readFromFile(String data_path) throws Exception {
        Scanner sc;
        int index = get_noLines(data_path);
        sc = new Scanner(new BufferedReader(new FileReader(data_path)));
        double[][] matrix = new double[index][index];
        int noLines = 0;
        while (sc.hasNextLine()) {
            for (int i = 0; i < matrix.length; i++) {
                String[] line = sc.nextLine().trim().split(" ");
                noLines++;
                for (int j = 0; j < line.length; j++) {
                    matrix[i][j] = Integer.parseInt(line[j]);
                }
            }
        }
        assertEquals(index, noLines);
        if (!isSymmetric(matrix)) throw new Exception("the matrix is not symmetric !");
        return matrix;
    }

    public static boolean isSymmetric(double matrix[][]) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        boolean symmetric = true;
        if (rows != cols) {
            symmetric = false;
        } else {

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (matrix[i][j] != matrix[j][i]) {
                        symmetric = false;
                        System.out.println(matrix[i][j]);
                        System.out.println(matrix[j][i]);
                        System.out.println();
                        break;
                    }
                }
            }
        }
        return symmetric;
    }

    //assertEquals Function of integers,with 88 as the exitcode.
    private static void assertEquals(int index, int noLines) {
        if (index != noLines) {
            System.out.println("You have a problem in your data !!");
        }
    }

    //Function to Get the no lines in a file , witch is the noNodes.
    public static int get_noLines(String file_location) throws IOException {
        Path path = Paths.get(file_location);
        return (int) Files.lines(path).count();
    }

    // Function to copy temporary solution to the final solution
    public static void copyToFinal(int curr_path[], int final_path[], int N) {
        for (int i = 0; i < N; i++)
            final_path[i] = curr_path[i];
        final_path[N] = curr_path[0];
    }

    // Function to find the minimum edge cost having an end at the vertex i
    public static double firstMin(double adj[][], int i, int N) {
        double min = Double.MAX_VALUE;
        for (int k = 0; k < N; k++)
            if (adj[i][k] < min && i != k)
                min = adj[i][k];
        return min;
    }

    // Function to find the second minimum edge cost having an end at the vertex i
    public static double secondMin(double adj[][], int i, int N) {
        double first = Double.MAX_VALUE, second = Double.MAX_VALUE;
        for (int j = 0; j < N; j++) {
            if (i == j) continue;
            if (adj[i][j] <= first) {
                second = first;
                first = adj[i][j];
            } else if (adj[i][j] <= second && adj[i][j] != first)
                second = adj[i][j];
        }
        return second;
    }

    //Function to compute initial bound
    public static double initial_bound(double adj[][], int N) {
        double curr_bound = 0.0;
        for (int i = 0; i < N; i++)
            curr_bound += (firstMin(adj, i, N) + secondMin(adj, i, N));

        // Rounding off the lower bound to an integer
        curr_bound = (curr_bound == 1) ? curr_bound / 2 + 1 :
                curr_bound / 2;
        return curr_bound;
    }

    //Function to print Exceptions and exit with exitcode 88
    public static void print_exception(Exception e) {
        System.out.println();
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        String exceptionAsString = sw.toString();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println("Time :" + dtf.format(now) + '\n' + exceptionAsString);
        System.out.println("Please  fix the exception and try again !");
        System.exit(88);
    }

    public static double[][] generateAdjMatrix(String fileName) throws TSPException {
        TSPFileParser t = new TSPFileParser(fileName);
        TSPCoordinate[] coordinates = t.parseFile();
        if (t.getFileType().equals("NOT DEFINED")) return null;
        int dim = t.getDimension();
        double table[][] = new double[dim][dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                table[i][j] = calculateDistance(coordinates[i], coordinates[j]);
                //System.out.print(table[i][j]+"   ");
            }
            //System.out.println('\n');
        }
        return table;
    }

    public static double calculateDistance(TSPCoordinate t1, TSPCoordinate t2) {
        double x1MINUSx2 = Math.pow((t1.getX() - t2.getX()), 2);
        double y1MINUSy2 = Math.pow((t1.getY() - t2.getY()), 2);
        return Math.abs(Math.sqrt(x1MINUSx2 + y1MINUSy2));
    }

}