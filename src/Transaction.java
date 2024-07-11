import java.security.PublicKey;
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
}
