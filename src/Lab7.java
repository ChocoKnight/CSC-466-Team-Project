
import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.*;

public class Lab7 {
    public static void main (String[] args){
//        Matrix data = new Matrix(process("files/data.txt"));
//        Matrix data = new Matrix(process("D:\\College\\Com Sci\\Junior Year\\466\\CSC-466-Team-Project\\files\\data.txt"));
//        System.out.println(data);

        ArrayList<PatientData> patientData = DataProcessor.processHeartDiseaseData("files/heart_2020_cleaned.csv");
        ArrayList<PatientData> sublist = new ArrayList<>(patientData.subList(0, 2000));

        String[] allAttributes = DataProcessor.getDataAttributes("files/heart_2020_cleaned.csv");
        Matrix patientMatrix = DataProcessor.turnPatientDataIntoMatrix(sublist, PatientData.attributes());
//        Matrix patientMatrix = DataProcessor.turnPatientDataIntoMatrix(patientData, allAttributes);

//        Tree decisionTree = buildDecisionTree(data, getAttributes(data), getAllRows(data), 0, 100);
        System.out.println();
//        decisionTree.printWholeTree();

        Tree patientTree = buildDecisionTree(patientMatrix, getAttributes(patientMatrix), getAllRows(patientMatrix), 0, 100);
        System.out.println();
//        patientTree.printWholeTree();

        Tree.printTree(patientTree, "", true);
    }

    public static ArrayList<ArrayList<String>> process (String filePath){
        ArrayList<ArrayList<String>> result = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null)
            {
                String[] splitLine = line.split(",");
                ArrayList<String> newEntry = new ArrayList<>();
                for (String elem : splitLine){
                    Double value = Double.parseDouble(elem);
                    Integer flooredVal = (int) Math.floor(value);
                    newEntry.add(flooredVal.toString());
                }
                result.add(newEntry);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public static Tree buildDecisionTree(Matrix data, ArrayList<Integer> attributes, ArrayList<Integer> rows, int level, double currentIGR){
        Tree newTree = new Tree(-1, null);
        newTree.setChildren(printDecisionTree(data, attributes, rows, level, currentIGR));
        return newTree;
    }
//    // returns a list of children for a tree layer
//    public static ArrayList<Tree> printDecisionTreeOriginal(Matrix data, ArrayList<Integer> attributes, ArrayList<Integer> rows, int level, double currentIGR){
//        ArrayList<Tree> children = new ArrayList<>();
////        Tree treeNode = null;
//        String tabs = "";
//        for (int i=0; i<level; i++) {
//            tabs.concat("\t");
//        }
//        int totalNumAttributes = data.getMatrix().get(0).size() - 1;
//        ArrayList<Double> IGRPerAttribute = new ArrayList<>(totalNumAttributes);
//        for (int i=0; i<totalNumAttributes; i++){
//            IGRPerAttribute.add((double) -1);
//        }
//
//        if (attributes.size()>0){
//
//            for (Integer attribute : attributes){
//                double IGR = data.computeIGR(attribute, rows);      // compute the IGR if you were to split on this attribute for the given rows
//                IGRPerAttribute.set(attribute, IGR);    // add this IGR to the arrayList where index 0 represents the IGR of "attribute 1" (technically attribute 0 in data but printed as attribute 1)
//            }
//            double maxIGR = Collections.max(IGRPerAttribute);       // find best attribute to split on based on their IGRs
//            if (maxIGR >= 0.01 && Math.abs(maxIGR - currentIGR) >= 0.01) {     // only split if the change in IGR is more than 0.01
//                int attributeToSplit = IGRPerAttribute.indexOf(maxIGR);
//                HashMap<Integer, ArrayList<Integer>> splitRows = data.split(attributeToSplit, rows);        // split the data based on attribute and store in map where map[attributeValue] = {rows related}
//
//                for (Map.Entry<Integer, ArrayList<Integer>> node : splitRows.entrySet()) {      // loop thorugh map[attributeValue] = {rows related} to go through all the nodes formed from split
//                    for (int i=0; i<level; i++){
//                        System.out.print("      ");
//                    }
//                    int attributeToSplitPrint = attributeToSplit + 1;
//                    System.out.println(tabs + "When attribute " + attributeToSplitPrint + " has value " + node.getKey());
//                    // this stores the ACTUAL attribute number index (the column)
//                    // for example, when we print the attribute in column index 2, we print out "attribute 3". This stores the 2 to keep it consistent
//                    Tree treeNode = new Tree(attributeToSplit, node.getKey());     //make a new treeNode for this attribute and attributeVal
//                    children.add(treeNode);
//
//
//                    // make a copy of attributes list and remove the attribute we split on from it.
//                    ArrayList<Integer> newAttributes = (ArrayList<Integer>) attributes.clone();
//                    Integer idxToRemove = newAttributes.indexOf(attributeToSplit);
//                    newAttributes.remove(idxToRemove);
//
//                    // recursively call the printDecisionTree with the attribute split on being removed, and the rows of the attribute cateory we are currently printing subtree of.
//                    // stores the children of that node we are currently splitting on
//                    treeNode.setChildren(printDecisionTree(data, newAttributes, node.getValue(), level + 1, maxIGR));
////                    }
//                }
//            }
//            else{
//                for (int i=0; i<level; i++){
//                    System.out.print("      ");
//                }
//                int label = data.findMostCommonValue(rows, totalNumAttributes);
//                System.out.println(tabs + "value = " + label);
//
//                Tree treeNode = new Tree(label);
//                children.add(treeNode);
//            }
//        }
//        return children;
//
//    }

    public static ArrayList<Tree> printDecisionTree(Matrix data, ArrayList<Integer> attributes, ArrayList<Integer> rows, int level, double currentIGR){
        ArrayList<Tree> children = new ArrayList<>();
//        Tree treeNode = null;
        String tabs = "";
        for (int i=0; i<level; i++) {
            tabs.concat("\t");
        }
        int totalNumAttributes = data.getMatrix().get(0).size() - 1;
        ArrayList<Double> IGRPerAttribute = new ArrayList<>(totalNumAttributes);
        for (int i=0; i<totalNumAttributes; i++){
            IGRPerAttribute.add((double) -1);
        }

        if (attributes.size()>0){

            for (Integer attribute : attributes){
                double IGR = data.computeIGR(attribute, rows);      // compute the IGR if you were to split on this attribute for the given rows
                IGRPerAttribute.set(attribute, IGR);    // add this IGR to the arrayList where index 0 represents the IGR of "attribute 1" (technically attribute 0 in data but printed as attribute 1)

                if(attribute == 4 || attribute == 5) {
                    IGRPerAttribute.set(attribute, -1.0);
                }
            }

            double maxIGR = Collections.max(IGRPerAttribute);       // find best attribute to split on based on their IGRs
            if (maxIGR >= 0.01 && Math.abs(maxIGR - currentIGR) >= 0.01) {     // only split if the change in IGR is more than 0.01
                int attributeToSplit = IGRPerAttribute.indexOf(maxIGR);
                HashMap<String, ArrayList<Integer>> splitRows = data.split(attributeToSplit, rows);        // split the data based on attribute and store in map where map[attributeValue] = {rows related}

                for (Map.Entry<String, ArrayList<Integer>> node : splitRows.entrySet()) {      // loop thorugh map[attributeValue] = {rows related} to go through all the nodes formed from split
                    for (int i=0; i<level; i++){
                        System.out.print("      ");
                    }
                    int attributeToSplitPrint = attributeToSplit + 1;
                    System.out.println(tabs + "When attribute " + PatientData.getPatientDataArrayListIndexCategoryName(attributeToSplit) + " has value " + node.getKey());
                    // this stores the ACTUAL attribute number index (the column)
                    // for example, when we print the attribute in column index 2, we print out "attribute 3". This stores the 2 to keep it consistent
                    Tree treeNode = new Tree(attributeToSplit, node.getKey());     //make a new treeNode for this attribute and attributeVal
                    children.add(treeNode);


                    // make a copy of attributes list and remove the attribute we split on from it.
                    ArrayList<Integer> newAttributes = (ArrayList<Integer>) attributes.clone();
                    Integer idxToRemove = newAttributes.indexOf(attributeToSplit);
                    newAttributes.remove(idxToRemove);

                    // recursively call the printDecisionTree with the attribute split on being removed, and the rows of the attribute cateory we are currently printing subtree of.
                    // stores the children of that node we are currently splitting on
                    treeNode.setChildren(printDecisionTree(data, newAttributes, node.getValue(), level + 1, maxIGR));
//                    }
                }
            }
            else{
                for (int i=0; i<level; i++){
                    System.out.print("      ");
                }
                String label = data.findMostCommonValue(rows, totalNumAttributes);
                System.out.println(tabs + "Heart Disease = " + label);

                Tree treeNode = new Tree(label);
                children.add(treeNode);
            }
        }
        return children;
    }

    public static ArrayList<Integer> getAttributes(Matrix data){
        ArrayList<Integer> attributes = new ArrayList<>();
        for (int i=0 ; i<data.getMatrix().get(0).size() - 1; i++){
            attributes.add(i);
        }
        return attributes;
    }

    public static ArrayList<Integer> getAllRows(Matrix data){
        ArrayList<Integer> rows = new ArrayList<>();
        for (int i=0 ; i<data.getMatrix().size(); i++){
            rows.add(i);
        }
        return rows;
    }

}
