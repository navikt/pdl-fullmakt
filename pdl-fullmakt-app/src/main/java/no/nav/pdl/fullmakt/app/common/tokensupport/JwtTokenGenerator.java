package no.nav.pdl.fullmakt.app.common.tokensupport;

import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

@Component
@Slf4j
public class JwtTokenGenerator {

    public static final int TOKEN_MAX_AGE_MINUTES = 9;

    /**
     * Load private pkcs8 pem file to get the private key
     *
     * @param key String of the Pkcs8 file
     * @return PrivateKey
     */
   /* public static PrivateKey loadPrivateKey(String key) {
        String privateKeyPEM = key
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        // decode to get the binary DER representation
        byte[] privateKeyDER = Base64.decodeBase64(privateKeyPEM);

        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyDER));
            return privateKey;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalArgumentException("Error occurred when reading key.", e);
        }
    }*/
}