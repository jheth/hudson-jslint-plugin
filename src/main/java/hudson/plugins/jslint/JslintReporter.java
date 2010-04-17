package hudson.plugins.jslint;

import hudson.maven.MavenBuild;
import hudson.maven.MavenBuildProxy;
import hudson.maven.MavenModule;
import hudson.maven.MojoInfo;
import hudson.model.Action;
import hudson.plugins.analysis.core.BuildResult;
import hudson.plugins.analysis.core.FilesParser;
import hudson.plugins.analysis.core.HealthAwareMavenReporter;
import hudson.plugins.analysis.core.ParserResult;
import hudson.plugins.analysis.util.PluginLogger;
import hudson.plugins.jslint.parser.JslintParser;
import java.io.IOException;
import org.apache.maven.project.MavenProject;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * Publishes the results of the JSLint analysis  (maven 2 project type).
 *
 * @author Joe Heth
 */
public class JslintReporter extends HealthAwareMavenReporter {

    /** Unique identifier of this class. */
    private static final long serialVersionUID = -5592892163830467313L;

    /** Default JSLint pattern. */
    private static final String JSLINT_XML_FILE = "jslint.xml";

    
    /**
     * Creates a new instance of <code>JslintReporter</code>.
     *
     * @param threshold
     *            Annotation threshold to be reached if a build should be considered as
     *            unstable.
     * @param newThreshold
     *            New annotations threshold to be reached if a build should be
     *            considered as unstable.
     * @param failureThreshold
     *            Annotation threshold to be reached if a build should be considered as
     *            failure.
     * @param newFailureThreshold
     *            New annotations threshold to be reached if a build should be
     *            considered as failure.
     * @param healthy
     *            Report health as 100% when the number of warnings is less than
     *            this value
     * @param unHealthy
     *            Report health as 0% when the number of warnings is greater
     *            than this value
     * @param thresholdLimit
     *            determines which warning priorities should be considered when
     *            evaluating the build stability and health
     */
    // CHECKSTYLE:OFF
    @DataBoundConstructor
    public JslintReporter(final String threshold, final String newThreshold,
            final String failureThreshold, final String newFailureThreshold,
            final String healthy, final String unHealthy, final String thresholdLimit) {
        super(threshold, newThreshold, failureThreshold, newFailureThreshold,
                healthy, unHealthy, thresholdLimit, "JSLint");
    }
    // CHECKSTYLE:ON

    /** {@inheritDoc} */
    @Override
    protected boolean acceptGoal(final String goal) {
        return "jslint".equals(goal) || "site".equals(goal);
    }
    /** {@inheritDoc} */
    @Override
    public ParserResult perform(final MavenBuildProxy build, final MavenProject pom, final MojoInfo mojo, final PluginLogger logger) throws InterruptedException, IOException {
        FilesParser jslintCollector = new FilesParser(logger, JSLINT_XML_FILE, new JslintParser(getDefaultEncoding()), true, false);
        return getTargetPath(pom).act(jslintCollector);
    }
    /** {@inheritDoc} */
    @Override
    protected BuildResult persistResult(final ParserResult project, final MavenBuild build) {
        JslintResult result = new JslintResult(build, getDefaultEncoding(), project);
        build.getActions().add(new MavenJslintResultAction(build, this, getDefaultEncoding(), result));
        build.registerAsProjectAction(JslintReporter.this);
        return result;
    }
    /** {@inheritDoc} */
    @Override
    public Action getProjectAction(final MavenModule module) {
        return new JslintProjectAction(module);
    }
    /** {@inheritDoc} */
    @Override
    protected Class<? extends Action> getResultActionClass() {
        return MavenJslintResultAction.class;
    }
}
