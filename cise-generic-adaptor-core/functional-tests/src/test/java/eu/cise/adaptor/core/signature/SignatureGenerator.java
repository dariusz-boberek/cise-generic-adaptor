package eu.cise.adaptor.core.signature;

import eu.cise.servicemodel.v1.message.Message;
import eu.cise.signature.SignatureService;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import eu.eucise.xml.XmlNotParsableException;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static eu.cise.adaptor.core.Utils.readFile;
import static eu.cise.signature.SignatureServiceBuilder.newSignatureService;

public class SignatureGenerator {

    /**
     * Demonstrates the process of signing a CISE message.
     *
     * @param args (not used)
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Exiting, provide XML file paths as arguments.");
            return;
        }
        XmlMapper xmlMapper = new DefaultXmlMapper.PrettyNotValidating();
        for (String filePath : args) {
            try {
                String ciseMessageXML = readFile(filePath);
                String signedCiseMessageXML = xmlMapper.toXML(sign(xmlMapper.fromXML(ciseMessageXML)));

                try (FileOutputStream fos = new FileOutputStream(filePath);
                     DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(fos))) {
                    outStream.writeBytes(signedCiseMessageXML);
                }
            } catch (IOException e) {
                System.err.println("Failed to process file: " + filePath + ", reason: " + e);
                return;
            } catch (XmlNotParsableException e) {
                System.err.println("Failed to parse XML from file: " + filePath + ", reason: " + e);
                return;
            }
        }

    }

    private static synchronized Message sign(Message message) {
        try {
            SignatureService signatureService = newSignatureService()
                    .withKeyStoreName("test.jks")
                    .withKeyStorePassword("cisesim")
                    .withPrivateKeyAlias("cisesim-nodeex.nodeex.eucise.ex")
                    .withPrivateKeyPassword("cisesim")
                    .build();

            return signatureService.sign(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to sign the message.", e);
        }
    }
}
