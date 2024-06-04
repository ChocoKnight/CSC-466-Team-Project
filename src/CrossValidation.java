import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CrossValidation {
    private ArrayList<ArrayList<String>> patientDataArrays;
    private RandomForest bestRandomForest;
    private int bestNumTrees;
    private double bestPercentDataPoints;
    private double bestPercentAttributes;

    public CrossValidation(ArrayList<ArrayList<String>> patientDataArrays) {
        this.patientDataArrays = patientDataArrays;
    }

    public void performCrossValidation() {
        // Split the data
        DataSplits dataSplits = splitData(patientDataArrays, 0.8, 0.1, 0.1);
        ArrayList<ArrayList<String>> trainingData = dataSplits.trainingData;
        ArrayList<ArrayList<String>> validationData = dataSplits.validationData;
        ArrayList<ArrayList<String>> testingData = dataSplits.testingData;

        // Hyperparameter ranges
        int[] numTreesOptions = {100, 200, 500};
        double[] percentDataPointsOptions = {0.6, 0.8, 1.0};
        double[] percentAttributesOptions = {0.6, 0.8, 1.0};

        double bestValidationAccuracy = 0.0;

        // ArrayList<PatientData> patientDataObjs = DataProcessor.processHeartDiseaseData("files/heart_2020_cleaned.csv");
        // ArrayList<PatientData> sublist = new ArrayList<>(patientDataObjs.subList(0, 2000));
        // String[] allAttributes = PatientData.attributes();
        // Matrix data = DataProcessor.turnPatientDataIntoMatrix(sublist, allAttributes);  

        for (int numTrees : numTreesOptions) {
            for (double percentDataPoints : percentDataPointsOptions) {
                for (double percentAttributes : percentAttributesOptions) {
                    RandomForest randomForest = new RandomForest();
                    randomForest.forest = RandomForest.generateForest(numTrees, percentDataPoints, percentAttributes, trainingData); //need to figure out how to create the traning data in the right format
                    double accuracy = validateModel(randomForest, validationData);
                    if (accuracy > bestValidationAccuracy) {
                        bestValidationAccuracy = accuracy;
                        bestNumTrees = numTrees;
                        bestPercentDataPoints = percentDataPoints;
                        bestPercentAttributes = percentAttributes;
                        bestRandomForest = randomForest;
                    }
                }
            }
        }

        // Evaluate the best model on the testing data
        double testAccuracy = validateModel(bestRandomForest, testingData);
        System.out.println("Best Model Parameters:");
        System.out.println("Number of Trees: " + bestNumTrees);
        System.out.println("Percent Data Points: " + bestPercentDataPoints);
        System.out.println("Percent Attributes: " + bestPercentAttributes);
        System.out.println("Test Accuracy: " + testAccuracy);
    }

    private DataSplits splitData(ArrayList<ArrayList<String>> data, double trainRatio, double valRatio, double testRatio) {
        ArrayList<ArrayList<String>> trainingData = new ArrayList<>();
        ArrayList<ArrayList<String>> validationData = new ArrayList<>();
        ArrayList<ArrayList<String>> testingData = new ArrayList<>();

        ArrayList<ArrayList<String>> hasDisease = new ArrayList<>();
        ArrayList<ArrayList<String>> noDisease = new ArrayList<>();

        // Split data into has disease and no disease
        for (ArrayList<String> patient : data) {
            if (patient.get(patient.size() - 1).equals("True")) {
                hasDisease.add(patient);
            } else {
                noDisease.add(patient);
            }
        }

        // Split each group into training, validation, and testing sets
        addToSplits(hasDisease, trainRatio, valRatio, testRatio, trainingData, validationData, testingData);
        addToSplits(noDisease, trainRatio, valRatio, testRatio, trainingData, validationData, testingData);

        Collections.shuffle(trainingData);
        Collections.shuffle(validationData);
        Collections.shuffle(testingData);

        return new DataSplits(trainingData, validationData, testingData);
    }

    private void addToSplits(ArrayList<ArrayList<String>> data, double trainRatio, double valRatio, double testRatio,
                             ArrayList<ArrayList<String>> trainingData, ArrayList<ArrayList<String>> validationData,
                             ArrayList<ArrayList<String>> testingData) {
        int trainSize = (int) (data.size() * trainRatio);
        int valSize = (int) (data.size() * valRatio);

        for (int i = 0; i < data.size(); i++) {
            if (i < trainSize) {
                trainingData.add(data.get(i));
            } else if (i < trainSize + valSize) {
                validationData.add(data.get(i));
            } else {
                testingData.add(data.get(i));
            }
        }
    }

    private double validateModel(RandomForest randomForest, ArrayList<ArrayList<String>> validationData) {
        int correctPredictions = 0;

        for (ArrayList<String> patient : validationData) {
            String actualLabel = patient.get(patient.size() - 1);
            String predictedLabel = randomForest.predict(randomForest.forest, patient);

            if (actualLabel.equals(predictedLabel)) {
                correctPredictions++;
            }
        }

        return (double) correctPredictions / validationData.size();
    }

    private static class DataSplits {
        ArrayList<ArrayList<String>> trainingData;
        ArrayList<ArrayList<String>> validationData;
        ArrayList<ArrayList<String>> testingData;

        public DataSplits(ArrayList<ArrayList<String>> trainingData, ArrayList<ArrayList<String>> validationData, ArrayList<ArrayList<String>> testingData) {
            this.trainingData = trainingData;
            this.validationData = validationData;
            this.testingData = testingData;
        }
    }
}
