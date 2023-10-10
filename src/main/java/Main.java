import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        // Logic.logic();
        logger.info("Main method started");
        GUI gui = new GUI(new Logic());
        logger.info("Program started");
        gui.engage();
        logger.info("Program engaged");
    }
}
