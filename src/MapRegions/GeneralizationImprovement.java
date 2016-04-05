package MapRegions;

import org.eclipse.ui.IWorkbenchPage;

import RefactoringDetectors.ExtractClassIdentification;
import RefactoringDetectors.ExtractMethodIdentification;
import RefactoringDetectors.MoveMethodIdentification;
import UI.RefactoringInfoWindow;
import UI.MainWindow;

import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;

/*	This class handles the creation of the Generalization Improvement map.
 * 	It's responsible for the graph creation as well as the 3-slide information window
 * 	for each of its refactorings.
 */
public class GeneralizationImprovement extends RefactoringRegion {
	
	public GeneralizationImprovement(MainWindow p, IWorkbenchPage pg)
	{
		super(p,pg,21,9);
		refactoringLabel.setText("Generalization Improvement");
	}

	public void popUp(mxCell cell)
	{
		String pathPrefix = "/GeneralizationImprovementView/images/";
		switch(cell.getId())
		{
			case "v1" :
				refactoringOptions.setRefactoringTitle("Self Encapsulate Field");
				refactoringOptions.setMotivationPath("/DataOrganizationView/images/SelfEncapsulateFieldMotivation.png");
				refactoringOptions.setExamplePath("/DataOrganizationView/images/SelfEncapsulateFieldExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow frameSelfEncapsulateField = new RefactoringInfoWindow(refactoringOptions);
				frameSelfEncapsulateField.setVisible(true);
				break;
			case "v2" :
				refactoringOptions.setRefactoringTitle("Pull Up Field");
				refactoringOptions.setMotivationPath(pathPrefix+"PullUpFieldMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"PullUpFieldExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow framePullUpField = new RefactoringInfoWindow(refactoringOptions);
				framePullUpField.setVisible(true);
				break;
			case "v3" :
				refactoringOptions.setRefactoringTitle("Collapse Hierarchy");
				refactoringOptions.setMotivationPath(pathPrefix+"CollapseHierarchyMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"CollapseHierarchyExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow frameCollapseHierarchy = new RefactoringInfoWindow(refactoringOptions);
				frameCollapseHierarchy.setVisible(true);
				break;
			case "v4" :
				refactoringOptions.setRefactoringTitle("Push Down Field");
				refactoringOptions.setMotivationPath(pathPrefix+"PushDownFieldMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"PushDownFieldExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow framePushDownField = new RefactoringInfoWindow(refactoringOptions);
				framePushDownField.setVisible(true);
				break;
			case "v5" :
				refactoringOptions.setRefactoringTitle("Replace Delegation with Inheritance");
				refactoringOptions.setMotivationPath(pathPrefix+"ReplaceDelegationWithInheritanceMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"ReplaceDelegationWithInheritanceExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow frameWithInheritance = new RefactoringInfoWindow(refactoringOptions);
				frameWithInheritance.setVisible(true);
				break;
			case "v6" :
				refactoringOptions.setRefactoringTitle("Push Down Method");
				refactoringOptions.setMotivationPath(pathPrefix+"PushDownMethodMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"PushDownMethodExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow framePushDownMethod = new RefactoringInfoWindow(refactoringOptions);
				framePushDownMethod.setVisible(true);
				break;
			case "v7" :
				refactoringOptions.setRefactoringTitle("Replace Inheritance with Delegation");
				refactoringOptions.setMotivationPath(pathPrefix+"ReplaceInheritanceWithDelegationMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"ReplaceInheritanceWithDelegationExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow frameWithDelegation = new RefactoringInfoWindow(refactoringOptions);
				frameWithDelegation.setVisible(true);
				break;
			case "v8" :
				refactoringOptions.setRefactoringTitle("Extract Interface");
				refactoringOptions.setMotivationPath(pathPrefix+"ExtractInterfaceMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"ExtractInterfaceExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow frameExtractInterface = new RefactoringInfoWindow(refactoringOptions);
				frameExtractInterface.setVisible(true);
				break;
			case "v9" :
				dataForIdentification = new Object[1];
				dataForIdentification[0] = map;
				
				refactoringOptions.setRefactoringTitle("Extract Class");
				refactoringOptions.setMotivationPath("/FeatureMovementBetweenObjectsView/images/ExtractClassMotivation.png");
				refactoringOptions.setExamplePath("/FeatureMovementBetweenObjectsView/images/ExtractClassExample.png");
				refactoringOptions.setIdentificationPath("/images/CustomIdentificationPlusTool.png");
				refactoringOptions.setDetector(new ExtractClassIdentification());
				refactoringOptions.setDetectorData(dataForIdentification);
				
				RefactoringInfoWindow frameExtractClass = new RefactoringInfoWindow(refactoringOptions);
				frameExtractClass.setVisible(true);
				break;
			case "v10" :
				refactoringOptions.setRefactoringTitle("Replace Type Code with State/Strategy");
				refactoringOptions.setMotivationPath("/DataOrganizationView/images/ReplaceTypeCodeWithStateMotivation.png");
				refactoringOptions.setExamplePath("/DataOrganizationView/images/ReplaceTypeCodeWithStateExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow frameTypeCodeWithState = new RefactoringInfoWindow(refactoringOptions);
				frameTypeCodeWithState.setVisible(true);
				break;
			case "v11" :
				refactoringOptions.setRefactoringTitle("Replace Type Code with Subclasses");
				refactoringOptions.setMotivationPath("/DataOrganizationView/images/ReplaceTypeCodeWithSubclassesMotivation.png");
				refactoringOptions.setExamplePath("/DataOrganizationView/images/ReplaceTypeCodeWithSubclassesExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow frameCodeWithSubclasses = new RefactoringInfoWindow(refactoringOptions);
				frameCodeWithSubclasses.setVisible(true);
				break;
			case "v12" :
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
			case "v13" :
				refactoringOptions.setRefactoringTitle("Replace Conditional with Polymorphism");
				refactoringOptions.setMotivationPath("/ConditionalExpressionSimplificationView/images/ReplaceConditionalWithPolymorphismMotivation.png");
				refactoringOptions.setExamplePath("/ConditionalExpressionSimplificationView/images/ReplaceConditionalWithPolymorphismExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow frameConditionalWithPolymorphism = new RefactoringInfoWindow(refactoringOptions);
				frameConditionalWithPolymorphism.setVisible(true);
				break;
			case "v14" :
				refactoringOptions.setRefactoringTitle("Replace Constructor with Factory Method");
				refactoringOptions.setMotivationPath("/MethodCallImprovementView/images/ReplaceWithFactoryMethodMotivation.png");
				refactoringOptions.setExamplePath("/MethodCallImprovementView/images/ReplaceWithFactoryMethodExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow replaceWithFactory = new RefactoringInfoWindow(refactoringOptions);
				replaceWithFactory.setVisible(true);
				break;
			case "v15" :
				refactoringOptions.setRefactoringTitle("Extract Subclass");
				refactoringOptions.setMotivationPath(pathPrefix+"ExtractSubclassMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"ExtractSubclassExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow frameExtractSubclass = new RefactoringInfoWindow(refactoringOptions);
				frameExtractSubclass.setVisible(true);
				break;
			case "v16" :
				refactoringOptions.setRefactoringTitle("Pull Up Constructor Body");
				refactoringOptions.setMotivationPath(pathPrefix+"PullUpConstructorBodyMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"PullUpConstructorBodyExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow frameConstructorBody = new RefactoringInfoWindow(refactoringOptions);
				frameConstructorBody.setVisible(true);
				break;
			case "v17" :
				refactoringOptions.setRefactoringTitle("Rename Method");
				refactoringOptions.setMotivationPath("/MethodCallImprovementView/images/RenameMethodMotivation.png");
				refactoringOptions.setExamplePath("/MethodCallImprovementView/images/RenameMethodExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow renameMethod = new RefactoringInfoWindow(refactoringOptions);
				renameMethod.setVisible(true);
				break;
			case "v18" :
				refactoringOptions.setRefactoringTitle("Extract Superclass");
				refactoringOptions.setMotivationPath(pathPrefix+"ExtractSuperclassMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"ExtractSuperclassExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow frameExtractSuperclass = new RefactoringInfoWindow(refactoringOptions);
				frameExtractSuperclass.setVisible(true);
				break;
			case "v19" :
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
			case "v20" :
				refactoringOptions.setRefactoringTitle("Pull Up Method");
				refactoringOptions.setMotivationPath(pathPrefix+"PullUpMethodMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"PullUpMethodExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow framePullUpMethod = new RefactoringInfoWindow(refactoringOptions);
				framePullUpMethod.setVisible(true);
				break;
			case "v21" :
				refactoringOptions.setRefactoringTitle("Form Template Method");
				refactoringOptions.setMotivationPath(pathPrefix+"FormTemplateMethodMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"FormTemplateMethodExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow frameFormTemplateMethod = new RefactoringInfoWindow(refactoringOptions);
				frameFormTemplateMethod.setVisible(true);
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
				externalVertices[0] = vertices[0] = (mxCell) g.insertVertex(p, "v1", "Self\nEncapsulate Field", 25, 25, 100, 50, "fillColor=#B0B0B6;fontColor=black");
				externalVertices[1] = vertices[8] = (mxCell) g.insertVertex(p, "v9", "Extract Class", 500, 650, 100, 50, "fillColor=#B0B0B6;fontColor=black");
				//externalVertices[2] = vertices[9] = (mxCell) g.insertVertex(p, "v10", "Replace Typecode\nwith\nState/Strategy", 650, 650, 100, 50, "fillColor=#B0B0B6;fontColor=black");
				//externalVertices[3] = vertices[10] = (mxCell) g.insertVertex(p, "v11", "Replace Typecode\nwith Subclasses", 875, 650, 100, 50, "fillColor=#B0B0B6;fontColor=black");
				externalVertices[4] = vertices[11] = (mxCell) g.insertVertex(p, "v12", "Move Method", 875, 650, 100, 50, "fillColor=#B0B0B6;fontColor=black");
				externalVertices[5] = vertices[12] = (mxCell) g.insertVertex(p, "v13", "Replace Conditional\nWith Polymorphism", 875, 525, 100, 50, "fillColor=#B0B0B6;fontColor=black");
				externalVertices[6] = vertices[13] = (mxCell) g.insertVertex(p, "v14", "Replace Constructor\nwith\nFactory Method", 875, 400, 100, 50, "fillColor=#B0B0B6;fontColor=black");
				externalVertices[7] = vertices[16] = (mxCell) g.insertVertex(p, "v17", "Rename Method", 700, 300, 100, 50, "fillColor=#B0B0B6;fontColor=black");
				externalVertices[8] = vertices[18] = (mxCell) g.insertVertex(p, "v19", "Extract Method", 500, 25, 100, 50, "fillColor=#B0B0B6;fontColor=black");

				succession.addRelation(vertices[1],vertices[0]);
				succession.addRelation(vertices[18],vertices[19]);
				partOf.addRelation(vertices[0],vertices[14]);
				partOf.addRelation(vertices[0],vertices[19]);
				//partOf.addRelation(vertices[14],vertices[9]);
				//partOf.addRelation(vertices[14],vertices[10]);
				partOf.addRelation(vertices[11],vertices[14]);
				partOf.addRelation(vertices[12],vertices[14]);
				partOf.addRelation(vertices[13],vertices[14]);
				partOf.addRelation(vertices[16],vertices[14]);
				partOf.addRelation(vertices[16],vertices[17]);
				partOf.addRelation(vertices[16],vertices[20]);
				partOf.addRelation(vertices[18],vertices[17]);
				insteadOf.addRelation(vertices[8],vertices[14]);
				insteadOf.addRelation(vertices[8],vertices[17]);
				insteadOf.addRelation(vertices[15],vertices[13]);
				
				succession.connectEdges(g, p);
				partOf.connectEdges(g, p);
				insteadOf.connectEdges(g, p);
			}
			else 
			{
				g.removeCells(externalVertices);
				
				for (int i = 0; i < externalVertices.length; i++) {
					if((i!=2)&&(i!=3))//Condition added after commenting out 2 nodes
					{	
						succession.removeRelatedEdges(externalVertices[i]);
						partOf.removeRelatedEdges(externalVertices[i]);
						insteadOf.removeRelatedEdges(externalVertices[i]);
					}
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
			vertices[1] = (mxCell) g.insertVertex(p, "v2", "Pull Up Field", 25, 150, 100, 50, "circle");
			vertices[2] = (mxCell) g.insertVertex(p, "v3", "Collapse Hierarchy", 25, 275, 100, 50, "circle");
			vertices[3] = (mxCell) g.insertVertex(p, "v4", "Push Down Field", 25, 525, 100, 50, "circle");
			vertices[4] = (mxCell) g.insertVertex(p, "v5", "Replace Delegation\nwith Inheritance", 25, 650, 105, 55, "circle");
			vertices[5] = (mxCell) g.insertVertex(p, "v6", "Push Down Method", 150, 400, 100, 50, "circle");
			vertices[6] = (mxCell) g.insertVertex(p, "v7", "Replace Inheritance\nwith Delegation", 200, 650, 105,55, "circle");
			vertices[7] = (mxCell) g.insertVertex(p, "v8", "Extract Interface", 350, 650, 100, 50, "circle");
			vertices[14] = (mxCell) g.insertVertex(p, "v15", "Extract Subclass", 700, 525, 100, 50, "circle");
			vertices[15] = (mxCell) g.insertVertex(p, "v16", "Pull Up\nConstructor Body", 875, 25, 100, 50, "circle");		
			vertices[17] = (mxCell) g.insertVertex(p, "v18", "Extract Superclass", 350, 210, 100, 50, "circle");
			vertices[19] = (mxCell) g.insertVertex(p, "v20", "Pull Up Method", 200, 25, 100, 50, "circle");
			vertices[20] = (mxCell) g.insertVertex(p, "v21", "Form\nTemplate Method", 700, 150, 100, 50, "circle");
			
			//Inner region connections
			succession.addRelation(vertices[4],vertices[6]);
			succession.addRelation(vertices[6],vertices[4]);
			succession.addRelation(vertices[17],vertices[6]);
			partOf.addRelation(vertices[1],vertices[2]);
			partOf.addRelation(vertices[5],vertices[2]);
			partOf.addRelation(vertices[3],vertices[2]);
			partOf.addRelation(vertices[5],vertices[14]);
			partOf.addRelation(vertices[3],vertices[14]);
			partOf.addRelation(vertices[15],vertices[17]);
			partOf.addRelation(vertices[1],vertices[17]);
			partOf.addRelation(vertices[19],vertices[17]);
			partOf.addRelation(vertices[19],vertices[2]);
			partOf.addRelation(vertices[1],vertices[19]);
			partOf.addRelation(vertices[19],vertices[20]);
			partOf.addRelation(vertices[20],vertices[17]);
			insteadOf.addRelation(vertices[13],vertices[15]);
			insteadOf.addRelation(vertices[17],vertices[7]);
			
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
