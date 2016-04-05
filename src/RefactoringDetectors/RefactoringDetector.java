package RefactoringDetectors;

import javax.swing.JFrame;

/*	This is the refactoring detector interface. Any detector added
 * 	must implement this interface. The getDetectorFrame must return
 * 	the JFrame which presents the results of the detection process to
 * 	the user. The opportunitiesFound method returns true of false 
 * 	depending on the detection outcome.
 */
public interface RefactoringDetector {
	
	JFrame getDetectorFrame(Object[] dataForIdentification);
	boolean opportunitiesFound();
}
