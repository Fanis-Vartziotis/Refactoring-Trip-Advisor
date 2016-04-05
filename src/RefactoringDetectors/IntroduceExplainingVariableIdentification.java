package RefactoringDetectors;

import gr.uom.java.ast.ASTReader;
import gr.uom.java.ast.AbstractMethodDeclaration;
import gr.uom.java.ast.ClassObject;
import gr.uom.java.ast.LocalVariableDeclarationObject;
import gr.uom.java.ast.MethodObject;
import gr.uom.java.ast.SystemObject;
import gr.uom.java.jdeodorant.refactoring.views.SliceAnnotation;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.WhileStatement;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.FindReplaceDocumentAdapter;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Position;
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

/*	This class implements the detection of Introduce Explaining Variable opportunities
 *	in the user's code. It searches for expressions with multiple operators.
 */
public class IntroduceExplainingVariableIdentification implements RefactoringDetector {

	private JFrame mainFrame;
	private JPanel mainPanel;
	private PackageExplorerSelection selectionInfo;
	private Display display;
	private IWorkbenchPage page;
	private boolean opportunitiesFound;
	private ArrayList<Expression> candidateExpressions;
	private ArrayList<ClassObject> classOfExpression;
	private ArrayList<AbstractMethodDeclaration> methodOfExpression;
	
	public IntroduceExplainingVariableIdentification()
	{
		mainFrame = new JFrame();
		mainFrame.setTitle("Introduce Explaining Variable Opportunities");
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
		
		beginASTParsing();
		
		if(candidateExpressions.isEmpty())
			opportunitiesFound = false;

		JLabel lblNewLabel = new JLabel("Choose a method from the right list to identify Introduce Explaining Variable Opportunities");
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
				
				Vector<String> methodNames = new Vector<String>();
				
				for (int i = 0; i < classOfExpression.size(); i++) {					
					if(value.equals(classOfExpression.get(i).getName()))
					{
						boolean flag = false;
						
						for (int j = 0; j < methodNames.size(); j++) {
							String methodName = methodNames.elementAt(j);
							
							if(methodName.equals(methodOfExpression.get(i).getName()))
								flag = true;
						}
						
						if(flag == false)
						{
							methodNames.add(methodOfExpression.get(i).getName());
						}
					}
						
				}
				
				for (int i = 0; i < methodNames.size(); i++) {
					list2Model.addElement(methodNames.elementAt(i));
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
		
		JButton btnNewButton = new JButton("OK");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(list_1.getSelectedValue() == null)
					JOptionPane.showMessageDialog(mainFrame, "You need to select a variable from the right list first.", "Warning", JOptionPane.WARNING_MESSAGE);
				else
				{
					String methodName = (String)list_1.getSelectedValue();

					highlightText(methodName);
					mainFrame.dispose();
				}
			}
		});
		btnNewButton.setFont(new Font("Arial", Font.BOLD, 16));
		btnNewButton.setBounds(344, 294, 106, 29);
		mainPanel.add(btnNewButton);
		
		return mainFrame;
	}

	@Override
	public boolean opportunitiesFound() {
		return opportunitiesFound;
	}
	
	private DefaultListModel computeListModel()
	{
		DefaultListModel listModel = new DefaultListModel();
		
		Vector<String> classNames = new Vector<String>();
		
		for(int i=0;i<classOfExpression.size();i++)
		{
			boolean flag = false;
			for (int j = 0; j < classNames.size(); j++) {
				String className = classNames.elementAt(j);
				if(className.equals(classOfExpression.get(i).getName()))
					flag = true;
			}
			
			if(flag == false)
			{
				classNames.add(classOfExpression.get(i).getName());
			}
				
		}
		
		for (int i = 0; i < classNames.size(); i++) {
			listModel.addElement(classNames.elementAt(i));
		}

		return listModel;
	}

	private void beginASTParsing()
	{
		candidateExpressions = new ArrayList<Expression>();
		classOfExpression = new ArrayList<ClassObject>();
		methodOfExpression = new ArrayList<AbstractMethodDeclaration>();
		
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
								
								List<LocalVariableDeclarationObject> localVariables = methodObject.getLocalVariableDeclarations();
								
								ClassObject declaringClass = systemObject.getClassObject(methodObject.getClassName());
								
								for (LocalVariableDeclarationObject local : localVariables) {
									if(local.getVariableDeclaration().getInitializer() != null)
									{
										getLongInfixExpressions(local.getVariableDeclaration().getInitializer(), true, declaringClass ,methodObject);
									}
								}
								
								findAssignments(statements,declaringClass,methodObject);
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
	
	private void findAssignments(List<Statement> statements, ClassObject classObject, AbstractMethodDeclaration methodObject)
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
					
					System.out.println("assignment = " + assign.toString());
					
					getLongInfixExpressions(assign.getRightHandSide(), true, classObject, methodObject);
				}
			}
			else if(statement instanceof ForStatement)
			{
				ForStatement forStatement = (ForStatement) statement;
				
				getLongInfixExpressions(forStatement.getExpression(), false, classObject, methodObject);
				
				Statement forBody = forStatement.getBody();
				
				if(forBody instanceof Block)
				{
					Block forBlock = (Block) forBody;
					findAssignments(forBlock.statements(), classObject, methodObject);
				}
				else 
				{
					recursiveStatements.add(forBody);
					findAssignments(recursiveStatements, classObject, methodObject);
				}
			}
			else if(statement instanceof DoStatement)
			{
				DoStatement doStatement = (DoStatement) statement;
				
				getLongInfixExpressions(doStatement.getExpression(), false, classObject, methodObject);
				
				Statement doBody = doStatement.getBody();
				
				if(doBody instanceof Block)
				{
					Block doBlock = (Block) doBody;
					findAssignments(doBlock.statements(), classObject, methodObject);
				}
				else 
				{
					recursiveStatements.add(doBody);
					findAssignments(recursiveStatements, classObject, methodObject);
				}
			}
			else if(statement instanceof WhileStatement)
			{
				WhileStatement whileStatement = (WhileStatement) statement;
				
				getLongInfixExpressions(whileStatement.getExpression(), false, classObject, methodObject);
				
				Statement whileBody = whileStatement.getBody();
				
				if(whileBody instanceof Block)
				{
					Block whileBlock = (Block) whileBody;
					findAssignments(whileBlock.statements(), classObject, methodObject);
				}
				else 
				{
					recursiveStatements.add(whileBody);
					findAssignments(recursiveStatements, classObject, methodObject);
				}
			}
			else if(statement instanceof IfStatement)
			{
				IfStatement ifStatement = (IfStatement) statement;
				
				getLongInfixExpressions(ifStatement.getExpression(), false, classObject, methodObject);
				
				Statement thenBody = ifStatement.getThenStatement();
				Statement elseBody = ifStatement.getElseStatement();
				
				if(thenBody instanceof Block)
				{
					Block thenBlock = (Block) thenBody;
					findAssignments(thenBlock.statements(), classObject, methodObject);
				}
				else 
				{
					recursiveStatements.add(thenBody);
					findAssignments(recursiveStatements, classObject, methodObject);
				}
				
				if(elseBody != null)
				{
					if(elseBody instanceof Block)
					{
						Block elseBlock = (Block) elseBody;
						findAssignments(elseBlock.statements(), classObject, methodObject);
					}
					else 
					{
						recursiveStatements.add(elseBody);
						findAssignments(recursiveStatements, classObject, methodObject);
					}
				}
			}
			else if(statement instanceof TryStatement)
			{
				TryStatement tryStatement = (TryStatement) statement;
				
				Block tryBody = tryStatement.getBody();
				Block finallyBody = tryStatement.getFinally();
				
				findAssignments(tryBody.statements(), classObject, methodObject);
				if(finallyBody != null)
					findAssignments(finallyBody.statements(), classObject, methodObject);
			}
		}
	}
	
	private void highlightText(final String methodName)
	{
		display.syncExec(new Runnable() {
		    public void run() {
		    	for (int i = 0; i < candidateExpressions.size(); i++) 
				{
		    		
		    		if(methodName.equals(methodOfExpression.get(i).getName()))
		    		{
		    			System.out.println("+++++++++++ SOURCE EDITOR FOR EXPRESSION ++++++++++++++++ " + candidateExpressions.get(i).toString());
		    			
						IFile sourceFile = classOfExpression.get(i).getIFile();
						
						IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(sourceFile.getName());
						
						if(desc == null)
							System.out.println("SHOULD NOT REACH HERE");
						try 
						{
							ITextEditor sourceEditor = (ITextEditor) page.openEditor(new FileEditorInput(sourceFile), desc.getId());
							
							IDocument document = sourceEditor.getDocumentProvider().getDocument(sourceEditor.getEditorInput());
							
							AnnotationModel annotationModel = (AnnotationModel)sourceEditor.getDocumentProvider().getAnnotationModel(sourceEditor.getEditorInput());
							
							FindReplaceDocumentAdapter findExpression = new FindReplaceDocumentAdapter(document); 
							
							IRegion region = findExpression.find(0, candidateExpressions.get(i).toString(), true, true, false, false);
							
							if(region != null)
							{
								int line = document.getLineOfOffset(region.getOffset());
								
								SliceAnnotation annotation = new SliceAnnotation(SliceAnnotation.EXTRACTION, document.get(document.getLineOffset(line), document.getLineLength(line)));
														
								annotationModel.addAnnotation(annotation, new Position(document.getLineOffset(line),document.getLineLength(line)));
							}
							else 
							{
								System.out.println("Expression not found in editor");
							}
						}
						catch (PartInitException e) {
							e.printStackTrace();
						}
						catch (BadLocationException e) {
							e.printStackTrace();
						}
		    		}
				}
		    }
		});
	}
	
	private void getLongInfixExpressions(Expression expression, boolean isAssignmentExpression, ClassObject classObject, AbstractMethodDeclaration methodObject)
	{
		if (expression instanceof InfixExpression) 
		{
			InfixExpression infixExp = (InfixExpression) expression;
			
			if(isAssignmentExpression)
			{
				//System.out.println("EXPRESSION: " + expression.toString());
				//System.out.println("LEFT: " + infixExp.getLeftOperand().toString());
				//System.out.println("RIGHT: " + infixExp.getRightOperand().toString());
				//System.out.println("HAS: " + infixExp.extendedOperands().size() + " EXTRA");

				if((infixExp.getLeftOperand() instanceof InfixExpression) && (infixExp.getRightOperand() instanceof InfixExpression))
				{
					candidateExpressions.add(expression);
					classOfExpression.add(classObject);
					methodOfExpression.add(methodObject);
				}
				else if(!(infixExp.getLeftOperand() instanceof InfixExpression) && (infixExp.getRightOperand() instanceof InfixExpression))
				{
					InfixExpression rightInfixExp = (InfixExpression) infixExp.getRightOperand();

					if((rightInfixExp.getRightOperand() instanceof InfixExpression) || (rightInfixExp.getLeftOperand() instanceof InfixExpression))
					{
						candidateExpressions.add(expression);
						classOfExpression.add(classObject);
						methodOfExpression.add(methodObject);
					}
					else 
					{
						if(((infixExp.hasExtendedOperands())&&(infixExp.extendedOperands().size() >= 1))
							|| ((rightInfixExp.hasExtendedOperands())&&(rightInfixExp.extendedOperands().size() >= 1)))
						{
							candidateExpressions.add(expression);
							classOfExpression.add(classObject);
							methodOfExpression.add(methodObject);
						}
					}
				}
				else if((infixExp.getLeftOperand() instanceof InfixExpression) && !(infixExp.getRightOperand() instanceof InfixExpression))
				{
					InfixExpression leftInfixExp = (InfixExpression) infixExp.getLeftOperand();

					if((leftInfixExp.getRightOperand() instanceof InfixExpression) || (leftInfixExp.getLeftOperand() instanceof InfixExpression))
					{
						candidateExpressions.add(expression);
						classOfExpression.add(classObject);
						methodOfExpression.add(methodObject);
					}
					else 
					{
						if(((infixExp.hasExtendedOperands())&&(infixExp.extendedOperands().size() >= 1))
							|| ((leftInfixExp.hasExtendedOperands())&&(leftInfixExp.extendedOperands().size() >= 1)))
						{
							candidateExpressions.add(expression);
							classOfExpression.add(classObject);
							methodOfExpression.add(methodObject);
						}
					}
				}
				else
				{
					if((infixExp.hasExtendedOperands())&&(infixExp.extendedOperands().size() >= 2))
					{
						candidateExpressions.add(expression);
						classOfExpression.add(classObject);
						methodOfExpression.add(methodObject);
					}
				}
			}
			else
			{
				getLongInfixExpressions(infixExp.getLeftOperand(), true, classObject, methodObject);
				getLongInfixExpressions(infixExp.getRightOperand(), true, classObject, methodObject);
			}
		}
	}
	
}
