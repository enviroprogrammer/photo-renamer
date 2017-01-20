package photo_renamer;

import java.awt.BorderLayout;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;


/**
 * An application that enables users to rename images based on a set of tags.
 * 
 * All buttons defined in this class follow the Observer Design Pattern, with
 * ActionListener as the Observer and the button as the Observable object.
 * 
 * @author monicaiqbal
 * @author fareenalimaquayyaim
 *
 */
public class PhotoRenamer {

  /**
   * The file before renaming.
   */
  private File oldFile;

  /**
   * The file after renaming.
   */
  private File renamedFile;

  /**
   * The name of the file before it was renamed.
   */
  private String oldName;

  /**
   * The name of the file after it was renamed.
   */
  private String newName;

  /**
   * The list of images in a given directory.
   */
  public ArrayList<String> listOfImages;

  /**
   * The list of names an image has ever had.
   */
  private ArrayList<String> oldNames;

  /**
   * The frame that contains the JPanel, JLabel, and JButtons.
   */
  private JFrame photoRenamer;

  /**
   * The panel that contains the JButtons.
   */
  private final JPanel buttonPanel;

  /**
   * The button that allows users to choose a directory when clicked.
   */
  private final JButton directoryChooser;

  /**
   * The button that allows users to view a history of all renaming that's been
   * done.
   */
  private final JButton renamingHistory;

  /**
   * The button that allows users to select an image.
   */
  private final JButton imageChooser;

  /**
   * The label displaying a message to the user on the JFrame.
   */
  private final JLabel welcome;

  /**
   * The file path of the text file containing the renaming log.
   */
  private String filePath;

  /**
   * An instance of RenamingHistory.
   */
  private RenamingHistory rh;

  /**
   * The timestamp associated with when the user renamed an image.
   */
  private String timestamp;

  /**
   * An image selected by the user.
   */
  private File selectedImage;

  /**
   * A representation of an image file.
   */
  private ImageFile imageFile;

  /**
   * An instance of MasterListManager, which can help us access the master list
   * by calling its respective getter.
   */
  private MasterListManager m;

  /**
   * The list of current tags of an image.
   */
  private ArrayList<Tag> currentTags;

  /**
   * Create an instance of PhotoRenamer.
   * 
   * @throws IOException
   */
  public PhotoRenamer() throws IOException {
    // the file path of renaming.txt can vary from computer to computer;
    // this can be adjusted in order to work with the user's computer
    filePath = "renaming.txt";
    rh = new RenamingHistory(filePath);
    m = new MasterListManager();
    timestamp = new SimpleDateFormat("yyyy/MM/dd hh:mm").format(new Date());
    photoRenamer = new JFrame("PhotoRenamer");
    buttonPanel = new JPanel();
    directoryChooser =
        new DirectoryChooserButton("View list of images in directory");
    imageChooser = new ImageChooserButton("Select an image");
    renamingHistory = new RenamingHistoryButton("View renaming history");
    welcome = new JLabel("Welcome to PhotoRenamer!", SwingConstants.CENTER);

    buttonPanel.add(directoryChooser);
    buttonPanel.add(imageChooser);
    buttonPanel.add(renamingHistory);
    photoRenamer.add(welcome, BorderLayout.PAGE_START);
    photoRenamer.add(buttonPanel);

    oldNames = new ArrayList<String>();
    listOfImages = new ArrayList<String>();
  }

  /**
   * Generate the GUI.
   */
  public void generateGui() {
    photoRenamer.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    photoRenamer.pack();
    photoRenamer.setLocationRelativeTo(null);
    photoRenamer.setVisible(true);
  }

  /**
   * Rename an image with a tag or set of tags and record any renaming activity.
   * 
   * @param image the image to be renamed
   * @param tags the tag(s) to rename the image with
   * @throws IOException
   */
  public void renameFile(ImageFile image) throws IOException {
    currentTags = image.getCurrentTags();
    oldFile = new File(image.getName());
    oldName = oldFile.getName();
    newName = oldName.substring(0, oldName.length() - 4);
    int lastIndex = currentTags.size() - 1;
    newName += " @" + currentTags.get(lastIndex).getName();
    newName = newName + image.getFileExtension(oldFile);
    image.setName(newName);
    renamedFile = new File(newName);
    oldFile.renameTo(renamedFile);
    oldNames.add(oldName);
    String data = "Old name: " + oldName + "\nNew name: " + newName
        + "\nDate and time: " + timestamp + "\n";
    rh.writeToFile(data);
  }

  /**
   * Revert an image back to a previous name and record this change.
   * 
   * @param image the image to be reverted back to an older name
   * @param name the name the user wants to rename the image to
   * @throws IOException
   */
  public void revertBackToOlderName(ImageFile image, String name)
      throws IOException {
    if (oldNames.contains(name)) {
      newName = name;
      oldName = image.getName();
      renamedFile = new File(newName);
      oldFile.renameTo(renamedFile);
      image.setName(newName);
      oldNames.add(oldName);
      String data = "\nOld name: " + oldName + "\nNew name: " + newName
          + "\nDate and time: " + timestamp;
      rh.writeToFile(data);
    }
  }

  /**
   * Given a directory, populate the list of images in that directory.
   * 
   * @param dir the directory to be checked for images
   */
  public void buildListOfImages(File dir) {
    File[] imageFiles = dir.listFiles();
    for (File f : imageFiles) {
      if (f.isFile()) { // we have an image file in this directory
        ImageFile image = new ImageFile(f.getName(), f.getPath());
        if (image.isValidImageFile(f)) {
          listOfImages.add(f.getName());
        }
      } else { // we have a subdirectory
        buildListOfImages(f);
      }
    }
  }

  /**
   * Create the image chooser. If an image is chosen, create the GUI containing
   * the image, its file path, and some buttons.
   */
  public void createImageChooser() {
    JFileChooser chooser = new JFileChooser();
    chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

    int result = chooser.showOpenDialog(photoRenamer.getContentPane());
    JLabel label = new JLabel();

    // check if user selected an image
    if (result == JFileChooser.APPROVE_OPTION) {
      selectedImage = chooser.getSelectedFile();

      // check if it exists
      if (selectedImage.exists()) {
        imageFile =
            new ImageFile(selectedImage.getName(), selectedImage.getPath());

        // check if image is valid
        if (imageFile.isValidImageFile(selectedImage)) {
          createImageViewer(selectedImage, imageFile);
        }

      } else {
        label.setText("No image selected");
      }
    }
  }

  /**
   * Create the window that displays the image chosen by the user as well as
   * several buttons.
   * 
   * @param selectedImage the image selected by the user
   * @param imageFile the representation of the selected image file
   */
  public void createImageViewer(File selectedImage, ImageFile imageFile) {
    JFrame imageWindow = new JFrame("Image Viewer");
    JPanel btnPanel = new JPanel();
    btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.Y_AXIS));
    JPanel imagePanel = new JPanel();
    JButton viewMasterList = new ViewMasterListButton("View master list");
    JButton rename = rename();
    JButton revert = revert();

    JButton addToImage = addToImage();

    JButton removeCurrentTag = removeCurrentTag(imageFile);
    JButton addTagsFromMasterList = addTagsFromMasterList(imageFile);



    // read from image file
    BufferedImage bi = null;
    try {
      bi = ImageIO.read(selectedImage);
    } catch (IOException e) {
      e.printStackTrace();
    }

    // create JLabel to fit image in
    JLabel imgLabel = new JLabel();
    // set size of JLabel
    imgLabel.setSize(1000, 800);
    // rescale image to fit label
    Image resizedImage = bi.getScaledInstance(imgLabel.getWidth(),
        imgLabel.getHeight(), Image.SCALE_DEFAULT);
    // create imageIcon with resizedImage
    ImageIcon resizedImageIcon = new ImageIcon(resizedImage);
    // set icon of this label to resizedImageIcon
    imgLabel.setIcon(resizedImageIcon);
    imagePanel.add(imgLabel);

    // add buttons to panel
    btnPanel.add(viewMasterList);
    btnPanel.add(revert);
    btnPanel.add(rename);
    btnPanel.add(addToImage);
    btnPanel.add(addTagsFromMasterList);
    btnPanel.add(removeCurrentTag);

    // put it all together
    imageWindow.add(btnPanel, BorderLayout.LINE_START);
    imageWindow.add(imagePanel);
    imageWindow.pack();
    imageWindow.setLocationRelativeTo(null);
    imageWindow.setVisible(true);
  }

  /**
   * A button that allows users to remove a certain tag from the current tags
   * when pressed.
   * 
   * @param imageFile the representation of the selected image file
   * @return the button that has been created
   */
  public JButton removeCurrentTag(ImageFile imageFile) {
    JButton b = new JButton("Remove current tag");
    b.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        JFrame jf = new JFrame("Remove Current Tag");
        JLabel msg = new JLabel("Enter a tag to remove: ");
        JTextField input = new JTextField();
        input.addActionListener(new ActionListener() {

          @Override
          public void actionPerformed(ActionEvent e) {
            if (input != null) {
              String name = input.getText();
              Tag tagToRemove = new Tag(name);
              for (Tag current : imageFile.getCurrentTags()) {
                if (current.equals(tagToRemove)) {
                  imageFile.getCurrentTags().remove(current);
                }
              }
              input.setText("");
            }
          }


        });
        jf.add(input);
        jf.add(msg, BorderLayout.PAGE_START);
        jf.pack();
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
      }

    });
    return b;
  }

  /**
   * A button that allows users to add tags from the master list to an image
   * when pressed.
   * 
   * @param imageFile the representation of the selected image file
   * @return the button that has been created
   */
  public JButton addTagsFromMasterList(ImageFile imageFile) {
    JButton b = new JButton("Add tags from master list to image");

    b.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        JFrame jf = new JFrame("Add Tags from Master List");
        JLabel msg = new JLabel(
            "Enter a tag from the master list to add to this image:");
        JTextField input = new JTextField();
        input.addActionListener(new ActionListener() {

          @Override
          public void actionPerformed(ActionEvent e) {
            if (input != null) {
              String name = input.getText();
              Tag tagToAdd = new Tag(name);
              for (Tag t : m.getMasterList()) {
                if (t.equals(tagToAdd)) {
                  imageFile.getCurrentTags().add(t);
                }
              }
              input.setText("");
            }
          }
        });
        jf.add(input);
        jf.add(msg, BorderLayout.PAGE_START);
        jf.pack();
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
      }
    });
    return b;
  }

  /**
   * The button that allows a user to add a tag directly to an image when
   * pressed.
   * 
   * @param imageFile the representation of the selected image file
   * @return the button that has been created
   */
  public JButton addToImage() {
    JButton addToImage = new JButton("Add tag to image");
    addToImage.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        JFrame jf = new JFrame("Add a Tag");
        JLabel msg = new JLabel("Enter a tag:");
        JTextField tf = new JTextField();
        tf.addActionListener(new ActionListener() {

          @Override
          public void actionPerformed(ActionEvent e) {
            if (tf != null) {
              String tagName = tf.getText();
              Tag tagToAdd = new Tag(tagName);
              imageFile.getCurrentTags().add(tagToAdd);
              m.getMasterList().add(tagToAdd);
            }
            tf.setText("");
          }

        });

        jf.add(tf);
        jf.add(msg, BorderLayout.PAGE_START);
        jf.pack();
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
      }

    });
    return addToImage;
  }

  /**
   * A button that allows users to rename an image based on the current tags.
   * 
   * @return the button that has been created
   */
  public JButton rename() {
    JButton rename = new JButton("Rename file");
    rename.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          renameFile(imageFile);
        } catch (IOException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
      }

    });
    return rename;
  }

  /**
   * The button that allows a user to revert an image file name back to a
   * previous one when pressed.
   * 
   * @param imageFile the representation of the selected image file
   * @return the button that has been created
   */
  public JButton revert() {
    JButton b = new JButton("Revert file name back to an older one");
    b.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        JFrame jf = new JFrame("Add a Tag");
        JLabel msg = new JLabel("Enter a previous file name:");
        JTextField tf = new JTextField();
        tf.addActionListener(new ActionListener() {

          @Override
          public void actionPerformed(ActionEvent e) {
            if (tf != null) {
              String name = tf.getText();
              try {
                revertBackToOlderName(imageFile, name);
              } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
              }
            }
            tf.setText("");
          }

        });

        jf.add(tf);
        jf.add(msg, BorderLayout.PAGE_START);
        jf.pack();
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
      }

    });
    return b;
  }

  /**
   * Return the list of images from a directory.
   * 
   * @return the list of images in a particular directory
   */
  public String viewListOfImages() {
    String result = "";
    for (String img : listOfImages) {
      result += img + "\n";
    }
    return result;
  }

  public static void main(String[] args) throws IOException {
    PhotoRenamer pr = new PhotoRenamer();
    pr.generateGui();
  }


}
