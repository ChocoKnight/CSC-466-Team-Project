
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
    public void setChildren(ArrayList<Tree> children){
        this.children = children;
    }

    public int getLabel() {
        return label;
    }
//    public void setParent(Tree parent){
//        this.parent = parent;
//    }

    public void printWholeTree(){
        for (Tree firstLayerNode : children){
            firstLayerNode.printTreeHelper(0);
        }
    }

    public void printTreeHelper(int level) {
        if (label != -1) {
            for (int i=0; i<level; i++){
                System.out.print("      ");
            }
            System.out.println("value = " + label);
            return;
        }

        for (int i=0; i<level; i++){
            System.out.print("      ");
        }
        System.out.println("When attribute " + (attribute + 1) + " has value " + attributeValue);

        for (Tree node : children){
            node.printTreeHelper(level + 1);
        }
    }
}