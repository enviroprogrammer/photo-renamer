package photo_renamer;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

/**
 * A test class for MasterListManager.
 * 
 * @author monicaiqbal
 * @author fareenalimaquayyaim
 *
 */
public class MasterListManagerTest {

  /**
   * An instance of MasterListManager that can be used to get the master list by
   * calling its getter method.
   */
  MasterListManager m;

  /**
   * The list of all available tags.
   */
  ArrayList<Tag> masterList;

  /**
   * An image tag.
   */
  Tag t1;

  /**
   * An image tag.
   */
  Tag t2;

  /**
   * An image tag.
   */
  Tag t3;

  /**
   * An image tag.
   */
  Tag t4;

  /**
   * An image tag.
   */
  Tag t5;

  @Before
  public void setUp() {
    m = new MasterListManager();
    masterList = m.getMasterList();
    t1 = new Tag("tag1");
    t2 = new Tag("tag2");
    t3 = new Tag("tag3");
    t4 = new Tag("tag4");
    t5 = new Tag("tag5");
  }

  @Test
  public void testaddTagOneTag() {
    m.addTag(t1);
    assertTrue(m.getListSize() == 1);
  }

  @Test
  public void testaddTagMultipleTags() {
    m.addTag(t1);
    m.addTag(t2);
    m.addTag(t3);
    assertTrue(m.getListSize() == 3);
  }

  @Test
  public void testRemoveOneTag() {
    m.addTag(t1);
    m.addTag(t2);
    m.addTag(t3);

    m.removeTag(t1);
    assertTrue(m.getListSize() == 2);
  }

  @Test
  public void testRemoveMultipleTags() {
    m.addTag(t1);
    m.addTag(t2);
    m.addTag(t3);

    m.removeTag(t1);
    m.removeTag(t2);
    assertTrue(m.getListSize() == 1);
  }

  @Test
  public void testRemoveNonexistentTag() {
    m.addTag(t1);
    m.addTag(t2);
    m.addTag(t3);

    m.removeTag(t4);
    assertTrue(m.getListSize() == 3);
  }

  @Test
  public void testRemoveNonexistentTags() {
    m.addTag(t1);
    m.addTag(t2);
    m.addTag(t3);

    m.removeTag(t4);
    m.removeTag(t5);
    assertTrue(m.getListSize() == 3);
  }

}
