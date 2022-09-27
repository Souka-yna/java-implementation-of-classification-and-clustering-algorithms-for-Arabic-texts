package Supervise_KNN;

import java.io.IOException;
import java.util.Map;

public class Test_KNN
{
	public static void main(String[] args) throws IOException
	{
		// TODO Auto-generated method stub
		 String pathfolder="Source/Documents";
		 Map<String, Map<String, Integer>> corpus=KNN.corpus(pathfolder);
		 System.out.println(corpus);
		 Map<String, Integer> doc=corpus.get("Source\\Documents\\1_2.txt");
		 System.out.println(doc);
		 char c=KNN.KNN_Classifieur(corpus, doc,3);
		 System.out.println("KNN CLASSE "+c);
	}
}