package Supervise_Naive_Bayes;

import java.io.IOException;
import java.util.Map;
import Supervise_KNN.KNN;

public class Test_Naive_Bayes 
{
	public static void main(String[] args) throws IOException
	{
		 String pathfolder="Source/Documents";
		 Map<String, Map<String, Integer>> corpus=KNN.corpus(pathfolder);
		 System.out.println(corpus);
		 Map<String, Integer> doc=corpus.get("Source\\Documents\\1_3.txt");
		 System.out.println(doc);
		 double d1=Naive_Bayes.produit_naive_bayes(corpus, doc,'1');
		 double d2=Naive_Bayes.produit_naive_bayes(corpus, doc,'2');
		 System.out.println("naive_bayes d1= "+d1+" d2="+d2);
	}
}