import javax.xml.crypto.Data;
import java.util.ArrayList;

public class RandomForest {
    public static Matrix data;
    public static String[] allAttributes;
    public static ArrayList<Tree> forest;
    
    public static void main(String[] args) {
        
        // HYPERPARAMETERS
        int numTrees = 100;
        double percentDataPoints = 0.8;
        double percentAttributes = 0.8;

        data = new Matrix(Lab7.process("/./files/data.txt"));
        ArrayList<PatientData> patientDataObjs = DataProcessor.processHeartDiseaseData("../files/heart_2020_cleaned.csv");
        ArrayList<ArrayList<String>> patientDataArrays = generateFromPatientObjs(patientDataObjs);
//        data = new Matrix(patientDataArrays);         // uncomment if we want to use actual dataset instead of the lab7 dataset

        String[] allAttributes = DataProcessor.getDataAttibutes("../files/heart_2020_cleaned.csv");
        int attributeCount = (int) Math.floor(allAttributes.length * percentAttributes);
        System.out.println("Number of attributes: " + attributeCount);

        int length = DataProcessor.getLength("../files/heart_2020_cleaned.csv");
        int rowCount = (int) Math.floor(length * percentDataPoints);
        System.out.println("Number of data points: " + rowCount);

        for (String attribute : allAttributes){
            System.out.println(attribute);
        }

        Tree decisionTree = Lab7.buildDecisionTree(data, Lab7.getAttributes(data), Lab7.getAllRows(data), 0, 100);
        System.out.println("");
        //decisionTree.printWholeTree();

        forest = generateForest(numTrees, percentDataPoints, percentAttributes);

    }

    // convert arraylist of Patient Objs into an arrayList of Stringss
    public static ArrayList<ArrayList<String>> generateFromPatientObjs(ArrayList<PatientData> patientData){
        ArrayList<ArrayList<String>> arrayListData = new ArrayList<>();

        for (PatientData dataObj : patientData){
            arrayListData.add(PatientData.getPatientDataArrayList(dataObj));
        }

        return arrayListData;
    }

    public static ArrayList<Tree> generateForest(int numTrees, double percentDataPoints, double percentAttributes){
        ArrayList<Tree> newForest = new ArrayList<>();


        int attributeCount = (int) Math.floor(allAttributes.length * percentAttributes);           //get number of attributes to select

        // randomly select the attributes for each tree in the forest
        for (int i = 0; i < numTrees; i++) {
            ArrayList<Integer> randomAttributes = new ArrayList<>();
            for (int j = 0; j < attributeCount; j++) {
                randomAttributes.add((int) Math.floor(Math.random() * allAttributes.length));
            }
            System.out.println("Random attributes: " + randomAttributes);
        }

        // randomly select the sample for each tree in forest
        for (int i = 0; i < numTrees; i++) {
            ArrayList<Integer> randomDataPointRows = new ArrayList<>();
            for (int j = 0; j < (int) Math.floor(data.getMatrix().size() * percentDataPoints); j++) {
                randomDataPointRows.add((int) Math.floor(Math.random() * data.getMatrix().size()));
            }
            System.out.println("Random data points: " + randomDataPointRows);
        }

        for (int i = 0; i < numTrees; i++){

        }
        return newForest;

    }

}
