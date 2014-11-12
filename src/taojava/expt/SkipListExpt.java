package taojava.expt;

import java.io.PrintWriter;
import java.util.ArrayList;

import taojava.util.SkipList;

/**
 * An experiment with SkipLists.
 * 
 * @author Samuel A. Rebelsky
 */
public class SkipListExpt
{
  public static void main(String[] args)
    throws Exception
  {
  
    PrintWriter pen = new PrintWriter(System.out, true);
    //ArrayList test = new ArrayList<String>();
    SkipList<Integer> ints = new SkipList<Integer>();
    SkipList<String> strings=new SkipList<String>();
    //strings.add("alphabetical");
    //strings.add("alphabetical");
    //strings.remove("alphabetical");
    //pen.println(strings.contains("alphabetical"));
//    skip.add(6);
//    skip.add(3);
//    skip.add(1);
//    skip.add(2);


    int val = 128;
    ints.add(val);
    ints.add(val);
    ints.remove(val);
    pen.println(ints.contains(val));
    //pen.println(ints.get(0));

    
//    pen.println(skip.get(0));
//    pen.println(skip.get(1));
//    pen.println(skip.get(2));
    
    //pen.println("length: "+s.length());
    
    
    //SortedListExpt.stringExperiment(pen, new SkipList<String>());
   
    pen.flush();
  } // main(String[])
} // class SkipListExpt

