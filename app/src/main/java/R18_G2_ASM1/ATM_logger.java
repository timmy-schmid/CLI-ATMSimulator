package R18_G2_ASM1;

import java.util.*;
import java.util.Date;
import java.util.logging.SimpleFormatter;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/** 
log error message history - if session disappears records all dates times
transaction log file (including error log - how we process (each steps))

What does it do? records error message + processing transaction info msgs (+ date)

method: write to log file, double check to see if there are any mistakes within system handling

declare
   v_date = to_string(sysdate,”YYYYMMDD);
   v_time = to_string(sys date,”YYYYMMDDHH:MI:SS”)e
   v_message =  v_time + “—“ + p_type_message + “—“ + p_message
   
    create_log_message(“Card.withdraw”, “WARNING”, “You have low balance < $1“)
    create_log_message(“Card.withdraw”, “STOP”, “Not enough money “)
  

* @author Anna Su
* @version 1.0
 1. validate each parameter, only accept INFO/ERROR/WARNING keyword
 2. design layout
 accept multiple-lines in one call

  references: https://stackoverflow.com/questions/15758685/how-to-write-logs-in-text-file-when-using-java-util-logging-logger/15758768

  http://www.java2s.com/Code/Java/Language-Basics/UseLoggerwithsimpleformatterandFileHandler.htm

   --> output, limits, count, pattern and formatter 
*/
public class ATM_logger{
  private messageType type;
  private String message;
  private String classMethod;
  private Date date; //current

  private final String path;

  public ATM_logger(){ //class name = LOGFILE ?
    this.type = messageType.INFO; //initially?
    this.message = null;
    this.classMethod = null;
    this.path = "./app/src/main/java/R18_G2_ASM1"; //./
    // this.path = "/Users/annasu/Downloads/USYD2021/SEMESTER 2/SOFT2412/ASSIGNMENT_1/R18_G2_ASM1/app/src/main/java/R18_G2_ASM1/logs";
  }

  //write to a log file

  /**
   createLogMessage
   This function validates the parameters before proceeding to store the required data into variables, only accepting the valid keywords.
   @param classMethod specific method from class that calls this function
   @param messageType type of message [string vs messageType]
   @param message description to write in log file
   */
  
  //example format: create_log_message(“Card.withdraw”, “INFO”, “Start processing “)
  public void createLogMessage(String classMethod, messageType type, String message) {
    //validate first they are of the right type
      this.classMethod = classMethod;
      this.type = type;
      this.message = message;
      // throw new InvalidTypeException("type is not a valid message type.");
  }

  //FH writes to a specific file/a rotating set of files
  public boolean checkFileExists(String path){
    boolean result = false;
    try {
      //open filehandler 
      FileHandler fh = new FileHandler(path);
      result = true;
    } catch (IOException e){
      System.out.println("ERROR: FILE PATH DOES NOT EXIST! :(");
    }

    // if (result == true) {
    //   System.out.println("File exists already, append v_message to the file! :))");
    // } else {
    //   System.out.println("File doesn't exist, create a new file and add v_message to it! :p"); //failed to configure logging to file
    // }
    return result;
  }

  public void writeToFile(String classMethod, messageType type, String message){
    //if the paramaeters are not null, write to file otherwise don't do anything??
    if (classMethod == null || type == null || message == null){
      System.out.println("Not time to write to file yet!");
    } else {
      //if result == true, file exists so append
      boolean result = this.checkFileExists(this.path);
      Logger logger = Logger.getLogger("ATM_logger");
      FileHandler fh = null;
      // System.out.println("LINE 115: ATM_logger file class: method = " + classMethod + ", type = " + type + ", message: " + message);
      // System.out.println("THIS>PATH ============ " + this.path);
      try {
        if (result == true) { //append to existing file 
          fh = new FileHandler(this.path +"/logTEST1.log", true);
        } else if (result == false){
          fh = new FileHandler(this.path+"/logTEST1.log");
        }
        logger.addHandler(fh); //adds a log handler to receive logging msgs

        //this provides output in human readable format to the log file!!
        SimpleFormatter sFormatter = new SimpleFormatter();
        fh.setFormatter(sFormatter);

        //now log msgs
        
        if (type == messageType.INFO){
          logger.info(this.classMethod + " --> message: " + this.message);
        } else if (type == messageType.ERROR){
          logger.severe(this.classMethod + " --> message: " + this.message);
        } else if (type == messageType.WARNING){
          logger.warning(this.classMethod + " --> message: " + this.message);
        }
      } catch (SecurityException e){
        e.printStackTrace();
      } catch (IOException e){
        e.printStackTrace();
      }
      // logger.info("WELCOME TO XYZ BANK!");
    }
  }

  public void run(){
    writeToFile(this.classMethod, this.type, this.message);


  }
}


/**
  logger.info("")
  logger.severe("error msg")
  logger.fine("fine msg")
  logger.warning("warning msg")
 */