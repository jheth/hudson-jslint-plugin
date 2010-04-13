package hudson.plugins.jslint;

import hudson.Extension;
import hudson.plugins.analysis.core.PluginDescriptor;

@Extension(ordinal = 200)
public final class JslintDescriptor extends PluginDescriptor {
	/** Plugin-in name */
	private static final String PLUGIN_NAME = "jslint";
	/** Icon to use for the result and project action */
	private static final String ACTION_ICON = "/plugin/jslint/icons/jslint-24x24.gif";

	public JslintDescriptor() {
		super(JslintPublisher.class);
	}

	public String getDisplayName() {
		return Messages.JSLINT_Publisher_Name();
	}

	public String getPluginName() {
		return PLUGIN_NAME;
	}

	public String getIconUrl() {
		return ACTION_ICON;
	}
}
