package taojava.analysis;

import java.io.PrintWriter;

import taojava.util.SortedArrayList;

/**
 * Quick and dirty analysis of SortedArrayLists.
 *
 * @author Samuel A. Rebelsky
 */
public class SortedArrayListAnalyzer
{

  public static void main(String[] args)
  {
    PrintWriter pen = new PrintWriter(System.out, true);
    SortedListAnalyzer.analyze(pen, new SortedArrayList<Integer>(), 32000, 20);
    pen.close();
    //Average        42       0       0     114     194      61     353
  } // main(String[])

} // SortedArrayListAnalyzer
