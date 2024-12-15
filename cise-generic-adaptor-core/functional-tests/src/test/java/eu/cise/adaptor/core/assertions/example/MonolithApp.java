package eu.cise.adaptor.core.assertions.example;

import eu.cise.adaptor.core.FunctionalAppContext;
import eu.cise.adaptor.core.messagehandler.port.in.IncomingNodeToMessageHandlerPort;

import java.io.IOException;

import static eu.cise.adaptor.core.Utils.readResource;

public class MonolithApp {

    private static FunctionalAppContext ctx;
    private static IncomingNodeToMessageHandlerPort incomingNodeToMessageHandlerPort;

    public static void main(String[] args) throws IOException {
        ctx = FunctionalAppContext.createDefaultAppContext();
        incomingNodeToMessageHandlerPort = ctx.getDomainCtx().getIncomingNodeToMessageHandlerPort();
        sampleRequest();
    }

    private static void sampleRequest() {
        String rawCiseMessage = readResource("messages/good_ones/Push_riskPushConsumer_Signature_OK.xml", MonolithApp.class);
        System.out.println("request parameter: " + rawCiseMessage);
        incomingNodeToMessageHandlerPort.requestSyncAck(rawCiseMessage);
    }


}
