package RefactoringDetectors;

import gr.uom.java.ast.ASTReader;
import gr.uom.java.ast.AbstractMethodDeclaration;
import gr.uom.java.ast.ClassObject;
import gr.uom.java.ast.LocalVariableDeclarationObject;
import gr.uom.java.ast.MethodObject;
import gr.uom.java.ast.SystemObject;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

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
import org.eclipse.jdt.core.dom.IExtendedModifier;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.IProgressService;

import DataHandling.PackageExplorerSelection;
import UI.MainWindow;

/*	This class implements the detection of Inline Method opportunities
 *	in the user's code. It searches for private methods containing a single
 *	statement whose prefix does not include "get","set","add" or "remove".
 */
public class InlineMethodIdentification implements RefactoringDetector {

	private JFrame mainFrame;
	private JPanel mainPanel;
	private PackageExplorerSelection selectionInfo;
	private boolean opportunitiesFound;
	private ArrayList<AbstractMethodDeclaration> candidateMethods;
	private ArrayList<ClassObject> declaringClasses;
	
	public InlineMethodIdentification()
	{
		mainFrame = new JFrame();
		mainFrame.setTitle("Inline Method Opportunities");
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
		MainWindow map = (MainWindow) dataForIdentification[0];	
		selectionInfo = map.getSelectionInfo();
		
		beginASTParsing();
	
		if(candidateMethods.isEmpty())
		{
			opportunitiesFound = false;
		}

		final JList list_1 = new JList();
		list_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_1.setBounds(434, 120, 350, 441);
		final DefaultListModel list2Model = new DefaultListModel();
		list_1.setModel(list2Model);
		mainPanel.add(list_1);
		
		final JList list = new JList();
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				String value = (String) list.getSelectedValue();
				list2Model.clear();
			
				for (int i = 0; i < declaringClasses.size(); i++) {
					if(declaringClasses.get(i).getName().equals(value))
					{
						list2Model.addElement(candidateMethods.get(i).getName());
					}
				}
				
				list_1.setModel(list2Model);
			}
		});
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBounds(10, 120, 350, 441);
		list.setModel(computeListModel());
		mainPanel.add(list);

		JLabel lblChooseAMethod = new JLabel("Choose a class to view its Inline Method Opportunities");
		lblChooseAMethod.setFont(new Font("Arial", Font.BOLD, 16));
		lblChooseAMethod.setHorizontalAlignment(SwingConstants.CENTER);
		lblChooseAMethod.setBounds(10, 26, 774, 27);
		mainPanel.add(lblChooseAMethod);
		
		JLabel lblMethods = new JLabel("Classes:");
		lblMethods.setFont(new Font("Arial", Font.BOLD, 16));
		lblMethods.setHorizontalAlignment(SwingConstants.CENTER);
		lblMethods.setBounds(10, 82, 350, 27);
		mainPanel.add(lblMethods);
		
		JLabel lblNewLabel = new JLabel("Methods:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 16));
		lblNewLabel.setBounds(434, 82, 350, 27);
		mainPanel.add(lblNewLabel);
		
		return mainFrame;
	}
	
	private DefaultListModel computeListModel()
	{
		DefaultListModel listModel = new DefaultListModel();
		ArrayList<String> listItems = new ArrayList<String>();
		boolean flag = false;
		
		for (int i = 0; i < declaringClasses.size(); i++) {
			//String classMethodString = tempVariables.get(i).getClassObject().getName() + "::" + tempVariables.get(i).getMethodObject().getName();
			flag = false;
			
			for (int j = 0; j < listItems.size(); j++) {
				if(listItems.get(j).equals(declaringClasses.get(i).getName()))
				{
					flag = true;
				}
			}
			
			if(flag == false)
			{
				listItems.add(declaringClasses.get(i).getName());
			}
		}
		
		for (int i = 0; i < listItems.size(); i++) {
			listModel.addElement(listItems.get(i));
		}

		return listModel;
	}
	
	@Override
	public boolean opportunitiesFound() {
		return opportunitiesFound;
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
								List<Statement> statements = methodObject.getMethodDeclaration().getBody().statements();
																
								ClassObject declaringClass = systemObject.getClassObject(methodObject.getClassName());
								
								List<IExtendedModifier> modifiers = methodObject.getMethodDeclaration().modifiers();
								
								boolean isPrivateOrProtected = false;
								
								for (int i = 0; i < modifiers.size(); i++) {
									if(modifiers.get(i) instanceof Modifier)
									{
										Modifier modifier = (Modifier) modifiers.get(i);
										
										if((modifier.isPrivate())||(modifier.isProtected()))
										{
											isPrivateOrProtected = true;
										}
									}
								}
								
								if((statements.size() == 1)
									&&(isPrivateOrProtected)
									&&(!methodObject.getName().startsWith("get"))
									&&(!methodObject.getName().startsWith("set"))
									&&(!methodObject.getName().startsWith("add"))
									&&(!methodObject.getName().startsWith("remove")))
								{
									candidateMethods.add(methodObject);
									declaringClasses.add(declaringClass);
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
