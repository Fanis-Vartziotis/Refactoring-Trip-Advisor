package DataHandling;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;

/*	This class handles the different types of selections
 * 	the user selects from Eclipse's Package Explorer.
 * 	The selection is given as input to the implemented
 * 	refactoring detectors to begin parsing the chosen project
 */
public class PackageExplorerSelection {
	
	private IJavaProject selectedProject;
	private IPackageFragmentRoot selectedPackageFragmentRoot;
	private IPackageFragment selectedPackageFragment;
	private ICompilationUnit selectedCompilationUnit;
	private IType selectedType;
	private IMethod selectedMethod;
	
	public void setSelections(IPackageFragmentRoot packageFragmentRoot, IPackageFragment packageFragment, ICompilationUnit compilationUnit, IType type, IMethod method)
	{
		selectedPackageFragmentRoot = packageFragmentRoot;
		selectedPackageFragment = packageFragment;
		selectedCompilationUnit = compilationUnit;
		selectedType = type;
		selectedMethod = method;
	}

	public IJavaProject getSelectedProject() {
		return selectedProject;
	}

	public void setSelectedProject(IJavaProject project) {
		selectedProject = project;
	}

	public IPackageFragmentRoot getSelectedPackageFragmentRoot() {
		return selectedPackageFragmentRoot;
	}

	public IPackageFragment getSelectedPackageFragment() {
		return selectedPackageFragment;
	}

	public ICompilationUnit getSelectedCompilationUnit() {
		return selectedCompilationUnit;
	}

	public IType getSelectedType() {
		return selectedType;
	}

	public IMethod getSelectedMethod() {
		return selectedMethod;
	}
}
