package taojava.expt;

import java.io.PrintWriter;
import java.util.ArrayList;

import taojava.util.SkipList;
import taojava.util.SortedList;

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
    
    SortedList<Integer> ints =
        new VerboseSortedList<Integer>(new SkipList<Integer>(), pen, "ints");
    SortedList<String> strings =
        new VerboseSortedList<String>(new SkipList<String>(), pen, "skip");



    SortedListExpt.stringExperiment(pen, new SkipList<String>());

    pen.flush();
  } // main(String[])
} // class SkipListExpt

