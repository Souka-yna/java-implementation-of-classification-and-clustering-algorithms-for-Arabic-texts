package CHI2;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class Chi2
{
  
  Map<String, Map<String, Integer>> bigMap;
  Map<String, String> termClass = new HashMap<>();
  Map<String, Integer> nbrDocClass;
  private int nbrDoc;
  Nlp nlp;
  
  public Chi2() throws FileNotFoundException
  {
    MapFiles m = new MapFiles();
    m.addCorpus("Source/القضاء العسكري",1);
        m.addCorpus("Source/تنفيذ الدستور",2);
        m.addCorpus("Source/حماية المستهلك",3);
        bigMap = m.getBigMap();
        nbrDocClass = m.getClassNum();
        //m.printBigMap();
        sum(nbrDocClass);
        String pathStopWords = "Source/stopwords_ar.txt";
        nlp = new Nlp(pathStopWords);
  }

  public void loop(String words)
  {
    float chi2Value = 0 ;
    float maxValueChi2 = 0;
    String classOfMaxValue = null;
    boolean firstTime;
    for(String term : words.split(" "))
    {
      firstTime = true;
      term = nlp.arabicStemming(term);
      for(String className : nbrDocClass.keySet())
      {
        chi2Value = chi_2(term, className);
        System.out.println(className+" = "+chi2Value);
        if(chi2Value > maxValueChi2 || firstTime)
        {
          maxValueChi2 = chi2Value;
          classOfMaxValue = className;
          firstTime = false;
        }
      }
      termClass.put(term, classOfMaxValue);
      System.out.println(term+" = "+classOfMaxValue);
    }
  }
  
  public Map<String, Map<String, Double>> loop_2(Map<String, Map<String, Integer>> corpus)
  {
    Map<String, Map<String, Double>> new_corpus = new HashMap<>();
    float chi2Value = 0 ;
    float maxValueChi2 = 0;
    String classOfMaxValue = null;
    boolean firstTime;
    for(String key: corpus.keySet())
    {
      HashMap<String, Double> val = new HashMap();
      for(String c: corpus.get(key).keySet())
      {
        firstTime = true;
        for(String className: nbrDocClass.keySet())
        {
          chi2Value = chi_2(c, className);
          if(chi2Value > maxValueChi2 || firstTime)
          {
            maxValueChi2 = chi2Value;
            classOfMaxValue = className;
            firstTime = false;
          }
          if(maxValueChi2>0.5)
          {
            val.put(c,(double) maxValueChi2);
          }
        }
        new_corpus.put(key, val);
      }
    }
    return new_corpus;
  }

  private float chi_2(String term, String className)
  {
    // TODO Auto-generated method stub
    int a = 0;
    int b = 0;
    int d = 0;
    for(String document : bigMap.keySet())
    {
      if(document.substring(0, 2).equals(className))
      {
        if(bigMap.get(document).containsKey(term))
        {
          a++;
        }
      }
      else
      {
        if(bigMap.get(document).containsKey(term))
        {
          b++;
        }
        else
        {
          d++;
        }
      }
    }
    return calculChi_2(a,b,nbrDocClass.get(className)-a,d);
  }

private float calculChi_2(int a, int b, int i, int c)
{
    // TODO Auto-generated method stub
    return (float) ((this.nbrDoc*Math.pow(a*c -i*b, 2))/((a+b)*(a+i)*(c+b)*(c+i)));
  }
  
  private void sum(Map<String, Integer> nbrDocClass2)
  {
    // TODO Auto-generated method stub
    int somme = 0;
    for(int value : nbrDocClass.values())
    {
      somme += value;
    }
    this.nbrDoc = somme;
  }
  
  public void afficher1(Map<String, Map<String, Integer>> corpus)
  {
    int i = 0;
    for(String key:corpus.keySet())
    {
      for(String c: corpus.get(key).keySet())
      {
          i++;
      }
    }
    System.out.println("il y a "+i+" terme dans le corpus");
    
  }
  
  public void afficher2(Map<String, Map<String, Double>> corpus)
  {
    int i = 0;
    for(String key:corpus.keySet())
    {
      for(String c: corpus.get(key).keySet())
      {
          i++;
      }
    }
    System.out.println("il y a "+i+" terme dans le corpus");
  }

  public static void main(String[] args) throws FileNotFoundException
  {
    // TODO Auto-generated method stub
    Map<String, Map<String, Double>> map = new HashMap<>();
    Chi2 chi2 = new Chi2();
    map = chi2.loop_2(chi2.bigMap);
    System.out.println("****** Avant : ******");
    chi2.afficher1(chi2.bigMap);
    System.out.println("****** Apres : ******");
    chi2.afficher2(map);
  }

  private void printTermValue() {
    // TODO Auto-generated method stub
    System.out.println(termClass.entrySet());
  }
}