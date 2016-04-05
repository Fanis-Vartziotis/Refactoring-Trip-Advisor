package MapRegions;


import org.eclipse.ui.IWorkbenchPage;

import RefactoringDetectors.ExtractMethodIdentification;
import RefactoringDetectors.MoveMethodIdentification;
import UI.RefactoringInfoWindow;
import UI.MainWindow;

import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;

/*	This class handles the creation of the Conditional Expression Simplification map.
 * 	It's responsible for the graph creation as well as the 3-slide information window
 * 	for each of its refactorings.
 */
public class ConditionalExpressionSimplification extends RefactoringRegion {

	public ConditionalExpressionSimplification(MainWindow p, IWorkbenchPage pg)
	{
		super(p,pg,12,4);
		refactoringLabel.setText("Conditional Expression Simplification");
	}
	
	public void popUp(mxCell cell)
	{
		String pathPrefix = "/ConditionalExpressionSimplificationView/images/";
		switch(cell.getId())
		{
			case "v1" :
				refactoringOptions.setRefactoringTitle("Consolidate Duplicate Conditional Fragments");
				refactoringOptions.setMotivationPath(pathPrefix+"ConsolidateDuplicateFragmentsMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"ConsolidateDuplicateFragmentsExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow frameFragments = new RefactoringInfoWindow(refactoringOptions);
				frameFragments.setVisible(true);
				break;
			case "v2" :
				refactoringOptions.setRefactoringTitle("Replace Nested Conditional with Guard Clauses");
				refactoringOptions.setMotivationPath(pathPrefix+"ReplaceNestedConditionalMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"ReplaceNestedConditionalExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow frameNestedConditional = new RefactoringInfoWindow(refactoringOptions);
				frameNestedConditional.setVisible(true);
				break;	
			case "v3" :
				refactoringOptions.setRefactoringTitle("Consolidate Conditional Expression");
				refactoringOptions.setMotivationPath(pathPrefix+"ConsolidateConditionalExpressionMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"ConsolidateConditionalExpressionExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow frameConditionalExpression = new RefactoringInfoWindow(refactoringOptions);
				frameConditionalExpression.setVisible(true);
				break;
			case "v4" :
				refactoringOptions.setRefactoringTitle("Introduce Assertion");
				refactoringOptions.setMotivationPath(pathPrefix+"IntroduceAssertionMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"IntroduceAssertionExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow frameAssertion = new RefactoringInfoWindow(refactoringOptions);
				frameAssertion.setVisible(true);
				break;
			case "v5" :
				refactoringOptions.setRefactoringTitle("Decompose Conditional");
				refactoringOptions.setMotivationPath(pathPrefix+"DecomposeConditionalMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"DecomposeConditionalExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow frameDecomposeConditional = new RefactoringInfoWindow(refactoringOptions);
				frameDecomposeConditional.setVisible(true);
				break;
			case "v6" :
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
				break;
			case "v7" :
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
			case "v8" :
				refactoringOptions.setRefactoringTitle("Replace Conditional with Polymorphism");
				refactoringOptions.setMotivationPath(pathPrefix+"ReplaceConditionalWithPolymorphismMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"ReplaceConditionalWithPolymorphismExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow framePolymorphism = new RefactoringInfoWindow(refactoringOptions);
				framePolymorphism.setVisible(true);
				break;
			case "v9" :
				refactoringOptions.setRefactoringTitle("Replace Type Code with Subclasses");
				refactoringOptions.setMotivationPath("/DataOrganizationView/images/ReplaceTypeCodeWithSubclassesMotivation.png");
				refactoringOptions.setExamplePath("/DataOrganizationView/images/ReplaceTypeCodeWithSubclassesExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow frameCodeWithSubclasses = new RefactoringInfoWindow(refactoringOptions);
				frameCodeWithSubclasses.setVisible(true);
				break;
			case "v10" :
				refactoringOptions.setRefactoringTitle("Remove Control Flag");
				refactoringOptions.setMotivationPath(pathPrefix+"RemoveControlFlagMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"RemoveControlFlagExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow frameControlFlag = new RefactoringInfoWindow(refactoringOptions);
				frameControlFlag.setVisible(true);
				break;
			case "v11" :
				refactoringOptions.setRefactoringTitle("Introduce Null Object");
				refactoringOptions.setMotivationPath(pathPrefix+"IntroduceNullObjectMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"IntroduceNullObjectExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow frameNullObject = new RefactoringInfoWindow(refactoringOptions);
				frameNullObject.setVisible(true);
				break;
			case "v12" :
				refactoringOptions.setRefactoringTitle("Replace Type Code with State/Strategy");
				refactoringOptions.setMotivationPath("/DataOrganizationView/images/ReplaceTypeCodeWithStateMotivation.png");
				refactoringOptions.setExamplePath("/DataOrganizationView/images/ReplaceTypeCodeWithStateExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow frameWithState = new RefactoringInfoWindow(refactoringOptions);
				frameWithState.setVisible(true);
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
				externalVertices[0] = vertices[5] = (mxCell) g.insertVertex(p, "v6", "Extract Method", 250, 437, 100, 50, "fillColor=#B0B0B6;fontColor=black");
				externalVertices[1] = vertices[6] = (mxCell) g.insertVertex(p, "v7", "Move Method", 505, 87, 100, 50, "fillColor=#B0B0B6;fontColor=black");
				externalVertices[2] = vertices[8] = (mxCell) g.insertVertex(p, "v9", "Replace Typecode\nwith Subclasses", 505, 612, 100, 50, "fillColor=#B0B0B6;fontColor=black");
				externalVertices[3] = vertices[11] = (mxCell) g.insertVertex(p, "v12", "Replace Typecode\nwith\nState/Strategy", 875, 612, 100, 50, "fillColor=#B0B0B6;fontColor=black");
				
				succession.addRelation(vertices[2],vertices[5]);
				succession.addRelation(vertices[3],vertices[5]);
				succession.addRelation(vertices[8],vertices[7]);
				succession.addRelation(vertices[11],vertices[7]);
				partOf.addRelation(vertices[5],vertices[4]);
				partOf.addRelation(vertices[5],vertices[7]);
				partOf.addRelation(vertices[6],vertices[7]);
				
				succession.connectEdges(g, p);
				partOf.connectEdges(g, p);
			}
			else 
			{
				g.removeCells(externalVertices);
				
				for (int i = 0; i < externalVertices.length; i++) {
					succession.removeRelatedEdges(externalVertices[i]);
					partOf.removeRelatedEdges(externalVertices[i]);
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
			vertices[0] = (mxCell) g.insertVertex(p, "v1", "Consolidate Duplicate\nConditional Fragments", 25, 87, 110, 55, "circle");			
			vertices[1] = (mxCell) g.insertVertex(p, "v2", "Replace Nested\nConditional With\nGuard Clauses", 25, 262, 108, 58, "circle");
			vertices[2] = (mxCell) g.insertVertex(p, "v3", "Consolidate\nConditional Expression", 25, 437, 108, 58, "circle");
			vertices[3] = (mxCell) g.insertVertex(p, "v4", "Introduce Assertion", 25, 612, 100, 50, "circle");
			vertices[4] = (mxCell) g.insertVertex(p, "v5", "Decompose\nConditional", 250, 262, 100, 50, "circle");		
			vertices[7] = (mxCell) g.insertVertex(p, "v8", "Replace Conditional\nWith Polymorphism", 500, 437, 110, 55, "circle");
			vertices[9] = (mxCell) g.insertVertex(p, "v10", "Remove\nControl Flag", 875, 87, 100, 50, "circle");
			vertices[10] = (mxCell) g.insertVertex(p, "v11", "Introduce Null Object", 875, 437, 100, 50, "circle");
			
			//Outer region connections
			succession.addRelation(vertices[0],vertices[4]);
			succession.addRelation(vertices[1],vertices[4]);
			succession.addRelation(vertices[2],vertices[4]);
			
			succession.connectEdges(g, p);
		}
		finally
		{
			g.getModel().endUpdate();
		}
	}
}
