package data;

public class VerifiedData<T> {

    /*
    Elements is the string version of the merkle tree
    signature is the signature of the hash of the elements
    public key is the corresponding key used to sign the hash and will be used to verify the signature.
    verified data is encrypted using the customers public key and hence cannot be tampered with after it has been signed and encrypted.
     */

    T elements;
    String signature;
    String publicKey;

    public T getElements() {
        return elements;
    }

    public void setElements(T elements) {
        this.elements = elements;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }


}
