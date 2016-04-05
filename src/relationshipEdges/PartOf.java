package relationshipEdges;

import com.mxgraph.view.mxGraph;

/*	This subclass is responsible for the drawing/connection
 * 	of the "isPartOf" edges in the graph.
 */
public class PartOf extends RelationshipEdge{
	
	public PartOf()
	{
		super();
	}
	
	public void connectEdges(mxGraph g, Object p)
	{
		g.getModel().beginUpdate();
		try
		{
			for (int i = edgeCounter; i < source.size(); i++) {
				g.insertEdge(p, null, null, source.get(i), destination.get(i),"isPartOf");
			}
			edgeCounter = source.size();
		}
		finally
		{
			g.getModel().endUpdate();
		}
	}

}
