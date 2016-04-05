package UI;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import DataHandling.RefactoringOptions;
import RefactoringDetectors.RefactoringDetector;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.awt.Cursor;

/*	This class handles the creation of the 3-slide information window for each refactoring.
 * 	Each of the 3-slides is an image displayed in the JLabel picLabel component. The user can
 * 	browse between slides using the arrow keys on the top left of the window. The first slide
 * 	contains the "Motivation" section that describes why you should perform the said refactoring.
 * 	The second slide is the "Example" section containing a simple code example on how to perform the
 * 	said refactoring. The third and last slide contains information about the available automated
 * 	tools for this refactoring. If there automated detection for the said refactoring is provided by RTA then
 *  a "Start Identification" button (JButton btnNewButton_2) appears on the third slide. If RTA does not
 *  provide with automated detection then a message prompting the user to click on "here" (JLabel hereLabel
 *  appears containing a link to a paper on refactoring identification.
 *  
 *  The data needed for the creation window (paths for each image slide, window title, corresponding detector 
 *  along with its data) are passed on this class' constructor via a RefactoringOptions object.
 */
public class RefactoringInfoWindow extends JFrame {

	private JPanel contentPane;
	private final int MOTIVATION = 1, EXAMPLE = 2, IDENTIFICATION = 3;
	private int currentSlide;

	public RefactoringInfoWindow(final RefactoringOptions options) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocation(new Point(100, 0));
		setResizable(true);
		//setSize(new Dimension(1360, 810));
		setSize(new Dimension(1100, 660));
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);	
		setContentPane(contentPane);
		setTitle(options.getRefactoringTitle());
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/repair.png")));		
		
		final Image motivation = Toolkit.getDefaultToolkit().getImage(getClass().getResource(options.getMotivationPath()));
		final Image example = Toolkit.getDefaultToolkit().getImage(getClass().getResource(options.getExamplePath()));
		final Image identification = Toolkit.getDefaultToolkit().getImage(getClass().getResource(options.getIdentificationPath()));;

		Image leftArrow = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/leftArrowSmall.png"));
		Image rightArrow = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/rightArrowSmall.png"));
		
		currentSlide = MOTIVATION;
		final JLabel picLabel = new JLabel(new ImageIcon(motivation));
		//picLabel.setBounds(0, 0, 1354, 782);			
		picLabel.setBounds(0, 0, 1085, 625);
		
		final JLabel hereLabel = new JLabel("here.");
		hereLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		hereLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try 
				{
					URL url = new URL("http://www.researchgate.net/publication/264791334_Identifying_Refactoring_Opportunities_in_Object-Oriented_Code_A_Systematic_Literature_Review");
					
					Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
					
					if((desktop != null)&&(desktop.isSupported(Desktop.Action.BROWSE)))
					{
						desktop.browse(url.toURI());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		hereLabel.setForeground(Color.BLUE);
		hereLabel.setFont(new Font("Calibri", Font.PLAIN, 35));
		hereLabel.setBounds(928, 299, 95, 44);
		hereLabel.setVisible(false);
		contentPane.add(hereLabel);
		
		final JButton btnNewButton = new JButton();
		btnNewButton.setToolTipText("Previous Page");
		final JButton btnNewButton_1 = new JButton();
		btnNewButton_1.setToolTipText("Next Page");
		
		final JButton btnNewButton_2 = new JButton("Start Identification");
		btnNewButton_2.setToolTipText("Identify opportunities for this refactoring.");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(options.getDetectorData()!=null)
				{
					if(options.getMap().getSelectionInfo().getSelectedProject() == null)
					{
						JOptionPane.showMessageDialog(contentPane, "You need to select a java file or a package from you Package Explorer first.", "Warning", JOptionPane.WARNING_MESSAGE);
					}
					else 
					{
						RefactoringDetector detector = options.getDetector();
						//Start the identification-detection process
						JFrame identificationFrame = detector.getDetectorFrame(options.getDetectorData());
						//Check to see if any opportunities detected
						if(detector.opportunitiesFound())
						{		
							//View the identification-detection window containing the suggested results
							identificationFrame.setVisible(true);
						}
						else
							JOptionPane.showMessageDialog(contentPane, "There are no opportunities for this refactoring choice.", "Information", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});
		btnNewButton_2.setFont(new Font("Arial", Font.BOLD, 16));
		if (options.getIdentificationPath().equals("/images/CustomIdentificationPlusTool.png")) 
		{
			btnNewButton_2.setBounds(435, 211, 188, 48);
		}
		else 
		{
			btnNewButton_2.setBounds(435, 311, 188, 48);
		}
		btnNewButton_2.setVisible(false);
		contentPane.add(btnNewButton_2);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentSlide==EXAMPLE)
				{
					picLabel.setIcon(new ImageIcon(motivation));
					currentSlide=MOTIVATION;
					btnNewButton_2.setVisible(false);
					btnNewButton.setEnabled(false);
					if (options.getIdentificationPath().equals("")) 
					{
						btnNewButton_1.setEnabled(true);
					}
				}
				else 
				{
					picLabel.setIcon(new ImageIcon(example));
					currentSlide=EXAMPLE;
					btnNewButton_2.setVisible(false);
					btnNewButton_1.setEnabled(true);
					if (hereLabel.isVisible()) 
					{
						hereLabel.setVisible(false);
					}
				}
			}
		});
		btnNewButton.setFocusable(false);
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setBounds(18, 11, 55, 55);
		btnNewButton.setIcon(new ImageIcon(leftArrow));
		btnNewButton.setEnabled(false);
		contentPane.add(btnNewButton);

		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentSlide==MOTIVATION)
				{
					picLabel.setIcon(new ImageIcon(example));
					currentSlide = EXAMPLE;
					btnNewButton_2.setVisible(false);
					btnNewButton.setEnabled(true);
					if (options.getIdentificationPath().equals("")) 
					{
						btnNewButton_1.setEnabled(false);
					}
				}
				else
				{
					picLabel.setIcon(new ImageIcon(identification));
					currentSlide = IDENTIFICATION;
					
					if (options.getIdentificationPath().startsWith("/images/CustomIdentification")) {
						btnNewButton_2.setVisible(true);
					}
					
					btnNewButton_1.setEnabled(false);
					
					if(options.getIdentificationPath().equals("/images/NoIdentificationNoTool.png"))
					{
						hereLabel.setVisible(true);
					}
				}
			}
		});
		btnNewButton_1.setFocusable(false);
		btnNewButton_1.setIcon(new ImageIcon(rightArrow));
		btnNewButton_1.setBackground(Color.WHITE);
		btnNewButton_1.setBounds(83, 11, 55, 55);
		contentPane.add(btnNewButton_1);
		
		contentPane.add(picLabel);	
	}
}
