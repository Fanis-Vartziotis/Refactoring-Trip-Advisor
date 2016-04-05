package ParsingHelpers;

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.Expression;

import gr.uom.java.ast.AbstractMethodDeclaration;
import gr.uom.java.ast.ClassObject;
import gr.uom.java.ast.ParameterObject;

/*	A helping class containing information about each methods parameter
 * 	during the project code parsing. It is used by the Remove Assignments
 * 	to Parameters Detector.
 */
public class MethodParameterObject {

	private ClassObject classObject;
	private AbstractMethodDeclaration methodObject;
	private ParameterObject parameter;
	private ArrayList<Expression> assignments;
	
	public MethodParameterObject(ClassObject cls, AbstractMethodDeclaration mtd, ParameterObject par)
	{
		classObject = cls;
		methodObject = mtd;
		parameter = par;
		assignments = new ArrayList<Expression>();
	}

	public ClassObject getClassObject() {
		return classObject;
	}

	public AbstractMethodDeclaration getMethodObject() {
		return methodObject;
	}

	public ParameterObject getParameter() {
		return parameter;
	}
	
	public void addExpression(Expression exp)
	{
		assignments.add(exp);
	}
	
	public boolean isAssigned()
	{
		if(assignments.isEmpty())
			return false;
		else
			return true;
	}
}
