package com.salesforce.tests.fs.broker;



import java.util.ArrayList;

import com.salesforce.tests.fs.exception.CommandBlankException;
import com.salesforce.tests.fs.exception.CommandExistenceException;
import com.salesforce.tests.model.commands.Command;

/**
 * Class responsible for interpreting the input given by users
 * It will set cmd as the command interpreted and give its parameters too
 */
public class Interpreter {

    private Command cmd;

    public Interpreter (String input) throws 
            InstantiationException, ClassNotFoundException,
            IllegalAccessException, CommandBlankException,
            CommandExistenceException {
        
        String command = Interpreter.extractCommand(input);
        String parameters = Interpreter.extractParameters(command, input);
        
        this.cmd = this.getCommand(command);

        this.cmd.setParameters(parameters);
    }

    /**
     * Returning command in interpreter
     * @return Command cmd
     */
    public Command getCmd() {
        return cmd;
    }

    
    /**
     * Formating the command to a correspondent class name, capitalizing
     * the first letter.
     * For instance: echo - Echo, ls - Ls, etc.
     * 
     * @param command
     * @return formatted string
     */
    private String capitalize (String command) {
        return command.substring(0, 1).toUpperCase() + command.substring(1);
    }
    
    /**
     * Returns commands using polymorphism
     * @param command - string with command
     * @return Command
     * 
     * @throws InstantiationException when command does not exist
     * @throws ClassNotFoundException when command does not exist
     * @throws IllegalAccessException when command does not exist
     */
    private Command getCommand(String command) throws InstantiationException, 
            ClassNotFoundException, IllegalAccessException,
            CommandBlankException, CommandExistenceException {
        
        if (command.equals("")) {
            throw new CommandBlankException();
        }

        command = this.capitalize(command);

        if (! Interpreter.verify(command)) {
            throw new CommandExistenceException();
        }

        
        Class<?> c = Class.forName("cmd." + command);
        	return (Command) c.newInstance();                
    }
    
        
    /**
     * Returns command. Eg.:
     * ls -r ../dir returns ls
     * echo "Text" > File returns echo
     * 
     * @param command
     * @return command
     */
    public static String extractCommand(String command) {
        int spaceIndex = command.indexOf(" ");
        
        if (spaceIndex > -1) {
            command = command.substring(0, command.indexOf(" ")).trim();
        }
        
        return command;
    }

    /**
     * Returns the parameters. Eg.:
     * ls -r ../dir returns -r ../dir
     * echo "Text" > File returns "Text" > File
     * 
     * @param command
     * @param input
     * 
     * @return parameters
     */
    public static String extractParameters(String command, String input) {
        int afterCommand = input.indexOf(command) + command.length();
        String parameters = input.substring(afterCommand).trim();
        
        if (! parameters.equals("")) {
            return parameters;
        }

        return null;
    }
    
    /**
     * Verifying whether the command exists
     * @param commmand
     * @return boolean
     */
    public static boolean verify (String commmand) {
        ArrayList<String> commands = new ArrayList<>();
        
        commands.add("Quit");
        commands.add("Pwd");
        commands.add("Ls");
        commands.add("Mkdir");
        commands.add("Cd");
        commands.add("Touch");
        commands.add("Get");
        commands.add("Man");
        commands.add("Mv");
        commands.add("Cp");
        commands.add("Rm");
        commands.add("Echo");
        commands.add("Rm");
        commands.add("Pushd");
        commands.add("Popd");

        return commands.contains(commmand);
    }
    
}