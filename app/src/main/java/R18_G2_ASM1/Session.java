package R18_G2_ASM1;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.text.DateFormat;


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
    private int sessionID;
    private SessionStatus currentStatus;
    private Transaction transaction;
    private Card card;
    private int pinAttemptNum;
    private File csvCard;

    /**
     * Constructs and inialises a new session.
     * @param ATM the attatched ATM which the session is running on.
     */
    public Session(ATM ATM){
        this.attachedATM = ATM;
        this.sessionID = 0;
        try {
            // csvCard = new File("./app/src/main/datasets/card.csv");
            csvCard = new File("/Users/annasu/Downloads/USYD2021/SEMESTER_2/SOFT2412/ASSIGNMENT_1/R18_G2_ASM1/app/src/main/datasets/card.csv");
        } catch (Exception e){
            System.out.println("----------------------------------lallalalllllalalallalalall -------------------------------->>>>\n");
            e.printStackTrace();
        }
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
     * Begins an ATM  up the ATM to interact with a user.
     * A user is promoted to insert their card. After insertion an ATM session commences.
     * After completion of the ATM session, the ATM interacts with its internal components depending on the sessions status.
     * 
     * Possible session status' are:
     * <ul>
     *  <li>FAIL_WITHDRAWAL - The card number does not match a card from XYZ bank database</li>
     *  <li>SUCCESS_WITHDRAWAL - The card entered is not active as it's start date is later than the current date.</li>
     *  <li>FAIL_DEPOSIT - The current date is before the entered cards expiration date.</li>
     *  <li>SUCCESS_DEPOSIT - The card entered has been reported as lost/stolen.</li>
     *  <li>SUCCESS_BALANCE - The card entered has been blocked due to too many PIN attempts.</li>
     *  <li>ADMIN_MODE - The ATM is set to Admin Mode to perform admin tasks such as topping up money or changing the ATM's location.</li>
     *  <li>CANCELLED - The session was cancelled by the user.</li>
     *  <li>SUCCESS - The session was succesfully completed by the user.</li>
     * </ul>
     * 
     *  The ATM will shutdown after a session status has been resolved.
     * 
     * @param cardNum the card number that the session should interact with
     */
    public void run(int cardNum){
        //create a new transaction?
        
        // try {
        //     this.validateSession(cardNum);
        //     // this.retrieveCardFromFile(cardNum, csvCard);
        //     this.attachedATM.getATMLogger().createLogMessage("session.run", messageType.INFO, "validate session passed!!");
        
        // } catch (InvalidTypeException e) {
        //     this.attachedATM.getATMLogger().createLogMessage("session.validateSession", messageType.ERROR, "validate session FAILED!");
        // }

        //testing here coz above functions dont work....? [retrievecardfromfile]
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        Date start_date = null;
        Date expiration_date = null;
        try {
            start_date = dateFormat1.parse("2018-03-05");
            expiration_date = dateFormat1.parse("2023-03-04");
        } catch (ParseException e){
            e.printStackTrace();
        }

        this.card = new Card(38431.29, 78503, start_date, expiration_date, false, false, false, 912012);
    
        // Transaction withdrawalA = new Transaction(this.attachedATM, TransactionType.WITHDRAWAL, this.card, 1);
        // withdrawalA.setAmount(100.50); //amount to withdrawal
        // withdrawalA.run(withdrawalA.getType());

        Transaction depositC = new Transaction(this.attachedATM, TransactionType.DEPOSIT, this.card, 1);
        depositC.setAmount(500.00); //amount to deposit --> check small amount first then super large
        depositC.run(depositC.getType());
        System.out.println("END OF 1st session ~~~~~~~~~~~~~~~~~~~~ line 106");
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
     * @param cardNum the cardNumber proided by the user
     * @param c a card from XYZ database //do not need this 
     * @return true if the session was validated. False if it was not. 
     * @throws InvalidTypeException
     */
    private Boolean validateSession(int cardNum) throws InvalidTypeException{
        card = retrieveCardFromFile(cardNum, csvCard);
        if (card == null){
            currentStatus = SessionStatus.INVALID_CARD_NUMBER;
            return false;
        }
        else if (card.getStart_date().after(card.getExpiration_date())){
            currentStatus = SessionStatus.CARD_NOT_ACTIVE;
            return false;
        }
        else if (card.isExpired()){
            currentStatus = SessionStatus.CARD_EXPIRED;
            return false;
        }
        else if (card.is_lost()){
            currentStatus = SessionStatus.CARD_LOST;
            return false;
        }
        else if (card.is_blocked()) {
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
     *  <li> accountNo - 6 digits</li> // ignore it for now
     *  <li> balance - double </li>
     *  <li> userType - either customer or admin</li>
     * </ul>
     * 
     * @param cardNum the users card number to check against the file
     * @param filePath the card .csv file
     * @return a Card object that contains all the data of the 1st matching card in the file. Null if no card found.
     * @throws InvalidTypeException
     */
    private Card retrieveCardFromFile(int cardNum, File csvCard) throws InvalidTypeException {
        int cardNumber = -1;
        Date startDate = null;
        Date expirationDate = null;
        Boolean lost;
        Boolean blocked;
        Boolean expired;
        int pin = -1;
        double balance = -1;
        String userType = null;

        try {
            Scanner myReader = new Scanner(csvCard);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] infoArr = data.split(",");
                try{
                    cardNumber = Integer.parseInt(infoArr[0]);
                } catch(NumberFormatException e){
                    System.out.println("Invalid int type, 4 digits");
                    return null;
                }
                // System.out.printf("Card number = [%d], csv card num = [%d\n", cardNum, cardNumber);

                String pattern = "yyyy-MM-dd";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

                try {
                    startDate = simpleDateFormat.parse(infoArr[1]);
                    expirationDate = simpleDateFormat.parse(infoArr[2]);
                } catch (ParseException e) {
                    System.out.println("formatted as \"yyyy-MM-dd");
                    e.printStackTrace();
                }

                Date dateNow = new Date();
                expired = expirationDate.before(dateNow);

                if (infoArr[3].equals("T")) {
                    lost = true;
                }
                else if (infoArr[3].equals("F")) {
                    lost = false;
                } else {
                    throw new InvalidTypeException("Invalid Boolean type, Expected: T or F ");
                }

                if (infoArr[4].equals("T")) {
                    blocked = Boolean.valueOf("true");
                }
                else if (infoArr[4].equals("F")) {
                    blocked = Boolean.valueOf("false");
                } else {
                    throw new InvalidTypeException("Invalid Boolean type, Expected: T or F ");
                }

                try {
                    pin = Integer.parseInt(infoArr[5]);
                } catch(NumberFormatException e){
                    System.out.println("Invalid int type, 4 digits");
                }


                //ignore account for now

                // probably should remove the string names coz it doesnt exist in card anymore
                // only distinguish by ID number?

                try {
                    balance = Double.parseDouble(infoArr[6]);
                } catch(NumberFormatException e){
                    System.out.println("Invalid double type");
                }


                if (infoArr[7].equals("customer")) {
                    userType = "customer";
                }
                else if (infoArr[7].equals("admin")) {
                    userType = "admin";
                } else {
                    throw new InvalidTypeException("Invalid user type, Expected: customer or admin ");
                }

                if (cardNumber == cardNum){
                    // this.card = new Card(userType, balance, cardNumber, startDate, expirationDate,
                    // lost, blocked, expired, pin);
                    this.card = new Card(balance, cardNumber, startDate, expirationDate,
                    lost, blocked, expired, pin);
                }

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            this.attachedATM.getATMLogger().createLogMessage("Session.retrieveCardFromFile", messageType.ERROR, "CSV FILE DOES NOT EXIST.");
            e.printStackTrace();
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
     * @param filePath the relative path of the card .csv file
     * @return weather writing was successful or not.
     */
    private boolean writeCardToFile(int cardNum, String filePath) {
        return true;
    }


        
    /**
     * Sets up a new Transaction object and runs it.
     * Updates the Status to Success.
     */
    public void transact(Card c){

    }

    // public boolean checkPIN(){
    //     if (card.getPin().equals(attachedATM.askForPIN)){
    //         return true;
    //     }
    //     return false;

    // }

    /**
     * checks the PIN entered with the user against the matching card in the XYZ Bank card database
     * @return weather the PIN was valid or not.
     */
    private boolean checkPIN(){
        if (card.getPin() == attachedATM.askForPIN()) {
            return true;
        }
        return false;
    }
    /**
     * gets this sessions current session status
     * @return a session status based on the SessionStatus enum.
     */
    public SessionStatus getStatus() {
        return null;
    }

    //can I move this out and create a new class just for it? :D

    // public class InvalidTypeException extends Exception { 
    //     public InvalidTypeException(String errorMessage) {
    //         super(errorMessage);
    //     }
    // }
}
