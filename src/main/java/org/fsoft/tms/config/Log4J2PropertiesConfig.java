package org.fsoft.tms.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Isabella on 27-May-2017.
 */
public class Log4J2PropertiesConfig {
    private static Logger logger = LogManager.getLogger();
    public void performSomeTask(){
        logger.debug("This is a debbug message");
        logger.info("This is a info message");
        logger.warn("This is a warn message");
        logger.fatal("This is a fatal message");
    }
}
