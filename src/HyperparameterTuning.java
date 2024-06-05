import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;

public class HyperparameterTuning {
    private final ArrayList<PatientData> patientDataObjs;
    private final String[] allAttributes;
    private double bestAccuracy;
    private int bestNumTrees;
    private double bestPercentDataPoints;
    private double bestPercentAttributes;

    public HyperparameterTuning(ArrayList<PatientData> patientDataObjs, String[] allAttributes) {
        this.patientDataObjs = patientDataObjs;
        this.allAttributes = allAttributes;
        this.bestAccuracy = 0.0;
        this.bestNumTrees = 0;
        this.bestPercentDataPoints = 0.0;
        this.bestPercentAttributes = 0.0;
    }

    public void performHyperParameterTuning() {
        splitDataAndTuneParameters();
        testBestModel();
    }

    private void splitDataAndTuneParameters() {
        ArrayList<PatientData> trainingData = new ArrayList<>();
        ArrayList<PatientData> validationData = new ArrayList<>();
        ArrayList<PatientData> testingData = new ArrayList<>();

        // Split patients with and without heart disease to have the same ratio
        ArrayList<PatientData> withHeartDisease = new ArrayList<>();
        ArrayList<PatientData> withoutHeartDisease = new ArrayList<>();

        for (PatientData patient : patientDataObjs) {
            if (patient.isHasHeartDisease()) {
                withHeartDisease.add(patient);
            } else {
                withoutHeartDisease.add(patient);
            }
        }
        splitData(withHeartDisease, trainingData, validationData, testingData);
        splitData(withoutHeartDisease, trainingData, validationData, testingData);
        // System.out.println(trainingData.size());
        // System.out.println(validationData.size());
        // System.out.println(testingData.size());
        Collections.shuffle(trainingData);
        Collections.shuffle(validationData);
        Collections.shuffle(testingData);

        Matrix trainingMatrix = DataProcessor.turnPatientDataIntoMatrix(trainingData, allAttributes);
        Matrix validationMatrix = DataProcessor.turnPatientDataIntoMatrix(validationData, allAttributes);
        Matrix testingMatrix = DataProcessor.turnPatientDataIntoMatrix(testingData, allAttributes);

        // Hyperparameter tuning
        int[] numTreesOptions = {400};
        double[] percentDataPointsOptions = {0.7};
        double[] percentAttributesOptions = {0.7};
        //accuracy is 0.789

        for (int numTrees : numTreesOptions) {
            for (double percentDataPoints : percentDataPointsOptions) {
                for (double percentAttributes : percentAttributesOptions) {
                    ArrayList<Tree> forest = RandomForest.generateForest(numTrees, percentDataPoints, percentAttributes, trainingMatrix);
                    double accuracy = validateModel(forest, validationMatrix);
                    if (accuracy > bestAccuracy) {
                        bestAccuracy = accuracy;
                        bestNumTrees = numTrees;
                        bestPercentDataPoints = percentDataPoints;
                        bestPercentAttributes = percentAttributes;
                    }
                }
            }
        }
    }

    private void splitData(ArrayList<PatientData> data, ArrayList<PatientData> trainingData, ArrayList<PatientData> validationData, ArrayList<PatientData> testingData) {
        Collections.shuffle(data);

        int trainSize = (int) (data.size() * 0.8);
        int validationSize = (int) (data.size() * 0.1);
        int testSize = data.size() - trainSize - validationSize;

        trainingData.addAll(data.subList(0, trainSize));
        validationData.addAll(data.subList(trainSize, trainSize + validationSize));
        testingData.addAll(data.subList(trainSize + validationSize, data.size()));
    }

    private double validateModel(ArrayList<Tree> forest, Matrix validationMatrix) {
        int correctPredictions = 0;
        int hasheartdisease = 0;
        for (ArrayList<String> dataEntry : validationMatrix.getMatrix()) {
            String actualLabel = dataEntry.get(dataEntry.size() - 1); 
            String predictedLabel = RandomForest.predict(forest, dataEntry);
            if (predictedLabel == "true"){
                hasheartdisease++;
            }
            if (actualLabel.equals(predictedLabel)) {
                correctPredictions++;
            }
        }
        return (double) correctPredictions / validationMatrix.getMatrix().size();
    }

    private void testBestModel() {
        ArrayList<PatientData> testingData = new ArrayList<>();

        // Split patients with and without heart disease separately to maintain the ratio
        ArrayList<PatientData> withHeartDisease = new ArrayList<>();
        ArrayList<PatientData> withoutHeartDisease = new ArrayList<>();

        for (PatientData patient : patientDataObjs) {
            if (patient.isHasHeartDisease()) {
                withHeartDisease.add(patient);
            } else {
                withoutHeartDisease.add(patient);
            }
        }

        splitData(withHeartDisease, new ArrayList<>(), new ArrayList<>(), testingData);
        splitData(withoutHeartDisease, new ArrayList<>(), new ArrayList<>(), testingData);
        Collections.shuffle(testingData);

        Matrix testingMatrix = DataProcessor.turnPatientDataIntoMatrix(testingData, allAttributes);

        ArrayList<Tree> bestForest = RandomForest.generateForest(bestNumTrees, bestPercentDataPoints, bestPercentAttributes, testingMatrix);
        double testAccuracy = validateModel(bestForest, testingMatrix);
      
        HashMap<String, Integer> precisionRecallF1Score = RandomForest.forestTPFPTNFPTable(testingMatrix, bestForest);
        double precision = RandomForest.findPrecision(precisionRecallF1Score);
        double recall = RandomForest.findRecall(precisionRecallF1Score);

        System.out.println("Best Hyper Parameters:");
        System.out.println();
        System.out.println("Number of Trees: " + bestNumTrees);
        System.out.println("Percent Data Points: " + bestPercentDataPoints);
        System.out.println("Percent Attributes: " + bestPercentAttributes);
        System.out.println("Test Accuracy of Best Model: " + testAccuracy);
        System.out.println("Precision: " + precision);
        System.out.println("Recall: " + recall);
        System.out.println("F1 Score: " + RandomForest.findF1Score(precision, recall));

    }
}
