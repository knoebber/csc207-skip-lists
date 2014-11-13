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
  int length;

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
    }//public Node
  } // class Node

  public class NodeLevel
  {
    NodeLevel next;
    Node ownNode;

    public NodeLevel(Node ownNode, NodeLevel next)
    {
      this.ownNode = ownNode;
      this.next = next;
    }//public NodeLevel

  } // class NodeLevel

  // +--------------+----------------------------------------------------
  // | Constructors |
  // +--------------+
  public SkipList(double prob)
  {
    this.header = new Node();
    this.header.levels.add(0, new NodeLevel(null, null));
    this.prob = prob; //the probability that determines height
    this.length = 0;

  }//SkipList constructor

  public SkipList()
  {
    this(.5);
  }//public SkipList

  // +-------------------------+-----------------------------------------
  // | Internal Helper Methods |
  // +-------------------------+
  /**
   * 
   * @return a new level between 0 and 19 dependent on prob
   */
  public int randomLevel()
  {
    int newLevel = 0;
    Random rand = new Random();
    while (rand.nextDouble() < prob)
      newLevel++;
    return Math.min(newLevel, MAX_LEVEL);
  }//randomLevel

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

        //start the iterator at the bottom of header
        public T next()
        {
          cursor = cursor.next;
          return cursor.ownNode.val;
        } // next()

        public boolean hasNext()
        {
          return cursor.next != null;
        } // hasNext()

        public void remove()
        {
          if (hasNext())
            {
              T element = cursor.next.ownNode.val;
              SkipList.this.remove(element);
            }// if hasNext
        }//remove

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
    for (int i = 0; i <= newLevel; i++)
      newNode.levels.add(i, new NodeLevel(newNode, null));

    int level = header.levels.size() - 1;

    if (level < newLevel) //connect the header if new is higher
      for (int i = header.levels.size(); i <= newLevel; i++)
        header.levels.add(i, new NodeLevel(header, newNode.levels.get(i)));

    //go through the rest of the list and make connections
    Node currentNode = header;
    NodeLevel currentLevel;
    while (true)
      {
        currentLevel = currentNode.levels.get(level);
        if (currentLevel.next != null)
          {
            int comparison = currentLevel.next.ownNode.val.compareTo(val);
            if (comparison > 0)
              {
                if (level <= newLevel)
                  {
                    //connect current to new and new to old
                    newNode.levels.get(level).next = currentLevel.next;
                    currentLevel.next = newNode.levels.get(level);
                  }
                if (level == 0)
                  {
                    break;
                  } // if at the bottom
                //move down
                level--;
              }
            else
              // if the value in the next node is <= val
              {
                //move sideways 
                currentNode = currentLevel.next.ownNode;
              }//else
          }//if next != null
        else
          {
            //if next is null
            if (level <= newLevel)
              {
                // connect current to new and new to null
                newNode.levels.get(level).next = null;
                currentLevel.next = newNode.levels.get(level);
              }
            if (level == 0)
              {
                break;
              } // if at the bottom
            //move down
            level--;
          }//else
      }//while
    length++;
  } // add(T val)

  /**
   * Determine if the set contains a particular value.
   */
  public boolean contains(T val)
  {

    Node currentNode = header;

    int level = header.levels.size() - 1; //start at the top 
    NodeLevel currentLevel = header.levels.get(level);

    while (true) //go until we find it or we reach the end
      {
        currentLevel = currentNode.levels.get(level);

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
    int level = header.levels.size() - 1;
    NodeLevel currentLevel = header.levels.get(level);
    while (true)//go until we find val or we reach the end
      {
        currentLevel = currentNode.levels.get(level);
        if (currentLevel.next != null)
          {
            int comparison = currentLevel.next.ownNode.val.compareTo(val);
            if (comparison < 0)
              {
                // move over
                currentNode = currentLevel.next.ownNode;
              }//if next<val
            else if (comparison > 0)
              {
                if (level == 0)
                  {
                    break;
                  } // if at the end
                // move down
                level--;
              }//if next>0
            else if (comparison == 0)
              {
                NodeLevel target = currentLevel.next.next;
                //skip over what is going to be removed, keep going until there are 
                //no more duplicates. Target will end at the location where currentLevel
                //should be connected
                while (target != null && target.ownNode.val.equals(val))
                  {
                    length--;
                    target = target.next;
                  }//while
                //change pointer 
                currentNode.levels.get(level).next = target;
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
      }//while(true)
    length--;
  } // remove(T)

  /**
   * Remove the last element from the set.
   *
   * @post !contains(get(length()-1))
   * @post For all lav != get(length()-1), if contains(lav) held before the call
   *   to remove, contains(lav) continues to hold.
   */
  public void remove()
  {
    T val = get(length() - 1);
    remove(val); //designates work to remove(val) (could be more efficient)
  } // remove()

  // +--------------------------+----------------------------------------
  // | Methods from SemiIndexed |
  // +--------------------------+

  /**
   * Get the element at index i.
   * Very slow because it moves along the bottom level, and true
   * indexing is not implemented.
   * @throws IndexOutOfBoundsException
   *   if the index is out of range (index < 0 || index >= length)
   */
  public T get(int i)
  {
    Iterator iter = iterator();
    for (int j = 0; j < i; j++)
      {
        iter.next();
      }//for 
    return (T) iter.next();
  } // get(int)

  /**
   * Determine the number of elements in the collection.
   */
  public int length()
  {
    return length;
  } // length()

} // class SkipList<T>
