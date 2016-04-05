package gr.uom.java.ast;

import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;

public abstract class CreationObject {
	private TypeObject type;
	//protected ASTInformation creation;
	protected ClassInstanceCreation creation;
	protected ArrayCreation creation2;
	
	public CreationObject(TypeObject type) {
		this.type = type;
	}

	public TypeObject getType() {
		return type;
	}
}
