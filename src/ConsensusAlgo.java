import java.util.List;

public class ConsensusAlgo {
    public static Boolean Verify (List<Block> mainChain, int difficulty) {
        Block currentBlock;
        Block prevBlock;
        String targetHash = new String(new char[difficulty]).replace('\0', '0');

        for (int i =0; i < mainChain.size(); i++) {
            currentBlock = mainChain.get(i);

            // checking validity of current block
            if (!currentBlock.Hash.equals(currentBlock.calculateHash())) {
                System.out.println("The new calculated Hash of Block does not match the original Hash");
                return false;
            }

            // checking validity of prev block
            if (mainChain.size() > 2) {
                prevBlock = mainChain.get(i - 1);
                if (!prevBlock.calculateHash().equals(currentBlock.PreviousHash)) {
                    System.out.println("The new calculated Hash of previous Block does not match the current Hash of new Block");
                    return false;
                }
            }

            // checking if hash is solved
            if (!currentBlock.Hash.substring(0, difficulty).equals(targetHash)) {
                System.out.println("Block has not been mined");
                return false;
            }
        }

        return true;
    }
}
