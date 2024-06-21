import java.util.Date;

public class Block {
    public String Hash;
    public String PreviousHash;
    private String Data;
    private long TimeStamp;

    public Block (String Data, String PreviousHash) {
        this.Data = Data;
        this.PreviousHash = PreviousHash;
        this.TimeStamp = new Date().getTime();
        this.Hash = calculateHash();
    }

    public String calculateHash () {
        return HashingAlgo.Hash(Data + PreviousHash + Long.toString(TimeStamp));
    }
}
