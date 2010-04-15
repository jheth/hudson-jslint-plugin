package hudson.plugins.jslint;

import hudson.Plugin;
import hudson.plugins.jslint.parser.JslintMessages;

/**
 * Initialize the JSLint messages and description.
 *
 * @author Joe Heth
 */
public class JslintPlugin extends Plugin {
	/** {@inheritDoc} */
	@Override
	public void start() {
		JslintMessages.getInstance().initialize();
	}
}
