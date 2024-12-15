package eu.cise.adaptor.core.common.logging;

import org.slf4j.Logger;


/**
 * <p>Abstract class designed for integration with the CISE Generic Adaptor.
 * It serves as a bridge between the adaptor's logging mechanism and the underlying logging framework.
 * This class provides unified logging capabilities across various adaptor implementations, ensuring consistent log message formats and levels.
 * The log level is controlled by the underlying logging framework (typically slf4j)
 * </p>
 *
 * <p><b>Key Features:</b></p>
 * <ul>
 *   <li><b>Multi-level logging:</b> Supports various log levels (debug, info, warn, error, trace) for detailed and structured logging.</li>
 *   <li><b>Custom message formatting:</b> Abstract method <code>convertToText</code> allows for custom formatting of <code>LoggerMessage</code> objects,
 *       catering to specific logging requirements of different adaptors.</li>
 *   <li><b>Framework-agnostic:</b> Through the abstract method <code>getInternalLogger</code>, this class can adapt to different logging frameworks,
 *       making it versatile across various adaptor implementations.</li>
 * </ul>
 *
 * <p><b>Usage:</b> Subclasses should implement the <code>convertToText</code> method to define how <code>LoggerMessage</code> objects are formatted into string logs.
 * Implement <code>getInternalLogger</code> to return the specific logger instance from the used logging framework.</p>
 */
public abstract class AdaptorLogger {

    public boolean isDebugEnabled() {
        return getInternalLogger().isDebugEnabled();
    }

    /**
     * Used to add a log message in DEBUG level
     * @param message The message to add
     */
    public void debug(LoggerMessage message) {
        getInternalLogger().debug(convertToText(message), message.getExtraLogVariables());
    }

    /**
     * Used to add a log message in INFO level
     * @param message The message to add
     */
    public void info(LoggerMessage message) {
        getInternalLogger().info(convertToText(message), message.getExtraLogVariables());
    }

    /**
     * Used to add a log message in WARN level
     * @param message The message to add
     */
    public void warn(LoggerMessage message) {
        getInternalLogger().warn(convertToText(message), message.getExtraLogVariables());
    }

    /**
     * Used to add a log message in ERROR level. Also, the exception that caused the error may be logged
     * @param message The message to log
     * @param ex The exception causing the error
     */
    public void error(LoggerMessage message, Exception ex) {
        getInternalLogger().error(convertToText(message), ex);
    }

    /**
     * Used to add a log message in ERROR level. Also, the exception that caused the error may be logged
     * @param message The message to log
     */
    public void error(LoggerMessage message) {
        getInternalLogger().error(convertToText(message), message.getExtraLogVariables());
    }


    /**
     * Used to add a log message in ERROR level. Also, the exception that caused the error may be logged
     * @param message The message to log
     * @param throwable The throwable causing the error
     */
    public void error(LoggerMessage message, Throwable throwable) {
        getInternalLogger().error(convertToText(message), throwable);
    }

    /**
     * sed to add a log message in TRACE level.
     * @param message The message to log
     */
    public void trace(LoggerMessage message) {
        getInternalLogger().trace(convertToText(message), message.getExtraLogVariables());
    }

    /**
     * This method is used in all the logging methods to transform the LoggerMessage provided into a String ready to be logged
     * @param message The message to log
     * @return The String representing the actual log message
     */
    protected abstract String convertToText(LoggerMessage message);

    /**
     * Return the underlying logger of the logging framework used
     * @return The internal logger used
     */
    protected abstract Logger getInternalLogger();

}
