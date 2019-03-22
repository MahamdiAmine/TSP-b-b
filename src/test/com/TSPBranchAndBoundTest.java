package com;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;

public class TSPBranchAndBoundTest extends TestCase {

    int mat[][];

    {
        try {
            mat = Utils.helper.readFromFile("./src/data/data.txt", 5);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    TSP_BranchAndBound t = new TSP_BranchAndBound(5, mat);
    int value=t.getFinal_weight_res();

    @Test
    public void testGetFinal_weight_res() {
        Assert.assertEquals(18, value);
    }
}