package tsp;

import java.io.*;
import java.util.*;

public class TSPFileParser {


    private boolean isTSPFileIn;
    private int adjGraph[][];
    private int dimension;
    private String fileType;
    private String fileName;

    public TSPFileParser(String fileName) throws TSPException {

        this.fileName = fileName;
        this.isTSPFileIn = true;
        this.fileType = "NOT DEFINED";
    }

    public boolean isTSPFileIn() {
        return this.isTSPFileIn;
    }

    public String getFileType() {
        return this.fileType;
    }

    public TSPCoordinate[] parseFile() throws TSPException {
        TSPCoordinate[] coordinates = new TSPCoordinate[1000];
        int counter = 0;
        boolean nodeCoordSection = false;
        try {
            BufferedReader in = new BufferedReader(new FileReader(this.fileName));
            String line;
            while ((line = in.readLine()) != null) {
                if (!line.equalsIgnoreCase("EOF") && !line.equalsIgnoreCase(" EOF") && !line.equals("")) {
                    if (!line.equalsIgnoreCase("NODE_COORD_SECTION") && !nodeCoordSection) {
                        if (line.equalsIgnoreCase("EDGE_WEIGHT_TYPE: EUC_2D") ||
                                line.equalsIgnoreCase("EDGE_WEIGHT_TYPE : EUC_2D")) {
                            fileType = "EUC_2D";
                            isTSPFileIn = true;
                        } else if (line.equalsIgnoreCase("EDGE_WEIGHT_TYPE: GEO") ||
                                line.equalsIgnoreCase("EDGE_WEIGHT_TYPE : GEO")) {
                            fileType = "GEO";
                            isTSPFileIn = true;
                        }
                    } else if (line.equalsIgnoreCase("NODE_COORD_SECTION")) {
                        nodeCoordSection = true;
                    } else {
                        StringTokenizer strTok = new StringTokenizer(line, " \t");
                        try {
                            counter = Integer.valueOf(strTok.nextToken());
                            if (fileType.equals("EUC_2D") || fileType.equals("GEO")) {
                                double x = Double.valueOf(strTok.nextToken());
                                double y = Double.valueOf(strTok.nextToken());
                                coordinates[counter - 1] = new TSPCoordinate(x, y);
                            } else {
                                this.isTSPFileIn = false;
                                throw new TSPException("Unrecognized file format!");
                            }
                        } catch (NoSuchElementException e) {
                            throw new TSPException("Could not parse file " + "'" + fileName + "'!");
                        }
                    }
                }
                this.dimension = counter;
            }
        } catch (
                FileNotFoundException e) {
            throw new TSPException("File " + "'" + fileName + "'" + " not found in the current directory!");
        } catch (
                IOException e) {
            throw new TSPException("Could not read from file " + "'" + fileName + "'!");
        }
        return coordinates;
    }

    public int getDimension() {
        return dimension;
    }

}