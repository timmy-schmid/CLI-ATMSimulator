package R18_G2_ASM1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.text.DateFormat;
import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.io.IOException;

import java.io.PrintStream;


class ATM_loggerTest{

  private StatusType type;
  private String message;
  private String classMethod;
  private Date date;

  private ATM_logger logger;
  
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream(); //for testing printing statements
  private final PrintStream originalOutput = System.out;

  @BeforeEach
  public void setUp() { 
    logger = new ATM_logger();
    type = StatusType.INFO;
    message = null;
    classMethod = null;

    //setupStream
    System.setOut(new PrintStream(outContent));
  }

  @AfterEach
  public void tearDown(){
    logger = null;

    //restoreStreams
    System.setOut(originalOutput);
  }

  @Test
  public void testNullMessagePart(){ 
    assertNull(classMethod);
    assertNull(message);
  }

  @Test
  public void testNullPath(){ 
    logger.setPath(null);
    assertNull(logger.getPath());
  }

  @Test //negative test, when message is null, don't write to file
  public void testNullWriteToFile() throws IOException {
    logger.setPath("src/test/logTests");
    logger.setLogFileName("/TestingLog1.log");
    logger.writeToFile(classMethod, type, message, logger.getFileName());
    assertEquals(outContent.toString(), "Not time to write to file yet!\n");
  }

  @Test
  public void testInvalidLogFileName(){ 
    logger.setLogFileName("");
    assertEquals("", logger.getFileName(), "Assertion failed!");
  }

  @Test
  public void testcanWriteToNewFile() throws IOException {
    //set a new log file name to write a random log message to it
    //assert the file exist and isnt empty (read the file)
    logger.setPath("src/test/logTests");
    logger.setLogFileName("/TestingLog1.log");
    logger.writeToFile("ATM_loggerTest.testcanWriteToNewFile", StatusType.INFO, "Successfully wrote to new log file!", logger.getFileName());

    File f = new File(logger.getPath() + logger.getFileName());
    boolean exists = f.exists();
    assertTrue(exists);
  }

  @Test
  public void testFileEmpty() throws IOException{ //when message or method is null, don't write to file
    logger.setPath("src/test/logTests");
    logger.setLogFileName("/TestingLog3.log");
    logger.writeToFile(null, StatusType.INFO, null, logger.getFileName());
    assertEquals(outContent.toString(), "Not time to write to file yet!\n");
    File file = new File(logger.getPath()+logger.getFileName());
    assert(file.length() == 0);
  }

  @Test //positive test case for WARNING status type
  public void testCanWriteWarningMessage(){
    logger.setPath("src/test/logTests");
    logger.setLogFileName("/TestingWarningLog1.log");
    logger.createLogMessage("class.method", StatusType.WARNING, "warning message");
    
    File file = new File(logger.getPath()+logger.getFileName());
    assert(file.length() != 0);
  }
  

  @Test //positive test case for FATAL status type
  public void testCanWriteFatalMessage(){
    logger.setPath("src/test/logTests");
    logger.setLogFileName("/TestingFatalLog1.log");
    logger.createLogMessage("class.method", StatusType.FATAL, "fatal error message");
    
    File file = new File(logger.getPath()+logger.getFileName());
    assert(file.length() != 0);
  }
}