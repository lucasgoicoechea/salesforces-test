package com.salesforce.tests.model.commands;

import com.salesforce.tests.model.directories.DirectoryFileTree;
import com.salesforce.tests.model.directories.DirectoryTree;
import com.salesforce.tests.model.structures.*;

public class Ls extends Command {
    DirectoryTree directoryTree;
    
    @Override 
    public void execute() {		
        try {
           this.run();
        } catch (Exception ex) {
            System.out.println("Command not found");
        }
}
    
	
    public void run () {
        listOn(DirectoryTree.getCurrent());
    }

    public void run (String[] args) {
    	//handles multiple parameters of paths (max is set for 2; this value
    	//can be increased to allow for more listing of paths
		for(String dir : args){
			//for each argument passed, check it exists and if it does 
			if (DirectoryTree.pathExists(dir)){
				Directory vDir = DirectoryTree.getVirtualCurrent();
				System.out.println(vDir.getName() + ":  \n");
				listOn(vDir);
			}
			else{
				System.out.println(dir + " does not exist.");
			}
		}
    	DirectoryTree.setVirtualCurrent(DirectoryTree.getCurrent());
    }
    
    public void listOn(Directory targetDirectory) {
    	// Listing both directories and files
        for (File file : DirectoryFileTree.getFilesOf(targetDirectory)) {
            System.out.println(file.getName());
        }
      
    }
   
    @Override
    public String toString() {
        return "Lists files/directories in the current working directory";
    }
    
}
