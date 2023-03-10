package bigth.myserver;

import bigth.myserver.config.encryption.JasyptConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class JasyptConfigTest {

    JasyptConfig jasyptConfig = new JasyptConfig();

    @Test
    void encrypt() {
        var encryptedText = jasyptConfig.stringEncryptor().encrypt("plain text");
        log.info(encryptedText);
    }
}