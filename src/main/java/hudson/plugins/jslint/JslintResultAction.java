package hudson.plugins.jslint;

import hudson.model.AbstractBuild;
import hudson.plugins.analysis.core.AbstractResultAction;
import hudson.plugins.analysis.core.HealthDescriptor;
import hudson.plugins.analysis.core.PluginDescriptor;

/**
 * Controls the live cycle of the JSLint results. This action persists the
 * results of the JSLint analysis of a build and displays the results on the
 * build page. The actual visualization of the results is defined in the
 * matching <code>summary.jelly</code> file.
 * <p>
 * Moreover, this class renders the JSLint result trend.
 * </p>
 *
 * @author Joe Heth
 */
public class JslintResultAction extends AbstractResultAction<JslintResult> {

    /**
     * Creates a new instance of <code>JslintResultAction</code>.
     *
     * @param owner
     *            the associated build of this action
     * @param healthDescriptor
     *            health descriptor to use
     * @param result
     *            the result in this build
     */
    public JslintResultAction(final AbstractBuild<?, ?> owner, final HealthDescriptor healthDescriptor, final JslintResult result) {
        super(owner, new JslintHealthDescriptor(healthDescriptor), result);
    }

    /**
     * Creates a new instance of <code>JslintResultAction</code>.
     *
     * @param owner
     *            the associated build of this action
     * @param healthDescriptor
     *            health descriptor to use
     */
    public JslintResultAction(final AbstractBuild<?, ?> owner, final HealthDescriptor healthDescriptor) {
        super(owner, new JslintHealthDescriptor(healthDescriptor));
    }

    /** {@inheritDoc} */
    public String getDisplayName() {
        return Messages.JSLINT_ProjectAction_Name();
    }

    /** {@inheritDoc} */
    @Override
    protected PluginDescriptor getDescriptor() {
        return new JslintDescriptor();
    }

    /** {@inheritDoc} */
    @Override
    public String getMultipleItemsTooltip(final int numberOfItems) {
        return Messages.JSLINT_ResultAction_MultipleWarnings(numberOfItems);
    }

    /** {@inheritDoc} */
    @Override
    public String getSingleItemTooltip() {
        return Messages.JSLINT_ResultAction_OneWarning();
    }

}
