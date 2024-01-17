package ru.pominov.config;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.Level;

public class AppLogger {

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    static {
        LOGGER.setUseParentHandlers(false);

        Handler[] handlers = LOGGER.getHandlers();
        for(Handler handler : handlers) {
            if(handler.getClass() == ConsoleHandler.class)
                LOGGER.removeHandler(handler);
        }

        LOGGER.setLevel(Level.ALL);

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
        LOGGER.addHandler(consoleHandler);
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}
