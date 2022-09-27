package CHI2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MapFiles
{
	private Nlp nlp;
	private String corpusPath;
	
	private Map<String, Map<String, Integer>> bigMap = new HashMap<>();
	private Map<String, Integer> classNum = new HashMap<>();
	
	public MapFiles() throws FileNotFoundException
	{
		String stopWordsPath = "Source/stopwords_ar.txt";
		nlp = new Nlp(stopWordsPath);
	}
	
	public MapFiles(String corpusPath, String stopWordsPath) throws FileNotFoundException
	{
		this.corpusPath = corpusPath;
		nlp = new Nlp(stopWordsPath);
	}
	
	public void printBigMap()
	{
		System.out.println(bigMap);
	}
	
	public Map<String, Integer> FileMap(Path file) throws FileNotFoundException
	{
		File fichier = new File(corpusPath + "\\" + file.getFileName());
		Scanner reader = new Scanner(fichier);
		Map<String, Integer> hmap = new HashMap<>();
		while(reader.hasNext())
		{
			String word = reader.next().toLowerCase();
			word = this.nlp.arabicStemming(word);
			// elimination of stop words and numrical words && !word.matches("[0-9]+%")
			if(!this.nlp.isStopWord(word))
			{
				//get the stemmer of the world
				if(hmap.containsKey(word))
				{
					hmap.replace(word, hmap.get(word)+1);
				}
				else
				{
					hmap.put(word, 1);
				}
			}
		}
		reader.close();
		return hmap;
	}
	
	public Map<String, Integer> getClassNum()
	{
		return classNum;
	}
	
	public Map<String, Map<String, Integer>> getBigMap()
	{
		return bigMap;
	}
	
	public void addCorpus(String pathCorpus, int i)
	{
		this.corpusPath = pathCorpus;
		int documentCounter = 0;
		try(DirectoryStream<Path> strm = Files.newDirectoryStream(Paths.get(pathCorpus)))
		{
			for(Path file : strm)
			{
				documentCounter++;
				Map<String, Integer> map = this.FileMap(file);
				bigMap.put("c"+i+" "+file.getFileName(), map);
			}
		}
		catch(IOException | DirectoryIteratorException e)
		{
			System.err.println(e);
		}
		classNum.put("c"+i, documentCounter);
	}
	
	public static void main(String[] args) throws IOException
	{
		MapFiles m = new MapFiles();
		System.out.println("start...");
		m.addCorpus("Source/القضاء العسكري",1);
		System.out.println("1 est termine...");
        m.addCorpus("Source/تنفيذ الدستور",2);
        System.out.println("2 est termine...");
        m.addCorpus("Source/حماية المستهلك",3);
        System.out.println("3 est termine...");
		m.printBigMap();
		//m.printNombreDocClass();
	}

	private void printNombreDocClass()
	{
		// TODO Auto-generated method stub
		System.out.println(classNum.entrySet());
	}
}