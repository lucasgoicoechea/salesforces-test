package com.salesforce.tests.model.commands;

import com.salesforce.tests.model.directories.DirectoryFileTree;
import com.salesforce.tests.model.directories.DirectoryTree;
import com.salesforce.tests.model.structures.*;

/**
 * MKDIR is responsible for the creating of directories in our file-system.
 * The user can provide a full path, and relative path and expect the directory
 * file to be made there.
 */
public class Mkdir extends Command {

    
    /**
     * Overriding execute so mkdir creates new directory
     */
    @Override
    public void execute () {
        if (! this.run()) {
            System.out.println("Directory already exist");
        }
    }

    
    /**
     * Returning parent directory
     * @return Directory - parent
     */
    private Directory getParentDirectory() {
        String path = getParameters();
        if (path.lastIndexOf("/") > -1) {
            path = path.substring(0, path.lastIndexOf("/"));
            return DirectoryTree.getDirectory(path);
        }
        
        return DirectoryTree.getCurrent();
    }

    private String getDirectoryTarget() {
        String path = getParameters();
        if (path.lastIndexOf("/") > -1) {
            path = path.substring(path.lastIndexOf("/") + 1);
            return path;
        }
        
        return path;
    }
    
    
    /**
     * Runs the command
     * @return boolean - created or not
     */
    public boolean run () {

        String directoryTarget = getDirectoryTarget();
        Directory parentDirectory = getParentDirectory();
        
        Directory newdir = new Directory(directoryTarget, parentDirectory);
        return DirectoryFileTree.addDirectory(newdir);
        
    }

    /**
     * Overriding toString so it can be used with man describing get command
     * @return String
     */    
    @Override
    public String toString() {
    	return "mkdir DIR: \n "
            + "Create directories, each of which may be relative"
            + " to the current directory or may be a full path.";
    }
    
}