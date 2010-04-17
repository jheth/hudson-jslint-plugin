package hudson.plugins.jslint.parser;

/**
 * Java Bean class for a violation of the JsLint format.
 *
 * @author Joe Heth
 */
public class Error {

// CHECKSTYLE:OFF
    /** Character offset of warning. */
    private int character;
    /** Evidence of the warning. */
    private String evidence;
    /** Line of the warning. */
    private int line;
    /** Reason for the warning. */
    private String reason;
// CHECKSTYLE:ON

    /**
     * Returns the character.
     *
     * @return the character
     */
    public int getChar() {
        return character;
    }

    /**
     * Sets the character to the specified value.
     *
     * @param character the value to set
     */
    public void setChar(final int character) {
        this.character = character;
    }

    /**
     * Returns the evidence.
     *
     * @return the evidence
     */
    public String getEvidence() {
        return evidence;
    }

    /**
     * Sets the evidence to the specified value.
     *
     * @param evidence the value to set
     */
    public void setEvidence(final String evidence) {
        this.evidence = evidence;
    }

    /**
     * Returns the reason.
     *
     * @return the reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets the reason to the specified value.
     *
     * @param reason the value to set
     */
    public void setReason(final String reason) {
        this.reason = reason;
    }

    /**
     * Returns the line.
     *
     * @return the line
     */
    public int getLine() {
        return line;
    }

    /**
     * Sets the line to the specified value.
     *
     * @param line the value to set
     */
    public void setLine(final int line) {
        this.line = line;
    }
}

