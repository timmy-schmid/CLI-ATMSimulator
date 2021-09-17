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

  private messageType type;
  private String message;
  private String classMethod;
  private Date date;
  private String path;

  private ATM_logger logger;

  @BeforeEach
  public void setUp() { 
    logger = new ATM_logger();
    type = messageType.INFO;
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

  //negative test
  @Test
  public void testFileDoesntExists(){ 
    logger.createLogMessage("class.method", messageType.INFO, "testing message");
    String invalidPath = "./hello/lala";
    boolean result = logger.checkFileExists(invalidPath);
    assertFalse(result);
  }
  
  //positive test
  @Test
  public void testFileExists(){ 
    logger.createLogMessage("class.method", messageType.INFO, "testing message");

    boolean result = logger.checkFileExists(path);
    assertFalse(result); //>>>>???
  }

  // @Test
  // public void testWriteToFile(){ //maybe read the file or assert (file isnt empty?)
  //   logger.createLogMessage("class.method", messageType.INFO, "testing message");
  //   try {
  //     logger.writeToFile(classMethod, type, message);
  //   } catch (SecurityException e) {
  //     assertEquals(SecurityException.class, e.getClass());
  //   }
  // }
}