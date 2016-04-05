package MapRegions;

import org.eclipse.ui.IWorkbenchPage;

import RefactoringDetectors.ExtractClassIdentification;
import RefactoringDetectors.ExtractMethodIdentification;
import RefactoringDetectors.MoveMethodIdentification;
import UI.RefactoringInfoWindow;
import UI.MainWindow;

import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;

/*	This class handles the creation of the Feature Movement Between Objects map.
 * 	It's responsible for the graph creation as well as the 3-slide information window
 * 	for each of its refactorings.
 */
public class FeatureMovementBetweenObjects extends RefactoringRegion {
	
	public FeatureMovementBetweenObjects(MainWindow p, IWorkbenchPage pg)
	{
		super(p,pg,13,5);
		refactoringLabel.setText("Feature Movement Between Objects");
	}

	public void popUp(mxCell cell)
	{
		String pathPrefix = "/FeatureMovementBetweenObjectsView/images/";
		switch(cell.getId())
		{
			case "v1" :
				refactoringOptions.setRefactoringTitle("Encapsulate Field");
				refactoringOptions.setMotivationPath("/DataOrganizationView/images/EncapsulateFieldMotivation.png");
				refactoringOptions.setExamplePath("/DataOrganizationView/images/EncapsulateFieldExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow frameEncapsulateField = new RefactoringInfoWindow(refactoringOptions);
				frameEncapsulateField.setVisible(true);
				break;
			case "v2" :
				refactoringOptions.setRefactoringTitle("Self Encapsulate Field");
				refactoringOptions.setMotivationPath("/DataOrganizationView/images/SelfEncapsulateFieldMotivation.png");
				refactoringOptions.setExamplePath("/DataOrganizationView/images/SelfEncapsulateFieldExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow frameSelfEncapsulateField = new RefactoringInfoWindow(refactoringOptions);
				frameSelfEncapsulateField.setVisible(true);
				break;
			case "v3" :
				refactoringOptions.setRefactoringTitle("Extract Interface");
				refactoringOptions.setMotivationPath("/GeneralizationImprovementView/images/ExtractInterfaceMotivation.png");
				refactoringOptions.setExamplePath("/GeneralizationImprovementView/images/ExtractInterfaceExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow frameExtractInterface = new RefactoringInfoWindow(refactoringOptions);
				frameExtractInterface.setVisible(true);
				break;
			case "v4" :
				refactoringOptions.setRefactoringTitle("Move Field");
				refactoringOptions.setMotivationPath(pathPrefix+"MoveFieldMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"MoveFieldExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow frameMoveField = new RefactoringInfoWindow(refactoringOptions);
				frameMoveField.setVisible(true);
				break;
			case "v5" :
				dataForIdentification = new Object[1];
				dataForIdentification[0] = map;
				
				refactoringOptions.setRefactoringTitle("Extract Class");
				refactoringOptions.setMotivationPath(pathPrefix+"ExtractClassMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"ExtractClassExample.png");
				refactoringOptions.setIdentificationPath("/images/CustomIdentificationPlusTool.png");
				refactoringOptions.setDetector(new ExtractClassIdentification());
				refactoringOptions.setDetectorData(dataForIdentification);
				
				RefactoringInfoWindow frameExtractClass = new RefactoringInfoWindow(refactoringOptions);
				frameExtractClass.setVisible(true);
				break;
			case "v6" :
				refactoringOptions.setRefactoringTitle("Inline Class");
				refactoringOptions.setMotivationPath(pathPrefix+"InlineClassMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"InlineClassExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow frameInlineClass = new RefactoringInfoWindow(refactoringOptions);
				frameInlineClass.setVisible(true);
				break;
			case "v7" :
				dataForIdentification = new Object[1];
				dataForIdentification[0] = map;
				
				refactoringOptions.setRefactoringTitle("Move Method");
				refactoringOptions.setMotivationPath(pathPrefix+"MoveMethodMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"MoveMethodExample.png");
				refactoringOptions.setIdentificationPath("/images/CustomIdentificationPlusTool.png");
				refactoringOptions.setDetector(new MoveMethodIdentification());
				refactoringOptions.setDetectorData(dataForIdentification);
				
				RefactoringInfoWindow moveMethod = new RefactoringInfoWindow(refactoringOptions);
				moveMethod.setVisible(true);
				break;
			case "v8" :
				refactoringOptions.setRefactoringTitle("Change Value to Reference");
				refactoringOptions.setMotivationPath("/DataOrganizationView/images/ChangeValueToReferenceMotivation.png");
				refactoringOptions.setExamplePath("/DataOrganizationView/images/ChangeValueToReferenceExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow frameValueToReference = new RefactoringInfoWindow(refactoringOptions);
				frameValueToReference.setVisible(true);
				break;
			case "v9" :
				refactoringOptions.setRefactoringTitle("Hide Delegate");
				refactoringOptions.setMotivationPath(pathPrefix+"HideDelegateMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"HideDelegateExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow frameHideDelegate = new RefactoringInfoWindow(refactoringOptions);
				frameHideDelegate.setVisible(true);
				break;
			case "v10" :
				refactoringOptions.setRefactoringTitle("Remove Middle Man");
				refactoringOptions.setMotivationPath(pathPrefix+"RemoveMiddleManMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"RemoveMiddleManExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow frameMiddleMan = new RefactoringInfoWindow(refactoringOptions);
				frameMiddleMan.setVisible(true);
				break;
			case "v11" :
				refactoringOptions.setRefactoringTitle("Introduce Foreign Method");
				refactoringOptions.setMotivationPath(pathPrefix+"IntroduceForeignMethodMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"IntroduceForeignMethodExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow frameForeignMethod = new RefactoringInfoWindow(refactoringOptions);
				frameForeignMethod.setVisible(true);
				break;
			case "v12" :
				refactoringOptions.setRefactoringTitle("Introduce Local Extension");
				refactoringOptions.setMotivationPath(pathPrefix+"IntroduceLocalExtensionMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"IntroduceLocalExtensionExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow frameExtension = new RefactoringInfoWindow(refactoringOptions);
				frameExtension.setVisible(true);
				break;
			case "v13" :
				dataForIdentification= new Object[3];
				dataForIdentification[0] = page;
				dataForIdentification[1] = display;
				dataForIdentification[2] = map;
				
				refactoringOptions.setRefactoringTitle("Extract Method");
				refactoringOptions.setMotivationPath("/MethodCompositionView/images/ExtractMethodMotivation.png");
				refactoringOptions.setExamplePath("/MethodCompositionView/images/ExtractMethodExample.png");
				refactoringOptions.setIdentificationPath("/images/CustomIdentificationPlusTool.png");
				refactoringOptions.setDetector(new ExtractMethodIdentification());
				refactoringOptions.setDetectorData(dataForIdentification);
				
				RefactoringInfoWindow extractMethod = new RefactoringInfoWindow(refactoringOptions);
				extractMethod.setVisible(true);
			default:
		}
	}
	
	public void updateGraph(boolean showExternal)
	{
		mxGraph g = graph.getMyGraph();
		Object p = graph.getParent();
		
		g.getModel().beginUpdate();
		try
		{
			if(showExternal)
			{
				externalVertices[0] = vertices[0] = (mxCell) g.insertVertex(p, "v1", "Encapsulate Field", 25, 262-50, 100, 50, "fillColor=#B0B0B6;fontColor=black");	
				externalVertices[1] = vertices[1] = (mxCell) g.insertVertex(p, "v2", "Self Encapsulate\nField", 25, 437-50, 100, 50, "fillColor=#B0B0B6;fontColor=black");
				externalVertices[2] = vertices[2] = (mxCell) g.insertVertex(p, "v3", "Extract Interface", 25, 612-50, 100, 50, "fillColor=#B0B0B6;fontColor=black");
				externalVertices[3] = vertices[7] = (mxCell) g.insertVertex(p, "v8", "Change Value\nto Reference", 625, 87-50, 100, 50, "fillColor=#B0B0B6;fontColor=black");
				externalVertices[4] = vertices[12] = (mxCell) g.insertVertex(p, "v13", "Extract Method", 750, 394-50, 100, 50, "fillColor=#B0B0B6;fontColor=black");
				
				succession.addRelation(vertices[0],vertices[3]);
				succession.addRelation(vertices[1],vertices[3]);
				succession.addRelation(vertices[2],vertices[5]);
				succession.addRelation(vertices[4],vertices[7]);
				succession.addRelation(vertices[12],vertices[6]);
				
				succession.connectEdges(g, p);
			}
			else 
			{
				g.removeCells(externalVertices);
				
				for (int i = 0; i < externalVertices.length; i++) {
					succession.removeRelatedEdges(externalVertices[i]);
				}
			}
		}
		finally
		{
			g.getModel().endUpdate();
		}
		graph.colorizeVertices(null, succession, partOf, insteadOf);
	}
	
	public void createGraph(Object p)
	{
		mxGraph g = graph.getMyGraph();
		
		g.getModel().beginUpdate();
		try
		{	
			//Add vertices to graph
			vertices[3] = (mxCell) g.insertVertex(p, "v4", "Move Field", 250, 350-50, 100, 50, "circle");
			vertices[4] = (mxCell) g.insertVertex(p, "v5", "Extract Class", 375, 87-50, 100, 50, "circle");
			vertices[5] = (mxCell) g.insertVertex(p, "v6", "Inline Class", 375, 612-50, 100, 50, "circle");
			vertices[6] = (mxCell) g.insertVertex(p, "v7", "Move Method", 500, 350-50, 100, 50, "circle");
			vertices[8] = (mxCell) g.insertVertex(p, "v9", "Hide Delegate", 625, 262-50, 100, 50, "circle");
			vertices[9] = (mxCell) g.insertVertex(p, "v10", "Remove Middle Man", 875, 262-50, 100, 50, "circle");
			vertices[10] = (mxCell) g.insertVertex(p, "v11", "Introduce Foreign\nMethod", 625, 525-50, 100, 50, "circle");
			vertices[11] = (mxCell) g.insertVertex(p, "v12", "Introduce Local\nExtension", 875, 525-50, 100, 50, "circle");

			//Inner region connections
			succession.addRelation(vertices[3],vertices[6]);
			succession.addRelation(vertices[8],vertices[4]);
			succession.addRelation(vertices[8],vertices[9]);
			succession.addRelation(vertices[9],vertices[8]);
			succession.addRelation(vertices[10],vertices[11]);
			succession.addRelation(vertices[11],vertices[10]);
			partOf.addRelation(vertices[3],vertices[4]);
			partOf.addRelation(vertices[3],vertices[5]);
			partOf.addRelation(vertices[6],vertices[4]);
			partOf.addRelation(vertices[6],vertices[5]);
			
			succession.connectEdges(g, p);
			partOf.connectEdges(g, p);
			insteadOf.connectEdges(g, p);
		}
		finally
		{
			g.getModel().endUpdate();
		}
	}
}
