package aist.demo.util;

import org.springframework.core.io.ClassPathResource;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;
import java.security.interfaces.RSAPublicKey;

@Deprecated
public class EncryptionUtil {

    private static final String PRIVATE_KEY_FILE = "privatKey.txt";
    private static final String PUBLIC_KEY_FILE = "publicKey.txt";

    public static void generateKey() throws NoSuchAlgorithmException, IOException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(1024, new SecureRandom());
        KeyPair key = keyGen.generateKeyPair();

        writeToFile(PUBLIC_KEY_FILE, key.getPublic());
        writeToFile(PRIVATE_KEY_FILE, key.getPrivate());
    }

    private static void writeToFile(String fileName, Object key) throws IOException {
        File privateKeyFile = new File(fileName);

        if (privateKeyFile.getParentFile() != null) {
            privateKeyFile.getParentFile().mkdirs();
        }
        privateKeyFile.delete();
        privateKeyFile.createNewFile();
        try (
                ObjectOutputStream privateKeyOS = new ObjectOutputStream(
                        new FileOutputStream(privateKeyFile))) {
            privateKeyOS.writeObject(key);
        }
    }

    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            if (((int) aByte & 0xff) < 0x10)
                sb.append("0");
            sb.append(Long.toString((int) aByte & 0xff, 16));
        }
        return sb.toString();
    }

    public static byte[] hexStringToBytes(String data) {
        int j = 0;
        byte[] results = new byte[data.length() / 2];

        for (int i = 0; i + 1 < data.length(); i += 2, j++) {
            results[j] = (byte) (Character.digit(data.charAt(i), 16) << 4);
            results[j] += (byte) (Character.digit(data.charAt(i + 1), 16));
        }
        return results;
    }

    public static String decrypt(byte[] text) throws Exception {
        byte[] decryptedText;
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            ObjectInputStream inputStream;
            ClassPathResource resource = new ClassPathResource(PRIVATE_KEY_FILE);
            InputStream stream = resource.getInputStream();
            inputStream = ((ObjectInputStream) stream);
            PrivateKey privateKey = (PrivateKey) inputStream.readObject();

            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            decryptedText = cipher.doFinal(text);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IOException | ClassNotFoundException
                | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            throw new Exception("Error while decrypting password", ex);
        }
        return new String(decryptedText);
    }

    public static String getModulus() throws IOException, ClassNotFoundException {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(PUBLIC_KEY_FILE))) {
            RSAPublicKey publicKey = (RSAPublicKey) inputStream.readObject();
            byte[] bytes = publicKey.getModulus().toByteArray();
            return bytesToHexString(bytes);
        }

    }

}