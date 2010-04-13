package hudson.plugins.jslint;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.Action;
import hudson.plugins.analysis.core.BuildResult;
import hudson.plugins.analysis.core.FilesParser;
import hudson.plugins.analysis.core.HealthAwarePublisher;
import hudson.plugins.analysis.core.ParserResult;
import hudson.plugins.analysis.util.PluginLogger;
import hudson.plugins.jslint.parser.JslintParser;
import java.io.IOException;
import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * Publishes the results of the JSLint analysis  (freestyle project type).
 *
 * @author Joe Heth
 */
public class JslintPublisher extends HealthAwarePublisher {

    /** Default JSLint pattern. */
    private static final String DEFAULT_PATTERN = "**/jslint.xml";

    /** Ant file-set pattern of files to work with. */
    private final String pattern;

    /**
     * Creates a new instance of <code>JslintPublisher</code>.
     *
     * @param pattern
     *            Ant file-set pattern to scan for JSLint files
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
     * @param defaultEncoding
     *            the default encoding to be used when reading and parsing files
     * @param useDeltaValues
     *            determines whether the absolute annotations delta or the
     *            actual annotations set difference should be used to evaluate
     *            the build stability
     */
    // CHECKSTYLE:OFF
    @SuppressWarnings("JSLint.ExcessiveParameterList")
    @DataBoundConstructor
    public JslintPublisher(final String pattern, final String threshold, final String newThreshold,
            final String failureThreshold, final String newFailureThreshold,
            final String healthy, final String unHealthy,
            final String thresholdLimit, final String defaultEncoding, final boolean useDeltaValues) {
        super(threshold, newThreshold, failureThreshold, newFailureThreshold,
                healthy, unHealthy, thresholdLimit, defaultEncoding, useDeltaValues, "JSLint");
        this.pattern = pattern;
    }
    // CHECKSTYLE:ON

    /**
     * Returns the Ant file-set pattern of files to work with.
     *
     * @return Ant file-set pattern of files to work with
     */
    public String getPattern() {
        return pattern;
    }

    /** {@inheritDoc} */
    @Override
    public Action getProjectAction(final AbstractProject<?, ?> project) {
        return new JslintProjectAction(project);
    }

    /** {@inheritDoc} */
    @Override
    public BuildResult perform(final AbstractBuild<?, ?> build, final PluginLogger logger) throws InterruptedException, IOException {
        logger.log("Collecting JSLint analysis files...");
        FilesParser jslintCollector = new FilesParser(logger, StringUtils.defaultIfEmpty(getPattern(), DEFAULT_PATTERN), new JslintParser(getDefaultEncoding()),
                isMavenBuild(build), isAntBuild(build));
        ParserResult project = build.getWorkspace().act(jslintCollector);
        JslintResult result = new JslintResult(build, getDefaultEncoding(), project);
        build.getActions().add(new JslintResultAction(build, this, result));
        return result;
    }

    /** {@inheritDoc} */
    @Override
    public JslintDescriptor getDescriptor() {
        return (JslintDescriptor)super.getDescriptor();
    }

}
