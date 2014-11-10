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
//    SkipList<Integer> skip = new SkipList<Integer>(.8);
//    skip.add(0);
//    skip.add(3);
//    skip.add(7);
//    skip.add(9);
//    pen.println(skip.contains(7));
//    skip.remove(7);
//    pen.println(skip.contains(7));
//    pen.println("get 2nd: "+skip.get(1));
//    pen.println("length: "+skip.length());
    //pen.println(skip.contains(0));
    SortedListExpt.stringExperiment(pen, new SkipList<String>());
   
    pen.flush();
  } // main(String[])
} // class SkipListExpt

