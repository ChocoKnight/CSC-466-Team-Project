import java.util.ArrayList;
import java.util.Collections;

public class CrossValidation {
    private int k;
    private RandomForest randomForest;
    private ArrayList<ArrayList<String>> patientDataArrays;  // List of patient data arrays

    public CrossValidation(int k, RandomForest randomForest, ArrayList<ArrayList<String>> patientDataArrays) {
        this.k = k;
        this.randomForest = randomForest;
        this.patientDataArrays = patientDataArrays;
    }

    public void performCrossValidation() {
        Collections.shuffle(patientDataArrays);  // Shuffle data before splitting

        int foldSize = patientDataArrays.size() / k;
        double totalAccuracy = 0.0;

        for (int i = 0; i < k; i++) {
            ArrayList<ArrayList<String>> trainingData = new ArrayList<>();
            ArrayList<ArrayList<String>> validationData = new ArrayList<>();

            // Create validation data for the i-th fold
            for (int j = 0; j < patientDataArrays.size(); j++) {
                if (j >= i * foldSize && j < (i + 1) * foldSize) {
                    validationData.add(patientDataArrays.get(j));
                } else {
                    trainingData.add(patientDataArrays.get(j));
                }
            }

            // Train the model on training data
            // randomForest.train(trainingData); //need to have this method Random Forest

            // Validate the model on validation data and calculate accuracy
            double accuracy = validateModel(validationData);
            totalAccuracy += accuracy;

            System.out.println("Fold " + (i + 1) + " Accuracy: " + accuracy);
        }

        double averageAccuracy = totalAccuracy / k;
        System.out.println("Average Accuracy: " + averageAccuracy);
    }

     private double validateModel(ArrayList<ArrayList<String>> validationData) {
//         int correctPredictions = 0;

//         for (ArrayList<String> patient : validationData) {
//             String actualLabel = patient.get(patient.size() - 1);  // Assuming the last element is the label
//             String predictedLabel = randomForest.predict(patient);  // Assuming predict() method

//             if (actualLabel.equals(predictedLabel)) {
//                 correctPredictions++;
//             }
//         }

//         return (double) correctPredictions / validationData.size();
         return 0;
     }
}

//Should work once we have a predict method
