import java.util.Date;

public class Block {
    public String Hash;
    public String PreviousHash;
    public final String Data;
    public final long TimeStamp;
    public int nonce;

    public Block (String Data, String PreviousHash) {
        this.Data = Data;
        this.PreviousHash = PreviousHash;
        this.TimeStamp = new Date().getTime();
        this.Hash = calculateHash();
    }

    public String calculateHash () {
        return HashingAlgo.Hash(Data + PreviousHash + Long.toString(TimeStamp) + Integer.toString(nonce));
    }

    // mining algo
    public void mine (int difficulty) throws Exception {
        Boolean POWCompleted = ProofOfWork(difficulty);
        if (POWCompleted) {
            System.out.println("Block Has been Mined: " + Hash);
        } else {
            throw new Exception("Proof of work could not complete, block didn't get mined");
        }
    }

    // algo for mining
    public Boolean ProofOfWork (int difficulty) {
        // string with difficulty leading 0s
        String target = new String(new char[difficulty]).replace('\0', '0');
        while (!Hash.substring(0, difficulty).equals(target)) {
            nonce++;
            Hash = calculateHash();
        }
        return true;
    }
}
