package org.yamashiro0110.sample.auth;

import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.keys.AesKey;
import org.jose4j.lang.JoseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.security.Key;

public class JwtTest {
    private final Key key = new AesKey("128bit__password".getBytes(StandardCharsets.UTF_8));

    private String jweCompactSerialization(final String payload) throws JoseException {
        final JsonWebEncryption jwe = new JsonWebEncryption();
        jwe.setPayload(payload);
        jwe.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.A128KW);
        jwe.setEncryptionMethodHeaderParameter(ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256);
        jwe.setKey(this.key);
        return jwe.getCompactSerialization();
    }

    private String jwePayload(final String jwt, final Key key) throws JoseException {
        final JsonWebEncryption jwe = new JsonWebEncryption();
        jwe.setAlgorithmConstraints(new AlgorithmConstraints(AlgorithmConstraints.ConstraintType.PERMIT, KeyManagementAlgorithmIdentifiers.A128KW));
        jwe.setContentEncryptionAlgorithmConstraints(new AlgorithmConstraints(AlgorithmConstraints.ConstraintType.PERMIT, ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256));
        jwe.setKey(key);
        jwe.setCompactSerialization(jwt);
        return jwe.getPayload();
    }

    private String jwePayload(final String jwt) throws JoseException {
        return this.jwePayload(jwt, this.key);
    }

    @Test
    public void jweGetCompactSerialization() throws JoseException {
        final String jwt = this.jweCompactSerialization("{key:value}");
        System.out.println("jwe: " + jwt);
    }

    @Test
    public void jwePayload() throws JoseException {
        final String jwt = this.jweCompactSerialization("{key:value}");
        final String payload = this.jwePayload(jwt);
        System.out.println("payload: " + payload);
    }

    @Test
    public void jweInvalidKey() {
        final Exception e = Assertions.assertThrows(JoseException.class, () -> {
            final String jwt = this.jweCompactSerialization("{key:value}");
            final Key key = new AesKey("invalid_password".getBytes(StandardCharsets.UTF_8));
            this.jwePayload(jwt, key);
        });

        System.out.println("error: " + e.getMessage());
    }

}
