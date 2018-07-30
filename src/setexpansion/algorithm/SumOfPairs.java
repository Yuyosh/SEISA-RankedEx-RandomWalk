/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setexpansion.algorithm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This class during preprocessing the file.
 * @author nawshad and sanket
 */
public class SumOfPairs {
    public static void sumOfPairs(String inputFile, String outputFile) throws IOException {
        
        FileWriter fos = new FileWriter(outputFile);
                PrintWriter dos = new PrintWriter(fos);
        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        try {
            String line = "";
            String prevt1="";
            String prevt2="";
            int total = 0;
            int curVal = 0;
            boolean first = true;
            //boolean endAtSameTerm = false;
            int lineno = 0;
            while ((line = br.readLine()) != null){
                String[] split = line.split("\t");
                //curVal += Integer.parseInt(split[3]);
                if(split.length != 4)
                	continue;
                if(first){
                	curVal += Integer.parseInt(split[3]);     
                	prevt1 = split[0];
                    prevt2 = split[1];
                	first = false;
                	continue;
                }
                if(!((split[0].equals(prevt1)&&split[1].equals(prevt2))||(split[1].equals(prevt1)&&split[0].equals(prevt2)))){
              	  dos.println(prevt1+"\t"+prevt2+"\t\t"+curVal);
              	  dos.flush();
              	  curVal = Integer.parseInt(split[3]);
              	  prevt1 = split[0];
              	  prevt2 = split[1];	
              	  //endAtSameTerm = false;
                }else{
                	curVal += Integer.parseInt(split[3]);     
                	prevt1 = split[0];
                    prevt2 = split[1];
                	//endAtSameTerm = true;
                }
                lineno = lineno + 1;
                if(lineno%10000000 == 0)
                System.out.println("Done line no:" + lineno);
                
            }
            
            	dos.println(prevt1+"\t"+prevt2+"\t\t"+curVal);
            	  dos.flush();
            	  System.out.println("Done last line");
            
            
        } finally {
            br.close();
        }
        dos.close();
        fos.close();
        System.out.println("Finished..");
    }
}
    

