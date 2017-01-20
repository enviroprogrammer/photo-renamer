package photo_renamer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * A class that is responsible for keeping track of all the renaming done.
 * 
 * @author monicaiqbal
 * @author fareenalimaquayyaim
 *
 */

public class RenamingHistory {
  /**
   * The file path where the log file is stored. Must be a text file.
   */
  private String filePath;


  public RenamingHistory(String filePath) {
    this.filePath = filePath;
  }

  public void writeToFile(String text) throws IOException {
    FileWriter fileWriter = new FileWriter(filePath, true);
    PrintWriter printWriter = new PrintWriter(fileWriter);
    printWriter.println(text);
    printWriter.close();
  }

  public void createFrame() throws IOException {
    JFrame frame = new JFrame("Renaming History");
    JTextArea log = new JTextArea(15, 50);
    BufferedReader buffer = new BufferedReader(new FileReader(filePath));
    String lineInFile = buffer.readLine();

    while (lineInFile != null) {
      log.append(lineInFile + "\n");
      lineInFile = buffer.readLine();
    }
    buffer.close();

    log.setEditable(false);
    JScrollPane scroller = new JScrollPane(log);

    frame.add(scroller);
    frame.setLocationRelativeTo(null);
    frame.pack();
    frame.setVisible(true);

  }


}
