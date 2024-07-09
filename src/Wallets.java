import java.security.*;
import java.security.spec.ECGenParameterSpec;

public class Wallets {
    public PrivateKey privateKey;
    public PublicKey publicKey;

    public Wallets () {
        try {
            // Elliptical curve algo to generate keypair and initializing it
            KeyPairGenerator KeyPairGen = KeyPairGenerator.getInstance("ECDSA", "BC");
            ECGenParameterSpec keySize = new ECGenParameterSpec("prime192v1");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            KeyPairGen.initialize(keySize, random);
            // generate the keypair and get them
            KeyPair keyPair = KeyPairGen.generateKeyPair();
            this.privateKey = keyPair.getPrivate();
            this.publicKey = keyPair.getPublic();
        } catch (NoSuchProviderException | NoSuchAlgorithmException e) {
            throw new RuntimeException("No such Algorithm/provider Found " + e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException("The algorithm used is invalid " + e);
        }
    }
}
