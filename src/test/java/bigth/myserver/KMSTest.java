package bigth.myserver;

import bigth.myserver.config.encryption.KMS;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class KMSTest {

    @Test
    void encryptAndDecrypt() {
        var plainText = "JASYPTKEYINMYGREATSERVER";
        var encrypted = KMS.encrypt(plainText);
        var decrypted = KMS.decrypt(encrypted);
        assertThat(plainText).isEqualTo(decrypted);
        log.info("encrypted: {}", encrypted);
    }
}