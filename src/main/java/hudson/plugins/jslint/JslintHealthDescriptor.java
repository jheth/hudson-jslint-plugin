package hudson.plugins.jslint;

import hudson.plugins.analysis.core.AbstractHealthDescriptor;
import hudson.plugins.analysis.core.HealthDescriptor;
import hudson.plugins.analysis.util.model.AnnotationProvider;
import org.jvnet.localizer.Localizable;

/**
 * A health descriptor fori JSLint build results.
 *
 * @author Joe Heth
 */
public class JslintHealthDescriptor extends AbstractHealthDescriptor {

    /**
     * Creates a new instance of {@link JslintHealthDescriptor} based on the
     * values of the specified descriptor.
     *
     * @param healthDescriptor the descriptor to copy the values from
     */
    public JslintHealthDescriptor(final HealthDescriptor healthDescriptor) {
        super(healthDescriptor);
    }

    /** {@inheritDoc} */
    @Override
    protected Localizable createDescription(final AnnotationProvider result) {

        if (result.getNumberOfAnnotations() == 0) {
            return Messages._JSLINT_ResultAction_HealthReportNoItem();
        }
        else if (result.getNumberOfAnnotations() == 1) {
            return Messages._JSLINT_ResultAction_HealthReportSingleItem();
        }
        else {
            return Messages._JSLINT_ResultAction_HealthReportMultipleItem(result.getNumberOfAnnotations());
        }
    }
}
