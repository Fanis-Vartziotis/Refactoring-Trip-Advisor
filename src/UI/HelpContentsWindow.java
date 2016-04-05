package UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.Color;

/*	This class handles the creation of the Help Window of Refactoring Trip Advisor.
 * 	It simply displays an image containing a text guide on RTA. The window title and
 * 	image path are passed as arguments in this class' constructor.   
 */

public class HelpContentsWindow extends JFrame {

	private JPanel contentPane;

	public HelpContentsWindow(String title, String imagePath) {
		setLocation(new Point(300, 0));
		setResizable(true);
		setSize(new Dimension(750, 750));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle(title);
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/repair.png")));
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		Image helpImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource(imagePath));
		
		JLabel helpLabel = new JLabel(new ImageIcon(helpImage));
		helpLabel.setOpaque(true);
		helpLabel.setBackground(Color.WHITE);
		
		JScrollPane scrollPane = new JScrollPane(helpLabel);
		scrollPane.setBackground(Color.WHITE);
		
		contentPane.add(scrollPane, BorderLayout.CENTER);
	}

}
