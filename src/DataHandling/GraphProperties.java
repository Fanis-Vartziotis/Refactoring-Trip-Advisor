package DataHandling;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import relationshipEdges.InsteadOf;
import relationshipEdges.PartOf;
import relationshipEdges.Succession;

import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;

/*	This class handles all the graph properties (node,edge styles)  
 * 	for the refactoring maps of Refactoring Trip Advisor.
 * 	It also handles the path coloring option Refactoring Trip Advisor provides.
 */

public class GraphProperties {

	private mxGraph graph;
	private final mxGraphComponent graphComponent;
	
	public GraphProperties()
	{
		graph = new mxGraph()
		{
			public String getToolTipForCell(Object cell)
			{
				String tooltip = "<html>" + "Left Click: Display Refactoring Succession Path" + "<br>" + "Right Click: Display Information about this Refactoring" + "</html>";
				return tooltip;
			}
		};
		
		initGraph();
		
		graphComponent = new mxGraphComponent(graph);
		graphComponent.setToolTips(true);
		graphComponent.getViewport().setBackground(Color.WHITE);
		//graphComponent.setEnabled(false);		
	}
	
	private void initGraph()
	{
		graph.getModel().beginUpdate();
		try
		{	
			mxStylesheet stylesheet = graph.getStylesheet();
			
			Hashtable<String, Object> style = new Hashtable<String, Object>();
			style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
			style.put(mxConstants.STYLE_FILLCOLOR, "#e6f2ff");
			style.put(mxConstants.STYLE_FONTCOLOR, "#000000");
			stylesheet.putCellStyle("circle", style);
			
			style = new Hashtable<String, Object>();
			style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
			stylesheet.putCellStyle("rect", style);
			
			graph.setCellsBendable(false);
			//graph.setCellsCloneable(false);
			graph.setCellsDeletable(true);
			graph.setCellsEditable(false);
			graph.setCellsMovable(true);
			graph.setCellsResizable(false);
			//graph.setCellsLocked(true);
			//graph.setCellsDisconnectable(false);
			graph.setCellsSelectable(true);
			graph.setVertexLabelsMovable(false);
			graph.setEdgeLabelsMovable(false);
						
			
			Map<String, Object> edges = new Hashtable<String, Object>();			
			edges.put(mxConstants.STYLE_STROKECOLOR, "black");
			edges.put(mxConstants.STYLE_STROKEWIDTH, 2);
			stylesheet.putCellStyle("succession", edges);
			
			edges = new Hashtable<String, Object>();
			edges.put(mxConstants.STYLE_DASHED, true);
			edges.put(mxConstants.STYLE_STROKECOLOR, "black");
			edges.put(mxConstants.STYLE_STROKEWIDTH, 2);
			stylesheet.putCellStyle("isPartOf", edges);	
			
			edges = new Hashtable<String, Object>();
			edges.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CURVE);
			edges.put(mxConstants.STYLE_STROKECOLOR, "black");
			edges.put(mxConstants.STYLE_STROKEWIDTH, 2);
			stylesheet.putCellStyle("curved", edges);	
			
			edges = new Hashtable<String, Object>();
			edges.put(mxConstants.STYLE_STARTARROW, mxConstants.ARROW_CLASSIC);
			edges.put(mxConstants.STYLE_DASHED, true);
			edges.put(mxConstants.STYLE_STROKECOLOR, "black");
			edges.put(mxConstants.STYLE_STROKEWIDTH, 2);
			stylesheet.putCellStyle("bi", edges);
			
			edges = new Hashtable<String, Object>();
			edges.put(mxConstants.STYLE_STROKECOLOR, "white");
			stylesheet.putCellStyle("biInv", edges);
			
			edges = new Hashtable<String, Object>();
			edges.put(mxConstants.STYLE_STARTARROW, mxConstants.ARROW_CLASSIC);
			edges.put(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_CLASSIC);
			edges.put(mxConstants.STYLE_DASHED, true);
			edges.put(mxConstants.STYLE_STROKECOLOR, "black");
			edges.put(mxConstants.STYLE_STROKEWIDTH, 2);
			stylesheet.putCellStyle("insteadOf", edges);
			
		}
		finally
		{
			graph.getModel().endUpdate();
		}
	}
	
	public void colorizeVertices(mxCell cell, Succession suc, PartOf part, InsteadOf in)
	{
		initColors();
		if(cell!=null)
		{
			graph.getView().getState(cell).getStyle().put(mxConstants.STYLE_FILLCOLOR, "yellow");
			
			ArrayList<Object> src = suc.getSource();
			ArrayList<Object> dest = suc.getDestination();
	
			colorizeNeighborhood(cell, src, dest,"succession");
			
			src = part.getSource();
			dest = part.getDestination();
			
			colorizeNeighborhood(cell, src, dest,"isPartOf");
			
			src = in.getSource();
			dest = in.getDestination();
			
			colorizeNeighborhood(cell, src, dest,"insteadOf");
		}
		
		graph.repaint();
	}
	
	private void colorizeNeighborhood(mxCell cell, ArrayList<Object> src, ArrayList<Object> dest,String relationshipType)
	{
		mxCell srcNode,destNode;	
		
		for (int i = 0; i < src.size(); i++) {
			srcNode = (mxCell)src.get(i);
			
			if(cell.getId().equals(srcNode.getId()))
			{
				destNode = (mxCell)dest.get(i);
				
				switch (relationshipType) {
				case "isPartOf":
					graph.getView().getState(destNode).getStyle().put(mxConstants.STYLE_FILLCOLOR, "ffcc80");//orange
					break;
				case "insteadOf":
					graph.getView().getState(destNode).getStyle().put(mxConstants.STYLE_FILLCOLOR, "#0099FF");//blue
					break;
				case "succession":
					graph.getView().getState(destNode).getStyle().put(mxConstants.STYLE_FILLCOLOR, "#00FF00");//green
					break;
				default:
					break;
				}				
			}
		}
		for (int i = 0; i < dest.size(); i++) {
			destNode = (mxCell)dest.get(i);
			
			if(cell.getId().equals(destNode.getId()))
			{
				srcNode = (mxCell)src.get(i);
				
				switch (relationshipType) {
				case "isPartOf":
					graph.getView().getState(srcNode).getStyle().put(mxConstants.STYLE_FILLCOLOR, "ffcc80");//orange
					break;
				case "insteadOf":
					graph.getView().getState(srcNode).getStyle().put(mxConstants.STYLE_FILLCOLOR, "#0099FF");//blue
					break;
				case "succession":
					graph.getView().getState(srcNode).getStyle().put(mxConstants.STYLE_FILLCOLOR, "red"); //red
					break;
				default:
					break;
				}	
			}
		}
	}
	
	public void initColors()
	{
		//System.out.println("initColors METHOD JUST GOT CALLED");
	
		//Initialize coloring for the graph
		Object[] nodes = graph.getChildVertices(graph.getDefaultParent());
		mxCell cell;
		//System.out.println("NODE LENGTH = " + nodes.length);
		
		for (int i = 0; i < nodes.length; i++) {
			cell = (mxCell)nodes[i];
			
			//System.out.println("NODE ID " + cell.getId());
			
			graph.getView().getState(cell).getStyle().put(mxConstants.STYLE_FILLCOLOR, "#e6f2ff");
		}
	}
	
	public Object getParent()
	{
		return graph.getDefaultParent();
	}
	
	public mxGraphComponent getMyGraphComponent()
	{
		return graphComponent;
	}
	
	public mxGraph getMyGraph()
	{
		return graph;
	}
}
