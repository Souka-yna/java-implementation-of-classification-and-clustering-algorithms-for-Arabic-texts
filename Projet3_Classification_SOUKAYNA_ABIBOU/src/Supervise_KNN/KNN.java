package Supervise_KNN;

import java.io.BufferedReader;  
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;
import java.util.Comparator;

import javax.swing.text.html.HTMLDocument.Iterator;

import common.constants.Stemmer;
import safar.basic.morphology.stemmer.factory.StemmerFactory;
import safar.basic.morphology.stemmer.interfaces.IStemmer;
import safar.basic.morphology.stemmer.model.StemmerAnalysis;
import safar.basic.morphology.stemmer.model.WordStemmerAnalysis;

public class KNN
{
	public static char KNN_Classifieur(Map<String, Map<String,Integer>> docs,Map<String,Integer> doc,int n)
	{
		 int d=0,lenght;
		 Double produit;
		 String nom_document;
	     char class_document = 0;
	     Map<String,Integer> doc1;
		 for (Map.Entry entry : docs.entrySet())
		 {
			 doc1=(Map<String, Integer>) entry.getValue();
			 produit=produit_scal(doc1, doc);
			 if(d<n) 
			 {
			 nom_document=(String) entry.getKey();
			 lenght=nom_document.length()-7;
			 class_document=nom_document.charAt(lenght);
	         d=d+1;
			 }
		 }
		 return class_document;
	}
	
	public static double produit_scal(Map<String, Integer> doc1,Map<String, Integer> doc2)
	{
	    double d=0;
		for (Map.Entry entry : doc1.entrySet()) 
		{ 
		   if(doc1.containsKey(entry.getKey()) && doc2.containsKey(entry.getKey())) 
		   {
			   d=d+doc1.get(entry.getKey())*doc2.get(entry.getKey());
		   }
		}
		return d;
	}
	
	public static Map<String, Map<String, Integer>> corpus(String pathfolder) throws IOException
	{
			String TxtString, text;
			String key = null;
			Map<String, Map<String, Integer>> docs = new HashMap<String, Map<String, Integer>>();
	        Map<String, Integer> doc=null;
			File folder = new File(pathfolder);
			File[] listOfFiles = folder.listFiles();
			for (File file : listOfFiles) 
			{
				text = "";
					BufferedReader br = new BufferedReader(new FileReader(file));
					key=""+file;
	                doc=new HashMap<String, Integer>();
	                
					
						while ((TxtString = br.readLine()) != null) 
						{
							text = text + " " + TxtString;
						}
				doc=KNN.FonctionStemmer(text);
			    docs.put(key, doc);
				
			}	
			return docs;
		}
	
	public static Map<String, Integer>  FonctionStemmer(String text) 
	{
		  IStemmer stemmer = StemmerFactory.getKhojaImplementation();
			
		  List<WordStemmerAnalysis> analysis = stemmer.stem(text); 
		  Map<String, Integer> doc=new HashMap<String, Integer>();
		  
		  List<StemmerAnalysis> listeofStemes = null; 
		
		  for (WordStemmerAnalysis wordStemmerAnalysis : analysis)
		  {
			  
		              listeofStemes = wordStemmerAnalysis.getListStemmerAnalysis();
		              for (StemmerAnalysis stem : listeofStemes) 
		              {
		                                if((stem.getType()).equals("ROOT")) 
		                                {
			  
		                                      if(doc.containsKey(stem.getMorpheme())) 
		                                      { 
			                                            Integer k= doc.get(stem.getMorpheme());
			                                            k++;
		                                                doc.put(stem.getMorpheme(), k); 
		                                       }else{
	                                                    	  doc.put(stem.getMorpheme(),1);
	                                                }
		                                  } 
		               } 
		  
		 }  
		return doc;
	}
	
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map)
	{
	      List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
	      list.sort(Entry.comparingByValue());
	      Collections.reverse(list);
	      Map<K, V> result = new LinkedHashMap<>();
	      for (Entry<K, V> entry : list)
	      {
	          result.put(entry.getKey(), entry.getValue());
	      }
	      

	      return result;
	  }
}