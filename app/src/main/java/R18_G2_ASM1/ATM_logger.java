package R18_G2_ASM1;

import java.util.*;
import java.util.logging.SimpleFormatter;
import java.util.logging.Logger;
import java.util.logging.Handler;
import java.util.logging.FileHandler;
import java.io.IOException;
import java.text.ParseException;

/** 

* TO DO: consider having a delay in time each time a function calls this method

* Logs that display onto screen/Standard ouput when a session runs.
* It records error messages, history and transaction processes.

* @author Anna Su
* @version 1.0

* 1. validate each parameter, only accept INFO/ERROR/WARNING keyword?
*
*
* The .log file has the following format:
* <ul> 
    <li> Date - formatted as 'MMM d, yyyy HH:mm:ss a' </li>
    <li> Name - Package.ATM_logger</li>
    <li> method - writeToFile</li>
  </ul>

  Followed by information in a similar format:
  <ul> 
    <li> messageType - either INFO, ERROR or WARNING</li>
    <li> classMethod - location of where function was called from</li>
    <li> message - Description of the message type</li>
  </ul>
  Sept 20, 2021 12:01:33 AM R18_G2_ASM1.ATM_logger writeToFile
*/
public class ATM_logger{
  private messageType type;
  private String message;
  private String classMethod;

  private String path;
  private String logFileName;

  private static FileHandler fh;

  /**
   * Constructs a new ATM_logger object
   */
  public ATM_logger(){ //class name = LOGFILE ?
    this.type = messageType.INFO; //initially?
    this.message = null;
    this.classMethod = null;
    // this.path = "./app/src/main/logs"; // double check later, using absolute path for now
    this.path = "/Users/annasu/Downloads/USYD2021/SEMESTER_2/SOFT2412/ASSIGNMENT_1/R18_G2_ASM1/app/src/main/logs";
    this.logFileName = "/SessionLog1.log";
  }

  public String getPath(){
    return this.path;
  }

  public void setPath(String path){ //for testing purposes or changing location of producing log file
    this.path = path;
  }

  public String getFileName(){
    return this.logFileName;
  }

  public void setLogFileName(String newName){
    this.logFileName = newName;
  }

  /**
   createLogMessage
   This function validates the parameters before proceeding to store the required data into variables, only accepting the valid keywords. It then writes to a log file.
   @param classMethod specific method from class that calls this function
   @param messageType type of message [string vs messageType]
   @param message description to write in log file
   */
  public void createLogMessage(String classMethod, messageType type, String message) {
    //validate first they are of the right type
    this.classMethod = classMethod;
    this.type = type;
    this.message = message;

    writeToFile(this.classMethod, this.type, this.message, this.getFileName());
  }

  /**
   writeToFile
   This function validates the parameters before proceeding to write to a specific log file in simple, human readable format.
   @param classMethod specific method from class that calls this function
   @param type type of message [string vs messageType]
   @param message description to write in log file
   */
  public void writeToFile(String classMethod, messageType type, String message, String logFileName){
    //if the parameters are not null, write to file otherwise keep waiting till info is provided
    if (classMethod == null || type == null || message == null) {
      System.out.println("Not time to write to file yet!!");

    } else {

      Logger logger = Logger.getLogger("ATM_logger");
      try {
        fh = new FileHandler(this.path + logFileName, true); //append to existing file 

        // this prevents output from showing onto console
        Logger globalLogger = Logger.getLogger("");
        Handler[] handlers = globalLogger.getHandlers();
        for (Handler handler : handlers) {
            globalLogger.removeHandler(handler);
        }

        logger.addHandler(fh); //adds a log handler to receive logging msgs

        //this provides output in human readable format to the log file!!
        SimpleFormatter sFormatter = new SimpleFormatter();
        fh.setFormatter(sFormatter);

        //now log msgs
        if (type == messageType.INFO){
          logger.info(this.classMethod + " --> MESSAGE: " + this.message);
        } else if (type == messageType.ERROR){
          logger.severe(this.classMethod + " --> MESSAGE: " + this.message);
        } else if (type == messageType.WARNING){
          logger.warning(this.classMethod + " --> MESSAGE: " + this.message);
        }
      } catch (IOException | SecurityException e){ 
        //e.g. if a security manager doesn't have but requires a logging permission, retrieved from https://docs.oracle.com/javase/7/docs/api/java/util/logging/FileHandler.html 
        //in case there are IO problems with opening the file
      }
    }
  }
}