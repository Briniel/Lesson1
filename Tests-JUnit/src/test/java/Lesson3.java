import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class Lesson3 {

    private Logger logger = LogManager.getLogger(Lesson3.class);

    @Test
    public void logExamlpe() {
        logger.info("Start Test");
        logger.error("It's error");
        logger.fatal("It's fatal");
        logger.debug("It's debug");
    }
}
