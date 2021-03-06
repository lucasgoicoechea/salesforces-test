package com.salesforce.tests.model.commands;

import com.salesforce.tests.model.directories.DirectoryFileTree;
import com.salesforce.tests.model.directories.DirectoryTree;
import com.salesforce.tests.model.structures.*;
import java.net.*;
import java.io.*;

public class Get extends Command {

    /**
     * Executing command
     * Overriding super class
     */
    @Override
    public void execute() {
        try {
            this.run();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    /**
     * Runs get command
     * @throws java.lang.Exception
     */
    public void run() throws Exception {
        String operator = operator();
        String url = getParameters().split(" ")[0];

        if (operator == null) {
            System.out.println(getContent(url));
            return;
        }
        
        outFile = true;
        String path = outPath(operator);
        if (path.equals("")) {
            throw new Exception("You have to provide an out file");
        }
        
        String filename = getLastFile(path);
        Directory parent = getLastFileParent(path);

        TextFile file;
        file = new TextFile(filename, parent, this.getContent(url));
        parent.addFile(file);
        
    }
    
    /**
     * Given a url returns its content
     * Eg.: get http://www.cs..../073.txt
     * Will get the contents of the file 073.txt and create a file in the cwd
     * 
     * @param url
     * @throws Exception if url is invalid or has no read permission
     * @return String content
     */
    public String getContent(String url) throws Exception {
        try {
            //URL is a web address. Retrieve the file at that URL and add it to the
            //current working directory
            URL address = new URL(url);
            URLConnection yc = address.openConnection();
            String content;

            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(yc.getInputStream()))) {
                String inputLine;
                content = "";
                while ((inputLine = in.readLine()) != null)
                    content += inputLine + "\n";
            }

            return content;
        } catch (MalformedURLException ex) {
            throw new Exception("Invalid URL given");
        } catch (IOException ex) {
            throw new Exception("Given URL has no read permission");
        }
    }


    /**
     * Overriding toString so it can be used with man describing get command
     * @return String
     */    
    @Override
    public String toString() {
        return "Gets content from given URL";
    }

}
