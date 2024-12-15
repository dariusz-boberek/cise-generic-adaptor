package eu.cise.adaptor.core.common.logging;

import org.slf4j.LoggerFactory;

/**
 * Helper class to instantiate a {@link JsonLogger}
 */
public class LogConfig {

    /**
     * Basis logging configuration based on slf4j underlying framework to configure the logger for the caller class.
     * This class uses the {@link JsonLogger} underneath and translates all subsequent messages through it
     *
     * @param clazz The class that needs to add a logger for
     * @return The created AdaptorLogger
     */
    public static AdaptorLogger configureLogging(Class clazz) {
        return new JsonLogger(LoggerFactory.getLogger(clazz));
    }
}
