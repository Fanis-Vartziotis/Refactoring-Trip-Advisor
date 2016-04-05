package RefactoringDetectors;

import gr.uom.java.distance.CandidateRefactoring;
import gr.uom.java.distance.CurrentSystem;
import gr.uom.java.distance.DistanceMatrix;
import gr.uom.java.distance.MoveMethodCandidateRefactoring;
import gr.uom.java.distance.MySystem;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.swing.ListSelectionModel;

import gr.uom.java.ast.ASTReader;
import gr.uom.java.ast.ClassObject;
import gr.uom.java.ast.SystemObject;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.IProgressService;

import DataHandling.PackageExplorerSelection;
import UI.MainWindow;

/*	This class implements the detection of Move Method opportunities
 *	in the user's code. It uses JDeodorant's move method detection
 *	algorithm.
 */
public class MoveMethodIdentification implements RefactoringDetector{

	private JFrame mainFrame;
	private JPanel mainPanel;
	private CandidateRefactoring[] moveTable;
	private PackageExplorerSelection selectionInfo;
	private boolean opportunitiesFound;

	public MoveMethodIdentification() {
		mainFrame = new JFrame();
		mainFrame.setTitle("Move Method Opportunities");
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
		
		computeMoveTable();

		if((moveTable==null)||(moveTable.length<=1))
			opportunitiesFound = false;
			
		final JLabel label = new JLabel("");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Arial", Font.PLAIN, 16));
		label.setBounds(415, 201, 369, 32);
		mainPanel.add(label);
		
		final JLabel label_1 = new JLabel("");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("Arial", Font.PLAIN, 16));
		label_1.setBounds(415, 357, 369, 32);
		mainPanel.add(label_1);
		
		final JList list = new JList();
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				String value = (String)list.getSelectedValue();
				
				for (int i = 0; i < moveTable.length; i++) {
					if(!moveTable[i].getSource().equals(""))
					{
						//String splitted[] = moveTable[i].getSourceEntity().split(":");
						if(moveTable[i].getSourceEntity().equals(value))
						{
							label.setText(moveTable[i].getSource());
							label_1.setText(moveTable[i].getTarget());
						}
					}
				}
			}
		});
		list.setSelectedIndex(0);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBounds(10, 100, 395, 460);
		list.setModel(computeListModel());
		mainPanel.add(list);
		
		JLabel lblNewLabel = new JLabel("Choose a method to view its destination");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 16));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 60, 395, 23);
		mainPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Source Class:");
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 16));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(415, 173, 369, 32);
		mainPanel.add(lblNewLabel_1);
		
		JLabel lblTargetClass = new JLabel("Target Class:");
		lblTargetClass.setHorizontalAlignment(SwingConstants.CENTER);
		lblTargetClass.setFont(new Font("Arial", Font.BOLD, 16));
		lblTargetClass.setBounds(415, 328, 369, 32);
		mainPanel.add(lblTargetClass);

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
		
		if(moveTable!=null)
		{
			for (int i = 0; i < moveTable.length; i++) {
				if(!moveTable[i].getSource().equals(""))
				{
					/*
					String splitted[] = moveTable[i].getSourceEntity().split(":");
					for (int j = 0; j < splitted.length; j++) {
						System.out.println("SPLIT[" + j + "]: " + splitted[j]);
					}*/
					//System.out.println("SOURCE: " + moveTable[i].getSource());
					//System.out.println("SOURCE ENTITY: " + moveTable[i].getSourceEntity());
					//System.out.println("SOURCE TYPE DECLARATION: " + moveTable[i].getSourceClassTypeDeclaration().toString());
					listModel.addElement(moveTable[i].getSourceEntity());
				}
			}
		}

		return listModel;
	}
	
	public void  computeMoveTable() {
		moveTable = null;
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
									new ASTReader(selectionInfo.getSelectedProject(), monitor);
								}
							});
						//}
						SystemObject systemObject = ASTReader.getSystemObject();
						Set<ClassObject> classObjectsToBeExamined = new LinkedHashSet<ClassObject>();
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
						else {
							classObjectsToBeExamined.addAll(systemObject.getClassObjects());
						}
			
						final Set<String> classNamesToBeExamined = new LinkedHashSet<String>();
						for(ClassObject classObject : classObjectsToBeExamined) {
							if(!classObject.isEnum())
								classNamesToBeExamined.add(classObject.getName());
						}
						MySystem system = new MySystem(systemObject, false);
						final DistanceMatrix distanceMatrix = new DistanceMatrix(system);
						ps.busyCursorWhile(new IRunnableWithProgress() {
							public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
								distanceMatrix.generateDistances(monitor);
							}
						});
						final List<MoveMethodCandidateRefactoring> moveMethodCandidateList = new ArrayList<MoveMethodCandidateRefactoring>();
			
						ps.busyCursorWhile(new IRunnableWithProgress() {
							public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
								moveMethodCandidateList.addAll(distanceMatrix.getMoveMethodCandidateRefactoringsByAccess(classNamesToBeExamined, monitor));
							}
						});
			
						moveTable = new CandidateRefactoring[moveMethodCandidateList.size() + 1];
						moveTable[0] = new CurrentSystem(distanceMatrix);
						int counter = 1;
						for(MoveMethodCandidateRefactoring candidate : moveMethodCandidateList) {
							moveTable[counter] = candidate;
							counter++;
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
