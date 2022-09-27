package NLP;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Test
{
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		ArrayList<String> Stopwords;
		ArrayList<String> Doc1, Doc2, Doc3;
		String requete;
		double tfidf, distance;
		TFIDFCalculator calculator = new TFIDFCalculator();
		
		Stopwords = Read_File.Lire_fichier("Source/stopwords_ar.txt");
		Doc1 = Read_File.Lire_fichier("Source/Demo1.txt");
		Doc2 = Read_File.Lire_fichier("Source/Demo2.txt");
		Doc3 = Read_File.Lire_fichier("source/Demo3.txt");		

		System.out.println("<==========================================>");
		System.out.println(Doc1);
		System.out.println("<==========================================>");
		System.out.println(Doc1.size());

		Doc1.removeAll(new HashSet(Stopwords));
		Doc2.removeAll(new HashSet(Stopwords));
		Doc3.removeAll(new HashSet(Stopwords));
				
		System.out.println("<==========================================>");
		System.out.println(Doc1);
		System.out.println("<==========================================>");
		System.out.println(Doc1.size());

		Doc1 = (ArrayList<String>) Read_File.SupprimerPoctuation(Doc1);
		Doc2 = (ArrayList<String>) Read_File.SupprimerPoctuation(Doc2);
		Doc3 = (ArrayList<String>) Read_File.SupprimerPoctuation(Doc3);

		System.out.println(Doc1);
		System.out.println("<==========================================>");
		System.out.println(Doc1.size());

		Doc1 = (ArrayList<String>) Read_File.SupprimLent(Doc1);
		Doc2 = (ArrayList<String>) Read_File.SupprimLent(Doc2);
		Doc3 = (ArrayList<String>) Read_File.SupprimLent(Doc3);

		System.out.println(Doc1);
		System.out.println("<==========================================>");
		System.out.println(Doc1.size());

		Doc1 = (ArrayList<String>) Read_File.PocStop(Doc1);
		Doc2 = (ArrayList<String>) Read_File.PocStop(Doc2);
		Doc3 = (ArrayList<String>) Read_File.PocStop(Doc3);

		System.out.println("<==========================================>");
		System.out.println(Doc1);
		System.out.println("<==========================================>");
		
		HashMap Map_Doc1 = new HashMap();
		HashMap Map_Doc2 = new HashMap();
		HashMap Map_Doc3 = new HashMap();
		Map_Doc1 = TFIDFCalculator.NombreOccurance(Doc1);
		Map_Doc2 = TFIDFCalculator.NombreOccurance(Doc2);
		Map_Doc3 = TFIDFCalculator.NombreOccurance(Doc3);

		HashMap<String, HashMap<String, Integer>> Corpuss = new HashMap();
		Corpuss.put("Map_Doc1", Map_Doc1);
		Corpuss.put("Map_Doc2", Map_Doc2);
		Corpuss.put("Map_Doc3", Map_Doc3);
		
		HashMap<String, HashMap<String, Double>> TFIDF = new HashMap();

		TFIDF = TFIDFCalculator.TFIDFMap(Corpuss);
				
		System.out.println("Matrice TFIDF :");
		for (String key : TFIDF.keySet())
		{
			System.out.println(key + "   " + TFIDF.get(key));
		}
		
		System.out.println("\nEntrer une phrase :");
		Scanner sc = new Scanner(System.in);
		requete = sc.nextLine();
		System.out.println("Vous avez saisi : " + requete);

		List<String> ListeRequete = Arrays.asList(requete.split(" "));
		System.out.println(ListeRequete);

		ListeRequete = (ArrayList<String>) Read_File.PocStop(ListeRequete);

		ListeRequete.removeAll(new HashSet(Stopwords));

		ListeRequete = (ArrayList<String>) Read_File.SupprimerPoctuation(ListeRequete);

		ListeRequete = (ArrayList<String>) Read_File.SupprimLent(ListeRequete);

		HashMap requteMap = new HashMap();

		requteMap = calculator.NombreOccurance(ListeRequete);
		Corpuss.put("requteMap", requteMap);
		TFIDF = calculator.TFIDFMap(Corpuss);
		System.out.println("Matrice TFIDF :");
		for (String key : TFIDF.keySet())
		{
			System.out.println(key + " " + TFIDF.get(key));
		}
		
		Distance dis = new Distance();
		for (String key : TFIDF.keySet())
		{
			if (key != "requteMap")
				{
					distance = dis.calculateCosineSimilarity(TFIDF.get("requteMap"), TFIDF.get(key));
					System.out.println("Distance entre " + key + " et requete :" + distance);
				}
		}
	}
}