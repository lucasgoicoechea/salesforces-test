package com.salesforce.tests.model.directories;

import com.salesforce.tests.model.structures.*;

import java.util.ArrayList;

/**
 * Class created to handle the tree with directory list
 */
public class DirectoryFileTree {
    /**
     * Attribute that contains directory list
     */
    private static ArrayList<Directory> tree;
    
    /**
     * Singleton object
     */
    private static DirectoryFileTree self;

    /**
     * Constructor that initiates the tree and adds root directory
     */
    private DirectoryFileTree (Directory root) {
        tree = new ArrayList<Directory>();
        tree.add(root);
    }

    /**
     * Creating instance
     * @param root directory
     * @return DirectoryFileTree instance
     */
    public static DirectoryFileTree create(Directory root) {
        if (!(self instanceof DirectoryFileTree)) {
            self = new DirectoryFileTree(root);
        }
        
        return self;
    }

    
    public static ArrayList<Directory> getTree() {
        return tree;
    }

    /**
     * Adding directory to the tree
     * @param newDir
     * @return boolean - whether the directory was added or not
     */
    public static boolean addDirectory(Directory newDir) {
        for (File file : getFilesOf(newDir.getParent())) {
            if (file.getName().equals(newDir.getName())) {
                return false;
            }
        }
        
        return tree.add(newDir);
    }


    /**
     * Removing directory to the tree
     * @param directory
     * @return boolean - whether the directory was removed or not
     */
    public static boolean removeDirectory(Directory directory) {
        if (tree.contains(directory))
            return tree.remove(directory);
        return false;
    }
    
    /**
     * Getting files of
     * @param parent
     * @return boolean - whether the directory was added or not
     */
    public static ArrayList<File> getFilesOf(Directory parent) {
        ArrayList<File> files = new ArrayList<File>();
        for (Directory d : getTree()) {
            Directory dParent = d.getParent();
            if (dParent == null) {
                continue;
            }

            if (d.getParent().getName().equals(parent.getName())) {
               files.add(d);
            }
        }
        
        for (TextFile t : parent.getContent()) {
            files.add(t);
        }
        
        return files;
    }

    public Directory findDirectory(String dirname) { 	
    	
        for (Directory dir : tree) {
            if (dir.getName().equals(dirname)) {
                return dir;
            }          
        }        
        return null;
    }
        
}
