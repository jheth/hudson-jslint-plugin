package hudson.plugins.jslint;
import hudson.maven.AggregatableAction;
import hudson.maven.MavenAggregatedReport;
import hudson.maven.MavenBuild;
import hudson.maven.MavenModule;
import hudson.maven.MavenModuleSet;
import hudson.maven.MavenModuleSetBuild;
import hudson.model.AbstractBuild;
import hudson.model.Action;
import hudson.plugins.analysis.core.HealthDescriptor;
import java.util.List;
import java.util.Map;
/**
 * A {@link JslintResultAction} for native maven jobs. This action
 * additionally provides result aggregation for sub-modules and for the main
 * project.
 *
 * @author Ulli Hafner
 */
public class MavenJslintResultAction extends JslintResultAction implements AggregatableAction, MavenAggregatedReport {
    /** Unique identifier of this class. */
    private static final long serialVersionUID = 1273798369273225973L;
    /** The default encoding to be used when reading and parsing files. */
    private final String defaultEncoding;
    /**
     * Creates a new instance of <code>MavenJslintResultAction</code>.
     *
     * @param owner
     *            the associated build of this action
     * @param healthDescriptor
     *            health descriptor to use
     * @param defaultEncoding
     *            the default encoding to be used when reading and parsing files
     */
    public MavenJslintResultAction(final AbstractBuild<?, ?> owner, final HealthDescriptor healthDescriptor,
            final String defaultEncoding) {
        super(owner, healthDescriptor);
        this.defaultEncoding = defaultEncoding;
    }
    /**
     * Creates a new instance of <code>MavenJslintResultAction</code>.
     *
     * @param owner
     *            the associated build of this action
     * @param healthDescriptor
     *            health descriptor to use
     * @param defaultEncoding
     *            the default encoding to be used when reading and parsing files
     * @param result
     *            the result in this build
     */
    public MavenJslintResultAction(final AbstractBuild<?, ?> owner, final HealthDescriptor healthDescriptor,
            final String defaultEncoding, final JslintResult result) {
        super(owner, healthDescriptor, result);
        this.defaultEncoding = defaultEncoding;
    }
    /** {@inheritDoc} */
    public MavenAggregatedReport createAggregatedAction(final MavenModuleSetBuild build, final Map<MavenModule, List<MavenBuild>> moduleBuilds) {
        return new MavenJslintResultAction(build, getHealthDescriptor(), defaultEncoding);
    }
    /** {@inheritDoc} */
    public Action getProjectAction(final MavenModuleSet moduleSet) {
        return new JslintProjectAction(moduleSet);
    }
    /** {@inheritDoc} */
    public Class<? extends AggregatableAction> getIndividualActionType() {
        return getClass();
    }
    /**
     * Called whenever a new module build is completed, to update the aggregated
     * report. When multiple builds complete simultaneously, Hudson serializes
     * the execution of this method, so this method needs not be
     * concurrency-safe.
     *
     * @param moduleBuilds
     *            Same as <tt>MavenModuleSet.getModuleBuilds()</tt> but provided
     *            for convenience and efficiency.
     * @param newBuild
     *            Newly completed build.
     */
    public void update(final Map<MavenModule, List<MavenBuild>> moduleBuilds, final MavenBuild newBuild) {
        JslintResult annotationsResult = new JslintResult(getOwner(), defaultEncoding, createAggregatedResult(moduleBuilds));
        setResult(annotationsResult);
        updateBuildHealth(newBuild, annotationsResult);
    }
    /** Backward compatibility. @deprecated */
    @SuppressWarnings("unused")
    @Deprecated
    private transient String height;
}

