package eu.cise.adaptor.quarkus.config;

import io.quarkus.runtime.Quarkus;
import io.smallrye.config.ConfigValidationException;
import io.smallrye.config.SmallRyeConfig;
import io.smallrye.config.SmallRyeConfigBuilder;
import org.eclipse.microprofile.config.ConfigProvider;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ConfigChecker {

    public static <T> T buildConfig(Class<T> configClass, List<Exception> validationExceptions) {
        try {
            SmallRyeConfig config = new SmallRyeConfigBuilder()
                    .withMapping(configClass)
                    .withSources(StreamSupport.stream(ConfigProvider.getConfig().getConfigSources().spliterator(), false).collect(Collectors.toList()))
                    .build();

            return config.getConfigMapping(configClass);
        } catch (Exception illegalStateException) {
            validationExceptions.add(illegalStateException);
        }
        return null;
    }

    public static void checkConfigurationValidationExceptions(List<Exception> validationExceptions) {
        StringBuilder stringBuilder = new StringBuilder("FATAL: Application failed to start\n");
        stringBuilder.append("Please revisit application.properties as there are configuration errors:\n");
        int currentProblem = 0;
        for (Exception illegalStateException : validationExceptions) {
            if (illegalStateException.getCause() instanceof ConfigValidationException) {
                ConfigValidationException ex = (ConfigValidationException) illegalStateException.getCause();
                for (int i = 0; i < ex.getProblemCount(); i++) {
                    currentProblem++;
                    stringBuilder.append(currentProblem).append(") ").append(ex.getProblem(i).getMessage()).append("\n");

                }
            } else {
                currentProblem++;
                stringBuilder.append(currentProblem).append(") ").append(illegalStateException.getMessage()).append("\n");
            }
        }
        // if there were validation exceptions, print output in System.err and die
        if (!validationExceptions.isEmpty()) {
            System.err.println(stringBuilder);
            Quarkus.asyncExit();
        }
    }

}
