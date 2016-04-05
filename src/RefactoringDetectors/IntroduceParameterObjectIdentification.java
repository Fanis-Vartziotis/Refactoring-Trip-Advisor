package RefactoringDetectors;

import gr.uom.java.ast.ASTReader;
import gr.uom.java.ast.ClassObject;
import gr.uom.java.ast.MethodObject;
import gr.uom.java.ast.ParameterObject;
import gr.uom.java.ast.SystemObject;
import gr.uom.java.ast.AbstractMethodDeclaration;

import java.awt.Dimension;
import java.awt.Point;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.ListIterator;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.IProgressService;

import DataHandling.PackageExplorerSelection;
import UI.MainWindow;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

/*	This class implements the detection of Introduce Parameter Object opportunities
 *	in the user's code. It searches for methods with long parameter lists.
 */
public class IntroduceParameterObjectIdentification implements RefactoringDetector 
{
	private JFrame mainFrame;
	private JPanel mainPanel;
	private PackageExplorerSelection selectionInfo;
	private boolean opportunitiesFound;
	private ArrayList<ClassObject> declaringClasses;
	private ArrayList<AbstractMethodDeclaration> candidateMethods;
	private JLabel lblNewLabel_1;
	
	public IntroduceParameterObjectIdentification()
	{
		mainFrame = new JFrame();
		mainFrame.setTitle("Introduce Parameter Object Opportunities");
		mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mainFrame.setResizable(false);
		mainFrame.setLocation(new Point(200, 100));
		mainFrame.setSize(new Dimension(548, 568));
		mainFrame.setIconImage(java.awt.Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/repair.png")));
		
		mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainFrame.setContentPane(mainPanel);
		mainPanel.setLayout(null);
		
		opportunitiesFound = true;
	}
	
	@Override
	public JFrame getDetectorFrame(Object[] dataForIdentification) {
		MainWindow map = (MainWindow) dataForIdentification[0];
		selectionInfo = map.getSelectionInfo();
		
		beginASTParsing();
		
		if(candidateMethods.isEmpty())
			opportunitiesFound = false;
		
		JLabel lblNewLabel = new JLabel("Opportunites for Introduce Parameter Object");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 16));
		lblNewLabel.setBounds(10, 11, 522, 30);
		mainPanel.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel(" found in the following methods");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 16));
		lblNewLabel_1.setBounds(10, 39, 522, 30);
		mainPanel.add(lblNewLabel_1);
		
		JList list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBounds(10, 80, 522, 449);
		list.setModel(computeListModel());
		mainPanel.add(list);
		
		return mainFrame;
	}

	@Override
	public boolean opportunitiesFound() {
		return opportunitiesFound;
	}

	private DefaultListModel computeListModel()
	{
		DefaultListModel listModel = new DefaultListModel();
		
		for (int i = 0; i < candidateMethods.size(); i++) {
			String classAndMethodString = declaringClasses.get(i).getName() + "::" + candidateMethods.get(i).getName();
			listModel.addElement(classAndMethodString);
		}

		return listModel;
	}
	
	private void beginASTParsing()
	{
		candidateMethods = new ArrayList<AbstractMethodDeclaration>();
		declaringClasses = new ArrayList<ClassObject>();
		
		if(selectionInfo.getSelectedProject() == null)
			System.out.println("null selected project - should not be printed");
		else 
		{
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
						
						//Some print tests
						
						if(!classObjectsToBeExamined.isEmpty())
						{
							for(ClassObject classObject : classObjectsToBeExamined)
							{
								if(!classObject.isEnum() && !classObject.isInterface()) 
								{
									ListIterator<MethodObject> methodIterator = classObject.getMethodIterator();
									while(methodIterator.hasNext()) 
									{
										methodObjectsToBeExamined.add(methodIterator.next());
									}
								}
							}
						}
						
						if(!methodObjectsToBeExamined.isEmpty())
						{
							for(AbstractMethodDeclaration methodObject : methodObjectsToBeExamined)
							{	
								ListIterator<ParameterObject> parameterIterator =  methodObject.getParameterListIterator();
								
								int parameterCounter = 0;

								while(parameterIterator.hasNext())
								{
									parameterCounter++;
									parameterIterator.next();
								}
																
								if(parameterCounter >= 5)
								{
									candidateMethods.add(methodObject);
									declaringClasses.add(systemObject.getClassObject(methodObject.getClassName()));
								}
							}
						}
					}
					catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
}
