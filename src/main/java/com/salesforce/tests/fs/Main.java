package com.salesforce.tests.fs;


import java.util.Scanner;

import com.salesforce.tests.fs.broker.Interpreter;
import com.salesforce.tests.fs.broker.JShell;
import com.salesforce.tests.fs.exception.CommandBlankException;
import com.salesforce.tests.fs.exception.CommandExistenceException;
import com.salesforce.tests.model.commands.Quit;
import com.salesforce.tests.model.directories.DirectoryTree;
import com.salesforce.tests.model.structures.Directory;
import com.salesforce.tests.model.structures.TextFile;




public class Main {

    /** Test-LTGoicoechea
     * Initiates the Directory Tree and what else is necessary to execute the 
     * shell
     */
    public Main() {
        // Adding the root folder to our Directory Tree
        DirectoryTree.createDirectoryTree(new Directory());
        //Adding the base file(s) that our system will start with
        DirectoryTree.getCurrent().addFile(new TextFile("file1", "File1 - Content"));
        DirectoryTree.getCurrent().addFile(new TextFile("file2", "File2 - Content"));
        DirectoryTree.getCurrent().addFile(new TextFile("file3", "File3 - Content"));

        //Adding a base directory
        Directory c = new Directory("directory1", DirectoryTree.getCurrent());

        //Adds files to that base directory
        c.addFile(new TextFile("file1.txt", c, "File1"));
        c.addFile(new TextFile("file2.txt", c, "File2"));
        c.addFile(new TextFile("file3.txt", c, "File3"));
        c.addFile(new TextFile("file4.txt", c, "File4"));

        DirectoryTree.add(c);
    }

    
    /**
     * @param strings
     * main method of JShell; initializes the shell, starts prompt for user
     * input
     * @throws Exception 
     */
    public static void main(String[] strings) throws Exception {
      
        Scanner sc = new Scanner(System.in);
        String input;

        do {
            //command line prompt
            System.out.print("/# ");
            input = sc.nextLine().trim();
            
            try {
                Interpreter in = new Interpreter(input);
                in.getCmd().execute();
            } catch (CommandBlankException e) {
            } catch (CommandExistenceException e) {
                System.out.println("Invalid Command");
            } catch (InstantiationException | ClassNotFoundException 
                    | IllegalAccessException e) {
                System.out.println(e);
            }
            
        } while(Quit.exit == false);
        
	System.exit(0);
  }
}
