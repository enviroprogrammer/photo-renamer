package photo_renamer;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A class representing an image file.
 * 
 * @author monicaiqbal
 * @author fareenalimaquayyaim
 *
 */
public class ImageFile {

  /*
   * The name of this file.
   */
  private String name;

  /*
   * The file path where the image can be found.
   */
  private String filePath;

  /*
   * The list of tags that are part of this image.
   */
  private ArrayList<Tag> currentTags;

  /*
   * The extensions that images must have in order to be valid.
   */
  private static final ArrayList<String> EXTENSIONS = new ArrayList<String>(
      Arrays.asList(".png", ".jpg", ".gif", ".JPG", ".PNG", ".GIF"));

  /**
   * Create a new image file with a given name and filePath.
   * 
   * @param name the name of this image
   * @param filePath the path this image belongs to
   */
  public ImageFile(String name, String filePath) {
    this.name = name;
    this.filePath = filePath;
    this.currentTags = new ArrayList<Tag>();
  }

  /**
   * Get the name of this file.
   * 
   * @return name of image file
   */
  public String getName() {
    return name;
  }

  /**
   * Set the name of this file.
   * 
   * @param name the name of this image file
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Get the extension of this file.
   * 
   * @param f a file with a certain extension
   * @return the file's extension
   */
  public String getFileExtension(File f) {
    String filename = f.getName();
    String s = "";
    for (int i = 0; i < filename.length(); i++) {
      char c = filename.charAt(i);
      if (c == '.') {
        s += filename.substring(i);
      }
    }
    return s;
  }

  /**
   * Check whether this file is a valid image file.
   * 
   * @param f the file to be checked for validity
   * @return true if it's a valid image file; false otherwise
   */
  public boolean isValidImageFile(File f) {
    return EXTENSIONS.contains(getFileExtension(f));
  }

  /**
   * Get the list of tags this image currently has
   * 
   * @return list of current tags belonging to this image
   */
  public ArrayList<Tag> getCurrentTags() {
    return currentTags;
  }

  /**
   * Remove tag from an image.
   * 
   * @param tag the tag to be removed
   */
  public void removeTagFromImage(Tag tag) {
    if (currentTags.contains(tag)) {
      currentTags.remove(tag);
    }

  }

  /**
   * Get the file path of this image.
   * 
   * @return the image's file path
   */
  public String getFilePath() {
    return filePath;
  }

}

