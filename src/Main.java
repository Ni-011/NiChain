import java.util.ArrayList;
import java.util.HashMap;
import com.google.gson.GsonBuilder;

public class Main {

    public static ArrayList<Block> mainChain = new ArrayList<>();

    public static int difficulty = 1;

    public static void main(String[] args) throws Exception {
        mainChain.add(new Block("This is the Genesis Block", "0"));
        System.out.println("Mining genesis Block......");
        mainChain.getFirst().mine(difficulty);

        mainChain.add(new Block("This is the Second Block", mainChain.getLast().Hash));
        System.out.println("Mining second Block......");
        mainChain.get(1).mine(difficulty);

        Boolean isChainValid = ConsensusAlgo.Verify(mainChain, difficulty);

        // uses consensus algo to check the validity of chain
        if (!isChainValid) {
            throw new Exception("The Chain is Invalid");
        }

        String mainChainJson = new GsonBuilder().setPrettyPrinting().create().toJson(mainChain);
        System.out.println(mainChainJson);
    }
}