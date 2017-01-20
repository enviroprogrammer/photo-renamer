package photo_renamer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A class that is responsible for maintaining Tag data. This class uses the
 * Iterator Design Pattern.
 * 
 * @author monicaiqbal
 * @author fareenalimaquayyaim
 *
 */
public class MasterListManager implements Iterable<Tag> {

  /**
   * The master list of tags.
   */
  private ArrayList<Tag> masterList;

  /**
   * The file path of the file that consists of the names of all tags in the
   * master list (represented as a .txt file).
   */
  
  public MasterListManager() {
    masterList = new ArrayList<Tag>();

  }

  /**
   * Return the master list of tags.
   * 
   * @return the master list of tags
   */
  public ArrayList<Tag> getMasterList() {
    return masterList;
  }

  /**
   * Add t to the master list if it doesn't contain t.
   * 
   * @param t the tag to be added
   */
  public void addTag(Tag t) {
    if (!masterList.contains(t)) {
      masterList.add(t);
    }
  }

  /**
   * Remove t from the master list if it contains t.
   * 
   * @param t the tag to be removed
   */
  public void removeTag(Tag t) {
    for (Iterator<Tag> iter = iterator(); iter.hasNext();) {
      Tag tag = iter.next();
      if (tag.equals(t)) {
        masterList.remove(t);
      }
    }

  }

  /**
   * Get the size of the master list.
   * 
   * @return the size of the master list
   */
  public int getListSize() {
    return masterList.size();
  }


  @Override
  public Iterator<Tag> iterator() {
    return new TagIterator();
  }


  /**
   * A class that implements Iterator to iterate over the master list.
   * 
   * @author monicaiqbal
   *
   */
  private class TagIterator implements Iterator<Tag> {
    /**
     * The current index of the master list.
     */
    private int index = 0;

    @Override
    public boolean hasNext() {
      return index < masterList.size();
    }

    @Override
    public Tag next() {
      Tag tag;
      try {
        tag = masterList.get(index);
      } catch (IndexOutOfBoundsException e) {
        throw new NoSuchElementException();
      }
      index += 1;
      return tag;
    }


  }
  
  public static void main(String[] args) {
    MasterListManager m = new MasterListManager();
    System.out.println(m.getMasterList());
    Tag t1 = new Tag("one");
    m.addTag(t1);
    Tag t2 = new Tag("two");
    m.addTag(t2);
    Tag t3 = new Tag("three");
    m.addTag(t3);
    System.out.println(m.getMasterList());
    
    m.removeTag(t3);

    System.out.println(m.getMasterList());
  }

}

