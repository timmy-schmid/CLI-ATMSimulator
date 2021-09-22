package R18_G2_ASM1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.io.IOException;

class ATM_loggerTest{

  private StatusType type;
  private String message;
  private String classMethod;
  private Date date;
  private String path;

  private ATM_logger logger;

  @BeforeEach
  public void setUp() { 
    logger = new ATM_logger();
    type = StatusType.INFO;
    message = null;
    classMethod = null;
    path = "./app/src/main/java/R18_G2_ASM1";
  }

  @AfterEach
  public void tearDown(){
    logger = null;
  }

  @Test
  public void testNullMessage(){ 
    logger.createLogMessage(classMethod, type, message);
    assertNull(classMethod);
    assertNull(message);
  }

  @Test
  public void testNullPath(){ 
    logger.setPath(null);
    assertNull(logger.getPath());
  }

  @Test
  public void testInvalidLogFileName(){ 
    logger.setLogFileName("");
    assertEquals("", logger.getFileName(), "Assertion failed!");
  }

  @Test
  public void testcanWriteToNewFile(){
    //set a new log file name to write a random log message to it
    //assert the file exist and isnt empty (read the file?)
    logger.setPath("src/test/logTests");
    // logger.setPath("/Users/annasu/Downloads/USYD2021/SEMESTER_2/SOFT2412/ASSIGNMENT_1/R18_G2_ASM1/app/src/test/logTests");
    logger.setLogFileName("/TestingLog1.log");
    logger.writeToFile("ATM_loggerTest.testcanWriteToNewFile", messageType.INFO, "Successfully wrote to new log file!", logger.getFileName());

    File f = new File(logger.getPath() + logger.getFileName());
    boolean exists = f.exists();
    assertTrue(exists);
  }

  @Test
  public void testFileEmpty(){ //when message or method is null, don't write to file
    logger.setPath("src/test/logTests"); //app/src...
    // logger.setPath("/Users/annasu/Downloads/USYD2021/SEMESTER_2/SOFT2412/ASSIGNMENT_1/R18_G2_ASM1/app/src/test/logTests");
    logger.setLogFileName("/TestingLog2.log");
    logger.writeToFile(null, messageType.INFO, null, logger.getFileName());

    File file = new File(logger.getPath()+logger.getFileName());
    assert(file.length() == 0);
  }

  //negative test
  // @Test
  // public void testFileDoesntExists(){ 
  //   logger.createLogMessage("class.method", messageType.INFO, "testing message");
  //   String invalidPath = "/Users/annasu/Downloads/USYD2021/SEMESTER_2/SOFT2412/ASSIGNMENT_1/R18_G2_ASM1/app/src/test/helloo";

  //   logger.setPath(invalidPath);
  //   logger.setLogFileName("/TestingLog2.log");
    
  //   logger.writeToFile("ATM_loggerTest.testFileDoesntExists", messageType.ERROR, "Failed to find right path!", logger.getFileName());
  // }
  
  //positive test
  // @Test
  // public void testFileExists(){ 
  //   logger.createLogMessage("class.method", messageType.INFO, "testing message");

  //   boolean result = logger.checkFileExists(path);
  //   assertFalse(result); //>>>>???
  // }

  // @Test
  // public void testWriteToFile(){ //maybe read the file or assert (file isnt empty?)
  //   logger.createLogMessage("class.method", messageType.INFO, "testing message");
  //   logger.setPath("./hello/lala"); //set to be an invalid path
  //   try {
  //     logger.writeToFile(classMethod, type, message);
  //   } catch (SecurityException e) {
  //     assertEquals(SecurityException.class, e.getClass());
  //   }
  // }
}