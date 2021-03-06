package taojava.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import static org.junit.Assert.*;

import org.junit.Test;

import taojava.util.SortedList;

/**
 * Generic tests of sorted lists.
 *
 * To test a particular implementation of sorted lists, subclass this
 * class and add an appropriate @Before clause to fill in strings and
 * ints.
 * 
 * @author Samuel A. Rebelsky
 */
public class SortedListTest
{
  // +--------+----------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * A sorted list of strings for tests.  (Gets set by the subclasses.)
   */
  SortedList<String> strings;

  /**
   * A sorted list of integers for tests.  (Gets set by the subclasses.)
   */
  SortedList<Integer> ints;

  /**
   * A random number generator for the randomized tests.
   */
  Random random = new Random();

  // +---------+---------------------------------------------------------
  // | Helpers |
  // +---------+

  /**
   * Dump a SortedList to stderr.
   */
  static <T extends Comparable<T>> void dump(SortedList<T> slist)
  {
    System.err.print("[");
    for (T val : slist)
      {
        System.err.print(val + " ");
      } // for
    System.err.println("]");
  } // dump

  /**
   * Determine if an iterator only returns values in non-decreasing
   * order.
   */
  static <T extends Comparable<T>> boolean inOrder(Iterator<T> it)
  {
    // Simple case: The empty iterator is in order.
    if (!it.hasNext())
      return true;
    // Otherwise, we need to compare neighboring elements, so
    // grab the first element.
    T current = it.next();
    // Step through the remaining elements
    while (it.hasNext())
      {
        // Get the next element
        T next = it.next();
        // Verify that the current node <= next
        if (current.compareTo(next) > 0)
          {
            return false;
          } // if (current > next)
        // Update the current node
        current = next;
      } // while
    // If we've made it this far, everything is in order
    return true;
  } // inOrder(Iterator<T> it)

  // +-------------+-----------------------------------------------------
  // | Basic Tests |
  // +-------------+

  /**
   * A really simple test.  Add an element and make sure that it's there.
   */
  @Test
  public void simpleTest()
  {
    strings.add("hello");
    assertTrue(strings.contains("hello"));
    assertFalse(strings.contains("goodbye"));
  } // simpleTest()

  /**
   * Another simple test.  The list should not contain anything when
   * we start out.
   */
  @Test
  public void emptyTest()
  {
    assertFalse(strings.contains("hello"));
  } // emptyTest()

  @Test
  public void checkLength()
  {
    assertEquals(0, strings.length());
    strings.add("apple");
    strings.add("can");
    assertEquals(2, strings.length());
    for (int i = 0; i < 100; i++)
      ints.add(i);
    
    assertEquals(100, ints.length());
    ints.remove(99);
    assertEquals(99, ints.length());
  }//checkLength

  /**
   * Tests if a list is empty after adding elements and removing
   * all of the elements not in order
   */
  @Test
  public void testEmpty()
  {
    strings.add("a");
    strings.add("b");
    strings.add("d");
    strings.add("z");

    strings.remove("d");
    strings.remove("z");
    strings.remove("a");
    strings.remove("b");
    assertEquals(0, strings.length());
  }//testEmpty

  @Test
  public void testGet()
  {
    ints.add(1);
    ints.add(0);
    ints.add(2);
    ints.add(3);
    int result;
    for (int i = 0; i < 4; i++)
      {
        result = ints.get(i);
        assertEquals(i, result);
      }//for

  }//testGet

  /**
   * Test if remove removes multiples of a value
   */
  @Test
  public void removeMultiples()
  {
    ints.add(1);
    ints.add(1);
    ints.add(1);
    ints.add(3);
    ints.remove(1);
    assertFalse(ints.contains(1));
  }//removeMultiples

  /**
   * This was actually a problem that we had. 
   */
  @Test
  public void testLargeIntDuplicateRemove()
  {
    int val = 128;
    ints.add(val);
    ints.add(val);
    ints.remove(val);
    assertFalse(ints.contains(128));
  }//testLargeIntDuplicateRemove
  
  @Test
  public void testAllOperations()
  {
    for(int i=0;i<1000;i++)
      {
        ints.add(i);
        assertEquals(ints.length(),i+1);
        assertTrue(ints.contains(i));
        assertEquals(ints.get(i),(Object) i);
      }
    Iterator iter=ints.iterator();
    Integer val;
    while(iter.hasNext())
      {
        val=(Integer) iter.next(); 
        if(iter.hasNext())
          assertTrue(val<(Integer)iter.next());
        iter.remove();
      }
    
  }//testLargeIntDuplicateRemove
  
  

  /**
   * Tests the iterator.
   */
  @Test
  public void testIterator()
  {
    
    strings.add("cat");
    strings.add("bat");
    strings.add("computer");
    strings.add("dinner");
    strings.add("phone");
    Iterator iter=strings.iterator();
    assertEquals("bat",iter.next());
    assertEquals("cat",iter.next());
    assertEquals("computer",iter.next());
    assertEquals("dinner",iter.next());
    assertEquals("phone",iter.next());
    assertFalse(iter.hasNext());
   
  }//testIterator

  // +-----------------+-------------------------------------------------
  // | RandomizedTests |
  // +-----------------+

  /**
   * Verify that a randomly created list is sorted.
   */
  @Test
  public void testOrdered()
  {
    // For reporting errors: an array of operations
    ArrayList<String> operations = new ArrayList<String>();
    // Add a bunch of values
    for (int i = 0; i < 100; i++)
      {
        int rand = random.nextInt(1000);
        ints.add(rand);
        operations.add("ints.add(" + rand + ")");
      } // for
    if (!inOrder(ints.iterator()))
      {
        System.err.println("inOrder() failed");
        for (String op : operations)
          System.err.println(op + ";");
        dump(ints);
        fail("The instructions did not produce a sorted list.");
      } // if the elements are not in order.
  } // testOrdered()

  /**
   * Verify that a randomly created list contains all the values
   * we added to the list.
   */
  @Test
  public void testContainsOnlyAdd()
  {
    ArrayList<String> operations = new ArrayList<String>();
    ArrayList<Integer> vals = new ArrayList<Integer>();
    // Add a bunch of values
    for (int i = 0; i < 100; i++)
      {
        int rand = random.nextInt(200);
        vals.add(rand);
        operations.add("ints.add(" + rand + ")");
        ints.add(rand);
      } // for i
    // Make sure that they are all there.
    for (Integer val : vals)
      {
        if (!ints.contains(val))
          {
            System.err.println("contains(" + val + ") failed");
            for (String op : operations)
              System.err.println(op + ";");
            dump(ints);
            fail(val + " is not in the sortedlist");
          } // if (!ints.contains(val))
      } // for val
  } // testContainsOnlyAdd()

  /**
   * An extensive randomized test.
   */
  @Test
  public void randomTest()
  {
    // Set up a list of all the operations we performed.  (That way,
    // we can replay a failed test.)
    ArrayList<String> operations = new ArrayList<String>();
    // Keep track of the values that are currently in the sorted list.
    ArrayList<Integer> vals = new ArrayList<Integer>();

    // Add a bunch of values
    for (int i = 0; i < 1000; i++)
      {
        boolean ok = true;
        int rand = random.nextInt(2000);
        // Half the time we add
        if (random.nextBoolean())
          {
            if (!ints.contains(rand))
              vals.add(rand);
            operations.add("ints.add(" + rand + ")");
            ints.add(rand);
            if (!ints.contains(rand))
              {
                System.err.println("After adding " + rand + " contains fails");
                ok = false;
              } // if (!ints.contains(rand))
          } // if we add
        // Half the time we remove
        else
          {
            operations.add("ints.remove(" + rand + ")");
            ints.remove(rand);
            vals.remove((Integer) rand);
            if (ints.contains(rand))
              {
                System.err.println("After removing " + rand
                                   + " contains succeeds");
                ok = false;
              } // if ints.contains(rand)
          } // if we remove
        // See if all of the appropriate elements are still there
        for (Integer val : vals)
          {
            if (!ints.contains(val))
              {
                System.err.println("ints no longer contains " + val);
                ok = false;
                break;
              } // if the value is no longer contained
          } // for each value
        // Dump the instructions if we've encountered an error
        if (!ok)
          {
            for (String op : operations)
              System.err.println(op + ";");
            dump(ints);
            fail("Operations failed");
          } // if (!ok)
      } // for i
  } // randomTest()
} // class SortedListTest
