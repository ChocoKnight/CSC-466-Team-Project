import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Collections;

public class RandomForest {
    public static ArrayList<Tree> forest;
    
    public static void main(String[] args) {
        
        // HYPERPARAMETERS
        int numTrees = 100;
        double percentDataPoints = 0.8;
        double percentAttributes = 0.8;

        Matrix data = new Matrix(Lab7.process("../files/data.txt"), new String[0]);
        ArrayList<PatientData> patientDataObjs = DataProcessor.processHeartDiseaseData("../files/heart_2020_cleaned.csv");
        String[] allAttributes = DataProcessor.getDataAttributes("../files/heart_2020_cleaned.csv");
//        data = DataProcessor.turnPatientDataIntoMatrix(patientDataObjs, allAttributes);       // uncomment to use heart data and not lab7 data

        forest = generateForest(numTrees, percentDataPoints, percentAttributes, data);


        // below is probably not needed. safe to delete

        Tree decisionTree = Lab7.buildDecisionTree(data, Lab7.getAttributes(data), Lab7.getAllRows(data), 0, 100);
        //decisionTree.printWholeTree();

        //prolly just some code used to test or sth. not part of functionality
//        int attributeCount = (int) Math.floor(allAttributes.length * percentAttributes);
//        System.out.println("Number of attributes: " + attributeCount);
//
//        int length = DataProcessor.getLength("../files/heart_2020_cleaned.csv");
//        int rowCount = (int) Math.floor(length * percentDataPoints);
//        System.out.println("Number of data points: " + rowCount);

    }

    public static ArrayList<Tree> generateForest(int numTrees, double percentDataPoints, double percentAttributes, Matrix data){
        ArrayList<Tree> newForest = new ArrayList<>();

        for (int i = 0; i < numTrees; i++){
            ArrayList<Integer> randomAttributes = generateRandomAttributes(data, percentAttributes);
            ArrayList<Integer> randomRowNums = generateRandomRows(data, percentDataPoints, true);

            // make a new Matrix with the filtered rows and attributes
            Matrix filteredData = data.generateSubData(randomAttributes, randomRowNums);

            // make a tree using the filtered data
            Tree decisionTree = Lab7.buildDecisionTree(filteredData, Lab7.getAttributes(filteredData), Lab7.getAllRows(filteredData), 0, 100);

            // add it to forest
            newForest.add(decisionTree);
        }
        return newForest;
    }

    public static ArrayList<Integer> generateRandomAttributes(Matrix data, double percentAttributes){
        int totalAttributesCount = data.getAttributes().length;
        int attributeCount = (int) Math.floor(totalAttributesCount * percentAttributes);           //get number of attributes to select
        // randomly select the attributes for each tree in the forest
        ArrayList<Integer> randomAttributes = new ArrayList<>();
        for (int j = 0; j < attributeCount; j++) {
            int selectedAttribute = (int) Math.floor(Math.random() * (totalAttributesCount - 1));       // totalAttrCount - 1 because we want to add in the last index (the label) manually in the end.

            // loop to ensure no attribute is selected more than once since it wouldnt make sense
            while (randomAttributes.contains(selectedAttribute)){
                selectedAttribute = (int) Math.floor(Math.random() * (totalAttributesCount - 1));
            }
            randomAttributes.add(totalAttributesCount);         // add the index for the
        }
        randomAttributes.add(totalAttributesCount - 1);     // add the index with the label since we are only selecting attributes and the label is stored as one of the attributes in Matrix obj.
        Collections.sort(randomAttributes);     // sort the indexes we got
        return randomAttributes;
    }

    public static ArrayList<Integer> generateRandomRows (Matrix data, double percentDataPoints, boolean withoutReplacement){
        int length = data.getMatrix().size();
        int rowCount = (int) Math.floor(length * percentDataPoints);            // get number of rows to select

        // randomly select the sample for each tree in forest
        ArrayList<Integer> randomDataPointRows = new ArrayList<>();
        for (int j = 0; j < rowCount; j++) {
            int selectedRow = (int) Math.floor(Math.random() * data.getMatrix().size());
            // loop to ensure that no duplicate rowNums are present in the randomly selected rows (can make this an option for sampling with replacement)
            while (withoutReplacement && randomDataPointRows.contains(selectedRow)){
                selectedRow = (int) Math.floor(Math.random() * data.getMatrix().size());
            }
            randomDataPointRows.add(selectedRow);
        }

        return randomDataPointRows;
    }

}
