/*
 * CommandProcessor.java
 *
 * Version 1.0
 * Copyright 2011 BobSoft Inc
 */
package spacetrader.controllers.market;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Robert
 * @version 1.0
 *
 */
public class CommandProcessor {
    /**
     * Push the commands here as we do them
     * Pop commands from here when they are undone
     */
     private List<AbstractCommand> undoStack;
     /**
      * Push the commands here as we undo them
      * Pop commands from here when they are redone
      */
     private List<AbstractCommand> redoStack;
     
     /**
      * 
      * Makes a new CommandProcessor
      */
     public CommandProcessor() {
         undoStack = new ArrayList<AbstractCommand>();
         redoStack = new ArrayList<AbstractCommand>();
     }
     
     /**
      * Actually does the command
      * @param cmd the command to execute
      */
     public void doCommand(AbstractCommand cmd) {
         //if command is undoable, push on stack
         if (cmd.doIt()) {
             undoStack.add(cmd);
         } else {
             //otherwise we have done something that cannot be undone, clear stack
             //undoStack.removeAll(undoStack);
         }
     }
     
     /**
      * Redo a command
      */
     public void redoCommand() {
         //if there is something on the redo stack
         if (!redoStack.isEmpty()) {
             //Then pop the command and execute it
             AbstractCommand cmd = redoStack.remove(redoStack.size() - 1);
             doCommand(cmd);
         }
     }
     
     /**
      * undo a command
      */
     public void undoCommand() {
         //If there are actually commands to be undone
         if (!undoStack.isEmpty()) {
             //Grab the next one to be undone and execute it
             AbstractCommand cmd = undoStack.remove(undoStack.size() - 1);
             cmd.undoIt();
             //Then add it to the redo stack
             redoStack.add(cmd);
         }
     }

    void undoCommand(BuyItemCommand cmd) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
