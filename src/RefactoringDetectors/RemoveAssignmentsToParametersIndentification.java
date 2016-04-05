package RefactoringDetectors;

import gr.uom.java.ast.ASTReader;
import gr.uom.java.ast.AbstractMethodDeclaration;
import gr.uom.java.ast.ClassObject;
import gr.uom.java.ast.MethodObject;
import gr.uom.java.ast.ParameterObject;
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
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.WhileStatement;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.IProgressService;

import DataHandling.PackageExplorerSelection;
import ParsingHelpers.MethodParameterObject;
import UI.MainWindow;

/*	This class implements the detection of Remove Assignments To Parameters opportunities
 *	in the user's code. It searches for any assignments done to each method's parameters.
 */
public class RemoveAssignmentsToParametersIndentification implements RefactoringDetector{

	private JFrame mainFrame;
	private JPanel mainPanel;
	private PackageExplorerSelection selectionInfo;
	private boolean opportunitiesFound;
	private ArrayList<MethodParameterObject> parameters;
	
	public RemoveAssignmentsToParametersIndentification()
	{
		mainFrame = new JFrame();
		mainFrame.setTitle("Remove Assignments to Parameters Opportunities");
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
		
		if(!assignedParametersFound())
			opportunitiesFound = false;
		
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
				String[] splitted = value.split("::");
				list2Model.clear();
			
				for (int i = 0; i < parameters.size(); i++) {
					if((parameters.get(i).getClassObject().getName().equals(splitted[0]))
						&&((parameters.get(i).getMethodObject().getName().equals(splitted[1])))
						&&(parameters.get(i).isAssigned()))
					{
						list2Model.addElement(parameters.get(i).getParameter().getType().toString() + " " + parameters.get(i).getParameter().getName());
					}
				}
				
				list_1.setModel(list2Model);
			}
		});
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBounds(10, 120, 350, 441);
		list.setModel(computeListModel());
		mainPanel.add(list);

		JLabel lblChooseAMethod = new JLabel("Choose a method to view Remove Assignments to Parameters Opportunities");
		lblChooseAMethod.setFont(new Font("Arial", Font.BOLD, 16));
		lblChooseAMethod.setHorizontalAlignment(SwingConstants.CENTER);
		lblChooseAMethod.setBounds(10, 26, 774, 27);
		mainPanel.add(lblChooseAMethod);
		
		JLabel lblMethods = new JLabel("Methods:");
		lblMethods.setFont(new Font("Arial", Font.BOLD, 16));
		lblMethods.setHorizontalAlignment(SwingConstants.CENTER);
		lblMethods.setBounds(10, 82, 350, 27);
		mainPanel.add(lblMethods);
		
		JLabel lblNewLabel = new JLabel("Temporary Variables:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 16));
		lblNewLabel.setBounds(434, 82, 350, 27);
		mainPanel.add(lblNewLabel);
	
		return mainFrame;
	}
	
	@Override
	public boolean opportunitiesFound() {
		return opportunitiesFound;
	}
	
	private boolean assignedParametersFound()
	{
		boolean result = false;
		
		if(!parameters.isEmpty())
		{
			for (int i = 0; i < parameters.size(); i++) 
			{
				if(parameters.get(i).isAssigned())
					result = true;
			}
		}
		
		return result;
	}
	
	private DefaultListModel computeListModel()
	{
		DefaultListModel listModel = new DefaultListModel();
		ArrayList<String> listItems = new ArrayList<String>();
		boolean flag = false;
		
		for (int i = 0; i < parameters.size(); i++) {
			String classMethodString = parameters.get(i).getClassObject().getName() + "::" + parameters.get(i).getMethodObject().getName();
			flag = false;
			
			for (int j = 0; j < listItems.size(); j++) {
				if(listItems.get(j).equals(classMethodString))
				{
					flag = true;
				}
			}
			
			if(flag == false)
			{
				listItems.add(classMethodString);
			}
		}
		
		for (int i = 0; i < listItems.size(); i++) {
			listModel.addElement(listItems.get(i));
		}

		return listModel;
	}
	
	private void beginASTParsing()
	{
		parameters = new ArrayList<MethodParameterObject>();
		
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
								
								List<Statement> statements = methodObject.getMethodDeclaration().getBody().statements();
								
								
								while(parameterIterator.hasNext())
								{
									parameters.add(new MethodParameterObject(systemObject.getClassObject(methodObject.getClassName()), methodObject, parameterIterator.next()));
								}
																
								findAssignments(statements);
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
	
	private void findAssignments(List<Statement> statements)
	{
		Expression expression;
		
		ArrayList<Statement> recursiveStatements = new ArrayList<Statement>();

		for (int i = 0; i < statements.size(); i++) {
			Statement statement = statements.get(i);		
			
			if(statement instanceof ExpressionStatement)
			{
				ExpressionStatement expStatement = (ExpressionStatement) statement;
				
				expression = expStatement.getExpression();
				
				if(expression instanceof Assignment)
				{
					Assignment assign = (Assignment) expression;
					
					String left = assign.getLeftHandSide().toString();
					
					for (int j = 0; j < parameters.size(); j++) {
						if(left.equals(parameters.get(j).getParameter().getName()))
						{
							parameters.get(j).addExpression(assign.getRightHandSide());
						}
					}
				}
			}
			else if(statement instanceof ForStatement)
			{
				ForStatement forStatement = (ForStatement) statement;
				
				Statement forBody = forStatement.getBody();
				
				if(forBody instanceof Block)
				{
					Block forBlock = (Block) forBody;
					findAssignments(forBlock.statements());
				}
				else 
				{
					recursiveStatements.add(forBody);
					findAssignments(recursiveStatements);
				}
			}
			else if(statement instanceof DoStatement)
			{
				DoStatement doStatement = (DoStatement) statement;
				
				Statement doBody = doStatement.getBody();
				
				if(doBody instanceof Block)
				{
					Block doBlock = (Block) doBody;
					findAssignments(doBlock.statements());
				}
				else 
				{
					recursiveStatements.add(doBody);
					findAssignments(recursiveStatements);
				}
			}
			else if(statement instanceof WhileStatement)
			{
				WhileStatement whileStatement = (WhileStatement) statement;
				
				Statement whileBody = whileStatement.getBody();
				
				if(whileBody instanceof Block)
				{
					Block whileBlock = (Block) whileBody;
					findAssignments(whileBlock.statements());
				}
				else 
				{
					recursiveStatements.add(whileBody);
					findAssignments(recursiveStatements);
				}
			}
			else if(statement instanceof IfStatement)
			{
				IfStatement ifStatement = (IfStatement) statement;
				
				Statement thenBody = ifStatement.getThenStatement();
				Statement elseBody = ifStatement.getElseStatement();
				
				if(thenBody instanceof Block)
				{
					Block thenBlock = (Block) thenBody;
					findAssignments(thenBlock.statements());
				}
				else 
				{
					recursiveStatements.add(thenBody);
					findAssignments(recursiveStatements);
				}
				
				if(elseBody != null)
				{
					if(elseBody instanceof Block)
					{
						Block elseBlock = (Block) elseBody;
						findAssignments(elseBlock.statements());
					}
					else 
					{
						recursiveStatements.add(elseBody);
						findAssignments(recursiveStatements);
					}
				}
			}
			else if(statement instanceof TryStatement)
			{
				TryStatement tryStatement = (TryStatement) statement;
				
				Block tryBody = tryStatement.getBody();
				Block finallyBody = tryStatement.getFinally();
				
				findAssignments(tryBody.statements());
				if(finallyBody != null)
					findAssignments(finallyBody.statements());
			}
		}
	}
}
