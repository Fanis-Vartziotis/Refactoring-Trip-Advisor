package MapRegions;

import RefactoringDetectors.ExtractMethodIdentification;
import RefactoringDetectors.InlineMethodIdentification;
import RefactoringDetectors.InlineTempIdentification;
import RefactoringDetectors.IntroduceExplainingVariableIdentification;
import RefactoringDetectors.MoveMethodIdentification;
import RefactoringDetectors.RemoveAssignmentsToParametersIndentification;
import RefactoringDetectors.ReplaceMethodWithMethodObjectIdentification;
import RefactoringDetectors.ReplaceTempWithQueryIdentification;
import RefactoringDetectors.SplitTemporaryVariableIdentification;
import UI.RefactoringInfoWindow;
import UI.MainWindow;

import com.mxgraph.model.*;
import com.mxgraph.view.*;

import org.eclipse.ui.IWorkbenchPage;

/*	This class handles the creation of the Method Composition map.
 * 	It's responsible for the graph creation as well as the 3-slide information window
 * 	for each of its refactorings.
 */
public class MethodComposition extends RefactoringRegion{

	public MethodComposition(MainWindow p, IWorkbenchPage pg) 
	{
		super(p,pg,11,2);	
		refactoringLabel.setText("Method Composition");
	}

	public void popUp(mxCell cell)
	{
		//System.out.println("ID = " + cell.getId());
		String pathPrefix = "/MethodCompositionView/images/";
		
		switch(cell.getId())
		{
			case "v1" :
				refactoringOptions.setRefactoringTitle("Separate Query from Modifier");
				refactoringOptions.setMotivationPath("/MethodCallImprovementView/images/SeparateQueryMotivation.png");
				refactoringOptions.setExamplePath("/MethodCallImprovementView/images/SeparateQueryExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow separateQuery = new RefactoringInfoWindow(refactoringOptions);
				separateQuery.setVisible(true);
				break;
			case "v2" :
				dataForIdentification= new Object[1];
				dataForIdentification[0] = map;
				
				refactoringOptions.setRefactoringTitle("Inline Temp");
				refactoringOptions.setMotivationPath(pathPrefix+"InlineTempMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"InlineTempExample.png");
				refactoringOptions.setIdentificationPath("/images/CustomIdentificationPlusTool.png");
				refactoringOptions.setDetector(new InlineTempIdentification());
				refactoringOptions.setDetectorData(dataForIdentification);
				
				RefactoringInfoWindow inlineTemp = new RefactoringInfoWindow(refactoringOptions);
				inlineTemp.setVisible(true);
				break;
			case "v3" :
				dataForIdentification= new Object[3];
				dataForIdentification[0] = page;
				dataForIdentification[1] = display;
				dataForIdentification[2] = map;
				
				refactoringOptions.setRefactoringTitle("Introduce Explaining Variable");
				refactoringOptions.setMotivationPath(pathPrefix+"IntroduceExplainingMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"IntroduceExplainingExample.png");
				refactoringOptions.setIdentificationPath("/images/CustomIdentificationNoTool.png");
				refactoringOptions.setDetector(new IntroduceExplainingVariableIdentification());
				refactoringOptions.setDetectorData(dataForIdentification);
				
				RefactoringInfoWindow introduceVariable= new RefactoringInfoWindow(refactoringOptions);
				introduceVariable.setVisible(true);
				break;
			case "v4" :
				dataForIdentification= new Object[1];
				dataForIdentification[0] = map;
				
				refactoringOptions.setRefactoringTitle("Split Temporary Variable");
				refactoringOptions.setMotivationPath(pathPrefix+"SplitVariableMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"SplitVariableExample.png");
				refactoringOptions.setIdentificationPath("/images/CustomIdentificationNoTool.png");
				refactoringOptions.setDetector(new SplitTemporaryVariableIdentification());
				refactoringOptions.setDetectorData(dataForIdentification);
				
				RefactoringInfoWindow splitTemporaryVariable = new RefactoringInfoWindow(refactoringOptions);
				splitTemporaryVariable.setVisible(true);
				break;
			case "v5" :
				dataForIdentification= new Object[1];
				dataForIdentification[0] = map;
				
				refactoringOptions.setRefactoringTitle("Inline Method");
				refactoringOptions.setMotivationPath(pathPrefix+"InlineMethodMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"InlineMethodExample.png");
				refactoringOptions.setIdentificationPath("/images/CustomIdentificationPlusTool.png");
				refactoringOptions.setDetector(new InlineMethodIdentification());
				refactoringOptions.setDetectorData(dataForIdentification);
				
				RefactoringInfoWindow inlineMethod = new RefactoringInfoWindow(refactoringOptions);
				inlineMethod.setVisible(true);
				break;
			case "v6" :
				dataForIdentification= new Object[1];
				dataForIdentification[0] = map;
				
				refactoringOptions.setRefactoringTitle("Replace Temp with Query");
				refactoringOptions.setMotivationPath(pathPrefix+"ReplaceTempMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"ReplaceTempExample.png");
				refactoringOptions.setIdentificationPath("/images/CustomIdentificationPlusTool.png");
				refactoringOptions.setDetector(new ReplaceTempWithQueryIdentification());
				refactoringOptions.setDetectorData(dataForIdentification);
				
				RefactoringInfoWindow replaceTemp = new RefactoringInfoWindow(refactoringOptions);
				replaceTemp.setVisible(true);
				break;
			case "v7" :
				dataForIdentification= new Object[3];
				dataForIdentification[0] = page;
				dataForIdentification[1] = display;
				dataForIdentification[2] = map;
				
				refactoringOptions.setRefactoringTitle("Replace Method with Method Object");
				refactoringOptions.setMotivationPath(pathPrefix+"ReplaceMethodMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"ReplaceMethodExample.png");
				refactoringOptions.setIdentificationPath("/images/CustomIdentificationPlusTool.png");
				refactoringOptions.setDetector(new ReplaceMethodWithMethodObjectIdentification());
				refactoringOptions.setDetectorData(dataForIdentification);
				
				RefactoringInfoWindow replaceMethod = new RefactoringInfoWindow(refactoringOptions);
				replaceMethod.setVisible(true);
				break;
			case "v8" :
				dataForIdentification= new Object[3];
				dataForIdentification[0] = page;
				dataForIdentification[1] = display;
				dataForIdentification[2] = map;
				
				refactoringOptions.setRefactoringTitle("Extract Method");
				refactoringOptions.setMotivationPath(pathPrefix+"ExtractMethodMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"ExtractMethodExample.png");
				refactoringOptions.setIdentificationPath("/images/CustomIdentificationPlusTool.png");
				refactoringOptions.setDetector(new ExtractMethodIdentification());
				refactoringOptions.setDetectorData(dataForIdentification);
				
				RefactoringInfoWindow extractMethod = new RefactoringInfoWindow(refactoringOptions);
				extractMethod.setVisible(true);
				break;
			case "v9" :
				dataForIdentification= new Object[1];
				dataForIdentification[0] = map;
				
				refactoringOptions.setRefactoringTitle("Remove Assignments to Parameters");
				refactoringOptions.setMotivationPath(pathPrefix+"RemoveAssignmentsMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"RemoveAssignmentsExample.png");
				refactoringOptions.setIdentificationPath("/images/CustomIdentificationNoTool.png");
				refactoringOptions.setDetector(new RemoveAssignmentsToParametersIndentification());
				refactoringOptions.setDetectorData(dataForIdentification);
				
				RefactoringInfoWindow removeAssignements = new RefactoringInfoWindow(refactoringOptions);
				removeAssignements.setVisible(true);
				break;
			case "v10" :
				refactoringOptions.setRefactoringTitle("Substitute Algorithm");
				refactoringOptions.setMotivationPath(pathPrefix+"SubstituteAlgorithmMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"SubstituteAlgorithmExample.png");
				refactoringOptions.setIdentificationPath("");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow substituteAlgorithm = new RefactoringInfoWindow(refactoringOptions);
				substituteAlgorithm.setVisible(true);
				break;
			case "v11" :
				dataForIdentification = new Object[1];
				dataForIdentification[0] = map;
				
				refactoringOptions.setRefactoringTitle("Move Method");
				refactoringOptions.setMotivationPath("/FeatureMovementBetweenObjectsView/images/MoveMethodMotivation.png");
				refactoringOptions.setExamplePath("/FeatureMovementBetweenObjectsView/images/MoveMethodExample.png");
				refactoringOptions.setIdentificationPath("/images/CustomIdentificationPlusTool.png");
				refactoringOptions.setDetector(new MoveMethodIdentification());
				refactoringOptions.setDetectorData(dataForIdentification);
				
				RefactoringInfoWindow moveMethod = new RefactoringInfoWindow(refactoringOptions);
				moveMethod.setVisible(true);
				break;
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
				externalVertices[0] = vertices[0] = (mxCell) g.insertVertex(p, "v1", "Separate Query\nfrom Modifier", 125, 87-50, 100, 50, "fillColor=#B0B0B6;fontColor=black");
				externalVertices[1] = vertices[10] = (mxCell) g.insertVertex(p, "v11", "Move Method", 875, 437-50, 100, 50, "fillColor=#B0B0B6;fontColor=black");
						
				succession.addRelation(vertices[0], vertices[5]);
				succession.addRelation(vertices[7],vertices[10]);
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
			vertices[1] = (mxCell) g.insertVertex(p, "v2", "Inline Temp", 125, 612-50, 100, 50, "circle");
			vertices[2] = (mxCell) g.insertVertex(p, "v3", "Introduce\nExplaining Variable", 125, 437-50, 100, 50, "circle");
			vertices[3] = (mxCell) g.insertVertex(p, "v4", "Split\nTemporary Variable", 125, 262-50, 100, 50, "circle");
			vertices[4] = (mxCell) g.insertVertex(p, "v5", "Inline Method", 375, 87-50, 100, 50, "circle");
			vertices[5] = (mxCell) g.insertVertex(p, "v6", "Replace Temp\nwith Query", 375, 350-50, 100, 50, "circle");
			vertices[6] = (mxCell) g.insertVertex(p, "v7", "Replace Method\nwith Method Object", 625, 87-50, 100, 50, "circle");
			vertices[7] = (mxCell) g.insertVertex(p, "v8", "Extract Method", 625, 437-50, 100, 50, "circle");
			vertices[8] = (mxCell) g.insertVertex(p, "v9", "Remove\nAssignments to\nParameters", 875, 262-50, 100, 50, "circle");
			vertices[9] = (mxCell) g.insertVertex(p, "v10", "Substitute Algorithm", 875, 612-50, 100, 50, "circle");

			//Inner region connections
			succession.addRelation(vertices[1], vertices[7]);
			succession.addRelation(vertices[2], vertices[5]);
			succession.addRelation(vertices[3], vertices[5]);
			succession.addRelation(vertices[4], vertices[6]);
			succession.addRelation(vertices[4], vertices[7]);
			succession.addRelation(vertices[5], vertices[7]);
			succession.addRelation(vertices[7], vertices[8]);
			succession.addRelation(vertices[7], vertices[9]);
			partOf.addRelation(vertices[1], vertices[5]);
			insteadOf.addRelation(vertices[2], vertices[7]);
			insteadOf.addRelation(vertices[6], vertices[7]);
			
			succession.connectEdges(g, p);
			partOf.connectEdges(g, p);
			insteadOf.connectEdges(g, p);
		}
		finally
		{
			g.getModel().endUpdate();
		}

		graph.colorizeVertices(null, succession,partOf,insteadOf);
	}

}
