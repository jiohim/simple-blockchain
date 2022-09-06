package pl.jiohim.blockchain;

public class Block {
    private final Block previous;
    private String hash;
    private long nonce;
    private String data;

    public Block( Block previous, String data) {
        this.data = data;
        this.previous = previous;
        HashGenerator.encode(this);
    }

    public void printChain() {
        if (this.previous != null) {
            this.previous.printChain();
        } else {
            System.out.println("Chain starting from genesis node:");
        }

        System.out.printf("Block {nonce: %s} {hash: %s} {data: %s} %n", this.nonce, this.hash, this.data);
    }

    public Block getPrevious() {
        return this.previous;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHash() {
        return this.hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public long getNonce() {
        return this.nonce;
    }

    public void setNonce(long nonce) {
        this.nonce = nonce;
    }
}
