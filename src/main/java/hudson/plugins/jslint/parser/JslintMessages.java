package hudson.plugins.jslint.parser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;

/**
 * Provides access to rule descriptions and examples.
 *
 * @author Ulli Hafner
 */
public final class JslintMessages {

    /** Singleton instance. */
    private static final JslintMessages INSTANCE = new JslintMessages();

    /** Available rule sets. */
    private final Map<String, Set> rules = new HashMap<String, Set>();

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
     * Initializes the rules.
     */
    public void initialize() {
	/*
        try {
            Iterator<RuleSet> ruleSets = new RuleSetFactory().getRegisteredRuleSets();
            for (Iterator<RuleSet> iterator = ruleSets; iterator.hasNext();) {
                RuleSet ruleSet = iterator.next();
                rules.put(ruleSet.getName(), ruleSet);
            }
        }
        catch (RuleSetNotFoundException exception) {
            Logger.getLogger(JslintMessages.class.getName()).log(Level.SEVERE, "Installation problem: can't access Jslint messages.");
        }
	*/
    }

    /**
     * Returns the message for the specified Jslint rule.
     *
     * @param ruleSetName
     *            Jslint rule set
     * @param ruleName
     *            Jslint rule ID
     * @return the message
     */
    public String getMessage(final String ruleSetName, final String ruleName) {
	/*
        if (rules.containsKey(ruleSetName)) {
            RuleSet ruleSet = rules.get(ruleSetName);
            Rule rule = ruleSet.getRuleByName(ruleName);
            if (rule != null) {
                return createMessage(rule);
            }
        }
	*/
        return StringUtils.EMPTY;
    }

    /**
     * Creates the message string to be shown for the specified rule.
     *
     * @param rule
     *            the rule
     * @return the message string to be shown for the specified rule
     */
    private String createMessage(final String rule) {
	/*
        List<String> examples = rule.getExamples();
        if (!examples.isEmpty()) {
            return rule.getDescription() + "<pre>" + examples.get(0) + "</pre>";
        }
        return rule.getDescription();
	*/
	return "Test Message";
    }
}

