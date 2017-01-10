package photo_renamer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;

/**
 * A button that allows the user to view the master list when clicked.
 * 
 * @author monicaiqbal
 * @author fareenalimaquayyaim
 *
 */
@SuppressWarnings("serial")
public class ViewMasterListButton extends JButton implements ActionListener {

  /**
   * Create a new ViewMasterListButton with text.
   * 
   * @param text the text to add to the button
   */
  public ViewMasterListButton(String text) {
    super(text);
    this.addActionListener(this);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    TagSelection ts;
    try {
      ts = new TagSelection();
      ts.generateFrame();
    } catch (IOException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
  }

}
