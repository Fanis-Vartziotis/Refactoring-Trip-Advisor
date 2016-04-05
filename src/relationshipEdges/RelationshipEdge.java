package relationshipEdges;

import java.util.ArrayList;

import com.mxgraph.model.mxCell;

/*	This superclass handles the edge set of the graph.
 * 	It provides functions for adding and removing edges
 * 	to the set.
 */
public class RelationshipEdge {

	protected ArrayList<Object> source,destination;
	protected int edgeCounter;
	
	public RelationshipEdge()
	{
		source = new ArrayList<Object>();
		destination = new ArrayList<Object>();
		edgeCounter = 0;
	}
	
	public ArrayList<Object> getSource() {
		return source;
	}

	public ArrayList<Object> getDestination() {
		return destination;
	}
	
	public boolean addRelation(Object l, Object r)
	{
		if((l != null)&&(r != null))
		{
			source.add(l);
			destination.add(r);
			return true;
		}
		return false;
	}
	
	public void removeRelatedEdges(mxCell cell)
	{
		mxCell srcNode,destNode;	
		
		for (int i = 0; i < source.size(); i++) {
			srcNode = (mxCell)source.get(i);
			
			if(cell.getId().equals(srcNode.getId()))
			{
				source.remove(i);
				destination.remove(i);
				edgeCounter = edgeCounter - 1;
				i = i - 1;
			}
		}
		for (int i = 0; i < destination.size(); i++) {
			destNode = (mxCell)destination.get(i);
			
			if(cell.getId().equals(destNode.getId()))
			{
				source.remove(i);
				destination.remove(i);
				edgeCounter = edgeCounter - 1;
				i = i - 1;
			}
		}		
	}
}
