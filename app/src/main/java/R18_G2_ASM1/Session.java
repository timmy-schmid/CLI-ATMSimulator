package R18_G2_ASM1;
import java.util.*;


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

    /**
     * Constructs and inialises a new session.
     * @param ATM the attatched ATM which the session is running on.
     */
    public Session(ATM ATM){
        this.attachedATM = ATM;
        this.sessionID = 0;
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
     * @param c a card from XYZ database
     * @return true if the session was validated. False if it was not.
     */
    private boolean validateSession(int cardNum, Card c){
        return false;
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
     *  <li> balance - double
     *  <li> userType - either customer or admin</li>
     * </ul>
     * 
     * @param cardNum the users card number to check against the file
     * @param filePath the relative path of the card .csv file
     * @return a Card object that contains all the data of the 1st matching card in the file. Null if no card found.
     */
    private Card retrieveCardFromFile(int cardNum, String filePath) {
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
    private boolean writeCardToFile(Card c, String filePath) {

    }

    /**
     * Sets up a new Transaction object and runs it.
     * Updates the Status to Success.
     */
    public void transact(Card c){

    }
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
}
