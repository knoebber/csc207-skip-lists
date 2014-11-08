package taojava.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * A randomized implementation of sorted lists.  
 * 
 * @author Samuel A. Rebelsky
 * @author Your Name Here
 */
public class SkipList<T extends Comparable<T>>
    implements SortedList<T>
{
  // +--------+----------------------------------------------------------
  // | Fields |
  // +--------+
  public final int MAX_LEVEL = 20;
  Node header;
  double prob;

  //Iterator iter;

  // +------------------+------------------------------------------------
  // | Internal Classes |
  // +------------------+

  /**
   * Nodes for skip lists.
   */
  public class Node
  {
    // +--------+--------------------------------------------------------
    // | Fields |
    // +--------+

    /**
     * The value stored in the node.
     */
    T val;
    ArrayList<NodeLevel> levels;

    public Node()
    {
      levels = new ArrayList<NodeLevel>();
    }
  } // class Node

  public class NodeLevel
  {
    NodeLevel next;
    Node ownNode;

    public NodeLevel(Node ownNode, NodeLevel next)
    {
      this.ownNode = ownNode;
      this.next = next;
    }
  } // class Node

  // +--------------+----------------------------------------------------
  // | Constructors |
  // +--------------+
  public SkipList()
  {
    this.header = new Node();
    this.header.levels.add(0, new NodeLevel(null, null));
    this.prob = .5; //the probability that determines height

  }//SkipList constructor

  // +-------------------------+-----------------------------------------
  // | Internal Helper Methods |
  // +-------------------------+
  public int randomLevel()
  {
    int newLevel = 1;
    Random rand = new Random();
    while (rand.nextDouble() < prob)
      newLevel++;
    return Math.min(newLevel, MAX_LEVEL);
  }

  // +-----------------------+-------------------------------------------
  // | Methods from Iterable |
  // +-----------------------+

  /**
   * Return a read-only iterator (one that does not implement the remove
   * method) that iterates the values of the list from smallest to
   * largest.
   */
  public Iterator<T> iterator()
  {
    // An underlying iterator.
    return new Iterator<T>()
      {
        NodeLevel cursor = header.levels.get(0);

        public T next()
        {
          cursor = cursor.next;
          return cursor.ownNode.val;
        } // next()

        public boolean hasNext()
        {
          return cursor.next != null;
        } // hasNext()

      }; // new Iterator<T>
  } // iterator()

  public void printConnections()
  {

  }

  // +------------------------+------------------------------------------
  // | Methods from SimpleSet |
  // +------------------------+

  /**
   * Add a value to the set.
   *
   * @post contains(val)
   * @post For all lav != val, if contains(lav) held before the call
   *   to add, contains(lav) continues to hold.
   */

  public void add(T val)
  {
    System.out.println("***** val: " + val);
    System.out.println("current length "+length());
    Node newNode = new Node();
    newNode.val = val; //set the node value

    int newLevel = randomLevel();
    System.out.println("newLevel: " + newLevel);
    for (int i = 0; i < newLevel; i++)
      {
        newNode.levels.add(i, new NodeLevel(newNode, null)); //sets newNodes pointers to null
      }
    if (length() == 0)
      header.levels.set(0, new NodeLevel(header, newNode.levels.get(0)));

    if (header.levels.size() < newLevel) //connect the header if new is higher
      for (int i = header.levels.size(); i < newLevel; i++)
        //header.levels.add(i, newNode.levels.get(i));
        header.levels.add(i, new NodeLevel(header, newNode.levels.get(i)));

    //go through the rest of the list and make connections
    Node currentNode = header;
    int level = header.levels.size() - 1;
    NodeLevel currentLevel;
    //NodeLevel currentLevel = header.levels.get(header.levels.size() - 1);
    /*
    while (currentNode != newNode && level >= 0)
      {
        currentLevel = currentNode.levels.get(level);
        if (currentLevel.next != null) //move sideways
          {
            System.out.println("moving sideways");
            currentNode = currentLevel.next.ownNode;
          }
        if (currentLevel.next == null)
          {
            if (level < newLevel)
              currentLevel.next = newNode.levels.get(level);
            
            if(level==0)
              currentLevel.next=newNode.levels.get(0);
            
            System.out.println("moving down from " + level + " to "
                               + (level - 1));
            level--;
          }//if next==null
      }
*/

  } // add(T val)

  /**
   * Determine if the set contains a particular value.
   */
  public boolean contains(T val)
  {
    Node currentNode = header;
    if (currentNode.val != val)
      {
        currentNode = currentNode.levels.get(0).next.ownNode;
      } // if
    else
      {
        return true;
      }
    return false;
  } // contains(T)

  /**
   * Remove an element from the set.
   *
   * @post !contains(val)
   * @post For all lav != val, if contains(lav) held before the call
   *   to remove, contains(lav) continues to hold.
   */
  public void remove(T val)
  {
    if (contains(val))
      {

      }
  } // remove(T)

  // +--------------------------+----------------------------------------
  // | Methods from SemiIndexed |
  // +--------------------------+

  /**
   * Get the element at index i.
   *
   * @throws IndexOutOfBoundsException
   *   if the index is out of range (index < 0 || index >= length)
   */
  public T get(int i)
  {
    // STUB
    return null;
  } // get(int)

  /**
   * Determine the number of elements in the collection.
   */
  public int length()
  {
    int length = 0;
    Iterator<T> iter = iterator();
    while (iter.hasNext())
      {
        length++;
        iter.next();
      } // while
    return length;
  } // length()

} // class SkipList<T>
