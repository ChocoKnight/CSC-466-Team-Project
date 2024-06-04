
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Tree {
    private int attribute;
    private String attributeValue;
    private String[] attributeNames;
    private ArrayList<Tree> children;
    private String label;

    public Tree(int attribute, String attributeValue, String[] attributeNames) {
        this.attribute = attribute;
        this.attributeValue = attributeValue;
        this.attributeNames = attributeNames;
        this.children = new ArrayList<>();
        this.label = null; // Unset label
    }

    public Tree(String label, String[] attributeNames) {
        this.attribute = -1;
        this.attributeValue = null;
        this.attributeNames = attributeNames;
        this.children = new ArrayList<>();
        this.label = label;
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
//        return PatientData.getPatientDataArrayListIndexCategoryName(attribute) + " = " + attributeValue;
        if (attribute < 0){
            return "Invalid Index";
        }
        return this.attributeNames[attribute] + " = " + attributeValue;
    }

    public String predict(ArrayList<String> input){
        // either root or label node
        if (this.attribute == -1){
            if (this.label == null){
                return "root";
            } else {
                return this.label;
            }
        }

        for (Tree child : children){
            int attributeIdxTree = child.attribute;

            // get the index of the attribute we are looking at as it was in original dataset before sampling and filtering features
            int attributeIdxOriginal = PatientData.getAttributeIdx(this.attributeNames[attributeIdxTree]);

            // if the attribute value of the input matches the val of the node we are looking at, recurse
            if (this.attributeValue.equals(input.get(attributeIdxOriginal))){
                return child.predict(input);
            }
        }
        return "I dunno o7";
    }
}