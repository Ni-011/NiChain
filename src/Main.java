import java.util.ArrayList;

public class Main {

    public static ArrayList<Block> mainChain = new ArrayList<>();

    public static void main(String[] args) {
        mainChain.add(new Block("This is the Genesis Block", "0"));
        mainChain.add(new Block("This is the Second Block", mainChain.getLast().Hash));
        mainChain.add(new Block("This is the Third Block", mainChain.getLast().Hash));

        for (int i = 0; i < mainChain.size(); i++) {
            System.out.println("Hash for Block " + (i+1) + ":" + mainChain.get(i).Hash);
        }
    }

    public static Boolean consensus () {
        Block currentBlock;
        Block prevBlock;

        for (int i =0; i < mainChain.size(); i++) {
            currentBlock = mainChain.get(i);
            prevBlock = mainChain.get(i - 1);

            if (!currentBlock.Hash.equals(currentBlock.calculateHash())) {
                System.out.println("The new calculated Hash of Block does not match the original Hash");
                return false;
            }

            if (!prevBlock.calculateHash().equals(currentBlock.PreviousHash)) {
                System.out.println("The new calculated Hash of previous Block does not match the current Hash of new Block");
                return false;
            }
        }

        return true;
    }
}