package NLP;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.FormatterClosedException;
import java.util.List;
import java.util.NoSuchElementException;
import safar.basic.morphology.stemmer.impl.KhojaStemmer;


public class Read_File
{
	public static ArrayList Lire_fichier(String path) throws IOException
	{
		ArrayList liste = new ArrayList();
		FileReader fr = new FileReader(path);
		BufferedReader br = new BufferedReader(fr);
		String sCurrentLine;
		while ((sCurrentLine = br.readLine()) != null)
		{
			ArrayList<String> tmp = new ArrayList<>(Arrays.asList(sCurrentLine.split(" ")));
			liste.addAll(tmp);
		}
		if (br != null)
		{
			br.close();
		}
		if (fr != null)
		{
			fr.close();
		}
		return liste;
	}
	
	public static List<String> SupprimerPoctuation(List<String> Documenet)
	{
		String text = "";
		String poctuation = "\\p{Punct}";
		String resultat;
		for (String s : Documenet)
		{
			text += s + "\t";
		}
		StringBuffer sb = new StringBuffer();
		for (String s : text.split(poctuation))
			sb.append(s);
		resultat = sb.toString();
		List<String> myList = new ArrayList<String>(Arrays.asList(resultat.split("\\s")));
		return myList;
	}
	
	public static List<String> SupprimLent(List<String> document)
	{
		String val;
		for (int i = 0; i < document.size(); i++)
		{
			val = document.get(i);
			if (val.length() < 2)
			{
				document.remove(i);
			}
		}
		return document;
	}
	
	public static List<String> PocStop(List<String> tokens)
	{
		KhojaStemmer mystemmer = new KhojaStemmer();
		ArrayList<String> lineout = new ArrayList<String>();

		try {
			for (String token : tokens)
			{
				String result = mystemmer.stem(token).get(0).getListStemmerAnalysis().get(0).getMorpheme();
				lineout.add(result);
			}
		}
		catch (NoSuchElementException excp)
		{
			System.err.println(excp.getMessage());
		} catch (FormatterClosedException excp)
		{
			System.err.println(excp.getMessage());
		}
		catch (IllegalStateException excp)
		{
			System.err.println(excp.getMessage());
		}
		return lineout;
	}
}