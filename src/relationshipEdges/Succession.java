package relationshipEdges;

import com.mxgraph.view.mxGraph;

/*	This subclass is responsible for the drawing/connection
 * 	of the "succession" edges in the graph.
 */
public class Succession extends RelationshipEdge{
	
	public Succession()
	{
		super();
	}
	
	public void connectEdges(mxGraph g, Object p)
	{
		g.getModel().beginUpdate();
		try
		{
			for (int i = edgeCounter; i < source.size(); i++) {
				g.insertEdge(p, null, null, source.get(i), destination.get(i),"succession");			
			}
			edgeCounter = source.size();
		}
		finally
		{
			g.getModel().endUpdate();
		}
	}
}
