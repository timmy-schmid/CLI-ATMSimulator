package R18_G2_ASM1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import java.math.BigDecimal;

import java.util.List;
    /**
     * Represents a single XYZ ATM session with a user.
     * A session uses the ATM it is running on to interact with the user in order to validate and transact on a user's bank card.
     * A session also stores a status which is determined by the user's actions.
     * @author Wei Zhang
     * @version 1.0
     */

public class Session {
    private ATM attachedATM;
    private SessionStatus currentStatus;
    private Transaction transaction;
    private Card card;
    private int pinAttemptNum;
    private File csvCard;
    private File tempFile;
    private TransactionType transactionType;
    private String userType;


    /**
     * Constructs and initialises a new session.
     * @param ATM the attatched ATM which the session is running on.
     */
    public Session(ATM ATM) {
        pinAttemptNum = 0;
        attachedATM = ATM;
        //this.sessionID = sessionID;
        //this.transactionType = transactionType;

        csvCard = new File("src/main/datasets/card.csv");
        tempFile = new File("src/main/datasets/cardTemp.csv");

        // String absolutePath = this.getClass().getResource("/").getPath();
        // absolutePath = absolutePath.substring(0,absolutePath.length()-24);
        // csvCard = new File(absolutePath + "src/main/datasets/card.csv");
        // tempFile = new File(absolutePath + "src/main/datasets/cardTemp.csv");
    
    }

    /**
     * Starts a new session.
     * The session will:
     * <ul>
     *   <li>Retrieve a matching card from the XYZ database</li>
     *   <li>Validate card information. If validation is unsuccessful the session will terminate.</li>
     *   <li>Determine if the user is an admin. If so set the session status to ADMIN_MODE and terminate.</li>
     *   <li>Sets up a new transaction and runs it</li>
     *   <li>Terminates the session</li>
     * </ul>
     * 
     * @param cardNum the card number that the session should interact with
     */
    public void run(int cardNum) {

        
        card  = this.retrieveCardFromFile(cardNum, csvCard);


        if (!validateSession(card)){
            return;
        }


        while (!checkPIN()){
            System.out.println("Your PIN is not correct!"+" "+(3-pinAttemptNum)+" attempt(s) left.");
            if (pinAttemptNum == 3){
                card.setIsBlocked(true);
                this.writeCardToFile(cardNum, csvCard);
                currentStatus = SessionStatus.CARD_BLOCKED;
                return;
            }
        }

        if (userType == "admin"){
            currentStatus = SessionStatus.ADMIN_MODE;
            return;
        }

        transactionType = attachedATM.askForTransType();

        transact(card, transactionType);
        this.writeCardToFile(cardNum, csvCard);
    }

    /**
     * Get the number of attempts
     * 
     * @return the number of attempts
     */
    public int getAttemptNum(){
        return pinAttemptNum;
    }

    /**
     * When the pin value is checked out is incorrect
     * Number of attempt will be increased
     */
    public void ifWrongPin(){
        pinAttemptNum++;
    }

    /**
     * When we want to get the current User type
     * @return current user type 
     */
    public String getUserType(){
        return userType;
    }


   /**
     * Used to verify a card number against the card provided.
     * Asks the user to enter their PIN (only 3 attempts).
     * If PIN is expired the session will need update the card in the XYZ card database (.csv) to be blocked. 
     * Updates the StatusSession according to the following checks:
     * <ul>
     *  <li> INVALID_CARD_NUMBER - The card number does not match a card from XYZ bank database</li>
     *  <li> CARD_NOT_ACTIVE - The card entered is not active as it's start date is later than the current date.</li>
     *  <li> CARD_EXPIRED - The current date is before the entered cards expiration date.</li>
     *  <li> CARD_LOST - The card entered has been reported as lost/stolen.</li>
     *  <li> CARD_BLOCKED - The card entered has been blocked due to too many PIN attempts.</li>
     * </ul>
     * 
     * @param card the card proided by the user
     * @param c a card from XYZ database //do not need this 
     * @return true if the session was validated. False if it was not. 
     * @throws InvalidTypeException
     */
    private Boolean validateSession(Card card) {
        if (card == null){
            if (currentStatus == null){
                currentStatus = SessionStatus.INVALID_CARD_NUMBER;
            }
            return false;
        }
        else if (card.getStartDate().after(card.getExpirationDate())){
            currentStatus = SessionStatus.CARD_NOT_ACTIVE;
            return false;
        }
        else if (card.isExpired()){
            currentStatus = SessionStatus.CARD_EXPIRED;
            return false;
        }
        else if (card.isLost()){
            currentStatus = SessionStatus.CARD_LOST;
            return false;
        }
        else if (card.isBlocked()) {
            currentStatus = SessionStatus.CARD_BLOCKED;
            return false;
        }
        return true;    
    }

    /**
     * Searches a .csv file for a card that matches the user's card number.
     * The first match in the file is then converted into a card object.
     * If no card is matched then Null is returned.
     * 
     * .csv file has the following columns with format:
     * <ul> 
     *  <li> cardNumber - 5 digits</li>
     *  <li> startDate - formatted as "yyyy-MM-dd"</li>
     *  <li> expirationDate - formatted as "yyyy-MM-dd"</li>
     *  <li> lost - T or F</li>
     *  <li> blocked - T or F</li>
     *  <li> pin - 4 digits </li>
     *  <li> accountNo - 6 digits</li>
     *  <li> balance - double </li>
     *  <li> userType - either customer or admin</li>
     * </ul>
     * 
     * @param cardNum the users card number to check against the file
     * @param csvCard the filePath the card .csv file
     * @return a Card object that contains all the data of the 1st matching card in the file. Null if no card found.
     */
    public Card retrieveCardFromFile(int cardNum, File csvCard) {
        int cardNumber = -1;
        Date startDate = null;
        Date expirationDate = null;
        Boolean lost = false;
        Boolean blocked = false;
        Boolean expired = false;
        int pin = -1;
        // double balance = -1;
        BigDecimal balance = new BigDecimal(-1);
        Card thisCard;

        try {
            Scanner myReader = new Scanner(csvCard);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] infoArr = data.split(",");
                
                try{
                    cardNumber = Integer.parseInt(infoArr[0]);
                } catch(NumberFormatException e){
                    currentStatus = SessionStatus.CARD_READ_ERROR;
                    break;
                }
                String pattern = "yyyy-MM-dd";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

                try {
                    startDate = simpleDateFormat.parse(infoArr[1]);
                    expirationDate = simpleDateFormat.parse(infoArr[2]);
                } catch (Exception e) {
                    currentStatus = SessionStatus.CARD_READ_ERROR;
                    break;
                }

                Date dateNow = new Date();
                expired = expirationDate.before(dateNow);

                if (infoArr[3].equals("T")) {
                    lost = true;
                }
                else if (infoArr[3].equals("F")) {
                    lost = false;
                } else {
                    currentStatus = SessionStatus.CARD_READ_ERROR;
                    //throw new InvalidTypeException("Invalid Boolean type, Expected: T or F "); Handle this internally within this class and create new SessionsStatus CARD_READ_ERROR
                }

                if (infoArr[4].equals("T")) {
                    blocked = Boolean.valueOf("true");
                }
                else if (infoArr[4].equals("F")) {
                    blocked = Boolean.valueOf("false");
                } else {
                    currentStatus = SessionStatus.CARD_READ_ERROR;
                    break;
                    //throw new InvalidTypeException("Invalid Boolean type, Expected: T or F "); Handle this internally within this class and create new SessionsStatus CARD_READ_ERROR
                }

                try {
                    pin = Integer.parseInt(infoArr[5]);
                } catch(NumberFormatException e){
                    currentStatus = SessionStatus.CARD_READ_ERROR;
                    break; // this should handled with CARD_READ_ERROR
                }


                //ignore account for now


                try {
                    // balance = Double.parseDouble(infoArr[6]);
                    balance = new BigDecimal(infoArr[6]);
                } catch(NumberFormatException e){
                    currentStatus = SessionStatus.CARD_READ_ERROR;
                    break; // this should handled with CARD_READ_ERROR
                }

                if (infoArr[7].equals("customer")) {
                    userType = "customer";
                }
                else if (infoArr[7].equals("admin")) {
                    userType = "admin";
                } else {
                    currentStatus = SessionStatus.CARD_READ_ERROR;
                    break;
                    //throw new InvalidTypeException("Invalid user type, Expected: customer or admin "); // this should handled with CARD_READ_ERROR
                }
                if (cardNumber == cardNum){
                    thisCard = new Card(balance, cardNumber, startDate, expirationDate,
                    lost, blocked, expired, pin);
                    return thisCard;
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            currentStatus = SessionStatus.DATABASE_FILE_ERROR;
        }

        return null;
    }

    /**
     * Searches a .csv file for an external that matches against the card provided.
     * The first match in the file is then updated with the provided card information.
     * If no card is matched then the write fails and false is returned.
     * 
     * .csv file has the following columns with format:
     * <ul> 
     *  <li> cardNumber - 5 digits</li>
     *  <li> startDate - formatted as "yyyy-MM-dd"</li>
     *  <li> expirationDate - formatted as "yyyy-MM-dd"</li>
     *  <li> lost - T or F</li>
     *  <li> blocked - T or F</li>
     *  <li> pin - 4 digits </li>
     *  <li> accountNo - 6 digits</li>
     *  <li> balance - double</li>
     *  <li> userType - either customer or admin</li>
     * </ul>
     * 
     * @param cardNum the users card number to check against the file
     * @param file the relative path of the card .csv file
     */
    public void writeCardToFile(int cardNum, File file) {
        
        try {
            Scanner myReader = new Scanner(file);
            FileWriter myWriter = new FileWriter(tempFile);
            while (myReader.hasNextLine()) {
                String str = myReader.nextLine();
                if (str.split(",")[0].equals(Integer.toString(cardNum))){
                    if (card.isBlocked()){
                        myWriter.write(str.substring(0,30)+'T'+str.substring(31,str.length())+"\n");

                    }
                    else{
                        String[] arr = str.split(",");
                        myWriter.write(arr[0]+","+arr[1]+","+arr[2]+","+arr[3]+","+arr[4]+","+arr[5]+","+card.balance.toString()+","+arr[7]+"\n");
                    }
                }
                else {
                    myWriter.write(str+"\n");
                }
            }
            myReader.close();
            myWriter.close();
        } catch (FileNotFoundException e) {
            currentStatus = SessionStatus.CARD_WRITE_ERROR;
            //add some handling for FileNotFound. Use CARD_WRITE_ERROR SessionStatus. Also we need to make sure that no transactions are made if a write error occurs.
        } catch (IOException e) {
            currentStatus = SessionStatus.DATABASE_FILE_ERROR;
            //add some handling for writing errors. USE DATABASE_FILE_ERROR SessionStatus.
        }

        // Rename file (or directory)
        boolean success = tempFile.renameTo(file);

    }
        
    /**
     * Sets up a new Transaction object and runs it.
     * Updates the Status to Success.
     * @param c the card that the transaction is to be run on
     * @param transactionType the type of transaction to perform on the card
     */
    public void transact(Card c, TransactionType transactionType){
        Transaction transaction = new Transaction(attachedATM, transactionType, c);
        boolean result = transaction.run(transactionType);
        //check result to update sessionstatus!
        if (result == true) {
            currentStatus = SessionStatus.SUCCESS;
        } else {
            // needs edit
            currentStatus = SessionStatus.CANCELLED;
        }
    }

    /**
     * checks the PIN entered with the user against the matching card in the XYZ Bank card database
     * @return weather the PIN was valid or not.
     */
    public boolean checkPIN(){
        if (card.getPin() == attachedATM.askForPIN()) {
            return true;
        }
        this.ifWrongPin();
        return false;
    }
    /**
     * gets this sessions current session status
     * @return a session status based on the SessionStatus enum.
     */
    public SessionStatus getStatus() {
        return currentStatus;
    }

    public class InvalidTypeException extends Exception { 
        public InvalidTypeException(String errorMessage) {
            super(errorMessage);
        }
    }

    /**
     * When we want to change the current card database
     * @param newFile the new csv file 
     */
    public void changeCsvFile(File newFile){
        csvCard = newFile;
    }


}
