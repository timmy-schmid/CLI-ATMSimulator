package R18_G2_ASM1;

import java.util.logging.SimpleFormatter;
import java.util.logging.Logger;
import java.util.logging.Handler;
import java.util.logging.FileHandler;
import java.io.IOException;
import java.text.ParseException;

/** 
* This ATM_logger class records error messages, transaction process history in logs files whenever a session runs.
* 
* TODO: The idea is to generate a new log file everyday with a descriptive date and location as its name.
* @author Anna Su
* @version 1.0
*
* The .log file has the following format:
* <ul> 
*   <li> Date - formatted as 'MMM d, yyyy HH:mm:ss a' </li>
*   <li> Name - Package.ATM_logger</li>
*   <li> method - writeToFile</li>
* </ul>
*
* Followed by information in a similar format:
* <ul> 
*   <li> StatusType - either INFO, ERROR or WARNING</li>
*   <li> classMethod - location of where function was called from</li>
*   <li> message - Description of the message type</li>
* </ul>
* Sept 20, 2021 12:01:33 AM R18_G2_ASM1.ATM_logger writeToFile
*/
public class ATM_logger{

  /**
   * A status type
   */
  private StatusType type;

  /**
   * A description message
   */
  private String message;

  /**
   * A class method showing where the createLogMessage function was called
   */
  private String classMethod;

  /**
   * The path where the log file will be made in
   */
  private String path;

  /**
   * The name of the log file
   */
  private String logFileName;

  /**
   * A file handler object
   */
  private static FileHandler fh;

  /**
   * Constructs a new ATM_logger object
   */
  public ATM_logger(){
    this.type = StatusType.INFO; //initially
    this.message = null;
    this.classMethod = null;
    this.path = "src/main/logs";
    this.logFileName = "/SessionLog1.log";
  }

  /**
   * getPath
   * @return returns the path
   */
  public String getPath(){
    return this.path;
  }

  /**
   * setPath
   * Used for testing purposes or changing location for producing log file
   * @param path sets the path to a different path
   */
  public void setPath(String path){
    this.path = path;
  }
  
  /**
   * getFileName
   * @return Retrieves the log file's name
   */
  public String getFileName(){
    return this.logFileName;
  }

  /**
   * setLogFileName
   * @param newName sets the log file's name to a new name
   */
  public void setLogFileName(String newName){
    this.logFileName = newName;
  }

  /**
   * createLogMessage
   * This function stores the required data into variables before writing to a log file.
   * @param classMethod specific method from class that calls this function
   * @param type the type of message to write in the log file
   * @param message description to write in log file
   */
  public void createLogMessage(String classMethod, StatusType type, String message) {
    this.classMethod = classMethod;
    this.type = type;
    this.message = message;
    writeToFile(this.classMethod, this.type, this.message, this.getFileName());
  }

  /**
   * writeToFile
   * This function validates the parameters before proceeding to write to a specific log file in simple, human readable format.
   * @param classMethod specific method from class that calls this function
   * @param type type of message
   * @param message description to write in log file
   * @param logFileName The location of the file where the log is to be written to
   */
  public void writeToFile(String classMethod, StatusType type, String message, String logFileName) {
    //if the parameters are not null, write to file otherwise keep waiting till info is provided
    if (classMethod == null || type == null || message == null) {
      System.out.println("Not time to write to file yet!");
      return;

    } else {
      Logger logger = Logger.getLogger("ATM_logger");
      try {
        fh = new FileHandler(this.path + logFileName, true); //append to existing file 

        // this prevents output from showing onto console
        Logger globalLogger = Logger.getLogger("");
        Handler[] handlers_ls = globalLogger.getHandlers();
        for (Handler handler : handlers_ls) {
          globalLogger.removeHandler(handler);
        }

        logger.addHandler(fh); //adds a log handler to receive logging msgs

        //this provides output in human readable format to the log file
        SimpleFormatter sFormatter = new SimpleFormatter();
        fh.setFormatter(sFormatter);

        //now log msgs
        if (type == StatusType.INFO){
          logger.info(this.classMethod + " --> MESSAGE: " + this.message);
        } else if (type == StatusType.ERROR){
          logger.severe(this.classMethod + " --> MESSAGE: " + this.message);
        } else if (type == StatusType.WARNING){
          logger.warning(this.classMethod + " --> MESSAGE: " + this.message);
        } else if (type == StatusType.FATAL){
          logger.warning(this.classMethod + " --> MESSAGE: " + this.message);
        }

      } catch (IOException | SecurityException e){ 
        System.out.println("Error with handling/opening the file.");
      }
    }
  }
}