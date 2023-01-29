package bigth.myserver;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class JasyptConfigTest {

    JasyptConfig jasyptConfig = new JasyptConfig();

    @Test
    void encrypt() {
        var encryptedText = jasyptConfig.stringEncryptor().encrypt("plain text");
        log.info(encryptedText);
    }
}