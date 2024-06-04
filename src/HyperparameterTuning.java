import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

public class HyperparameterTuning {
    private ArrayList<PatientData> patientDataObjs;
    private String[] allAttributes;
    private double bestF1Score;
    private int bestNumTrees;
    private double bestPercentDataPoints;
    private double bestPercentAttributes;

    public HyperparameterTuning(ArrayList<PatientData> patientDataObjs, String[] allAttributes) {
        this.patientDataObjs = patientDataObjs;
        this.allAttributes = allAttributes;
        this.bestF1Score = 0.0;
        this.bestNumTrees = 0;
        this.bestPercentDataPoints = 0.0;
        this.bestPercentAttributes = 0.0;
    }

    public void performHyperparameterTuning() {
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

        Matrix trainingMatrix = DataProcessor.turnPatientDataIntoMatrix(trainingData, allAttributes);
        Matrix validationMatrix = DataProcessor.turnPatientDataIntoMatrix(validationData, allAttributes);
        Matrix testingMatrix = DataProcessor.turnPatientDataIntoMatrix(testingData, allAttributes);

        // Hyperparameter tuning
        int[] numTreesOptions = {100};
        double[] percentDataPointsOptions = {0.4, 0.5, 0.6, 0.7, 0.8};
        double[] percentAttributesOptions = {0.5, 0.6, 0.7, 0.8, 0.9};

        for (int numTrees : numTreesOptions) {
            for (double percentDataPoints : percentDataPointsOptions) {
                for (double percentAttributes : percentAttributesOptions) {
                    ArrayList<Tree> forest = RandomForest.generateForest(numTrees, percentDataPoints, percentAttributes, trainingMatrix);
                    double f1Score = validateModel(forest, validationMatrix);
                    if (f1Score > bestF1Score) {
                        bestF1Score = f1Score;
                        bestNumTrees = numTrees;
                        bestPercentDataPoints = percentDataPoints;
                        bestPercentAttributes = percentAttributes;
                    }
                }
            }
        }

        System.out.println("Best Hyperparameters:");
        System.out.println("Number of Trees: " + bestNumTrees);
        System.out.println("Percent Data Points: " + bestPercentDataPoints);
        System.out.println("Percent Attributes: " + bestPercentAttributes);
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
        int truePositive = 0;
        int falsePositive = 0;
        int trueNegative = 0;
        int falseNegative = 0;

        for (ArrayList<String> dataEntry : validationMatrix.getMatrix()) {
            String actualLabel = dataEntry.get(dataEntry.size() - 1); // Assuming the last element is the label
            String predictedLabel = RandomForest.predict(forest, dataEntry);

            if (predictedLabel.equals("True")) {
                if (actualLabel.equals("True")) {
                    truePositive++;
                } else {
                    falsePositive++;
                }
            } else {
                if (actualLabel.equals("True")) {
                    falseNegative++;
                } else {
                    trueNegative++;
                }
            }
        }

        double precision = (truePositive + falsePositive) > 0 ? (double) truePositive / (truePositive + falsePositive) : 0.0;
        double recall = (truePositive + falseNegative) > 0 ? (double) truePositive / (truePositive + falseNegative) : 0.0;
        double f1Score = (precision + recall) > 0 ? 2 * ((precision * recall) / (precision + recall)) : 0.0;

        return f1Score;
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

        Matrix testingMatrix = DataProcessor.turnPatientDataIntoMatrix(testingData, allAttributes);

        ArrayList<Tree> bestForest = RandomForest.generateForest(bestNumTrees, bestPercentDataPoints, bestPercentAttributes, testingMatrix);
        double testF1Score = validateModel(bestForest, testingMatrix);
        System.out.println("Test F1 Score of Best Model: " + testF1Score);
    }
}
