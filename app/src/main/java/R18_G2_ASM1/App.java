/*
 * This Java source file was generated by the Gradle 'init' task.
 */

package R18_G2_ASM1;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        MoneyStack m = new MoneyStack();
        ATM atm = new ATM("Canberra", m);
        Session session = new Session(atm);
        atm.run(session);
    }
}