package setexpansion.util;

import java.util.Set;

import com.google.common.collect.Sets;
/**
 * This class is used to calculate the jaccard similarity.
 * @author sanket
 *
 */
public class Util {

	public static double getSimilarity(Set<String> listI, Set<String> listJ) {
		Set<String> intersection = Sets.intersection(listI, listJ);
		Set<String> union = Sets.union(listI, listJ);
		double similarity = (double)((double)intersection.size() / (double)union.size());
		return similarity;
	}
   public static void main(String[] args) {
	System.out.println(1/4.0);
}
}
