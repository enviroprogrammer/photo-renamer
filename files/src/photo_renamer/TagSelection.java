package photo_renamer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * A class that is responsible for displaying tag information onto a JFrame so
 * that the user can add, select, and/or remove tags.
 * 
 * @author monicaiqbal
 * @author fareenalimaquayyaim
 *
 */
public class TagSelection {

  /**
   * The list of checkboxes.
   */
  private ArrayList<JCheckBox> checkboxes = new ArrayList<JCheckBox>();

  /**
   * The list of selected tags.
   */
  private ArrayList<JCheckBox> selectedTags = new ArrayList<JCheckBox>();

  // List<JCheckBox> tagsFromFile = new ArrayList<JCheckBox>();

  /**
   * An instance of MasterListManager.
   */
  private MasterListManager m;

  private String filePath;

  private JFrame tagSelectionFrame;

  private JPanel checkBoxPanel;

  public TagSelection() throws IOException {
    m = new MasterListManager();
  }

  /**
   * Write the text to a file located at filePath.
   * 
   * @param text the text that will be written in the file
   * @throws IOException
   */
  public void writeTagToFile(String text) throws IOException {
    // the file path of tags.txt can vary from computer to computer;
    // this can be adjusted in order to work with the user's computer
    filePath =
        "/Users/monicaiqbal/Desktop/year off/CSC207/assignments/a2/group_0587/a3/src/photo_renamer/tags.txt";
    FileWriter fileWriter = new FileWriter(filePath, true);
    PrintWriter printWriter = new PrintWriter(fileWriter);
    printWriter.println(text);
    printWriter.close();
  }

  /**
   * Remove the line consisting of the tag the user wants to remove.
   * 
   * @param text the line to be removed
   * @param tempFilePath a temporary file path where all lines but the line to
   *        be removed will go
   * @throws IOException
   */
  public void removeLineFromFile(String lineToRemove, String tempFilePath)
      throws IOException {
    File inputFile = new File(filePath);
    File tempFile = new File(tempFilePath);

    BufferedReader buffReader = new BufferedReader(new FileReader(inputFile));
    PrintWriter printWriter = new PrintWriter(new FileWriter(tempFile));

    String currentLine;

    while ((currentLine = buffReader.readLine()) != null) {
      String trimmed = currentLine.trim();
      if (!trimmed.equals(lineToRemove)) {
        printWriter.println(trimmed);
      }
    }

    printWriter.close();
    buffReader.close();

    tempFile.renameTo(inputFile);
  }

  /**
   * Generate the tag selection frame.
   * 
   * @throws IOException
   */
  public void generateFrame() throws IOException {
    // the file path of tags.txt can vary from computer to computer;
    // this can be adjusted in order to work with the user's computer
    filePath =
        "/Users/monicaiqbal/Desktop/year off/CSC207/assignments/a2/group_0587/a3/src/photo_renamer/tags.txt";
    File f = new File(filePath);
    tagSelectionFrame = new JFrame("Tag Selection");
    JPanel buttonPanel = new JPanel();
    JLabel message = new JLabel("Available tags:", SwingConstants.CENTER);
    checkBoxPanel = new JPanel();

    if (f.exists()) {
      BufferedReader buffer = new BufferedReader(new FileReader(filePath));
      String lineInFile;

      while ((lineInFile = buffer.readLine()) != null) {
        JCheckBox c = new JCheckBox(lineInFile);
        checkBoxPanel.add(c);
      }
      buffer.close();
    } else {
      f.createNewFile();
    }
    JButton addToMasterList = new JButton("Add to master list");
    addToMasterList.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        addToMasterList();
      }

    });
    JButton removeFromMasterList = new JButton("Remove from master list");
    removeFromMasterList.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          removeFromMasterList();
        } catch (IOException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
      }


    });

    buttonPanel.add(addToMasterList);
    buttonPanel.add(removeFromMasterList);

    tagSelectionFrame.add(buttonPanel, BorderLayout.PAGE_END);
    tagSelectionFrame.add(checkBoxPanel, BorderLayout.LINE_START);
    tagSelectionFrame.add(message, BorderLayout.PAGE_START);
    tagSelectionFrame.pack();
    tagSelectionFrame.setLocationRelativeTo(null);
    tagSelectionFrame.setVisible(true);

  }

  /**
   * Returns the list of tag checkbox that are selected.
   */
  public List<JCheckBox> getSelectedTags() {
    if (!selectedTags.isEmpty()) {
      return selectedTags;
    }
    return null;
  }

  /**
   * Add the tags that are typed in the textfield to the masterlist. Create
   * checkbox of the tags and add the checkbox to the checkbox panel.
   */
  public void addToMasterList() {
    JFrame frame = new JFrame();
    JLabel message = new JLabel("Enter name of tag:", SwingConstants.CENTER);
    JTextField input = new JTextField();
    input.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (input != null) {
          String tagInCheckbox = input.getText();
          Tag newTag = new Tag(tagInCheckbox);
          for (Tag t : m.getMasterList()) {
            if (!t.equals(newTag)) {
              m.addTag(newTag);
            }
          }
          JCheckBox check = new JCheckBox(tagInCheckbox);
          check.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
              if (check.isSelected()) {
                selectedTags.add(check);
              }
            }

          });
          checkboxes.add(check);
          try {
            writeTagToFile(tagInCheckbox);
          } catch (IOException e1) {
            e1.printStackTrace();
          }
          input.setText("");
          checkBoxPanel.add(check);
          checkBoxPanel.updateUI();
          tagSelectionFrame.add(checkBoxPanel, BorderLayout.LINE_START);

        }
      }
    });
    frame.add(message, BorderLayout.PAGE_START);
    frame.add(input, BorderLayout.PAGE_END);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }

  /**
   * Remove the tag checkbox from the masterlist and checkbox panel.
   */
  public void removeFromMasterList() throws IOException {
    // add a temporary file path to write all lines of text from tags.txt
    // excluding the one we want to remove
    // this can vary based on the user's computer and can be adjusted
    // accordingly
    String tempFilePath =
        "/Users/monicaiqbal/Desktop/year off/CSC207/assignments/a2/group_0587/a3/src/photo_renamer/temp.txt";
    for (Component comp : checkBoxPanel.getComponents()) {
      JCheckBox ch = (JCheckBox) comp;
      String s = ch.getText();
      Tag tagToRemove = new Tag(s);
      if (ch.isSelected()) {
        for (Tag tag : m.getMasterList()) {
          if (tagToRemove.equals(tag)) {
            m.removeTag(tag);
          }
          System.out.println(m.getMasterList());
        }
        removeLineFromFile(s, tempFilePath);
        checkBoxPanel.remove(comp);
        checkBoxPanel.updateUI();
      }
    }
  }
}


