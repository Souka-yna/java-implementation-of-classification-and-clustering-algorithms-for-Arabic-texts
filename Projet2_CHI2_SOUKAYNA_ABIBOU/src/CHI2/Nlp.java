package CHI2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import safar.basic.morphology.stemmer.impl.KhojaStemmer;

public class Nlp
{
	private Map<String, Integer> stopWords = new HashMap<>();
	private String pathStopWords;
	
	public Nlp(String pathStopWords) throws FileNotFoundException
	{
		this.pathStopWords = pathStopWords;
		this.stopWords();
	}

	private void stopWords() throws FileNotFoundException
	{
		// TODO Auto-generated method stub
		String path = this.pathStopWords;
		File file = new File(path);
		Scanner reader = new Scanner(file);
		String word = null;
		while(reader.hasNext())
		{
			word = reader.next();
			this.stopWords.put(word, 1);
		}
		reader.close();
	}
	
	public String arabicStemming(String s)
	{
		KhojaStemmer stemm = new KhojaStemmer();
		s = stemm.stem(s).get(0).getListStemmerAnalysis().get(0).getMorpheme();
		return s;
	}
	
	public boolean isStopWord(String word)
	{
		if(this.stopWords.containsKey(word))
			return true;
		return false;
	}

	public String getPathStopWords()
	{
		return pathStopWords;
	}

	public void setPathStopWords(String pathStopWords)
	{
		this.pathStopWords = pathStopWords;
	}
	
}
