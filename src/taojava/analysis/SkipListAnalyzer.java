package taojava.analysis;

import java.io.PrintWriter;

import taojava.util.SkipList;

/**
 * Quick and dirty analysis of SkipLists.
 *
 * @author Samuel A. Rebelsky
 */
public class SkipListAnalyzer
{
  /**
   * An analysis of a SkipList<Integer>:
   * Note: all of the times mentioned in the following analysis are averages of the 7
   * categories of testing (add/1, index, iterate, add/2, rem/1, rem/2, total).
   * 
   * A typical iteration of the testing suite results in the following averages
   * corresponding to the six categories listed:
   * Average:        39    7865       1      52      25      10    7987
   * From these results, we see that indexing by traversing the bottom level
   * of a skip list is extremely slow (7865 milliseconds). We recognize that the
   * indexing will be faster in other implementations and that our skip list is not 
   * to use our inefficient indexing method. Our total, taking the 
   * indexing out of account, is 137 milliseconds. Therefore our total timing is actually
   * significantly less than the totals of the Sorted Array Testing and Linked List
   * Testing (353 and 1607 milliseconds respectively).Overall, SkipLists 
   * appear to be more efficient than normal linked lists. Removal is slightly faster in
   * a linked list due to proper indexing and simplicity of node changes. Compared to
   * skip lists, the array list is also near perfect in its implementation of indexing
   * and iterations due to the inherent indexes in the array list structure.
   * 
   * Taking this analysis into consideration one would use a skip list if the program
   * did not necessitate the use of indexing. If it did, however, the array list
   * structure would be significantly more efficient compared to a rudimentary indexing
   * implementation. Removal will be slightly more efficient using skip lists than array
   * lists, however both are beaten out by the node structure of a linked list. 
   * Therefore if the program needed to be able to remove elements frequently, a linked
   * list would be most efficient.
   * 
   */
  public static void main(String[] args)
  {
    PrintWriter pen = new PrintWriter(System.out, true);
    SortedListAnalyzer.analyze(pen, new SkipList<Integer>(), 32000, 20);
    pen.close();
  } // main(String[])

} // SkipListAnalyzer
