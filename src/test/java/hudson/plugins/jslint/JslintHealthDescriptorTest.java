package hudson.plugins.jslint;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import hudson.plugins.analysis.core.AbstractHealthDescriptor;
import hudson.plugins.analysis.core.HealthDescriptor;
import hudson.plugins.analysis.core.NullHealthDescriptor;
import hudson.plugins.analysis.test.AbstractHealthDescriptorTest;
import hudson.plugins.analysis.util.model.AnnotationProvider;

import org.junit.Test;
import org.jvnet.localizer.Localizable;

/**
 * Tests the class {@link JslintHealthDescriptor}.
 *
 * @author Joe Heth
 */
public class JslintHealthDescriptorTest extends AbstractHealthDescriptorTest {
    /**
     * Verify number of items.
     */
    @Test
    public void verifyNumberOfItems() {
        AnnotationProvider provider = mock(AnnotationProvider.class);
        JslintHealthDescriptor healthDescriptor = new JslintHealthDescriptor(NullHealthDescriptor.NULL_HEALTH_DESCRIPTOR);

        Localizable description = healthDescriptor.createDescription(provider);
        assertEquals(WRONG_DESCRIPTION, Messages.JSLint_ResultAction_HealthReportNoItem(), description.toString());

        when(provider.getNumberOfAnnotations()).thenReturn(1);
        description = healthDescriptor.createDescription(provider);
        assertEquals(WRONG_DESCRIPTION, Messages.JSLint_ResultAction_HealthReportSingleItem(), description.toString());

        when(provider.getNumberOfAnnotations()).thenReturn(2);
        description = healthDescriptor.createDescription(provider);
        assertEquals(WRONG_DESCRIPTION, Messages.JSLint_ResultAction_HealthReportMultipleItem(2), description.toString());
    }

    /** {@inheritDoc} */
    @Override
    protected AbstractHealthDescriptor createHealthDescriptor(final HealthDescriptor healthDescriptor) {
        return new JslintHealthDescriptor(healthDescriptor);
    }
}

