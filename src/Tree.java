
import java.util.ArrayList;
import java.util.HashMap;

public class Tree {
    private int attribute;
    private String attributeValue;
    private ArrayList<Tree> children;
//    private Tree parent;
    private String label;

    public Tree(int attribute, String attributeValue) {
        this.attribute = attribute;
        this.attributeValue = attributeValue;
        this.children = new ArrayList<>();
//        this.parent = null;
        this.label = null; // Unset label
    }

    public Tree(String label) {
        this.attribute = -1;
        this.attributeValue = null;
        this.children = new ArrayList<>();
        this.label = label; // Unset label
    }

    public void addChild(Tree child) {
        this.children.add(child);
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getAttribute() {
        return attribute;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public ArrayList<Tree> getChildren() {
        return children;
    }
    public void setChildren(ArrayList<Tree> children){
        this.children = children;
    }

    public String getLabel() {
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
        if (label != null) {
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