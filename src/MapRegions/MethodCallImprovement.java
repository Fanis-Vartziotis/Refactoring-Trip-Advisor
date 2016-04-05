package MapRegions;

import org.eclipse.ui.IWorkbenchPage;

import RefactoringDetectors.ExtractMethodIdentification;
import RefactoringDetectors.IntroduceParameterObjectIdentification;
import RefactoringDetectors.MoveMethodIdentification;
import UI.RefactoringInfoWindow;
import UI.MainWindow;

import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;

/*	This class handles the creation of the Method Call Improvement map.
 * 	It's responsible for the graph creation as well as the 3-slide information window
 * 	for each of its refactorings.
 */
public class MethodCallImprovement extends RefactoringRegion{
	
	public MethodCallImprovement(MainWindow p, IWorkbenchPage pg)
	{
		super(p,pg,19,4);	
		refactoringLabel.setText("Method Call Improvement");
	}
	
	public void popUp(mxCell cell)
	{
		String pathPrefix = "/MethodCallImprovementView/images/";
		switch(cell.getId())
		{
			case "v1" :
				refactoringOptions.setRefactoringTitle("Replace Parameter with Method");
				refactoringOptions.setMotivationPath(pathPrefix+"ReplaceParameterWithMethodMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"ReplaceParameterWithMethodExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow replaceParameterWithMethod = new RefactoringInfoWindow(refactoringOptions);
				replaceParameterWithMethod.setVisible(true);
				break;
			case "v2" :
				refactoringOptions.setRefactoringTitle("Add Parameter");
				refactoringOptions.setMotivationPath(pathPrefix+"AddParameterMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"AddParameterExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow addParameter = new RefactoringInfoWindow(refactoringOptions);
				addParameter.setVisible(true);
				break;
			case "v3" :
				refactoringOptions.setRefactoringTitle("Parameterize Method");
				refactoringOptions.setMotivationPath(pathPrefix+"ParameterizeMethodMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"ParameterizeMethodExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow parameterizeMethod = new RefactoringInfoWindow(refactoringOptions);
				parameterizeMethod.setVisible(true);
				break;
			case "v4" :
				refactoringOptions.setRefactoringTitle("Separate Query from Modifier");
				refactoringOptions.setMotivationPath(pathPrefix+"SeparateQueryMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"SeparateQueryExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow separateQuery = new RefactoringInfoWindow(refactoringOptions);
				separateQuery.setVisible(true);
				break;
			case "v5" :
				refactoringOptions.setRefactoringTitle("Remove Parameter");
				refactoringOptions.setMotivationPath(pathPrefix+"RemoveParameterMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"RemoveParameterExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow removeParameter =  new RefactoringInfoWindow(refactoringOptions);
				removeParameter.setVisible(true);
				break;
			case "v6" :
				dataForIdentification= new Object[1];
				dataForIdentification[0] = map;
				
				refactoringOptions.setRefactoringTitle("Introduce Parameter Object");
				refactoringOptions.setMotivationPath(pathPrefix+"IntroduceParameterObjectMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"IntroduceParameterObjectExample.png");
				refactoringOptions.setIdentificationPath("/images/CustomIdentificationPlusTool.png");
				refactoringOptions.setDetector(new IntroduceParameterObjectIdentification());
				refactoringOptions.setDetectorData(dataForIdentification);
				
				RefactoringInfoWindow introduceParameter = new RefactoringInfoWindow(refactoringOptions);
				introduceParameter.setVisible(true);
				break;
			case "v7" :
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
			case "v8" :
				refactoringOptions.setRefactoringTitle("Remove Setting Method");
				refactoringOptions.setMotivationPath(pathPrefix+"RemoveSettingMethodMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"RemoveSettingMethodExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow removeSettingMethod = new RefactoringInfoWindow(refactoringOptions);
				removeSettingMethod.setVisible(true);
				break;
			case "v9" :
				refactoringOptions.setRefactoringTitle("Rename Method");
				refactoringOptions.setMotivationPath(pathPrefix+"RenameMethodMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"RenameMethodExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow renameMethod = new RefactoringInfoWindow(refactoringOptions);
				renameMethod.setVisible(true);
				break;
			case "v10" :
				refactoringOptions.setRefactoringTitle("Preserve Whole Object");
				refactoringOptions.setMotivationPath(pathPrefix+"PreserveWholeObjectMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"PreserveWholeObjectExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow preserveObject = new RefactoringInfoWindow(refactoringOptions);
				preserveObject.setVisible(true);
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
			case "v12" :
				refactoringOptions.setRefactoringTitle("Hide Method");
				refactoringOptions.setMotivationPath(pathPrefix+"HideMethodMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"HideMethodExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow hideMethod = new RefactoringInfoWindow(refactoringOptions);
				hideMethod.setVisible(true);
				break;
			case "v13" :
				refactoringOptions.setRefactoringTitle("Change Value to Reference");
				refactoringOptions.setMotivationPath("/DataOrganizationView/images/ChangeValueToReferenceMotivation.png");
				refactoringOptions.setExamplePath("/DataOrganizationView/images/ChangeValueToReferenceExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow chageValueToReference = new RefactoringInfoWindow(refactoringOptions);
				chageValueToReference.setVisible(true);
				break;
			case "v14" :
				refactoringOptions.setRefactoringTitle("Replace Constructor with Factory Method");
				refactoringOptions.setMotivationPath(pathPrefix+"ReplaceWithFactoryMethodMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"ReplaceWithFactoryMethodExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow replaceWithFactory = new RefactoringInfoWindow(refactoringOptions);
				replaceWithFactory.setVisible(true);
				break;
			case "v15" :
				refactoringOptions.setRefactoringTitle("Replace Parameter with Explicit Methods");
				refactoringOptions.setMotivationPath(pathPrefix+"ReplaceWithExplicitMethodsMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"ReplaceWithExplicitMethodsExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow replaceWithExplicitMethods = new RefactoringInfoWindow(refactoringOptions);
				replaceWithExplicitMethods.setVisible(true);
				break;
			case "v16" :
				refactoringOptions.setRefactoringTitle("Replace Conditional with Polymorphism");
				refactoringOptions.setMotivationPath("/ConditionalExpressionSimplificationView/images/ReplaceConditionalWithPolymorphismMotivation.png");
				refactoringOptions.setExamplePath("/ConditionalExpressionSimplificationView/images/ReplaceConditionalWithPolymorphismExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow replaceWithPolymorphism = new RefactoringInfoWindow(refactoringOptions);
				replaceWithPolymorphism.setVisible(true);
				break;
			case "v17" :
				refactoringOptions.setRefactoringTitle("Replace Exception with Test");
				refactoringOptions.setMotivationPath(pathPrefix+"ReplaceExceptionWithTestMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"ReplaceExceptionWithTestExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow replaceWithTest = new RefactoringInfoWindow(refactoringOptions);
				replaceWithTest.setVisible(true);
				break;
			case "v18" :
				refactoringOptions.setRefactoringTitle("Replace Error Code with Exception");
				refactoringOptions.setMotivationPath(pathPrefix+"ReplaceErrorCodeWithExceptionMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"ReplaceErrorCodeWithExceptionExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow replaceErrorCode = new RefactoringInfoWindow(refactoringOptions);
				replaceErrorCode.setVisible(true);
				break;
			case "v19" :
				refactoringOptions.setRefactoringTitle("Encapsulate Downcast");
				refactoringOptions.setMotivationPath(pathPrefix+"EncapsulateDowncastMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"EncapsulateDowncastExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow encapsulateDowncast = new RefactoringInfoWindow(refactoringOptions);
				encapsulateDowncast.setVisible(true);
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
				externalVertices[0] = vertices[6] = (mxCell) g.insertVertex(p, "v7", "Extract Method", 200, 612, 100, 50, "fillColor=#B0B0B6;fontColor=black");
				externalVertices[1] = vertices[10] = (mxCell) g.insertVertex(p, "v11", "Move Method", 375, 612, 100, 50, "fillColor=#B0B0B6;fontColor=black");
				externalVertices[2] = vertices[12] = (mxCell) g.insertVertex(p, "v13", "Change Value\nto Reference", 630, 437, 100, 50, "fillColor=#B0B0B6;fontColor=black");
				externalVertices[3] = vertices[15] = (mxCell) g.insertVertex(p, "v16", "Replace Conditional\nwith Polymorphism", 875, 262, 100, 50, "fillColor=#B0B0B6;fontColor=black");
				
				succession.addRelation(vertices[5], vertices[6]);
				succession.addRelation(vertices[5], vertices[10]);
				partOf.addRelation(vertices[6], vertices[3]);
				partOf.addRelation(vertices[13], vertices[12]);
				insteadOf.addRelation(vertices[9], vertices[10]);
				insteadOf.addRelation(vertices[14], vertices[15]);
				
				succession.connectEdges(g, p);
				partOf.connectEdges(g, p);
				insteadOf.connectEdges(g, p);
			}
			else 
			{
				g.removeCells(externalVertices);
				
				for (int i = 0; i < externalVertices.length; i++) {
					succession.removeRelatedEdges(externalVertices[i]);
					partOf.removeRelatedEdges(externalVertices[i]);
					insteadOf.removeRelatedEdges(externalVertices[i]);
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
			vertices[0] = (mxCell) g.insertVertex(p, "v1", "Replace Parameter\nwith Method", 25, 87, 100, 50, "circle");			
			vertices[1] = (mxCell) g.insertVertex(p, "v2", "Add Parameter", 25, 262, 100, 50, "circle");
			vertices[2] = (mxCell) g.insertVertex(p, "v3", "Parameterize\nMethod", 25, 437, 100, 50, "circle");
			vertices[3] = (mxCell) g.insertVertex(p, "v4", "Separate Query\nfrom Modifier", 25, 612, 100, 50, "circle");
			vertices[4] = (mxCell) g.insertVertex(p, "v5", "Remove Parameter", 205, 87, 100, 50, "circle");
			vertices[5] = (mxCell) g.insertVertex(p, "v6", "Introduce Parameter\nObject", 200, 262, 110, 55, "circle");
			vertices[7] = (mxCell) g.insertVertex(p, "v8", "Remove Setting\nMethod", 375, 87, 100, 50, "circle");
			vertices[8] = (mxCell) g.insertVertex(p, "v9", "Rename Method", 375, 262, 100, 50, "circle");
			vertices[9] = (mxCell) g.insertVertex(p, "v10", "Preserve Whole\nObject", 375, 437, 100, 50, "circle");
			vertices[11] = (mxCell) g.insertVertex(p, "v12", "Hide Method", 625, 87, 100, 50, "circle");
			vertices[13] = (mxCell) g.insertVertex(p, "v14", "Replace\nConstructor with\nFactory Method", 625, 612, 110, 60, "circle");
			vertices[14] = (mxCell) g.insertVertex(p, "v15", "Replace\nParameter with\nExplicit Method", 875, 87, 100, 50, "circle");
			vertices[16] = (mxCell) g.insertVertex(p, "v17", "Replace Exception\nwith Test", 875, 437, 100, 50, "circle");
			vertices[17] = (mxCell) g.insertVertex(p, "v18", "Replace ErrorCode\nwith Exception", 875, 612, 100, 50, "circle");
			vertices[18] = (mxCell) g.insertVertex(p, "v19", "Encapsulate\nDowncast", 625, 262, 100, 50, "circle");

			//Inner region connections
			succession.addRelation(vertices[5], vertices[9]);
			succession.addRelation(vertices[16], vertices[17]);
			succession.addRelation(vertices[17], vertices[16]);
			partOf.addRelation(vertices[4], vertices[0]);
			partOf.addRelation(vertices[1], vertices[2]);
			partOf.addRelation(vertices[4], vertices[5]);
			partOf.addRelation(vertices[1], vertices[5]);
			
			succession.connectEdges(g, p);
			partOf.connectEdges(g, p);
		}
		finally
		{
			g.getModel().endUpdate();
		}
	}
}
