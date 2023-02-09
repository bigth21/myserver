package bigth.myserver.config.encryption;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JasyptConfig {

    private final String ENCRYPTED_KEY = "AQICAHhDd11ecqbBy1k+OZKqV1U3ZMGoRl/WhqdDWXcihgT6hQGTQFDLvZfMMrTkUNlOWtkKAAAAdjB0BgkqhkiG9w0BBwagZzBlAgEAMGAGCSqGSIb3DQEHATAeBglghkgBZQMEAS4wEQQMW4AeMqqRsFn82+SRAgEQgDNKlMbpwG3UUopCg87cfYoPqCN61zjMGdOhkowazXEDxpc931JDSRODFlKdG6BTmu6nusE=";
    private final String ALGORITHM = "PBEWithMD5AndDES";

    @Bean("jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        var encryptor = new PooledPBEStringEncryptor();
        var config = new SimpleStringPBEConfig();
        config.setPassword(KMS.decrypt(ENCRYPTED_KEY));
        config.setAlgorithm(ALGORITHM);
        config.setKeyObtentionIterations(1000);
        config.setPoolSize(1);
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        return encryptor;
    }
}
