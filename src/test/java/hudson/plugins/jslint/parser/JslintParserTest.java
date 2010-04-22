package hudson.plugins.jslint.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import hudson.plugins.analysis.util.model.FileAnnotation;
import hudson.plugins.analysis.util.model.MavenModule;
import hudson.plugins.analysis.util.model.Priority;
import hudson.plugins.analysis.util.model.WorkspaceFile;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Iterator;

import org.junit.Test;

/**
 *  Tests the extraction of JSLint analysis results.
 */
public class JslintParserTest {
	
    /**
     * Tests parsing of a simple JSLint file.
     *
     * @throws InvocationTargetException Signals that an I/O exception has occurred
     */
    @Test
    public void testParseJslintXML() throws InvocationTargetException {
        JslintMessages.getInstance().initialize();
		
        InputStream inputStream = JslintParserTest.class.getResourceAsStream("jslint-sample.xml");

        Collection<FileAnnotation> annotations = new JslintParser().parse(inputStream, "empty");
        
        MavenModule module = new MavenModule();
        module.addAnnotations(annotations);

        assertEquals("Wrong number of annotations detected.", 6, module.getNumberOfAnnotations());
        Collection<WorkspaceFile> files = module.getFiles();
        assertEquals("Wrong number of files detected.", 2, files.size());
        WorkspaceFile file = files.iterator().next();
        assertEquals("file1.js not detected.", "/path/to/js/file1.js", file.getName());
        assertEquals("Wrong number of annotations detected.", 5, file.getNumberOfAnnotations());

        Iterator<FileAnnotation> iterator = annotations.iterator();
        
        boolean hasChecked = false;
        while (iterator.hasNext()) {
            FileAnnotation annotation = iterator.next();
            assertTrue("Annotations is of wrong type.", annotation instanceof Warning);
            Warning warning = (Warning)annotation;
            assertEquals("Wrong number of line ranges detected.", 1, warning.getLineRanges().size());
            if (warning.getPrimaryLineNumber() == 24) {
                assertEquals("Wrong category detected.", "", warning.getCategory());
                assertEquals("Wrong type detected.", "White Space", warning.getType());
                assertEquals("Wrong priority detected.", Priority.NORMAL, warning.getPriority());
                assertEquals("Wrong description detected.", 
                	"Char: 25; Evidence: initialize: function(selectedList, options) {", warning.getToolTip());
                assertEquals("Wrong message detected.",
                        "Missing space after 'function'.", warning.getMessage());
                hasChecked = true;
            }
        }
        assertTrue("Warning is not in checkstyle.xml file.", hasChecked);
        
    }
	
}
