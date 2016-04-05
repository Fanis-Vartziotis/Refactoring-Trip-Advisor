package DataHandling;

/*	This class contains all the data needed by RefactoringInfoWindow class
 * 	to create the 3-slide information window for each refactoring.
 */
import RefactoringDetectors.ExtractMethodIdentification;
import RefactoringDetectors.InlineTempIdentification;
import RefactoringDetectors.MoveMethodIdentification;
import RefactoringDetectors.RefactoringDetector;
import UI.MainWindow;

public class RefactoringOptions {
	private MainWindow map;
	private RefactoringDetector detector;
	private Object[] detectorData;
	private String refactoringTitle, motivationPath, examplePath, identificationPath;
	
	public RefactoringOptions(MainWindow tripMap, RefactoringDetector refDetector, Object[] decData) 
	{
		map = tripMap;
		detector = refDetector;
		detectorData = decData;
	}

	public MainWindow getMap() {
		return map;
	}

	public RefactoringDetector getDetector() {		
		switch (detector.getClass().getSimpleName()) 
		{
			case "InlineTempIdentification":
				return new InlineTempIdentification();
			case "ExtractMethodIdentification":
				return new ExtractMethodIdentification();
			case "MoveMethodIdentification":
				return new MoveMethodIdentification();
			default:
				return detector;
		}
	}

	public Object[] getDetectorData() {
		return detectorData;
	}

	public String getRefactoringTitle() {
		return refactoringTitle;
	}

	public String getMotivationPath() {
		return motivationPath;
	}

	public String getExamplePath() {
		return examplePath;
	}

	public String getIdentificationPath() {
		return identificationPath;
	}

	public void setMap(MainWindow map) {
		this.map = map;
	}

	public void setDetector(RefactoringDetector detector) {
		this.detector = detector;
	}

	public void setDetectorData(Object[] detectorData) {
		this.detectorData = detectorData;
	}

	public void setRefactoringTitle(String refactoringTitle) {
		this.refactoringTitle = refactoringTitle;
	}

	public void setMotivationPath(String motivationPath) {
		this.motivationPath = motivationPath;
	}

	public void setExamplePath(String examplePath) {
		this.examplePath = examplePath;
	}

	public void setIdentificationPath(String identificationPathString) {
		this.identificationPath = identificationPathString;
	}
	
	
}
