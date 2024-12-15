package eu.cise.adaptor.core.common.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;

/**
 * This class is the main implementation of the AdaptorLogger in the CISE Generic Adaptor.
 * It transforms all the LoggerMessage objects it gets into JSON format and returns their string representation
 */
public class JsonLogger extends AdaptorLogger {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
    }

    final Logger internalLogger;

    public JsonLogger(Logger logger) {
        internalLogger = logger;
    }

    @Override
    protected String convertToText(LoggerMessage loggerMessage) {
        try {
            return objectMapper.writeValueAsString(loggerMessage.getAttributes());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting domain message to JSON", e);
        }
    }

    @Override
    protected Logger getInternalLogger() {
        return internalLogger;
    }

}
