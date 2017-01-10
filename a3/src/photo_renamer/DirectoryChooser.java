package photo_renamer;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * A class that displays all directories and their contents on a user's
 * computer.
 * 
 * @author monicaiqbal
 * @author fareenalimaquayyaim
 *
 */
@SuppressWarnings("serial")
public class DirectoryChooser extends JPanel {

	/**
	 * An instance of PhotoRenamer that is used to call instance methods that
	 * help generate the list of images.
	 */
	private final PhotoRenamer pr;

	/**
	 * Create a new DirectoryChooser.
	 * @throws IOException 
	 */
	public DirectoryChooser() throws IOException {
		pr = new PhotoRenamer();
	}

	/**
	 * Create the window that contains all directories and their contents. If a
	 * directory is selected, display the chosen directory's path and all images
	 * found in that directory as well as its subdirectories. A new window opens
	 * immediately.
	 */
	public void createDirFrame() {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		int result = chooser.showOpenDialog(this);
		JLabel label = new JLabel();

		// enter this statement if user chooses a directory
		if (result == JFileChooser.APPROVE_OPTION) {
			File dir = chooser.getSelectedFile();
			JFrame f = new JFrame("Image Listing");
			JTextArea images = new JTextArea(15, 50);
			images.setEditable(false);
			JScrollPane sp = new JScrollPane(images);
			label.setText("Selected directory: " + dir.getAbsolutePath());

			// temporary text is set while list of images is loading
			images.setText("Getting list of images...");

			// call PhotoRenamer instance method to build list of images using
			// selected directory
			pr.buildListOfImages(dir);

			// add String representation of image list to JTextArea
			String contents = pr.viewListOfImages();
			images.setText(contents);

			// put all contents together in one window
			f.add(label, BorderLayout.PAGE_START);
			f.add(sp, BorderLayout.CENTER);
			f.setLocationRelativeTo(null);
			f.pack();
			f.setVisible(true);
		} else {
			label.setText("No directory selected");
		}
	}

}
