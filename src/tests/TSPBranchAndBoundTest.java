package tests;

import com.TSP_BranchAndBound;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import static Utils.helper.generateAdjMatrix;
import static Utils.helper.print_exception;

public class TSPBranchAndBoundTest extends TestCase {

    double[][] mat, mat1, mat2;
    double matrix[][];

    {
        try {
            mat = Utils.helper.readFromFile("./src/data/data.txt");
            mat1 = Utils.helper.readFromFile("./src/data/data01230-66.txt");
            mat2 = Utils.helper.readFromFile("./src/data/data4.txt");
            matrix = generateAdjMatrix("./src/data/burma14.tsp");
        } catch (Exception e) {
            print_exception(e);
        }
    }

    TSP_BranchAndBound tsp = new TSP_BranchAndBound(matrix);
    //    TSP_BranchAndBound tsp3 = new TSP_BranchAndBound( mat3);
    double value = tsp.getFinal_weight_res();
//    int value1 = tsp1.getFinal_weight_res();
//    int value2 = tsp2.getFinal_weight_res();
//    int value3=tsp3.getFinal_weight_res();

    @Test
    public void testGetFinal_weight_res() {
        Assert.assertEquals(18.02, value);
//        Assert.assertEquals(66, value1);
//        Assert.assertEquals(207, value2);
//        Assert.assertEquals(18, value3);
    }
}