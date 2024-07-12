import java.security.*;
import java.util.ArrayList;
import java.util.Base64;

public class Transaction {
    public String TransactionId;
    public PublicKey senderAdd;
    public PublicKey receiverAdd;
    public Float amount;
    public byte[] sign;
    public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
    public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();
    public int transactionCount = 0;

    public Transaction (PublicKey from, PublicKey to, Float amount, ArrayList<TransactionInput> inputs) {
        this.inputs = inputs;
        this.amount = amount;
        this.senderAdd = from;
        this.receiverAdd = to;
    }

    public String keyToString (PublicKey key) {
        byte[] keyBytes = key.getEncoded();
        return Base64.getEncoder().encodeToString(keyBytes);
    }

    public String Hash() {
        // 2 transactions must not have same hash
        transactionCount++;
        return HashingAlgo.Hash(keyToString(senderAdd) + keyToString(receiverAdd) + Float.toString(amount) + transactionCount);
    }

    public void generateSignature (PrivateKey key) {
        String Data = keyToString(senderAdd) + keyToString(receiverAdd) + Float.toString(amount);
        Signature ECDSA;
        try {
            ECDSA = Signature.getInstance("ECDSA", "BC");
            ECDSA.initSign(key); // initiating the sign with private key
            ECDSA.update(Data.getBytes()); // hashes the data to sign
            this.sign = ECDSA.sign(); // creating the sign and storing it
        } catch (NoSuchProviderException | NoSuchAlgorithmException e) {
            throw new RuntimeException("ECDSA algo or BC provider not found " + e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException("Error while signing " + e);
        } catch (SignatureException e) {
            throw new RuntimeException("Error while updating data into algo" + e);
        }
    }

    //is signature valid?
    public boolean verifySignature () {
        String Data = keyToString(senderAdd) + keyToString(receiverAdd) + Float.toString(amount);
        try {
            Signature ECDSA = Signature.getInstance("ECDSA", "BC");
            ECDSA.initVerify(senderAdd); // signature singed with private key of sender will be verified by his public key
            ECDSA.update(Data.getBytes()); // this data will be hashed and compared to original hash made during signing
            return ECDSA.verify(this.sign); // returns boolean value of verification
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException("ECDSA algorithm or its provider not found" + e);
        } catch (SignatureException e) {
            throw new RuntimeException("Error while verifying the signature " + e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException("The Sender Public key provided for verification is invalid " + e);
        }
    }
}
