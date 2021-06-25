package com.salesforce.tests.model.commands;



public class Quit extends Command {	
    
    public static boolean exit = false;
    
    /**
     * Executing command
     * @return String
     */
    @Override
    public void execute () {
        exit = true;
    }   
    
    /**
     * Overriding toString to describe what exit does
     * @return String
     */
    @Override
    public String toString() {
        return "Terminates the shell.";
    }

}
