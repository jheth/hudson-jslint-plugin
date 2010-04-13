package hudson.plugins.jslint.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Java Bean class for a errors collection of the Jslint format.
 *
 * @author Joe Heth
 */
public class Jslint {

    /** All files of this violations collection. */
    private final List<File> files = new ArrayList<File>();

    /**
     * Adds a new file to this bug collection.
     *
     * @param file the file to add
     */
    public void addFile(final File file) {
        files.add(file);
    }

    /**
     * Returns all files of this violations collection. The returned collection is
     * read-only.
     *
     * @return all files of this bug collection
     */
    public Collection<File> getFiles() {
        return Collections.unmodifiableCollection(files);
    }
}

