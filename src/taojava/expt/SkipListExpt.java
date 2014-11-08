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
    SkipList<Integer> skip = new SkipList<Integer>();
    skip.add(0);
    skip.add(1);
    skip.add(2);
    skip.add(3);
    skip.add(4);
    pen.println("length: "+skip.length());
    //pen.println(skip.contains(0));
    //SortedListExpt.stringExperiment(pen, new SkipList<String>());
   
    pen.flush();
  } // main(String[])
} // class SkipListExpt

