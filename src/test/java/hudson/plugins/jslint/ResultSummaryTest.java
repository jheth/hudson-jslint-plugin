package hudson.plugins.jslint;

import static org.mockito.Mockito.*;
import hudson.plugins.analysis.test.AbstractEnglishLocaleTest;
import junit.framework.Assert;

import org.junit.Test;

/**
 * Tests the class {@link ResultSummary}.
 */
public class ResultSummaryTest extends AbstractEnglishLocaleTest {
    /**
     * Checks the text for no warnings from 0 files.
     */
    @Test
    public void test0WarningsIn0File() {
        checkSummaryText(0, 0, "Checkstyle: 0 warnings from 0 Checkstyle files.");
    }

    /**
     * Checks the text for no warnings from 1 file.
     */
    @Test
    public void test0WarningsIn1File() {
        checkSummaryText(0, 1, "Checkstyle: 0 warnings from one Checkstyle file.");
    }

    /**
     * Checks the text for no warnings from 5 files.
     */
    @Test
    public void test0WarningsIn5Files() {
        checkSummaryText(0, 5, "Checkstyle: 0 warnings from 5 Checkstyle files.");
    }

    /**
     * Checks the text for 1 warning from 2 files.
     */
    @Test
    public void test1WarningIn2Files() {
        checkSummaryText(1, 2, "Checkstyle: <a href=\"checkstyleResult\">1 warning</a> from 2 Checkstyle files.");
    }

    /**
     * Checks the text for 5 warnings from 1 file.
     */
    @Test
    public void test5WarningsIn1File() {
        checkSummaryText(5, 1, "Checkstyle: <a href=\"checkstyleResult\">5 warnings</a> from one Checkstyle file.");
    }

    /**
     * Parameterized test case to check the message text for the specified
     * number of warnings and files.
     *
     * @param numberOfWarnings
     *            the number of warnings
     * @param numberOfFiles
     *            the number of files
     * @param expectedMessage
     *            the expected message
     */
    private void checkSummaryText(final int numberOfWarnings, final int numberOfFiles, final String expectedMessage) {
        JslintResult result = mock(JslintResult.class);
        when(result.getNumberOfAnnotations()).thenReturn(numberOfWarnings);
        when(result.getNumberOfModules()).thenReturn(numberOfFiles);

        Assert.assertEquals("Wrong summary message created.", expectedMessage, ResultSummary.createSummary(result));
    }

    /**
     * Checks the delta message for no new and no fixed warnings.
     */
    @Test
    public void testNoDelta() {
        checkDeltaText(0, 0, "");
    }

    /**
     * Checks the delta message for 1 new and no fixed warnings.
     */
    @Test
    public void testOnly1New() {
        checkDeltaText(0, 1, "<li><a href=\"checkstyleResult/new\">1 new warning</a></li>");
    }

    /**
     * Checks the delta message for 5 new and no fixed warnings.
     */
    @Test
    public void testOnly5New() {
        checkDeltaText(0, 5, "<li><a href=\"checkstyleResult/new\">5 new warnings</a></li>");
    }

    /**
     * Checks the delta message for 1 fixed and no new warnings.
     */
    @Test
    public void testOnly1Fixed() {
        checkDeltaText(1, 0, "<li><a href=\"checkstyleResult/fixed\">1 fixed warning</a></li>");
    }

    /**
     * Checks the delta message for 5 fixed and no new warnings.
     */
    @Test
    public void testOnly5Fixed() {
        checkDeltaText(5, 0, "<li><a href=\"checkstyleResult/fixed\">5 fixed warnings</a></li>");
    }

    /**
     * Checks the delta message for 5 fixed and 5 new warnings.
     */
    @Test
    public void test5New5Fixed() {
        checkDeltaText(5, 5,
                "<li><a href=\"checkstyleResult/new\">5 new warnings</a></li>"
                + "<li><a href=\"checkstyleResult/fixed\">5 fixed warnings</a></li>");
    }

    /**
     * Checks the delta message for 5 fixed and 5 new warnings.
     */
    @Test
    public void test5New1Fixed() {
        checkDeltaText(1, 5,
        "<li><a href=\"checkstyleResult/new\">5 new warnings</a></li>"
        + "<li><a href=\"checkstyleResult/fixed\">1 fixed warning</a></li>");
    }

    /**
     * Checks the delta message for 5 fixed and 5 new warnings.
     */
    @Test
    public void test1New5Fixed() {
        checkDeltaText(5, 1,
                "<li><a href=\"checkstyleResult/new\">1 new warning</a></li>"
                + "<li><a href=\"checkstyleResult/fixed\">5 fixed warnings</a></li>");
    }

    /**
     * Checks the delta message for 5 fixed and 5 new warnings.
     */
    @Test
    public void test1New1Fixed() {
        checkDeltaText(1, 1,
                "<li><a href=\"checkstyleResult/new\">1 new warning</a></li>"
                + "<li><a href=\"checkstyleResult/fixed\">1 fixed warning</a></li>");
    }

    /**
     * Parameterized test case to check the message text for the specified
     * number of warnings and files.
     *
     * @param numberOfFixedWarnings
     *            the number of fixed warnings
     * @param numberOfNewWarnings
     *            the number of new warnings
     * @param expectedMessage
     *            the expected message
     */
    private void checkDeltaText(final int numberOfFixedWarnings, final int numberOfNewWarnings, final String expectedMessage) {
        JslintResult result = mock(JslintResult.class);
        when(result.getNumberOfFixedWarnings()).thenReturn(numberOfFixedWarnings);
        when(result.getNumberOfNewWarnings()).thenReturn(numberOfNewWarnings);

        Assert.assertEquals("Wrong delta message created.", expectedMessage, ResultSummary.createDeltaMessage(result));
    }
}

