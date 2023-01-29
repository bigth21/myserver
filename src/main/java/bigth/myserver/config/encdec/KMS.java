package bigth.myserver.config.encdec;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.AWSKMSClientBuilder;
import com.amazonaws.services.kms.model.DecryptRequest;
import com.amazonaws.services.kms.model.EncryptRequest;
import org.apache.commons.codec.binary.Base64;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class KMS {

    private static final String PROFILE = "myserver";
    private static final String KEY_ID = "2119a0d9-262d-4e05-b708-c47a7aa97c3f";

    public static String encrypt(String text) {
        AWSKMS kmsClient = AWSKMSClientBuilder.standard()
                .withCredentials(new ProfileCredentialsProvider(PROFILE))
                .withRegion(Regions.AP_NORTHEAST_2)
                .build();

        EncryptRequest request = new EncryptRequest();
        request.withKeyId(KEY_ID);
        request.withPlaintext(ByteBuffer.wrap(text.getBytes(StandardCharsets.UTF_8)));

        byte[] cipherBytes = kmsClient.encrypt(request).getCiphertextBlob().array();
        return Base64.encodeBase64String(cipherBytes);
    }

    public static String decrypt(String cipherBase64) {
        AWSKMS kmsClient = AWSKMSClientBuilder.standard()
                .withCredentials(new ProfileCredentialsProvider(PROFILE))
                .withRegion(Regions.AP_NORTHEAST_2)
                .build();

        DecryptRequest request = new DecryptRequest();
        request.withKeyId(KEY_ID);
        request.withCiphertextBlob(ByteBuffer.wrap(Base64.decodeBase64(cipherBase64)));

        byte[] textBytes = kmsClient.decrypt(request).getPlaintext().array();
        return new String(textBytes);
    }
}
