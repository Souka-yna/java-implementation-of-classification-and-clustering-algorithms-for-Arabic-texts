package NLP;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TFIDFCalculator 
{ 
	public static HashMap NombreOccurance(List<String> document)
	{
		HashMap<String, Integer> H = new HashMap();
		for (String word : document)
		{
			if (H.keySet().contains(word))
				H.put(word, H.get(word) + 1);
			else
			{
				H.put(word, 1);
			}
		}
		return H;
	}
	
	public static double tf(HashMap<String, Integer> doc, String term)
	{
		int somme = 0;
		for (String v : doc.keySet())
		{
			somme += doc.get(v);
		}
		return doc.get(term) / (double) somme;
	}

	public static double idf(HashMap<String, HashMap<String, Integer>> Corpus, String term)
	{
		int n = 0;
		int d = Corpus.size();
		for (String key : Corpus.keySet())
		{
			if (Corpus.get(key).keySet().contains(term))
			{
				n++;
			}
		}
		return Math.log(d / n);
	}

	public static double tfIdf(HashMap<String, Integer> doc, HashMap<String, HashMap<String, Integer>> docs, String term)
	{
		return tf(doc, term) * idf(docs, term);
	}
	
	public static HashMap TFIDFMap(HashMap<String, HashMap<String, Integer>> Corpuss)
	{
		HashMap<String, HashMap<String, Double>> TFIDF = new HashMap();
		double tfidf;
		for (String key : Corpuss.keySet())
		{
			HashMap<String, Double> Val = new HashMap();
			for (String B : Corpuss.get(key).keySet())
			{
				tfidf = tfIdf(Corpuss.get(key), Corpuss, B);
				Val.put(B, tfidf);
			}
			TFIDF.put(key, Val);
		}
		return TFIDF;
	}
}
