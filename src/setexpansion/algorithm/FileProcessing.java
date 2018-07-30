/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setexpansion.algorithm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This class is required to sorting and finding the term pairs.
 * @author Nawshad and Sanket
 */
public class FileProcessing {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
    SumOfTerms.sumOfTerms("smallTermSorted.txt", "smalltermCount.txt");
    SumOfPairs.sumOfPairs("smallTermPairSorted.txt", "smallTermPairCount.txt");

    }
        
}
    

