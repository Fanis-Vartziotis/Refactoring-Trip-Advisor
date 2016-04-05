package gr.uom.java.ast.decomposition;

import java.util.List;

import gr.uom.java.ast.ASTInformation;
import gr.uom.java.ast.ASTInformationGenerator;

import org.eclipse.jdt.core.dom.Statement;

public abstract class AbstractStatement extends AbstractMethodFragment {

	private Statement statement;
	private StatementType type;
	
    public AbstractStatement(Statement statement, StatementType type, AbstractMethodFragment parent) {
    	super(parent);
    	this.type = type;
    	this.statement = statement;
    }

    public Statement getStatement() {
    	return statement;
    }

	public StatementType getType() {
		return type;
	}

	public abstract List<String> stringRepresentation();
}
