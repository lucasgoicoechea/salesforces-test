package com.salesforce.tests.model.directories;

import com.salesforce.tests.model.structures.*;


import java.util.ArrayList;


public class DirectoryTree {
    
    private static ArrayList<Directory> tree = new ArrayList<Directory>();
    private static DirectoryTree self; 
    private static Directory current;
    private static Directory root;
    private static Directory virtualCurrent;
    private DirectoryFileTree fileTree;
    
    private DirectoryTree (Directory root) {
        DirectoryTree.current = root;
        DirectoryTree.virtualCurrent = root;
        DirectoryTree.root = root;
        //adding root folder to the directory tree
        fileTree = DirectoryFileTree.create(root);
//                DirectoryTree.add(root);
    }
    
    public static DirectoryTree createDirectoryTree (Directory root) {
        if (! (self instanceof DirectoryTree)) {
            self = new DirectoryTree(root);
        }

        return self;
    }


    public static ArrayList<Directory> getTree() {
        return tree;
        //return fileTree;
    }

    public static void setCurrent(Directory directory) {
        //if (tree.contains(directory)) {
            virtualCurrent = directory;
            current = directory;
        //}
    }

    public static Directory getCurrent() {
        return DirectoryTree.current;
    }

    public static void setVirtualCurrent(Directory directory) {
        virtualCurrent = directory;
    }

    public static Directory getVirtualCurrent() {
        return DirectoryTree.virtualCurrent;
    }

    public static void add(Directory directory) {
        DirectoryFileTree.addDirectory(directory);
    }
    
    public boolean addDirectory(Directory directory) {
        return DirectoryFileTree.addDirectory(directory);
    }

    public static ArrayList<Directory> getFoldersOn(Directory target) {
        ArrayList<Directory> directories = new ArrayList<Directory>();
        for (Directory directory : tree) {
            if (directory.getParent() == target) {
                directories.add(directory);
            } 
        }
        
        return directories;
    }

    public static ArrayList<Directory> getFoldersOnCurrent() {
        return getFoldersOn(current);
    }

    public static Directory findDirOn(Directory current, String directoryName) {
        for (File d : DirectoryFileTree.getFilesOf(current)) {
            if (d.getName().equals(directoryName)) {
                return (Directory) d;
            }
        }
        return null;
    }
    
    /**
     * Setting path to current if exists
     * @param path - String
     * @return true if was possible to set path
     */
    public static boolean setPath(String path) {
        Directory directory = DirectoryTree.getDirectory(path);
        
        if (directory != null) {
            DirectoryTree.setCurrent(directory);

            return true;
        }
        
        return false;
    }

    /**
     * Getting directory of path
     * @param path
     * @return found Directory or null
     */
    public static Directory getDirectory(String path) {
        
        if (path.charAt(0) == '/') {
            DirectoryTree.setVirtualCurrent(root);
            path = path.substring(1);
        }

        String[] directories = path.split("/");
        
        for (String directory : directories) {
            Directory virtual = DirectoryTree.getVirtualCurrent();
            Directory parent = virtual.getParent();

            if (directory.equals("..")) {

                if (parent == null) {
                    DirectoryTree.setVirtualCurrent(DirectoryTree.getCurrent());
                    return null;
                }

                DirectoryTree.setVirtualCurrent(parent);
            } else {
                Directory currentDirectory;
                currentDirectory = DirectoryTree.findDirOn(virtual, directory);

                if (currentDirectory == null) {
                    DirectoryTree.setVirtualCurrent(DirectoryTree.getCurrent());
                    return null;
                }

                DirectoryTree.setVirtualCurrent(currentDirectory);
            }

        }

        Directory directoryFound = DirectoryTree.getVirtualCurrent();
        DirectoryTree.setVirtualCurrent(DirectoryTree.getCurrent());
        
        return directoryFound;
    }

    /**
     * Getting File of path
     * @param path
     * @return found TextFile or null
     */
    public static File getFile(String path) {
        Directory parent;
        String filename = path;
        parent = DirectoryTree.getCurrent();
        
        if (path.contains("/")) {
            filename = path.substring(path.lastIndexOf("/") + 1);
            path = path.substring(0, path.lastIndexOf("/"));
            parent = getDirectory(path);
        }
        
        for (File file : DirectoryFileTree.getFilesOf(parent)) {
            if (file.getName().equals(filename)) {
                return file;
            }
        }
        
        return null;
        
    }
    
    //checks if path exists and if it does it sets a virtual current directory
    public static Boolean pathExists(String path) {
    	
        String[] directories = path.split("/");
        
        for (String directory : directories) {
            Directory virtual = DirectoryTree.getVirtualCurrent();
            Directory parent = virtual.getParent();
            
            if (directory.equals("..")) {

                if (parent == null) {
                    return false;
                }

                DirectoryTree.setVirtualCurrent(parent);
            } else {
                Directory currentDirectory;
                currentDirectory = DirectoryTree.findDirOn(virtual, directory);

                if (currentDirectory == null) {
                    return false;
                }

                DirectoryTree.setVirtualCurrent(currentDirectory);
            }

        }
        
        return true;
        
/*        
    	//returns user to the root folder
    	if(path.equals("/")){
    		
    		Directory rt = DirectoryTree.findDirOn(null, "/");
    		DirectoryTree.setVirtualCurrent(rt);
    		return true;
    	}
    	
    	if (path.equals("..")){
    		Directory virtual = DirectoryTree.getVirtualCurrent();
        	Directory parent = virtual.getParent();
        	
        	if(parent == null){
        		return false;
        	}
        	
        	System.out.println("going to parent: " + parent.getName());
        	DirectoryTree.setVirtualCurrent(parent);
        	for (File c : parent.content){
        		System.out.println(c);
        	}
        	return true;
        }
    	
    	
	        
	        //Directory virtual = DirectoryTree.getVirtualCurrent();
	    	
	        //first index cases (ex. cd .., cd ./ ----)
	    
	        
	    //*************** FULL PATH *********************
	    if (path.startsWith("/")){
	    	DirectoryTree.setVirtualCurrent(DirectoryTree.findDirOn(null, "/"));
	    	path = path.substring(1);
	    	System.out.println("Path: " + path);
	    }
	    //else{
	    //	DirectoryTree.setVirtualCurrent(DirectoryTree.getVirtualCurrent());
	    //}
	    String[] directories = path.split("/");
	    
        for (String directory : directories) {
        	Directory virtual = DirectoryTree.getVirtualCurrent();
        	//handles double dot [parent DIR]
            if (directory.equals("..")) {
            	System.out.println(".. path");
                Directory parent = virtual.getParent();

                if (parent == null) {
                    return false;
                }
	
                DirectoryTree.setVirtualCurrent(parent);
            }
            //handles single dot [self DIR -- relative path]
            else if(directory.equals(".")){
            	//DirectoryTree.setVirtualCurrent(getVirtualCurrent());
            	System.out.println(" ./ path");
            	//do nothing (self)
            } 
       
            else {
                Directory currentDirectory;
                currentDirectory = DirectoryTree.findDirOn(virtual, directory);

                if (currentDirectory == null) {
                    return false;
                }

                DirectoryTree.setVirtualCurrent(currentDirectory);
                
            }
        }//for()
		        
    	
        return true; */
        
    }
    
    
    public static void destroy () {
        self = null;
        tree = new ArrayList<Directory>();
        current = null;
        virtualCurrent = null;
        
    }

}
