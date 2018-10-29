//***********************
//NAME: <Dennis Vo>
//ID: <A12347682>
//LOGIN: <cs12srk>
//***********************

package hw7;

import java.util.ArrayList;

/** 
 * This class creates an 2 instances of a binary search tree and includes 
 * several methods that work with the corresponding trees.
 * @version 1.0
 * @author Dennis Vo
 * @since 2016-05-15
 */

public class BSTree<T extends Comparable<? super T>>{

  private int nelems;       //Number of nodes in the trees
  private BSTNode root1;    //Root of first tree
  private BSTNode root2;    //Root of second tree
  private BSTNode foundNode;
  // node found in find helper method and used in findMoreInfo

  protected class BSTNode{
    T elem;
    BSTNode lChild;
    BSTNode rChild;
    BSTNode outer;

    /**
     * Ctor that instantiates an individual node of a BST.
     * @param lChild is the left child of the node
     * @param rChild is the right child of the node
     * @param outer is the corresponding node of the other tree
     * @param elem is the key value of the node
     */
    public BSTNode(BSTNode lChild, BSTNode rChild, BSTNode outer, T elem) {
      this.lChild = lChild;
      this.rChild = rChild;
      this.outer = outer;
      this.elem = elem;
    }
    
    /**
     * Gets the key value of a node.
     * @return key value of any type T
     */
    public T getElem() {
      return elem;
    }
    
    /**
     * Returns a node's left child.
     * @return a node representing the left child of the node
     */
    public BSTNode getLChild() {
      return lChild;
    }
    
    /**
     * Returns a node's right child.
     * @return a node representing the right child of the node
     */
    public BSTNode getRChild() {
      return rChild;
    }
    
    /**
     * Returns the corresponding node from the other tree.
     * @return a node representing the matching node from the other tree.
     */
    public BSTNode getOuterNode() {
      return outer;
    }
    
    /**
     * Sets a new key value for the node.
     * @param newElem represents the new value.
     */
    public void setElem(T newElem) {
      this.elem = newElem;
    }
    
    /**
     * Sets a new left child for the root of the sub-tree.
     * @param newLChild is the new left child.
     */
    public void setLChild( BSTNode newLChild) {
      this.lChild = newLChild;
    }
    
    /**
     * Sets a new right child for the root of the sub-tree.
     * @param newRChild is the new right child.
     */
    public void setRChild( BSTNode newRChild) {
      this.rChild = newRChild;
    }
    
    /**
     * Sets a new matching node for the current node/root.
     * @param newOuterNode is the new outer/matching node.
     */
    public void setOuterNode( BSTNode newOuterNode) {
      this.outer = newOuterNode;
    }
  }

  //BSTree methods

  /**
   * This ctor creates an instance of the BST.
   */
  public BSTree() {
    root1 = null;
    root2 = null;
    nelems = 0;
  }
 
  /**
   * Grabs the root, or the very first node inserted, of the current BSTree.
   * @param treeChoice chooses which tree we are pulling the root from
   * @return a root of a tree
   * @throws IllegalArgumentException
   */
  public BSTNode getRoot(int treeChoice) throws IllegalArgumentException {
    if (treeChoice == 1) {
      return root1;
    } 
    else if (treeChoice == 2) {
      return root2;
    } else {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Gives the number of nodes in 1 tree.
   * @return number of elements
   */
  public int getSize() {
    return nelems;
  }

  /**
   * Inserts a new Node into each tree, one containing each element
   * @param firstElem is the key value of the node of the first tree
   * @param secondElem is the key value of the node of the second tree
   * @throws NullPointerException
   */
  public void insert(T firstElem, T secondElem) throws NullPointerException{
    if (firstElem == null || secondElem == null) {
      throw new NullPointerException();
    }
    
    /* For when the trees are empty */
    if (root1 == null && root2 == null) {
      root1 = new BSTNode(null, null, null, firstElem);
      root2 = new BSTNode(null, null, root1, secondElem);
      root1.setOuterNode(root2);
      nelems++;
    } else {
      /* Call a helper recursive method to add to the trees */
      BSTNode firstTemp = insertHelper(root1, firstElem);
      BSTNode secondTemp = insertHelper(root2,secondElem);
      // need to store each node as a temp node
      firstTemp.setOuterNode(secondTemp);
      secondTemp.setOuterNode(firstTemp);
      // then set nodes of each tree to their respective outer nodes
      nelems++;
    }
  }
  
  /**
   * Recursive helper method that takes a current node/root of a sub-tree and
   * the key value of the node we are adding to the sub-tree.
   * @param currNode is the current root of the sub-tree
   * @param elem is the key value of the new node
   * @return gives the node added to the tree
   */
  private BSTNode insertHelper(BSTNode currNode, T elem) {
    int compare = elem.compareTo(currNode.getElem());
    // stores an int to use for comparisons/conditions for where to add
    
    /* if the new elem is less than the root's we go to the left child */
    if (compare < 0) {
      if (currNode.getLChild() == null) {
        currNode.setLChild(new BSTNode(null,null,null,elem));
        return currNode.getLChild();
      } else {
        // if current left child is occupied, we recursively move down tree
        return insertHelper(currNode.getLChild(), elem);
      }
    }

    /* if the new elem is bigger than the root's we go to the right child */
    else {
      if (currNode.getRChild() == null) {
        currNode.setRChild(new BSTNode(null,null,null,elem));
        return currNode.getRChild();
      } else {
        // if current right child is occupied, we recursively moved down tree
        return insertHelper(currNode.getRChild(), elem);
      }
    }
  }
  
  /**
   * Answers whether or not the element can be found in either tree.
   * @param elem is the element we are searching for
   * @return true if element can be found
   * @throws NullPointerException
   */
  public boolean findElem(T elem) throws NullPointerException {
    if (elem == null) {
      throw new NullPointerException();
    }
    /* Calls upon helper method to go through trees to find element */
    if (findHelper(root1,elem) == true || findHelper(root2,elem) == true) {
      return true;
    } else {
      return false;
    }
  }
  
  /**
   * Looks within a node's subtree to look for the element. Useful in both the
   * finder methods in this class.
   * @param currNode is the root of the subtree
   * @param elem is element we're looking for
   * @return true if element can be found
   */
  private boolean findHelper(BSTNode currNode, T elem) {
    if (currNode == null) {
      return false;
    }
    /* if elem is bigger than root's elem, we look through left subtree */
    if (elem.compareTo(currNode.getElem()) < 0) {
      return findHelper(currNode.getLChild(), elem);
    }
    /* otherwise look through right subtree */
    else if (elem.compareTo(currNode.getElem()) > 0) {
      return findHelper(currNode.getRChild(), elem);
    } else {
      // if the current node has the elem we're looking for, we return true
      foundNode = currNode;
      // foundNode used to return it's outer node in findMoreInfo
      return true; // case where elem and currRoot's data are equal
    }
  }
  
  /**
   * Looks for a node containing an elem and returns its matching node's elem
   * @param elem is elem we are looking for
   * @return the corresponding node's element
   * @throws NullPointerException
   * @throws IllegalArgumentException
   */

  public T findMoreInfo(T elem) throws 
      NullPointerException,IllegalArgumentException {

    if (elem == null) {
      throw new NullPointerException();
    }
    
    /* We have two cases depending on whether we find the elem in the first
     * tree or the second.
     */
    if (findHelper(root1,elem)) {
      return foundNode.getOuterNode().getElem();
    }
    else if (findHelper(root2,elem)) {
      return foundNode.getOuterNode().getElem();
    } else {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Prints out the contents of a tree in order, followed by its corresponding
   * element each time.
   * @param elemArray is the array we are populating with the elements
   * @param treeChoice determines which tree's elements go first
   * @throws NullPointerException
   * @throws IllegalArgumentException
   */
  public void printToArray(T[] elemArray, int treeChoice)
      throws NullPointerException,IllegalArgumentException {
    
    if (getSize() == 0) {
      throw new NullPointerException();
    }
    
    ArrayList<T> firstList = new ArrayList<T>();
    ArrayList<T> secondList = new ArrayList<T>();
    // need two arraylists, one for each choice, then we can copy to an array
    
    /* Both trees call inOrder recursive helper method to visit the nodes in a
     * sorted order. Then, we populate our array with these nodes' elements.
     */
    if (treeChoice == 1) {
      inOrder(root1, firstList);
      for (int i = 0; i < firstList.size(); i++) {
          elemArray[i] = firstList.get(i);
          System.out.println(elemArray[i]);
      }
    }
    
    else if (treeChoice == 2) {
      inOrder(root2, secondList);
      for (int i = 0; i < secondList.size(); i++) {
        elemArray[i] = secondList.get(i);
        System.out.println(elemArray[i]);
      }
    } else {
      throw new IllegalArgumentException();
    }
    

  }
  
  /**
   * This method visits the nodes of a tree in order from least to greatest.
   * @param currNode is the root of the subtree we search through
   * @param list is the arraylist we are adding to so we can copy it to an array
   */
  private void inOrder(BSTNode currNode, ArrayList<T> list) {
    if (currNode != null) {
      inOrder(currNode.getLChild(), list);
      list.add(currNode.getElem()); // add current node's elem
      list.add(currNode.getOuterNode().getElem()); // then its matching elem
      inOrder(currNode.getRChild(), list);
    }
  }
}
