package dev.konstantinou.urlshortener.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtUtilsTests {

    @Test
    void given_a_username_and_the_privileges_a_valid_jwt_should_be_returned() {

        System.out.println(JwtUtils.crateDefaultRsaJwt("bill", null, CryptoUtils.createRsaKeyPair()));
        assertTrue(JwtUtils.createDefaultSymmetricJwt("username", null, "secret" ) != null);
    }

}