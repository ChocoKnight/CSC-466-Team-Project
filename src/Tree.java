
import java.util.ArrayList;
import java.util.HashMap;

public class Tree {
    private int attribute;
    private int attributeValue;
    private ArrayList<Tree> children;
//    private Tree parent;
    private int label;

    public Tree(int attribute, int attributeValue) {
        this.attribute = attribute;
        this.attributeValue = attributeValue;
        this.children = new ArrayList<>();
//        this.parent = null;
        this.label = -1; // Unset label
    }

    public Tree(int label) {
        this.attribute = -1;
        this.attributeValue = -1;
        this.children = new ArrayList<>();
        this.label = label; // Unset label
    }

    public void addChild(Tree child) {
        this.children.add(child);
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public int getAttribute() {
        return attribute;
    }

    public int getAttributeValue() {
        return attributeValue;
    }

    public ArrayList<Tree> getChildren() {
        return children;
    }

    public int getLabel() {
        return label;
    }
//    public void setParent(Tree parent){
//        this.parent = parent;
//    }

    public void printTree(String indent) {
        if (label != -1) {
            System.out.println(indent + "value = " + label);
        } else {
            System.out.println(indent + "When attribute " + (attribute + 1) + " has value " + attributeValue);
            for (Tree child : children) {
                child.printTree(indent + "      ");
            }
        }
    }
}