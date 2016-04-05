package RefactoringDetectors;

import gr.uom.java.ast.ASTReader;
import gr.uom.java.ast.AbstractMethodDeclaration;
import gr.uom.java.ast.ClassObject;
import gr.uom.java.ast.MethodObject;
import gr.uom.java.ast.SystemObject;
import gr.uom.java.jdeodorant.refactoring.manipulators.ASTSlice;
import gr.uom.java.jdeodorant.refactoring.manipulators.ASTSliceGroup;
import gr.uom.java.jdeodorant.refactoring.views.LongMethod;
import gr.uom.java.jdeodorant.refactoring.views.SliceAnnotation;

import java.awt.Point;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Dimension;

import javax.swing.JLabel;

import java.awt.Component;

import javax.swing.SwingConstants;
import javax.swing.JList;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.ListSelectionModel;

import java.awt.Font;

import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JButton;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.AnnotationModel;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.progress.IProgressService;
import org.eclipse.ui.texteditor.ITextEditor;

import DataHandling.PackageExplorerSelection;
import UI.MainWindow;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/*	This class implements the detection of Extract Method opportunities
 *	in the user's code. It uses the code slicing algorithm implemented 
 * 	by JDeodorant.
 */
public class ExtractMethodIdentification implements RefactoringDetector{

	private JFrame mainFrame;
	private JPanel mainPanel;
	private ASTSliceGroup[] slices;
	private IWorkbenchPage page;
	private Display display;
	private PackageExplorerSelection selectionInfo;
	private boolean opportunitiesFound;

	public ExtractMethodIdentification()
	{
		mainFrame = new JFrame();
		mainFrame.setTitle("Extract Method Opportunities");
		mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mainFrame.setResizable(false);
		mainFrame.setLocation(new Point(200, 100));
		mainFrame.setSize(new Dimension(800, 600));
		mainFrame.setIconImage(java.awt.Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/repair.png")));
		
		mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainFrame.setContentPane(mainPanel);
		mainPanel.setLayout(null);
		
		opportunitiesFound = true;
	}
	
	@Override
	public JFrame getDetectorFrame(Object[] dataForIdentification) {
		page = (IWorkbenchPage) dataForIdentification[0];
		display = (Display) dataForIdentification[1];
		
		MainWindow map = (MainWindow) dataForIdentification[2];
		selectionInfo = map.getSelectionInfo();
		
		computeTheASTTable();
		
		if((slices==null)||(slices.length==0))
		{
			opportunitiesFound = false;
		}
			
		JLabel lblNewLabel = new JLabel("Choose a Variable Criterion from the right list to identify Extract Method Opportunities");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 16));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNewLabel.setBounds(15, 39, 764, 20);
		mainPanel.add(lblNewLabel);
		
		final JList list_1 = new JList();
		list_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_1.setBounds(465, 134, 314, 410);
		final DefaultListModel list2Model = new DefaultListModel();
		list_1.setModel(list2Model);
		mainPanel.add(list_1);
		
		final JList list = new JList();
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				String value = (String)list.getSelectedValue();
				list2Model.clear();
				for (int i = 0; i < slices.length; i++) {					
					if(value.equals(slices[i].getSourceMethodDeclaration().getName().toString()))
						list2Model.addElement(slices[i].getLocalVariableCriterion().getName().toString());
				}
				list_1.setModel(list2Model);
			}
		});
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBounds(15, 134, 314, 410);
		list.setModel(computeListModel());
		list.setSelectedIndex(0);
		mainPanel.add(list);
	
		
		JLabel lblNewLabel_1 = new JLabel("Methods");
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(15, 98, 314, 20);
		mainPanel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Variables");
		lblNewLabel_2.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(465, 98, 314, 20);
		mainPanel.add(lblNewLabel_2);
		
		JButton btnNewButton = new JButton("OK");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(list_1.getSelectedValue() == null)
					JOptionPane.showMessageDialog(mainFrame, "You need to select a variable from the right list first.", "Warning", JOptionPane.WARNING_MESSAGE);
				else
				{
					String value = (String)list.getSelectedValue();
					String value2 =  (String)list_1.getSelectedValue();
					for (int i = 0; i < slices.length; i++) {					
						if((value.equals(slices[i].getSourceMethodDeclaration().getName().toString()))
							&&(value2.equals(slices[i].getLocalVariableCriterion().getName().toString())))
						{
							Set<ASTSlice> candidates = slices[i].getCandidates();
							int statements = 0;
							
							for(ASTSlice candidate : candidates)
							{
								if(candidate.getSliceStatements().size() > statements)
								{
									statements = candidate.getSliceStatements().size();
								}
							}
							
							for(ASTSlice candidate : candidates)
							{
								if(candidate.getSliceStatements().size() == statements)
								{
									highlightText(candidate);
									mainFrame.dispose();
								}
							}
						}
					}
				}
			}
		});
		btnNewButton.setFont(new Font("Arial", Font.BOLD, 16));
		btnNewButton.setBounds(344, 294, 106, 29);
		mainPanel.add(btnNewButton);
		
		return mainFrame;
	}

	@Override
	public boolean opportunitiesFound()
	{
		return opportunitiesFound;
	}
	
	private DefaultListModel computeListModel()
	{
		DefaultListModel listModel = new DefaultListModel();
		
		Vector<MethodDeclaration> mNames = new Vector<MethodDeclaration>();
		
		if(slices != null)
		{
			for(int i=0;i<slices.length;i++)
			{
				boolean flag = false;
				for (int j = 0; j < mNames.size(); j++) {
					MethodDeclaration method = mNames.elementAt(j);
					if(method.equals(slices[i].getSourceMethodDeclaration()))
						flag = true;
				}
				if(flag == false)
					mNames.add(slices[i].getSourceMethodDeclaration());
			}
			
			for (int i = 0; i < mNames.size(); i++) {
				listModel.addElement(mNames.elementAt(i).getName().toString());
			}
		}
		
		return listModel;
	}
	
	private void highlightText(final ASTSlice slice)
	{
		display.syncExec(new Runnable() {
		    public void run() {
				//IEditorReference[] editors = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getEditorReferences();
				IFile sourceFile = slice.getIFile();
				IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(sourceFile.getName());
				
				if(desc == null)
					System.out.println("SHOULD NOT REACH HERE");
				try {
					
					ITextEditor sourceEditor = (ITextEditor) page.openEditor(new FileEditorInput(sourceFile), desc.getId());

					Object[] highlightPositionMaps = slice.getHighlightPositions();
					Map<Position, String> annotationMap = (Map<Position, String>)highlightPositionMaps[0];
					Map<Position, Boolean> duplicationMap = (Map<Position, Boolean>)highlightPositionMaps[1];
					AnnotationModel annotationModel = (AnnotationModel)sourceEditor.getDocumentProvider().getAnnotationModel(sourceEditor.getEditorInput());
					Iterator<Annotation> annotationIterator = annotationModel.getAnnotationIterator();
					while(annotationIterator.hasNext()) {
						Annotation currentAnnotation = annotationIterator.next();
						if(currentAnnotation.getType().equals(SliceAnnotation.EXTRACTION) || currentAnnotation.getType().equals(SliceAnnotation.DUPLICATION)) {
							annotationModel.removeAnnotation(currentAnnotation);
						}
					}
					for(Position position : annotationMap.keySet()) {
						SliceAnnotation annotation = null;
						String annotationText = annotationMap.get(position);
						boolean duplicated = duplicationMap.get(position);
						if(duplicated)
							annotation = new SliceAnnotation(SliceAnnotation.DUPLICATION, annotationText);
						else
							annotation = new SliceAnnotation(SliceAnnotation.EXTRACTION, annotationText);
						annotationModel.addAnnotation(annotation, position);
					}
					List<Position> positions = new ArrayList<Position>(annotationMap.keySet());
					Position firstPosition = positions.get(0);
					Position lastPosition = positions.get(positions.size()-1);
					int offset = firstPosition.getOffset();
					int length = lastPosition.getOffset() + lastPosition.getLength() - firstPosition.getOffset();
					sourceEditor.setHighlightRange(offset, length, true);
					
				} catch (PartInitException e) {
					e.printStackTrace();
				} 
				/*catch (JavaModelException e) {
					e.printStackTrace();
				}*/
		    }
		});
	}
	
	private void computeTheASTTable() {
		slices = null;
		if(selectionInfo.getSelectedProject() == null)
			System.out.println("null selected project - should not be printed");
		else {
			PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
				public void run(){
					try {
						IWorkbench wb = PlatformUI.getWorkbench();
						IProgressService ps = wb.getProgressService();
						/*if(ASTReader.getSystemObject() != null && selectionInfo.getSelectedProject().equals(ASTReader.getExaminedProject())) {
							new ASTReader(selectionInfo.getSelectedProject(), ASTReader.getSystemObject(), null);
						}
						else {*/
							ps.busyCursorWhile(new IRunnableWithProgress() {
								public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
									new ASTReader(selectionInfo.getSelectedProject(), null);
								}
							});
						//}
						final SystemObject systemObject = ASTReader.getSystemObject();
						final Set<ClassObject> classObjectsToBeExamined = new LinkedHashSet<ClassObject>();
						final Set<AbstractMethodDeclaration> methodObjectsToBeExamined = new LinkedHashSet<AbstractMethodDeclaration>();
						if(selectionInfo.getSelectedPackageFragmentRoot() != null) {
							classObjectsToBeExamined.addAll(systemObject.getClassObjects(selectionInfo.getSelectedPackageFragmentRoot()));
						}
						else if(selectionInfo.getSelectedPackageFragment() != null) {
							classObjectsToBeExamined.addAll(systemObject.getClassObjects(selectionInfo.getSelectedPackageFragment()));
						}
						else if(selectionInfo.getSelectedCompilationUnit() != null) {
							classObjectsToBeExamined.addAll(systemObject.getClassObjects(selectionInfo.getSelectedCompilationUnit()));
						}
						else if(selectionInfo.getSelectedType() != null) {
							classObjectsToBeExamined.addAll(systemObject.getClassObjects(selectionInfo.getSelectedType()));
						}
						else if(selectionInfo.getSelectedMethod() != null) {
							AbstractMethodDeclaration methodObject = systemObject.getMethodObject(selectionInfo.getSelectedMethod());
							if(methodObject != null) {
								ClassObject declaringClass = systemObject.getClassObject(methodObject.getClassName());
								if(declaringClass != null && !declaringClass.isEnum() && !declaringClass.isInterface() && methodObject.getMethodBody() != null)
									methodObjectsToBeExamined.add(methodObject);
							}
						}
						else {
							classObjectsToBeExamined.addAll(systemObject.getClassObjects());
						}
						final List<ASTSliceGroup> extractedSliceGroups = new ArrayList<ASTSliceGroup>();
			
						ps.busyCursorWhile(new IRunnableWithProgress() {
							public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
								if(!classObjectsToBeExamined.isEmpty()) {
									int workSize = 0;
									for(ClassObject classObject : classObjectsToBeExamined) {
										workSize += classObject.getNumberOfMethods();
									}
									monitor.beginTask("Identification of Extract Method refactoring opportunities", workSize);
									for(ClassObject classObject : classObjectsToBeExamined) {
										if(!classObject.isEnum() && !classObject.isInterface()) {
											ListIterator<MethodObject> methodIterator = classObject.getMethodIterator();
											while(methodIterator.hasNext()) {
												if(monitor.isCanceled())
													throw new OperationCanceledException();
												MethodObject methodObject = methodIterator.next();
												LongMethod.processMethod(extractedSliceGroups,classObject, methodObject);
												monitor.worked(1);
											}
										}
									}
								}
								else if(!methodObjectsToBeExamined.isEmpty()) {
									int workSize = methodObjectsToBeExamined.size();
									monitor.beginTask("Identification of Extract Method refactoring opportunities", workSize);
									for(AbstractMethodDeclaration methodObject : methodObjectsToBeExamined) {
										if(monitor.isCanceled())
											throw new OperationCanceledException();
										ClassObject classObject = systemObject.getClassObject(methodObject.getClassName());
										LongMethod.processMethod(extractedSliceGroups, classObject, methodObject);
										monitor.worked(1);
									}
								}
								monitor.done();
							}
						});
			
						slices = new ASTSliceGroup[extractedSliceGroups.size()];
						for(int i=0; i<extractedSliceGroups.size(); i++) {
							slices[i] = extractedSliceGroups.get(i);
						}
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

}
