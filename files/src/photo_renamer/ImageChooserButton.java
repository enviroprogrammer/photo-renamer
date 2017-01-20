package photo_renamer;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;

/**
 * A button that selects images from the directory. This class follows the
 * Observer Design Pattern, with ActionListener as the Observer and the button
 * as the Observable object.
 * 
 * @author monicaiqbal
 * @author fareenalimaquayyaim
 *
 */

@SuppressWarnings("serial")
public class ImageChooserButton extends JButton implements ActionListener {

  /**
   * Create a new ImageChooserButton with text.
   * 
   * @param text the text that will appear on the button
   */

  public ImageChooserButton(String text) {
    super(text);
    this.addActionListener(this);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    PhotoRenamer pr;
    try {
      pr = new PhotoRenamer();
      pr.createImageChooser();
    } catch (IOException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
  }

}
