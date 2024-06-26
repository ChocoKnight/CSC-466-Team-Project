import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RandomForest {
    public static ArrayList<Tree> forest;

    public static void main(String[] args) {
        ArrayList<PatientData> patientDataObjs = DataProcessor.processHeartDiseaseData("files/heart_2020_cleaned.csv");

        int totalPatients = 20000; // example value
        ArrayList<PatientData> balancedData = createBalancedPatientDataArray(patientDataObjs, totalPatients);

        String[] allAttributes = PatientData.attributes();
        Matrix balancedMatrix = DataProcessor.turnPatientDataIntoMatrix(balancedData, allAttributes);

        Tree patientTree = Lab7.buildDecisionTree(balancedMatrix, Lab7.getAttributes(balancedMatrix), Lab7.getAllRows(balancedMatrix), 0, 100);

        System.out.println();
        Tree.printTree(patientTree, "", false);

        // Perform hyper parameter tuning and testing
        HyperparameterTuning hyperparameterTuning = new HyperparameterTuning(balancedData, allAttributes);
        hyperparameterTuning.performHyperParameterTuning();
    }

    public static ArrayList<PatientData> createBalancedPatientDataArray(ArrayList<PatientData> patientDataObjs, int totalPatients) {
        ArrayList<PatientData> withHeartDisease = new ArrayList<>();
        ArrayList<PatientData> withoutHeartDisease = new ArrayList<>();

        for (PatientData patient : patientDataObjs) {
            if (patient.isHasHeartDisease()) {
                withHeartDisease.add(patient);
            } else {
                withoutHeartDisease.add(patient);
            }
        }

        Collections.shuffle(withHeartDisease);
        Collections.shuffle(withoutHeartDisease);

        int numberOfPatientsPerGroup = totalPatients / 2;

        numberOfPatientsPerGroup = Math.min(numberOfPatientsPerGroup, withHeartDisease.size());
        numberOfPatientsPerGroup = Math.min(numberOfPatientsPerGroup, withoutHeartDisease.size());

        ArrayList<PatientData> balancedData = new ArrayList<>();
        balancedData.addAll(withHeartDisease.subList(0, numberOfPatientsPerGroup));
        balancedData.addAll(withoutHeartDisease.subList(0, numberOfPatientsPerGroup));

        Collections.shuffle(balancedData);

        return balancedData;
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
            randomAttributes.add(selectedAttribute);
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
            while (withoutReplacement && randomDataPointRows.contains(selectedRow)){        // if withoutReplacement is set to false, this loop is skipped everytime so we can have duplicate rows in sample (sampling with replacement)
                selectedRow = (int) Math.floor(Math.random() * data.getMatrix().size());
            }
            randomDataPointRows.add(selectedRow);
        }

        return randomDataPointRows;
    }

    public static String predict(ArrayList<Tree> forest, ArrayList<String> patient){
        ArrayList<String> predictions = new ArrayList<>();
        for (Tree tree : forest){
            String prediction = tree.predict(patient);
            predictions.add(prediction);
        }

        return findMostCommonPrediction(predictions);
    }

    public static String findMostCommonPrediction(ArrayList<String> predictions){
        HashMap<String, Integer> predictionCounts = new HashMap<>();

        for (String prediction : predictions){
            predictionCounts.put(prediction, predictionCounts.getOrDefault(prediction, 0) + 1);
        }

        return Collections.max(predictionCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public static HashMap<String, Integer> forestTPFPTNFPTable(Matrix data, ArrayList<Tree> forest) {
        HashMap<String, Integer> forestResult = new HashMap<>();
        forestResult.put("TruePositive", 0);
        forestResult.put("FalsePositive", 0);
        forestResult.put("TrueNegative", 0);
        forestResult.put("FalseNegative", 0);
        forestResult.put("NoResult", 0);

        for(ArrayList<String> dataEntry : data.getMatrix()) {
            String result = getResult(forest, dataEntry);

            forestResult.replace(result, forestResult.get(result) + 1);
        }

        return forestResult;
    }

    private static String getResult(ArrayList<Tree> forest, ArrayList<String> dataEntry) {
        String forestPrediction = RandomForest.predict(forest, dataEntry);

        String result = "";
        if(forestPrediction.equals("true")) {
            if(dataEntry.get(dataEntry.size() - 1).equals("true")) {
                // TP
                result = "TruePositive";
            } else{
                // FP
                result = "FalsePositive";
            }
        } else if (forestPrediction.equals("false")){
            if(dataEntry.get(dataEntry.size() - 1).equals("true")) {
                // FN
                result = "FalseNegative";
            } else {
                // TN
                result = "TrueNegative";
            }
        } else {
            result = "NoResult";
        }
        return result;
    }

    public static double findPrecision(HashMap<String, Integer> forestTPFPTNFPTable) {
        return (double) forestTPFPTNFPTable.get("TruePositive") / (forestTPFPTNFPTable.get("TruePositive") + forestTPFPTNFPTable.get("FalsePositive"));
    }

    public static double findRecall(HashMap<String, Integer> forestTPFPTNFPTable) {
        return (double) forestTPFPTNFPTable.get("TruePositive") / (forestTPFPTNFPTable.get("TruePositive") + forestTPFPTNFPTable.get("FalseNegative"));
    }

    public static double findF1Score(double precision, double recall) {
        double b = 1;
        return ((1 + Math.pow(b, 2)) * precision * recall) / (Math.pow(b, 2) * precision + recall);
    }
}
