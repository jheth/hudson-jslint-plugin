package hudson.plugins.jslint.parser;

import hudson.plugins.analysis.util.model.AbstractAnnotation;
import hudson.plugins.analysis.util.model.Priority;

import org.apache.commons.lang.StringUtils;

/**
 * A serializable Java Bean class representing a warning.
 * <p>
 * Note: this class has a natural ordering that is inconsistent with equals.
 * </p>
 *
 * @author Joe Heth
 */
public class Warning extends AbstractAnnotation {

    /** Origin of the annotation. */
    public static final String ORIGIN = "jslint";

    private int character;
    private String evidence;
    
    /**
     * Creates a new instance of {@link Warning}.
     *
     * @param priority
     *            the priority
     * @param message
     *            the message of the warning
     * @param category
     *            the warning category
     * @param type
     *            the identifier of the warning type
     * @param start
     *            the first line of the line range
     * @param end
     *            the last line of the line range
     */
    public Warning(final Priority priority, final String message, final String category, final String type,
            final int start, final int end) {
        super(priority, message, start, end, category, type);
        setOrigin(ORIGIN);
    }

    /**
     * Creates a new instance of {@link Warning}.
     *
     * @param priority
     *            the priority
     * @param message
     *            the message of the warning
     * @param category
     *            the warning category
     * @param type
     *            the identifier of the warning type
     * @param lineNumber
     *            the line number of the warning in the corresponding file
     */
    public Warning(final Priority priority, final String message, final String category, final String type, final int lineNumber) {
        this(priority, message, category, type, lineNumber, lineNumber);
    }

    /** {@inheritDoc} */
    public String getToolTip() {
        return "Char: " + this.character + "; Evidence: " + this.evidence.trim(); 
    }

    public int getCharacter() {
        return character;
    }

    public void setCharacter(int character) {
        this.character = character;
    }

    public String getEvidence() {
        return evidence;
    }

    public void setEvidence(String evidence) {
        if (evidence == null) {
            evidence = "";
         }
         this.evidence = evidence;
    }
    
}

