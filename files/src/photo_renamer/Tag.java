package photo_renamer;


/**
 * A class that represents an image tag.
 * 
 * @author monicaiqbal
 * @author fareenalimaquayyaim
 *
 */
public class Tag {

  /**
   * The name of this tag.
   */
  private String name;

  /**
   * Create a new tag with the given name.
   * 
   * @param name the name of this tag
   */
  public Tag(String name) {
    this.name = name;
  }

  /**
   * Get the name of this tag.
   * 
   * @return name of tag
   */
  public String getName() {
    return name;
  }

  /**
   * Set the name of this tag.
   * 
   * @param name the name of this tag
   */
  public void setName(String name) {
    this.name = name;
  }

}
