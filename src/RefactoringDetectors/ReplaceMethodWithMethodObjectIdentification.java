package RefactoringDetectors;

import gr.uom.java.ast.ASTReader;
import gr.uom.java.ast.AbstractMethodDeclaration;
import gr.uom.java.ast.ClassObject;
import gr.uom.java.ast.MethodObject;
import gr.uom.java.ast.SystemObject;
import gr.uom.java.jdeodorant.refactoring.manipulators.ASTSliceGroup;
import gr.uom.java.jdeodorant.refactoring.views.LongMethod;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.IProgressService;

import DataHandling.PackageExplorerSelection;
import UI.MainWindow;

/*	This class implements the detection of Replace Method with Method Object opportunities
 *	in the user's code. It uses the same algorithm with Extract Method detection to 
 *	suggest methods for replacement with new objects.
 */
public class ReplaceMethodWithMethodObjectIdentification implements RefactoringDetector {

	private JFrame mainFrame;
	private JPanel mainPanel;
	private ASTSliceGroup[] slices;
	private IWorkbenchPage page;
	private Display display;
	private PackageExplorerSelection selectionInfo;
	private boolean opportunitiesFound;

	public ReplaceMethodWithMethodObjectIdentification()
	{
		mainFrame = new JFrame();
		mainFrame.setTitle("Replace Method with Method Object Opportunities");
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
			opportunitiesFound = false;
			
		JLabel lblNewLabel = new JLabel("Choose a class to view its Replace Method with Method Object Opportunities");
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
				
				Vector<String> mNames = new Vector<String>();
				
				for (int i = 0; i < slices.length; i++) {	
					if(value.equals(slices[i].getSourceTypeDeclaration().getName().toString())){
						
						boolean flag = false;
						for (int j = 0; j < mNames.size(); j++) {
							String methodName = mNames.elementAt(j);
							
							if(methodName.equals(slices[i].getSourceMethodDeclaration().getName().toString()))
								flag = true;
						}
						
						if(flag == false)
						{
							mNames.add(slices[i].getSourceMethodDeclaration().getName().toString());
						}
					}
				}
				
				for (int i = 0; i < mNames.size(); i++) {
					list2Model.addElement(mNames.elementAt(i));
				}
				
				list_1.setModel(list2Model);
			}
		});
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBounds(15, 134, 314, 410);
		list.setModel(computeListModel());
		list.setSelectedIndex(0);
		mainPanel.add(list);
	
		
		JLabel lblNewLabel_1 = new JLabel("Classes");
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(15, 98, 314, 20);
		mainPanel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Methods");
		lblNewLabel_2.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(465, 98, 314, 20);
		mainPanel.add(lblNewLabel_2);
	
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
		
		Vector<String> mNames = new Vector<String>();
		
		for(int i=0;i<slices.length;i++)
		{
			boolean flag = false;
			for (int j = 0; j < mNames.size(); j++) {
				String className = mNames.elementAt(j);
				if(className.equals(slices[i].getSourceTypeDeclaration().getName().toString()))
					flag = true;
			}
			
			if(flag == false)
			{
				mNames.add(slices[i].getSourceTypeDeclaration().getName().toString());
			}
				
		}
		
		for (int i = 0; i < mNames.size(); i++) {
			listModel.addElement(mNames.elementAt(i));
		}

		return listModel;
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
						if(ASTReader.getSystemObject() != null && selectionInfo.getSelectedProject().equals(ASTReader.getExaminedProject())) {
							new ASTReader(selectionInfo.getSelectedProject(), ASTReader.getSystemObject(), null);
						}
						else {
							ps.busyCursorWhile(new IRunnableWithProgress() {
								public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
									new ASTReader(selectionInfo.getSelectedProject(), null);
								}
							});
						}
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
