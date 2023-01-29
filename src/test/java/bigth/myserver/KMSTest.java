package bigth.myserver;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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