package photo_renamer;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;

/**
 * A button that will direct the user to the directory chooser window once
 * clicked. This class follows the Observer Design Pattern, with ActionListener
 * as the Observer and the button as the Observable object.
 * 
 * @author monicaiqbal
 * @author fareenalimaquayyaim
 *
 */
@SuppressWarnings("serial")
public class DirectoryChooserButton extends JButton implements ActionListener {

  /**
   * Create a new DirectoryChooserButton with text.
   * 
   * @param text the text to be added to the button
   */
  public DirectoryChooserButton(String text) {
    super(text);
    this.addActionListener(this);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    DirectoryChooser dc;
    try {
      dc = new DirectoryChooser();
      dc.createDirFrame();
    } catch (IOException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
  }

}
