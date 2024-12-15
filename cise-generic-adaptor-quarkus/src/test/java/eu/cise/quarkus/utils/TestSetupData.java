package eu.cise.quarkus.utils;

import java.util.HashSet;

import static java.util.Arrays.asList;

public class TestSetupData {

    /**
     * This is to be used as service IDs exposed by the plugins to the Generic Adaptor.
     * Those values are present also in test xml-s.
     */
    public static final HashSet<String> serviceIdsStub = new HashSet<>(asList(
            "node04.risk.pull.provider",
            "node08.risk.push.consumer",
            "node06.vessel.push.consumer"
    ));
}
