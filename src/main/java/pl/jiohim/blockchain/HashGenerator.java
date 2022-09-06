package pl.jiohim.blockchain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;

 class HashGenerator {

    private static final int DIFFICULTY = 3;


    static String encode(Object... data) {
        try {

            //concatenate String version of all input data
            String input = Arrays.stream(data).map(Object::toString).reduce(String::concat)
                    .orElseThrow(BlockChainException::new);

            //apply sha256 to input
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            //convert to hex
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) buffer.append('0');
                buffer.append(hex);
            }
            return buffer.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Will mine a suitable hash digest for this block, and then set the digest and the nonce values on the block.
     *
     * @param block
     * @return
     */
    static String encode(Block block) {
        long start = System.currentTimeMillis();
        long nonce = 0;
        String previousHash = (block.getPrevious() != null) ? block.getPrevious().getHash() : "";
        String data = block.getData();

        //that many 0's in a string
        String prefix = new String(new char[DIFFICULTY]).replace('\0', '0');

        String hash;
        do {
            nonce++;
            hash = HashGenerator.encode(previousHash, data, nonce);
        } while (!hash.startsWith(prefix));

        //update the block
        block.setHash(hash);
        block.setNonce(nonce);

        System.out.printf("Calculated hash in %-4s ms for block with data: %s %n", System.currentTimeMillis() - start, block.getData());
        return hash;
    }

    /**
     * Validates the hash digest for the block given its position in the chain and its nonce value.
     *
     * @param block
     * @throws BlockChainException if the validation fails
     */
    static void validate(Block block) throws BlockChainException {
        String previousHash = (block.getPrevious() != null) ? block.getPrevious().getHash() : "";
        String data = block.getData();
        long nonce = block.getNonce();
        String expected = HashGenerator.encode(previousHash, data, nonce);
        if (!expected.equals(block.getHash())) {
            throw new BlockChainException(String.format("invalid hash in block with data: %s", data));
        }
    }
}
