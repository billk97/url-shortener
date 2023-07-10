package dev.konstantinou.urlshortener.utils;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class CryptoUtils {

    public static KeyPair createRsaKeyPair() {
        KeyPairGenerator generator;
        try {
            generator = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("An error occurred while generating RSA " + e);
        }
        generator.initialize(2048);
        KeyPair keyPair = generator.generateKeyPair();
        printPublicKey(keyPair);
        printPrivateKey(keyPair);
        return  keyPair;
    }

    public static String getPrivateKeyInPKCSFormat(KeyPair keyPair) {
        return new String(Base64.getEncoder().encode(keyPair.getPrivate().getEncoded()));
    }

    public static String getPublicKeyInPemFormat(KeyPair keyPair) {
        return new String(Base64.getEncoder().encode(keyPair.getPublic().getEncoded()));
    }

    public static KeyPair fromString(String publicKey, String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] encodedPublicKey = Base64.getDecoder().decode(publicKey);
        X509EncodedKeySpec x509 = new X509EncodedKeySpec(encodedPublicKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey rsaPublicKey = keyFactory.generatePublic(x509);

        byte[] encodesPrivateKey = Base64.getDecoder().decode(privateKey);
        PKCS8EncodedKeySpec pkcs = new PKCS8EncodedKeySpec(encodesPrivateKey);
        PrivateKey rsaPrivateKey = keyFactory.generatePrivate(pkcs);
        return new KeyPair(rsaPublicKey, rsaPrivateKey);
    }


    public static void printPublicKey(KeyPair keyPair) {
        System.out.println("-----BEGIN PUBLIC KEY-----");
        System.out.println(getPublicKeyInPemFormat(keyPair));
        System.out.println("-----END PUBLIC KEY-----");
    }

    public static void printPrivateKey(KeyPair keyPair) {
        System.out.println("-----BEGIN PRIVATE KEY-----");
        System.out.println(getPrivateKeyInPKCSFormat(keyPair));
        System.out.println("-----END PRIVATE KEY-----");
    }


}
