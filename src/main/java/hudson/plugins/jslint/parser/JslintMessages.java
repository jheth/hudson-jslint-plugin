package hudson.plugins.jslint.parser;


/**
 * Provides access to JSLint messages.
 *
 * @author Joe Heth
 */
public final class JslintMessages {

    /** Singleton instance. */
    private static final JslintMessages INSTANCE = new JslintMessages();

    /**
     * Returns the singleton instance.
     *
     * @return the singleton instance
     */
    public static JslintMessages getInstance() {
        return INSTANCE;
    }

    /**
     * Creates a new instance of <code>JslintMessages</code>.
     */
    private JslintMessages() {
        // prevents instantiation
    }

    /**
     * Initialize.
     */
    public void initialize() {
        // nothing yet
    }

    public String getType(String reason) {

        String type = "Other";
        if (reason.contains("Missing space")) {		
            type = "White Space";
        } else if (reason.contains("indentation")) {
            type = "Indentation";
        } else if (reason.contains("line breaking")) {
            type = "Line Break";
        } else if (reason.contains("dot notation")) {
            type = "Dot Notation";
        } else if (reason.contains("semicolon")) {
        	type = "Semicolon";
        } else if (reason.contains("comma")) {
        	type = "Comma";
        }
        return type;
    }

}
