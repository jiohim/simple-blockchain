package pl.jiohim.blockchain;

public class BlockChainMain {
    public static void main(String[] args) throws Exception {

        //TODO: create a chain of blocks
        Block genesisBlock_production = new Block(null, "Bitter butter boom");
        Block secondBlock_transcontinental = new Block(genesisBlock_production, "Ain't no mountain high enough");
        Block thirdBlock_distribution = new Block ( secondBlock_transcontinental, "Free in the Knowledge");

        //TODO: print the chain
        thirdBlock_distribution.printChain();

        //TODO: validate the chain
        thirdBlock_distribution.setData("000ab961425adb9ee0f853618ac5eba1654c60fabfb15b454549c59af6a711c6");
        HashGenerator.validate(thirdBlock_distribution);
    }
}
