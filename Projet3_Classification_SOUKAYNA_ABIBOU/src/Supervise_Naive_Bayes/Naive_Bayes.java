package Supervise_Naive_Bayes;

import java.util.HashMap;
import java.util.Map;

public class Naive_Bayes
{
	public static double naive_bayes(Map<String, Map<String,Integer>> docs,String term,char c)
	{
		int nk,n,m;
		nk=nombre_mot_k_repete_dans_class(docs, term, c);
		n=nombre_mots_dans_class(docs, c);
		m=nombre_mots_non_repeter(docs);
		double p;
		p=(double)(nk+1)/(m+n);
		return p;
	}

	public static int nombre_mots_dans_class(Map<String, Map<String,Integer>> docs,char c)
	{
			int lenght,n=0;
			String nom_document;
			char class_document;
			 for (Map.Entry entry : docs.entrySet())
			 {
				 nom_document=(String) entry.getKey();
				 lenght=nom_document.length()-7;
				 class_document=nom_document.charAt(lenght);
				 Map<String,Integer> document=new HashMap<String, Integer>();
				 document=(Map<String, Integer>) entry.getValue();
				 if(class_document==c) 
				 {
					 for(Map.Entry entry1 : document.entrySet())
					 {
						 int v=(int) entry1.getValue();
						 n=n+v;
					 }
				 }
				
		     }
			 return n;
	}
	public static int nombre_mots_non_repeter(Map<String, Map<String,Integer>> docs) 
	{     
		 Map<String,Integer> doc;
		 Map<String,Integer> document_mots_general = new HashMap<String, Integer>();;
		 for (Map.Entry entry : docs.entrySet())
		 {
			 doc=new HashMap<String, Integer>();
			 Map<String,Integer> document=new HashMap<String, Integer>();
			 document=(Map<String, Integer>) entry.getValue();
			 for (Map.Entry element : document.entrySet())
			 {
				 
				 String mot=(String) element.getKey();
				 if(!document_mots_general.containsKey(mot))
				 {
					 
					 document_mots_general.put(mot, 0);
				 }
			 }
	     }
		 return document_mots_general.size();
	}
	public static int nombre_mot_k_repete_dans_class(Map<String, Map<String,Integer>> docs,String term,char c)
	{
			int lenght,n=0;
			String nom_document;
			char class_document;
			 for (Map.Entry entry : docs.entrySet())
			 {
				 nom_document=(String) entry.getKey();
				 lenght=nom_document.length()-7;
				 class_document=nom_document.charAt(lenght);
				 Map<String,Integer> document=new HashMap<String, Integer>();
				 document=(Map<String, Integer>) entry.getValue();
				 
				 if(document.containsKey(term) && class_document==c) 
				 {
					 n=document.get(term)+n;
				 }
				
		     }
			 return n;
	}
	public static double produit_naive_bayes(Map<String, Map<String,Integer>> docs,Map<String,Integer> doc,char c)
	{
		double d=1;
		for(Map.Entry entry : doc.entrySet())
		{
			d=d*naive_bayes(docs, (String)entry.getKey(),c);
		}
		return d;
	}
}