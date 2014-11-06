/*
 * AbstractCommand.java
 *
 * Version 1.0
 * Copyright 2011 BobSoft Inc
 */
package spacetrader.controllers.market;

/**
 * @author Robert
 * @version 1.0
 *
 */
public abstract class AbstractCommand {
      public static final CommandProcessor INSTANCE = new CommandProcessor();
      public abstract boolean doIt();
      public abstract boolean undoIt();
      
}
