package eu.cise.adaptor.quarkus.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;

@ConfigMapping(prefix = "signature")
public interface SignatureConfig {

    @WithName("keystore.filename")
    String keystoreFileName();

    @WithName("keystore.password")
    String keystorePassword();

    @WithName("privatekey.alias")
    String privateKeyAlias();

    @WithName("privatekey.password")
    String privateKeyPassword();

    @WithName("conf.dir")
    String confDir();
}
