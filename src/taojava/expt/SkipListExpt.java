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
    SortedListExpt.stringExperiment(pen, new SkipList<String>());
   
    pen.flush();
  } // main(String[])
} // class SkipListExpt

