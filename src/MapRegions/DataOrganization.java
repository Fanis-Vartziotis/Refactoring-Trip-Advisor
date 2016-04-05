package MapRegions;

import org.eclipse.ui.IWorkbenchPage;



import RefactoringDetectors.ExtractMethodIdentification;
import RefactoringDetectors.InlineMethodIdentification;
import RefactoringDetectors.MoveMethodIdentification;
import UI.RefactoringInfoWindow;
import UI.MainWindow;

import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;

/*	This class handles the creation of the Data Organization map.
 * 	It's responsible for the graph creation as well as the 3-slide information window
 * 	for each of its refactorings.
 */
public class DataOrganization extends RefactoringRegion {
	
	public DataOrganization(MainWindow p, IWorkbenchPage pg)
	{
		super(p,pg,24,8);
		refactoringLabel.setText("Data Organization");
	}
	
	public void popUp(mxCell cell)
	{
		String pathPrefix = "/DataOrganizationView/images/";
		switch(cell.getId())
		{
			case "v1" :
				refactoringOptions.setRefactoringTitle("Substitute Algorithm");
				refactoringOptions.setMotivationPath("/MethodCompositionView/images/SubstituteAlgorithmMotivation.png");
				refactoringOptions.setExamplePath("/MethodCompositionView/images/SubstituteAlgorithmExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow substituteAlgorithm = new RefactoringInfoWindow(refactoringOptions);
				substituteAlgorithm.setVisible(true);
				break;
			case "v2" :
				refactoringOptions.setRefactoringTitle("Change Bidirectional Assosiation to Unidirectional");
				refactoringOptions.setMotivationPath(pathPrefix+"BidirectionalToUndirectionalMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"BidirectionalToUndirectionalExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow frameToUndirectional = new RefactoringInfoWindow(refactoringOptions);
				frameToUndirectional.setVisible(true);
				break;
			case "v3" :
				refactoringOptions.setRefactoringTitle("Change Unidirectional Assosiation to Bidirectional");
				refactoringOptions.setMotivationPath(pathPrefix+"UndirectionalToBidirectionalMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"UndirectionalToBidirectionalExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow frameToBidirectional = new RefactoringInfoWindow(refactoringOptions);
				frameToBidirectional.setVisible(true);
				break;
			case "v4" :
				refactoringOptions.setRefactoringTitle("Replace Data Value with Object");
				refactoringOptions.setMotivationPath(pathPrefix+"ReplaceDataValueWithObjectMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"ReplaceDataValueWithObjectExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow frameValueWithObject = new RefactoringInfoWindow(refactoringOptions);
				frameValueWithObject.setVisible(true);
				break;
			case "v5" :
				refactoringOptions.setRefactoringTitle("Change Value to Reference");
				refactoringOptions.setMotivationPath(pathPrefix+"ChangeValueToReferenceMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"ChangeValueToReferenceExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow frameToReference = new RefactoringInfoWindow(refactoringOptions);
				frameToReference.setVisible(true);
				break;
			case "v6" :
				refactoringOptions.setRefactoringTitle("Self Encapsulate Field");
				refactoringOptions.setMotivationPath(pathPrefix+"SelfEncapsulateFieldMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"SelfEncapsulateFieldExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow frameSelfEncapsulateField = new RefactoringInfoWindow(refactoringOptions);
				frameSelfEncapsulateField.setVisible(true);
				break;
			case "v7" :
				refactoringOptions.setRefactoringTitle("Duplicate Observed Data");
				refactoringOptions.setMotivationPath(pathPrefix+"DuplicateObservedDataMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"DuplicateObservedDataExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow frameDuplicateObservedData = new RefactoringInfoWindow(refactoringOptions);
				frameDuplicateObservedData.setVisible(true);
				break;
			case "v8" :
				dataForIdentification= new Object[1];
				dataForIdentification[0] = map;
				
				refactoringOptions.setRefactoringTitle("Inline Method");
				refactoringOptions.setMotivationPath("/MethodCompositionView/images/InlineMethodMotivation.png");
				refactoringOptions.setExamplePath("/MethodCompositionView/images/InlineMethodExample.png");
				refactoringOptions.setIdentificationPath("/images/CustomIdentificationPlusTool.png");
				refactoringOptions.setDetector(new InlineMethodIdentification());
				refactoringOptions.setDetectorData(dataForIdentification);
				
				RefactoringInfoWindow inlineMethod = new RefactoringInfoWindow(refactoringOptions);
				inlineMethod.setVisible(true);
				break;
			case "v9" :
				refactoringOptions.setRefactoringTitle("Replace Subclass with Fields");
				refactoringOptions.setMotivationPath(pathPrefix+"ReplaceSubclassWithFieldsMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"ReplaceSubclassWithFieldsExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow frameSubclassWithFields = new RefactoringInfoWindow(refactoringOptions);
				frameSubclassWithFields.setVisible(true);
				break;
			case "v10" :
				refactoringOptions.setRefactoringTitle("Replace Constructor with Factory Method");
				refactoringOptions.setMotivationPath("/MethodCallImprovementView/images/ReplaceWithFactoryMethodMotivation.png");
				refactoringOptions.setExamplePath("/MethodCallImprovementView/images/ReplaceWithFactoryMethodExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow replaceWithFactory = new RefactoringInfoWindow(refactoringOptions);
				replaceWithFactory.setVisible(true);
				break;
			case "v11" :
				refactoringOptions.setRefactoringTitle("Replace Record with Data Class");
				refactoringOptions.setMotivationPath(pathPrefix+"ReplaceRecordWithDataClassMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"ReplaceRecordWithDataClassExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow frameWithDataClass = new RefactoringInfoWindow(refactoringOptions);
				frameWithDataClass.setVisible(true);
				break;
			case "v12" :
				refactoringOptions.setRefactoringTitle("Replace Array with Object");
				refactoringOptions.setMotivationPath(pathPrefix+"ReplaceArrayWithObjectMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"ReplaceArrayWithObjectExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow frameArrayWithObject = new RefactoringInfoWindow(refactoringOptions);
				frameArrayWithObject.setVisible(true);
				break;
			case "v13" :
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
			case "v14" :
				refactoringOptions.setRefactoringTitle("Encapsulate Collection");
				refactoringOptions.setMotivationPath(pathPrefix+"EncapsulateCollectionMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"EncapsulateCollectionExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow frameEncapsulateCollection = new RefactoringInfoWindow(refactoringOptions);
				frameEncapsulateCollection.setVisible(true);
				break;
			case "v15" :
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
			case "v16" :
				refactoringOptions.setRefactoringTitle("Encapsulate Field");
				refactoringOptions.setMotivationPath(pathPrefix+"EncapsulateFieldMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"EncapsulateFieldExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow frameEncapsulateField = new RefactoringInfoWindow(refactoringOptions);
				frameEncapsulateField.setVisible(true);
				break;
			case "v17" :
				refactoringOptions.setRefactoringTitle("Replace Type Code with Subclasses");
				refactoringOptions.setMotivationPath(pathPrefix+"ReplaceTypeCodeWithSubclassesMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"ReplaceTypeCodeWithSubclassesExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow frameCodeWithSubclasses = new RefactoringInfoWindow(refactoringOptions);
				frameCodeWithSubclasses.setVisible(true);
				break;
			case "v18" :
				refactoringOptions.setRefactoringTitle("Replace Type Code with Class");
				refactoringOptions.setMotivationPath(pathPrefix+"ReplaceTypeCodeWithClassMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"ReplaceTypeCodeWithClassExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow frameTypeCodeWithClass = new RefactoringInfoWindow(refactoringOptions);
				frameTypeCodeWithClass.setVisible(true);
				break;
			case "v19" :
				refactoringOptions.setRefactoringTitle("Rename Method");
				refactoringOptions.setMotivationPath("/MethodCallImprovementView/images/RenameMethodMotivation.png");
				refactoringOptions.setExamplePath("/MethodCallImprovementView/images/RenameMethodExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow renameMethod = new RefactoringInfoWindow(refactoringOptions);
				renameMethod.setVisible(true);
				break;
			case "v20" :
				refactoringOptions.setRefactoringTitle("ReplaceConditionalWithPolymorphism");
				refactoringOptions.setMotivationPath("/ConditionalExpressionSimplificationView/images/ReplaceConditionalWithPolymorphismMotivation.png");
				refactoringOptions.setExamplePath("/ConditionalExpressionSimplificationView/images/ReplaceConditionalWithPolymorphismExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow frameConditionalWithPolymorphism = new RefactoringInfoWindow(refactoringOptions);
				frameConditionalWithPolymorphism.setVisible(true);
				break;
			case "v21" :
				refactoringOptions.setRefactoringTitle("Replace Type Code with State/Strategy");
				refactoringOptions.setMotivationPath(pathPrefix+"ReplaceTypeCodeWithStateMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"ReplaceTypeCodeWithStateExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow frameWithState = new RefactoringInfoWindow(refactoringOptions);
				frameWithState.setVisible(true);
				break;
			case "v22" :
				refactoringOptions.setRefactoringTitle("Replace Magic Number");
				refactoringOptions.setMotivationPath(pathPrefix+"ReplaceMagicNumberMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"ReplaceMagicNumberExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow frameMagicNumber = new RefactoringInfoWindow(refactoringOptions);
				frameMagicNumber.setVisible(true);
				break;
			case "v23" :
				refactoringOptions.setRefactoringTitle("Remove Setting Method");
				refactoringOptions.setMotivationPath("/MethodCallImprovementView/images/RemoveSettingMethodMotivation.png");
				refactoringOptions.setExamplePath("/MethodCallImprovementView/images/RemoveSettingMethodExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow removeSettingMethod = new RefactoringInfoWindow(refactoringOptions);
				removeSettingMethod.setVisible(true);
				break;
			case "v24" :
				refactoringOptions.setRefactoringTitle("Change Reference to Value");
				refactoringOptions.setMotivationPath(pathPrefix+"ChangeReferenceToValueMotivation.png");
				refactoringOptions.setExamplePath(pathPrefix+"ChangeReferenceToValueExample.png");
				refactoringOptions.setIdentificationPath("/images/NoIdentificationNoTool.png");
				refactoringOptions.setDetector(null);
				refactoringOptions.setDetectorData(null);
				
				RefactoringInfoWindow frameToValue = new RefactoringInfoWindow(refactoringOptions);
				frameToValue.setVisible(true);
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
				externalVertices[0] = vertices[0] = (mxCell) g.insertVertex(p, "v1", "Substitute Algorithm", 35, 25, 100, 50, "fillColor=#B0B0B6;fontColor=black");	
				externalVertices[1] = vertices[7] = (mxCell) g.insertVertex(p, "v8", "Inline Method", 200, 350, 100, 50, "fillColor=#B0B0B6;fontColor=black");
				externalVertices[2] = vertices[9] = (mxCell) g.insertVertex(p, "v10", "Replace\nConstructor with\nFactory Method", 200, 650, 100, 50, "fillColor=#B0B0B6;fontColor=black");
				externalVertices[3] = vertices[12] = (mxCell) g.insertVertex(p, "v13", "Move Method", 875, 350, 100, 50, "fillColor=#B0B0B6;fontColor=black");
				externalVertices[4] = vertices[14] = (mxCell) g.insertVertex(p, "v15", "Extract Method", 875, 650, 100, 50, "fillColor=#B0B0B6;fontColor=black");
				externalVertices[5] = vertices[18] = (mxCell) g.insertVertex(p, "v19", "Rename Method", 700, 650, 100, 50, "fillColor=#B0B0B6;fontColor=black");
				externalVertices[6] = vertices[19] = (mxCell) g.insertVertex(p, "v20", "Replace Conditional\nWith Polymorphism", 525, 350, 100, 50, "fillColor=#B0B0B6;fontColor=black");
				externalVertices[7] = vertices[22] = (mxCell) g.insertVertex(p, "v23", "Remove\nSetting Method", 525, 25, 100, 50, "fillColor=#B0B0B6;fontColor=black");
						
				succession.addRelation(vertices[11], vertices[12]);
				succession.addRelation(vertices[15], vertices[12]);
				succession.addRelation(vertices[16], vertices[19]);
				succession.addRelation(vertices[20], vertices[19]);
				partOf.addRelation(vertices[0], vertices[1]);
				partOf.addRelation(vertices[7], vertices[8]);
				partOf.addRelation(vertices[9], vertices[4]);
				partOf.addRelation(vertices[9], vertices[8]);
				partOf.addRelation(vertices[12], vertices[13]);
				partOf.addRelation(vertices[14], vertices[13]);
				partOf.addRelation(vertices[18], vertices[13]);
				partOf.addRelation(vertices[18], vertices[17]);
				partOf.addRelation(vertices[22], vertices[23]);
				
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
					
			vertices[1] = (mxCell) g.insertVertex(p, "v2", "Change\nBidirectional Association\nto Unidirectional", 25, 185, 120, 55, "circle");
			vertices[2] = (mxCell) g.insertVertex(p, "v3", "Change\nUnidirectional Association\nto Bidirectional", 25, 350, 123, 55, "circle");
			vertices[3] = (mxCell) g.insertVertex(p, "v4", "Replace\nData Value\nwith Object", 25, 500, 100, 50, "circle");
			vertices[4] = (mxCell) g.insertVertex(p, "v5", "Change Value\nto Reference", 25, 650, 100, 50, "circle");
			vertices[5] = (mxCell) g.insertVertex(p, "v6", "Self\nEncapsulate Field", 200, 25, 100, 50, "circle");
			vertices[6] = (mxCell) g.insertVertex(p, "v7", "Duplicate\nObserved Data", 200, 185, 100,50, "circle");
			vertices[8] = (mxCell) g.insertVertex(p, "v9", "Replace Subclass\nwith Fields", 200, 500, 100, 50, "circle");
			vertices[10] = (mxCell) g.insertVertex(p, "v11", "Replace Record\nwith Data Class", 875, 25, 100, 50, "circle");
			vertices[11] = (mxCell) g.insertVertex(p, "v12", "Replace Array\nwith Object", 875, 185, 100, 50, "circle");
			vertices[13] = (mxCell) g.insertVertex(p, "v14", "Encapsulate\nCollection", 875, 500, 100, 50, "circle");
			vertices[15] = (mxCell) g.insertVertex(p, "v16", "Encapsulate Field", 700, 25, 100, 50, "circle");
			vertices[16] = (mxCell) g.insertVertex(p, "v17", "Replace Type Code\nwith Subclasses", 700, 350, 105, 55, "circle");
			vertices[17] = (mxCell) g.insertVertex(p, "v18", "Replace Type Code\nwith Class", 700, 500, 105, 55, "circle");
			vertices[20] = (mxCell) g.insertVertex(p, "v21", "Replace\nType Code with\nState/Strategy", 525, 500, 100, 50, "circle");
			vertices[21] = (mxCell) g.insertVertex(p, "v22", "Replace Magic\nNumber with\nSymbolic Constant", 525, 650, 110, 55, "circle");
			vertices[23] = (mxCell) g.insertVertex(p, "v24", "Change Reference\nto Value", 525, 185, 100, 50, "circle");

			//Inner region connections
			succession.addRelation(vertices[1], vertices[2]);
			succession.addRelation(vertices[2], vertices[1]);
			succession.addRelation(vertices[3], vertices[4]);
			partOf.addRelation(vertices[5], vertices[1]);
			partOf.addRelation(vertices[5], vertices[6]);
			insteadOf.addRelation(vertices[10], vertices[11]);
			insteadOf.addRelation(vertices[17], vertices[16]);
			insteadOf.addRelation(vertices[17], vertices[20]);
			insteadOf.addRelation(vertices[17], vertices[21]);

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
