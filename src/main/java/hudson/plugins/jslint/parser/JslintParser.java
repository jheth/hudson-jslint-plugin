package hudson.plugins.jslint.parser;

import hudson.plugins.analysis.core.AbstractAnnotationParser;
import hudson.plugins.analysis.util.JavaPackageDetector;
import hudson.plugins.analysis.util.model.FileAnnotation;
import hudson.plugins.analysis.util.model.Priority;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.digester.Digester;
import org.apache.commons.lang.StringUtils;
import org.xml.sax.SAXException;

/**
 * A parser for Jslint XML files.
 *
 * @author Joe Heth
 */
public class JslintParser extends AbstractAnnotationParser {

    /**
     * Creates a new instance of {@link JslintParser}.
     */
    public JslintParser() {
        super(StringUtils.EMPTY);
    }

    /**
     * Creates a new instance of {@link JslintParser}.
     *
     * @param defaultEncoding
     *            the default encoding to be used when reading and parsing files
     */
    public JslintParser(final String defaultEncoding) {
        super(defaultEncoding);
    }

    /** {@inheritDoc} */
    @Override
    public Collection<FileAnnotation> parse(final InputStream file, final String moduleName) throws InvocationTargetException {
        try {
            Digester digester = new Digester();
            digester.setValidating(false);
            digester.setClassLoader(JslintParser.class.getClassLoader());

            String rootXPath = "jslint";
            digester.addObjectCreate(rootXPath, Jslint.class);
            digester.addSetProperties(rootXPath);

            String fileXPath = "jslint/file";
            digester.addObjectCreate(fileXPath, hudson.plugins.jslint.parser.File.class);
            digester.addSetProperties(fileXPath);
            digester.addSetNext(fileXPath, "addFile", hudson.plugins.jslint.parser.File.class.getName());

            String bugXPath = "jslint/file/error";
            digester.addObjectCreate(bugXPath, Error.class);
            digester.addSetProperties(bugXPath);
            digester.addSetNext(bugXPath, "addError", Error.class.getName());

            Jslint module;
            module = (Jslint)digester.parse(new InputStreamReader(file, "UTF-8"));
            if (module == null) {
                throw new SAXException("Input stream is not a Jslint file.");
            }

            return convert(module, moduleName);
        }
        catch (IOException exception) {
            throw new InvocationTargetException(exception);
        }
        catch (SAXException exception) {
            throw new InvocationTargetException(exception);
        }
    }

    /**
     * Converts the internal structure to the annotations API.
     *
     * @param collection
     *            the internal maven module
     * @param moduleName
     *            name of the maven module
     * @return a maven module of the annotations API
     */
    private Collection<FileAnnotation> convert(final Jslint collection, final String moduleName) {
        ArrayList<FileAnnotation> annotations = new ArrayList<FileAnnotation>();

        for (hudson.plugins.jslint.parser.File file : collection.getFiles()) {
            if (isValidWarning(file)) {
                String packageName = new JavaPackageDetector().detectPackageName(file.getName());
                for (Error error : file.getErrors()) {
                    Priority priority;
                    if ("error".equalsIgnoreCase(error.getSeverity())) {
                        priority = Priority.HIGH;
                    }
                    else if ("warning".equalsIgnoreCase(error.getSeverity())) {
                        priority = Priority.NORMAL;
                    }
                    else if ("info".equalsIgnoreCase(error.getSeverity())) {
                        priority = Priority.LOW;
                    }
                    else {
                        continue; // ignore
                    }
                    String source = error.getSource();
                    String type = StringUtils.substringAfterLast(source, ".");
                    String category = StringUtils.substringAfterLast(StringUtils.substringBeforeLast(source, "."), ".");

                    Warning warning = new Warning(priority, error.getMessage(), StringUtils.capitalize(category),
                            type, error.getLine(), error.getLine());
                    warning.setModuleName(moduleName);
                    warning.setFileName(file.getName());
                    warning.setPackageName(packageName);

                    try {
                        warning.setContextHashCode(createContextHashCode(file.getName(), error.getLine()));
                    }
                    catch (IOException exception) {
                        // ignore and continue
                    }
                    annotations.add(warning);
                }
            }
        }
        return annotations;
    }

    /**
     * Returns <code>true</code> if this warning is valid or <code>false</code>
     * if the warning can't be processed by the jslint plug-in.
     *
     * @param file the file to check
     * @return <code>true</code> if this warning is valid
     */
    private boolean isValidWarning(final hudson.plugins.jslint.parser.File file) {
        return !file.getName().endsWith("package.html");
    }
}

