package MapRegions;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import relationshipEdges.InsteadOf;
import relationshipEdges.PartOf;
import relationshipEdges.Succession;
import DataHandling.GraphProperties;
import DataHandling.RefactoringOptions;
import UI.MainWindow;

import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.layout.mxParallelEdgeLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;

/*	This superclass handles the initialization of each different 
 * 	refactoring region of the map. It implements the coloring listener
 * 	as well as the show/hide button listener for the external refactorings.
 * 	Each region subclass must implement the methods that handle the 
 * 	graph creation and the 3-slide information window popup.
 */
public abstract class RefactoringRegion {

	protected mxCell[] vertices;
	protected mxCell[] externalVertices;
	protected Succession succession;
	protected PartOf partOf;
	protected InsteadOf insteadOf;
	protected GraphProperties graph;
	protected JPanel graphPanel;

	protected Display display;
	protected MainWindow map;
	protected IWorkbenchPage page;
	
	protected Object[] dataForIdentification;
	protected RefactoringOptions refactoringOptions;
	
	protected JLabel refactoringLabel;
	
	public RefactoringRegion(MainWindow p, IWorkbenchPage pg, int verticesNo, int extVerticesNo)
	{
		display = PlatformUI.getWorkbench().getDisplay();
		
		succession = new Succession();
		partOf = new PartOf();
		insteadOf = new InsteadOf();
		
		graphPanel = new JPanel();
		graphPanel.setSize(new Dimension(1020, 700));
		graphPanel.setBackground(Color.WHITE);
		graphPanel.setLayout(new BorderLayout(0, 0));
		
		graph = new GraphProperties();
		
		vertices = new mxCell[verticesNo];
		externalVertices = new mxCell[extVerticesNo];	
		
		page = pg;
		map = p;
		
		refactoringOptions = new RefactoringOptions(map,null,null);
		
		createGraph(graph.getParent());
		
		mxIGraphLayout layout = new mxParallelEdgeLayout(graph.getMyGraph());
		layout.execute(graph.getParent());
		
		final mxGraphComponent graphComponent = graph.getMyGraphComponent();
		
		graphComponent.getGraphControl().addMouseListener(
				new MouseAdapter() 
				{
					public void mouseReleased(MouseEvent e)
					{
						mxCell cell = (mxCell) graphComponent.getCellAt(e.getX(), e.getY());
						
						if((cell != null)&&(cell.isVertex()))
						{
							if(e.getButton() == MouseEvent.BUTTON1)
							{
								graph.colorizeVertices(cell,succession,partOf,insteadOf);
							}
							else if(e.getButton() == MouseEvent.BUTTON3)
							{
								graph.colorizeVertices(cell,succession,partOf,insteadOf);
								popUp(cell);
							}
						}
					}			
				} 
			);		
		
		graphPanel.add(graphComponent, BorderLayout.CENTER);
		
		graph.initColors();
		
		refactoringLabel = new JLabel("");
		refactoringLabel.setFont(new Font("Arial", Font.BOLD, 15));
		refactoringLabel.setOpaque(true);
		refactoringLabel.setBackground(Color.LIGHT_GRAY);
		graphPanel.add(refactoringLabel, BorderLayout.SOUTH);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Show/Hide refactorings from external regions");
		rdbtnNewRadioButton.setSelected(true);
		updateGraph(true);
		rdbtnNewRadioButton.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				if(event.getStateChange() == ItemEvent.SELECTED)
				{
					updateGraph(true);
				}
				else if(event.getStateChange() == ItemEvent.DESELECTED)
				{
					updateGraph(false);
				}
			}
		});
		rdbtnNewRadioButton.setToolTipText("Click to show/hide relations with refactorings from other regions.");
		rdbtnNewRadioButton.setFocusPainted(false);
		rdbtnNewRadioButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		graphPanel.add(rdbtnNewRadioButton, BorderLayout.NORTH);
	}
	
	public JPanel getGraphPanel()
	{
		return graphPanel;
	}
	
	public abstract void popUp(mxCell cell);
	
	public abstract void updateGraph(boolean showExternal);
	
	public abstract void createGraph(Object p);
}
