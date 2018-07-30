package setexpansion.algorithm;

import static setexpansion.util.MapUtility.DESC;
import static setexpansion.util.MapUtility.sortByComparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import setexpansion.util.MapUtility;


/**
 * This class provide the implementation of the Dynamic Thresholding algorithm
 * @author Sanket and Nawshad
 */
public class DynamicSeisa {
	
	private static final int MAX_ITER = 5;
	
	public static Set<String> getExpandedSet(Set<String> seeds , double alpha)
    {
       
        /*HashMap<String, Integer > dictionary = new HashMap<String, Integer>(){{
            put("a", 0);
            put("b", 1);
            put("c", 2);
            put("d", 3);
            put("e", 4);
            put("f", 5); 
        }};*/
        
        //List<String> dictionary = Arrays.asList("a","b","c","d","e","f");
//        Set<String> seeds = new HashSet();
//        seeds.add("internship");
//        seeds.add("temporary");
        
        MapUtility mUtil = new MapUtility();
        Set<String> dictionary = mUtil.termCountMap.keySet();
        Map<String, Double > relScoreMap = mUtil.findRelScore( dictionary, seeds);
        //double fa = mUtil.pairWiseMatrixLookUp(dictionary, pairWiseMatrix, "f", "a");
        //double fb = mUtil.pairWiseMatrixLookUp(dictionary, pairWiseMatrix, "f", "b");
        //System.out.println("fa:"+fa+" fb:"+fb);
        
        //MapUtility.printMap(relScoreMap);
        Map<String, Double> sortedMapDesc = sortByComparator(relScoreMap, DESC);
//        System.out.println("Sorted Rel Scores:");
//        mUtil.printMap(sortedMapDesc);
//        
        
        Iterator it = relScoreMap.entrySet().iterator();
        double relScore[] = new double[relScoreMap.size()];
        int i = 0;
        while(it.hasNext()){
        	Map.Entry entry = (Map.Entry) it.next();
        	relScore[i] = (Double) entry.getValue();
        	i++;
        }
        //double thresHold = 0.69;
        int thresHoldIndex = mUtil.OtsusThreshold(mUtil.getValuesFromMap(sortedMapDesc));
        double thresHold = mUtil.getValuesFromMap(sortedMapDesc).get(thresHoldIndex);
       // double thresHold = mUtil.OtsusThreshold(relScore);
        //data[mUtil.OtsusThreshold(data)];
        
        Map<String, Double> topKRelScores = mUtil.findTopK(sortedMapDesc, thresHold);
//        System.out.println("TopK Rel Scores:");
//        mUtil.printMap(topKRelScores);
        
        //double alpha = 0.9;
        Set<String> prevRelTerms = topKRelScores.keySet();
        
        int iterator = 0;
        
        while(iterator<MAX_ITER){
        	System.out.println("Iteration:" + iterator);
        	System.out.println("Threshold in iterator:" + iterator + " =" + thresHold);
            Map<String, Double > simScoreMap = 
                    mUtil.findRelScore(dictionary, prevRelTerms);
//            System.out.println("Sim Scores:");
//            mUtil.printMap(simScoreMap); 
//            
            Map<String, Double > gScoreMap = mUtil.findGScore(relScoreMap, simScoreMap,alpha);
//            System.out.println("g Scores:");
//            mUtil.printMap(gScoreMap);
//            
//            Iterator entries = gScoreMap.entrySet().iterator();
//            double gScores[] = new double[gScoreMap.size()];
//            int j = 0;
//            while(entries.hasNext()){
//            	Map.Entry entry = (Map.Entry) entries.next();
//            	gScores[j] = (Double) entry.getValue();
//            	j++;
//            }
            
            Map<String, Double> sortedGScoreMap = sortByComparator(gScoreMap, DESC);
            thresHoldIndex = mUtil.OtsusThreshold(mUtil.getValuesFromMap(sortedGScoreMap));
            thresHold = mUtil.getValuesFromMap(sortedGScoreMap).get(thresHoldIndex);
            //thresHold = mUtil.OtsusThreshold(gScores);
            //thresHold = 0.69;
            Set<String> nextRelTerms = mUtil.findTopK(sortedGScoreMap, thresHold).keySet();
            prevRelTerms = nextRelTerms;
            iterator++;
            

          
         }  
      
      return prevRelTerms;
         
    }

}
