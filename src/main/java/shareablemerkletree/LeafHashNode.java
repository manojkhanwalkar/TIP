package shareablemerkletree;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class LeafHashNode implements Node {

    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // has only one child - assume left child .

  //  @JsonManagedReference
    HashNode parent;

// @JsonBackReference
 AttributeNode child;

 String hash;

    @Override
    public Node getLeft() {
        return child;
    }

    @Override
    public String getHash() {
        return hash;
    }

    @Override
    public Node getParent() {
        return parent;
    }

    @Override
    public void setLeft(Node node) {
        child = (AttributeNode) node;
    }

    @Override
    public void setHash(String hash) {

        this.hash = hash;

    }

    @Override
    public void setParent(Node parent) {

        this.parent = (HashNode)parent;
    }

    @Override
    public String toString() {
        return "LeafHashNode{" +
                "id=" + id +
                ", child=" + child +
                ", hash='" + hash + '\'' +
                '}';
    }
}
