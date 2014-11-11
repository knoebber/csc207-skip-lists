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
  public final int MAX_LEVEL = 19;
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
  public SkipList(double prob)
  {
    this.header = new Node();
    this.header.levels.add(0, new NodeLevel(null, null));
    this.prob = prob; //the probability that determines height

  }//SkipList constructor

  public SkipList()
  {
    this(.5);
  }

  // +-------------------------+-----------------------------------------
  // | Internal Helper Methods |
  // +-------------------------+
  public int randomLevel() //returns a new level between 0 and 19
  {
    int newLevel = 0;
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
    //System.out.println("***** val: " + val);
    Node newNode = new Node();
    newNode.val = val; //set the node value

    int newLevel = randomLevel();
    //System.out.println("newLevel: " + newLevel);
    for (int i = 0; i <= newLevel; i++)
      {
        newNode.levels.add(i, new NodeLevel(newNode, null)); //sets newNodes pointers to null
      }
    //if (length() == 0)
    //header.levels.set(0, new NodeLevel(header, newNode.levels.get(0)));
    int level = header.levels.size() - 1;
    if (level < newLevel) //connect the header if new is higher
      for (int i = header.levels.size(); i <= newLevel; i++)
        //header.levels.add(i, newNode.levels.get(i));
        header.levels.add(i, new NodeLevel(header, newNode.levels.get(i)));

    //go through the rest of the list and make connections
    Node currentNode = header;
    //int level = header.levels.size() - 1;
    NodeLevel currentLevel;
    //NodeLevel currentLevel = header.levels.get(header.levels.size() - 1);

    while (true)
      {
        //System.out.println("currentNode val= " + currentNode.val);
        currentLevel = currentNode.levels.get(level);
       //System.out.println("current level: " + level);
        //System.out.println("next node: "+ currentLevel.next.ownNode);

        if (currentLevel.next != null) //move sideways
          {
            currentNode = currentLevel.next.ownNode;
          }
        if (currentLevel.next == null)
          {
            if (level <= newLevel)
              {
                currentLevel.next = newNode.levels.get(level); //set the current level to point to newNode
                currentLevel.next.ownNode = newNode;
                if (level == 0)
                  {
                    break;
                  }
              }
            level--;
          }//if next==null
        //done=level<=0 && currentNode==newNode;
      }

  } // add(T val)

  /**
   * Determine if the set contains a particular value.
   */
  public boolean contains(T val)
  {
    Node currentNode = header;

    int level = header.levels.size() - 1;
    NodeLevel currentLevel = header.levels.get(level);
    while (true)
      {
        currentLevel = currentNode.levels.get(level);
        //System.out.println("checking value: "+currentLevel.next.ownNode.val);
        if (currentLevel.next != null)
          {
            int comparison = currentLevel.next.ownNode.val.compareTo(val);
            if (comparison < 0)
              {
                currentNode = currentLevel.next.ownNode;
              }//if next<val
            if (comparison > 0)
              {
                if (level == 0)
                  {
                    return false;
                  } // if at the end
                level--;
              }//if next>val
            if (comparison == 0)
              {
                return true;
              }//if next==val
          } // if next exists
        else
          {
            if (level == 0)
              {
                return false;
              } // if at the end
            level--;//move down
          } // else
      } // while
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
    Node currentNode = header;
    // val?
    int level = header.levels.size() - 1;
    NodeLevel currentLevel = header.levels.get(level);
    while (true)
      {
        currentLevel = currentNode.levels.get(level);
        if (currentLevel.next != null)
          {
            int comparison = currentLevel.next.ownNode.val.compareTo(val);
            if (comparison < 0)
              {
                currentNode = currentLevel.next.ownNode;
                System.out.println("going sideways");
              }//if next<val
            if (comparison > 0)
              {
                if (level == 0)
                  {
                    break;
                  } // if at the end
                level--;
                System.out.println("moving down");
              }//if next>0
            if (comparison == 0)
              {
                //change pt
                if (currentLevel.next.next != null)
                  {
                    currentNode.levels.get(level).next = currentLevel.next.next;
                    //System.out.println("connecting at level "+level+": "+currentNode.val+" to "+currentLevel.next.next.ownNode.val);
                  }
                else
                  {
                    currentNode.levels.get(level).next = null;
                    System.out.println("connecting at level "+level+" to null");
                  }
                if (level == 0)
                  {
                    break;
                  } // if at the end
                level--;//move down
              }//if next==0
          } // if next exists
        else
          {
            if (level == 0)
              {
                break;
              } // if at the end
            level--;//move down
          } // else
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
    Iterator iter = iterator();
    for(int j = 0; j < i; j++)
      {
        iter.next();
      }
    return (T) iter.next();
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
