package hudson.plugins.jslint;

import hudson.Extension;
import hudson.maven.MavenReporter;
import hudson.plugins.analysis.core.ReporterDescriptor;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.StaplerRequest;

/**
 * Descriptor for the class {@link JslintReporter}. Used as a singleton. The
 * class is marked as public so that it can be accessed from views.
 *
 * @author Joe Heth 
 */
@Extension(ordinal = 200)
public class JslintReporterDescriptor extends ReporterDescriptor {

    /**
     * Creates a new instance of <code>JslintReporterDescriptor</code>.
     */
    public JslintReporterDescriptor() {
        super(JslintReporter.class, new JslintDescriptor());
    }

    /** {@inheritDoc} */
    @Override
    public MavenReporter newInstance(final StaplerRequest request, final JSONObject formData) throws FormException {
        return request.bindJSON(JslintReporter.class, formData);
    }
}
