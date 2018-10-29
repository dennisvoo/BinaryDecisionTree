//***********************
//NAME: <Dennis Vo>
//ID: <A12347682>
//LOGIN: <cs12srk>
//***********************

package hw7;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/** 
 * This tester file tests the methods of BSTree.java
 * @version 1.0
 * @author Dennis Vo
 * @since 2016-05-15
 */

public class BSTreeTester {
  private BSTree<String> stringTree;
  private BSTree<Integer> numTree;
  private BSTree<String> myTree; // used for testing In Order Traversal
	
  /**
   * This sets up multiple BSTrees to use for testing.
   */
  @Before
  public void setUp() {
    stringTree = new BSTree<String>();
    stringTree.insert("Dennis", "Vo");
    
    numTree = new BSTree<Integer>();
    numTree.insert(1234, 4321);
    
    myTree = new BSTree<String>();
  }

  /**
   * Catch exception for invalid arg and checks if correct root is given.
   */
  @Test
  public void testGetRoot() {
    try {
      stringTree.getRoot(0);
      fail("invalid arg");
    } catch (IllegalArgumentException e) {}
    
    assertEquals("Dennis", stringTree.getRoot(1).getElem());
    assertEquals("Vo", stringTree.getRoot(2).getElem());
    
    assertEquals(new Integer(1234), numTree.getRoot(1).getElem());
    assertEquals(new Integer(4321), numTree.getRoot(2).getElem());
  }
  
  /**
   * Tester for getting size of a tree. Also tests after inserting.
   */
  @Test
  public void testSize() {
    assertEquals(1, numTree.getSize());
    numTree.insert(5678, 8765);
    assertEquals(2, numTree.getSize());
  }
  
  /**
   * Using this method to catch the NullPointerException. Insert will pretty
   * much be tested implicitly in the other tests.
   */
  @Test
  public void testInsert() {
    try {
      stringTree.insert(null, "2");
      fail("adding null object");
    } catch (NullPointerException e) {} 
  }
  
  /**
   * Checks boolean statements for whether or not an element is found in the
   * trees.
   */
  @Test
  public void testFindElem() {
    assertTrue(!stringTree.findElem("name"));
    assertTrue(stringTree.findElem("Dennis"));
    assertTrue(numTree.findElem(new Integer(1234)));
  }
  
  /**
   * This tests a method where you search for the matching element for a
   * particular element.
   */
  @Test
  public void testFindMoreInfo() {
    try {
      stringTree.findMoreInfo(null);
      fail("looking for null");
    } catch (NullPointerException e) {}
    try {
      stringTree.findMoreInfo("name");
      fail("arg isn't in any tree");
    } catch (IllegalArgumentException e) {}
    
    
    assertEquals("Dennis", stringTree.findMoreInfo("Vo"));
    assertEquals("Vo", stringTree.findMoreInfo("Dennis"));
    assertEquals(new Integer(4321), numTree.findMoreInfo(1234));
  }
  
  /**
   * This prints out a tree in sorted order. It depends on what choice you
   * choose as well. Choosing 1 will print the element in the first tree first,
   * then it's matching element will be printed next. The print out will make
   * up an array.
   */
  @Test
  public void testPrint() {
    String[] array1 = new String[18];
    String[] array2 = new String[18];
    
    try {
      myTree.printToArray(array1,1);
      fail("tree is empty");
    } catch (NullPointerException e) {}
    try {
      stringTree.printToArray(array2,0);
      fail("invalid arg");
    } catch (IllegalArgumentException e) {}
    
    myTree.insert("Richard","Tran");
    myTree.insert("Pooja", "Bhat");
    myTree.insert("Zach", "Meyer");
    myTree.insert("Marina", "Langlois");
    myTree.insert("Thiago", "Marback");
    myTree.insert("Siwei", "Xu");
    myTree.insert("Annie", "Xiao");
    myTree.insert("Don", "Vo");
    myTree.insert("Haoran", "Sun");
    
    System.out.println("Printing Choice 1:");
    myTree.printToArray(array1, 1);
    assertEquals("Annie", array1[0]);
    assertEquals("Xiao", array1[1]);
    assertEquals("Don", array1[2]);
    assertEquals("Vo", array1[3]);
    System.out.println("Printing Choice 2:");
    myTree.printToArray(array2, 2);
    assertEquals("Bhat", array2[0]);
    assertEquals("Pooja", array2[1]);
    assertEquals("Langlois", array2[2]);
    assertEquals("Marina", array2[3]);
  }
}
