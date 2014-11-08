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
      levels= new ArrayList<NodeLevel>();
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
    this.header=new Node();
    this.header.levels.add(null);
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
        Node cursor = header;

        public T next()
        {
          //cursor = cursor.levels.get(0);
          return cursor.val;
        } // next()

        public boolean hasNext()
        {
          return cursor.levels.get(0) != null;
        } // hasNext()

      }; // new Iterator<T>
  } // iterator()

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
    Node newNode = new Node();
    newNode.val = val; //set the node value

    int newLevel = randomLevel();
    for (int i = 0; i < newLevel; i++)
      {
        newNode.levels.add(i, new NodeLevel(newNode, null)); //sets newNodes pointers to null
      }

    if (header.levels.size() < newLevel) //connect the header if new is higher
      for (int i = header.levels.size(); i < newLevel; i++)
        header.levels.set(i, newNode.levels.get(i));

    //go through the rest of the list and make connections
    Node currentNode = header;
    while (currentNode != newNode)
      {
        for (int i = 0; i < currentNode.levels.size(); i++)
          {
            if (currentNode.levels.get(i) == null)
              {
                currentNode.levels.get(i).next = newNode.levels.get(i);
              } // if
          } // for
      } // while
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
    // STUB
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
    // STUB
    return 0;
  } // length()

} // class SkipList<T>
