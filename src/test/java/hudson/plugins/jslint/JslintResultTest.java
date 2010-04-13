package hudson.plugins.jslint;

import hudson.model.AbstractBuild;
import hudson.plugins.analysis.core.BuildHistory;
import hudson.plugins.analysis.core.ParserResult;
import hudson.plugins.analysis.test.BuildResultTest;

/**
 * Tests the class {@link JslintResult}.
 */
public class JslintResultTest extends BuildResultTest<JslintResult> {
    /** {@inheritDoc} */
    @Override
    protected JslintResult createBuildResult(final AbstractBuild<?, ?> build, final ParserResult project, final BuildHistory history) {
        return new JslintResult(build, null, project, history);
    }
}

