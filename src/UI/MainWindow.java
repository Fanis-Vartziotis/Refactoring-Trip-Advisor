package UI;

import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.awt.Dimension;
import javax.swing.JMenuItem;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jface.viewers.IStructuredSelection;

import relationshipEdges.InsteadOf;
import relationshipEdges.PartOf;
import relationshipEdges.Succession;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxParallelEdgeLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;

import DataHandling.GraphProperties;
import DataHandling.PackageExplorerSelection;
import MapRegions.ConditionalExpressionSimplification;
import MapRegions.DataOrganization;
import MapRegions.FeatureMovementBetweenObjects;
import MapRegions.GeneralizationImprovement;
import MapRegions.MethodCallImprovement;
import MapRegions.MethodComposition;

/*	This class handles the creation of the main window of Refactoring Trip Advisor.
 * 	The RTA application starts by clicking on the corresponding option under Eclipse's
 * 	"Refactor" menu. That is the reason why this class needs to implement the IWorkbenchWindowActionDelegate
 * 	interface. This class creates the starting region graph as well as all the RTA menus and submenus.
 * 	It also contains the Package Explorer listener and updates a PackageExplorerSelection object
 * 	every time the user changes his selection. 
 */
public class MainWindow implements IWorkbenchWindowActionDelegate{

	private JFrame tripAdvisorFrame;
	
	private PackageExplorerSelection selectionInfo = new PackageExplorerSelection();
	
	private MethodComposition mGraphComposition;
	private FeatureMovementBetweenObjects mFeatureMovement;
	private MethodCallImprovement mMethodCall;
	private ConditionalExpressionSimplification mCondExpression;
	private GeneralizationImprovement mGeneralImp;
	private DataOrganization mDataOrganization;
	
	private mxCell[] regions;
	private GraphProperties regionGraph;
	private Succession succession;
	private PartOf partOf;
	private InsteadOf insteadOf;
	private MainWindow thisObject; 
	private IWorkbenchPage page;
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void run(IAction action) {
		initialize();
	}
	
	public void selectionChanged(IAction action, ISelection selection) {
		System.out.println("------------- SELECTION CHANGED ----------------");
		
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection)selection;
			Object element = structuredSelection.getFirstElement();
			IJavaProject javaProject = null;
			if(element instanceof IJavaProject) {
				javaProject = (IJavaProject)element;

				selectionInfo.setSelections(null, null, null, null, null);
			}
			else if(element instanceof IPackageFragmentRoot) {
				IPackageFragmentRoot packageFragmentRoot = (IPackageFragmentRoot)element;
				javaProject = packageFragmentRoot.getJavaProject();
				
				selectionInfo.setSelections(packageFragmentRoot, null, null, null, null);
			}
			else if(element instanceof IPackageFragment) {
				IPackageFragment packageFragment = (IPackageFragment)element;
				javaProject = packageFragment.getJavaProject();
				
				selectionInfo.setSelections(null, packageFragment, null, null, null);
			}
			else if(element instanceof ICompilationUnit) {
				ICompilationUnit compilationUnit = (ICompilationUnit)element;
				javaProject = compilationUnit.getJavaProject();
				
				selectionInfo.setSelections(null, null, compilationUnit, null, null);
			}
			else if(element instanceof IType) {
				IType type = (IType)element;
				javaProject = type.getJavaProject();
				
				selectionInfo.setSelections(null, null, null, type, null);
			}
			else if(element instanceof IMethod) {
				IMethod method = (IMethod)element;
				javaProject = method.getJavaProject();
				
				selectionInfo.setSelections(null, null, null, null, method);
			}
			if(javaProject != null && !javaProject.equals(selectionInfo.getSelectedProject())) {
				//selectedProject = javaProject;	
				
				selectionInfo.setSelectedProject(javaProject);
			}
		}		
	}
	

	public void dispose() {
		try {
			this.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void init(IWorkbenchWindow window) {		
	}
	
	public PackageExplorerSelection getSelectionInfo() {
		return selectionInfo;
	}
	
	private void createRegionGraph()
	{
		regions = new mxCell[10];
		regionGraph = new GraphProperties();
		
		succession = new Succession();
		partOf = new PartOf();
		insteadOf = new InsteadOf();
		
		Object parent = regionGraph.getParent();
		mxGraph g = regionGraph.getMyGraph();
		
		g.getModel().beginUpdate();
		try
		{
			regions[0] = (mxCell) g.insertVertex(parent, "v0", "Method Composition", 0, 0, 150, 100, "circle");
			regions[1] = (mxCell) g.insertVertex(parent, "v1", "Feature Movement\nBetween Objects", 0, 0, 150, 100, "circle");
			regions[2] = (mxCell) g.insertVertex(parent, "v2", "Method Call Improvement", 0, 0, 150, 100, "circle");
			regions[3] = (mxCell) g.insertVertex(parent, "v3", "Conditional Expression\nSimplification", 0, 0, 150, 100, "circle");
			regions[4] = (mxCell) g.insertVertex(parent, "v4", "Generalization Improvement", 0, 0, 150, 100, "circle");
			regions[5] = (mxCell) g.insertVertex(parent, "v5", "Data Organization", 0, 0, 150, 100, "circle");
			
			//Method Composition
			succession.addRelation(regions[0], regions[1]);
			succession.addRelation(regions[0], regions[2]);
			succession.addRelation(regions[4], regions[0]);
			succession.addRelation(regions[2], regions[0]);
			partOf.addRelation(regions[4], regions[0]);
			partOf.addRelation(regions[5], regions[0]);
			
			//Feature Movement
			succession.addRelation(regions[5], regions[1]);
			succession.addRelation(regions[2], regions[1]);
			partOf.addRelation(regions[5], regions[1]);
			partOf.addRelation(regions[4], regions[1]);
			partOf.addRelation(regions[3], regions[1]);
			insteadOf.addRelation(regions[4], regions[1]);
			insteadOf.addRelation(regions[2], regions[1]);
			
			//Method Call Improvement
			partOf.addRelation(regions[5], regions[2]);
			insteadOf.addRelation(regions[4], regions[2]);
			insteadOf.addRelation(regions[3], regions[2]);
			
			//Conditional Expression Simplification
			succession.addRelation(regions[5], regions[3]);
			partOf.addRelation(regions[4], regions[3]);
			
			//Generalization Improvement
			partOf.addRelation(regions[5], regions[4]);
			
			succession.connectEdges(g, parent);
			partOf.connectEdges(g, parent);
			insteadOf.connectEdges(g, parent);

		}
		finally
		{
			g.getModel().endUpdate();
		}
		
		mxCircleLayout layout = new mxCircleLayout(g);
		layout.setX0(125);
		layout.setY0(5);
		layout.execute(parent);
		
		mxParallelEdgeLayout layout2 = new mxParallelEdgeLayout(g);
		layout2.execute(parent);
		
		regionGraph.getMyGraphComponent().getGraphControl().addMouseListener(
			new MouseAdapter()
			{
				public void mouseReleased(MouseEvent e)
				{
					mxCell cell = (mxCell) regionGraph.getMyGraphComponent().getCellAt(e.getX(), e.getY());
					
					if((cell != null)&&(cell.isVertex()))
					{
						if(e.getButton() == MouseEvent.BUTTON3)
						{
							switch(cell.getId())
							{
								case "v0" :
									mGraphComposition =  new MethodComposition(thisObject,page);
									tripAdvisorFrame.setContentPane(mGraphComposition.getGraphPanel());
									tripAdvisorFrame.revalidate();
									break;
								case "v1" :
									mFeatureMovement = new FeatureMovementBetweenObjects(thisObject,page);
									tripAdvisorFrame.setContentPane(mFeatureMovement.getGraphPanel());
									tripAdvisorFrame.revalidate();
									break;
								case "v2" :
									mMethodCall = new MethodCallImprovement(thisObject,page);
									tripAdvisorFrame.setContentPane(mMethodCall.getGraphPanel());
									tripAdvisorFrame.revalidate();
									break;
								case "v3" :
									mCondExpression = new ConditionalExpressionSimplification(thisObject,page);
									tripAdvisorFrame.setContentPane(mCondExpression.getGraphPanel());
									tripAdvisorFrame.revalidate();
									break;
								case "v4" :
									mGeneralImp = new GeneralizationImprovement(thisObject,page);
									tripAdvisorFrame.setContentPane(mGeneralImp.getGraphPanel());
									tripAdvisorFrame.revalidate();
									break;
								case "v5" :
									mDataOrganization = new DataOrganization(thisObject,page);	
									tripAdvisorFrame.setContentPane(mDataOrganization.getGraphPanel());
									tripAdvisorFrame.revalidate();
									break;
								default:
							}
						}
					}
				}
			}
		);
		
		regionGraph.colorizeVertices(null, succession,partOf,insteadOf);
	}
	

	public void initialize() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");			
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		//Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		
		thisObject = this;
		page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		
		tripAdvisorFrame = new JFrame();
		tripAdvisorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//tripAdvisorFrame.setLocation(new Point(100, 100));
		tripAdvisorFrame.setResizable(true);
		tripAdvisorFrame.setSize(new Dimension(1020, 750));
		
		//tripAdvisorFrame.setBounds(0, 0,1000/*(int) (0.8 * screensize.width)*/,(int) (0.95 * screensize.height));
		
		tripAdvisorFrame.setTitle("Refactoring Trip Advisor (beta)");		
		tripAdvisorFrame.setIconImage(java.awt.Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/repair.png")));
		
		tripAdvisorFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e)
            {
				dispose();
				e.getWindow().dispose();
            }
		});
		
		
		JMenuBar menuBar = new JMenuBar();
		tripAdvisorFrame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmTest = new JMenuItem("Close");
		mntmTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tripAdvisorFrame.dispose();
			}
		});
		mnFile.add(mntmTest);
		
		JMenu mnNewMenu = new JMenu("Navigate");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmHome = new JMenuItem("Home");	
		mnNewMenu.add(mntmHome);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAboutRefactoring = new JMenuItem("About Refactoring");
		mntmAboutRefactoring.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new HelpContentsWindow("About Refactoring", "/images/AboutRefactoring.png").setVisible(true);
			}
		});
		mnHelp.add(mntmAboutRefactoring);
		
		JMenuItem mntmHowToUse = new JMenuItem("How to use");
		mntmHowToUse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new HelpContentsWindow("How to use Refactoring Trip Advisor", "/images/HowToUse.png").setVisible(true);
			}
		});
		mnHelp.add(mntmHowToUse);
		
		JMenuItem mntmRefactoringDetectors = new JMenuItem("Refactoring Detectors");
		mntmRefactoringDetectors.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new HelpContentsWindow("Refactoring Detectors", "/images/RefactoringDetectors.png").setVisible(true);
			}
		});
		mnHelp.add(mntmRefactoringDetectors);
		
		mntmHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createRegionGraph();
				tripAdvisorFrame.setContentPane(regionGraph.getMyGraphComponent());
				tripAdvisorFrame.revalidate();
			}
		});
		
		createRegionGraph();
		tripAdvisorFrame.setContentPane(regionGraph.getMyGraphComponent());;
		
		tripAdvisorFrame.setVisible(true);
	}	

}
