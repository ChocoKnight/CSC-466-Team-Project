
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Tree {
    private int attribute;
    private String attributeValue;
    private ArrayList<Tree> children;
    private String label;

    public Tree(int attribute, String attributeValue) {
        this.attribute = attribute;
        this.attributeValue = attributeValue;
        this.children = new ArrayList<>();
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
        System.out.println("When attribute " + (attribute) + " has value " + attributeValue);

        for (Tree node : children){
            node.printTreeHelper(level + 1);
        }
    }

    public static void printTree(Tree node, String prefix, boolean isLast) {
        if (node == null) return;

        if(node.attribute == -1 && node.label == null) {
            System.out.println("Root");
        } else {
            System.out.print(prefix);
            if (isLast) {
                System.out.print("└── ");
                prefix += "    ";
            } else {
                System.out.print("├── ");
                prefix += "│   ";
            }
            System.out.println(node.toString());
        }

        for (int i = 0; i < node.getChildren().size(); i++) {
            Tree child = node.getChildren().get(i);
            printTree(child, prefix, i == node.getChildren().size() - 1);
        }
    }

    @Override
    public String toString() {
        if (label != null) {
            return "Heart Disease = " + label;
        }
        return PatientData.getPatientDataArrayListIndexCategoryName(attribute) + " = " + attributeValue;
    }
}