package hudson.plugins.jslint;

import hudson.model.AbstractProject;
import hudson.plugins.analysis.core.AbstractProjectAction;

/**
 * Entry point to visualize the JSLint trend graph in the project screen.
 * Drawing of the graph is delegated to the associated
 * {@link JslintResultAction}
 *
 * @author Joe Heth
 */
public class JslintProjectAction extends AbstractProjectAction {

	/**
	 * Instantiates a new find bugs project action.
	 *
	 * @param project
	 *            the project that owns this action
	 */
	public JslintProjectAction(final AbstractProject<?, ?> project) {
		super(project, JslintResultAction.class, new JslintDescriptor());
	}

	/** {@inheritDoc} */
	public String getDisplayName() {
		return Messages.JSLINT_ProjectAction_Name();
	}

	/** {@inheritDoc} */
	@Override
	public String getTrendName() {
		return Messages.JSLINT_Trend_Name();
	}
}

