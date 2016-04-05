package ParsingHelpers;

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.Expression;

import gr.uom.java.ast.AbstractMethodDeclaration;
import gr.uom.java.ast.ClassObject;
import gr.uom.java.ast.LocalVariableDeclarationObject;
import gr.uom.java.ast.TypeObject;

/*	A helping class containing information about each methods local variable
 * 	during the project code parsing. It is used by the Inline Temp Detector,
 * 	Replace Temp with Query Detector and Split Temporary Variable Detector.
 */
public class VariableObject {

	private ClassObject classObject;
	private AbstractMethodDeclaration methodObject;
	private LocalVariableDeclarationObject variable;
	private TypeObject type;
	private ArrayList<Expression> assignments;
	
	public VariableObject(ClassObject cls, AbstractMethodDeclaration mtd, TypeObject tp, LocalVariableDeclarationObject var, Expression initializer)
	{
		classObject = cls;
		methodObject = mtd;
		type = tp;
		variable = var;
		assignments = new ArrayList<Expression>();
		if(initializer!=null)
			assignments.add(initializer);
	}
	
	public void addExpression(Expression exp)
	{
		assignments.add(exp);
	}

	public ClassObject getClassObject() {
		return classObject;
	}

	public AbstractMethodDeclaration getMethodObject() {
		return methodObject;
	}

	public ArrayList<Expression> getAssignments() {
		return assignments;
	}

	public LocalVariableDeclarationObject getVariable() {
		return variable;
	}
	
	public TypeObject getType() {
		return type;
	}

	public boolean isPrimitive()
	{
		boolean result = false;
		
		switch (type.toString()) {
		case "byte":
			result = true;
			break;
		case "short":
			result = true;
			break;
		case "char":
			result = true;
			break;
		case "int":
			result = true;
			break;
		case "long":
			result = true;
			break;
		case "float":
			result = true;
			break;
		case "double":
			result = true;
			break;
		case "boolean":
			result = true;
			break;
		case "void":
			result = true;
			break;
		default:
			break;
		}
		
		return result;
	}
	
	public void printDetails()
	{
		System.out.println("Class name: " + classObject.getName() + "\nMethod name: " + methodObject.getName() + "\nType:" + type.toString() + "\nVariable Name: " + variable.getName() + "\nAssignments:");
		for (int i = 0; i < assignments.size(); i++) {
			System.out.println(assignments.get(i).toString());
		}
	}
}
