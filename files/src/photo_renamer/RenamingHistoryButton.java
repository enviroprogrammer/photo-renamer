package photo_renamer;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;

/**
 * A button that is used to see all the renaming that are done. This class
 * follows the Observer Design Pattern, with ActionListener as the Observer and
 * the button as theObservable object.
 * 
 * @author monicaiqbal
 * @author fareenalimaquayyaim
 *
 */

@SuppressWarnings("serial")
public class RenamingHistoryButton extends JButton implements ActionListener {

  /**
   * Create a new RenamingHistoryButton with text.
   * 
   * @param text the text that will appear on the button
   */

  public RenamingHistoryButton(String text) {
    super(text);
    this.addActionListener(this);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String filePath =
        "/Users/monicaiqbal/Desktop/year off/CSC207/assignments/a2/group_0587/a3/src/photo_renamer/renaming.txt";

    RenamingHistory rh = new RenamingHistory(filePath);

    try {
      rh.createFrame();
    } catch (IOException e1) {
      e1.printStackTrace();
    }
  }

}
