import java.util.ArrayList;

public class RandomForest {
    
    public static void main(String[] args) {
        
        // HYPERPARAMETERS
        int numTrees = 100;
        double percentAttributes = 0.8;
        double percentDataPoints = 0.8;

        String[] allAttributes = DataProcessor.getDataAttibutes("../files/heart_2020_cleaned.csv");
        int attributeCount = (int) Math.floor(allAttributes.length * percentAttributes);
        System.out.println("Number of attributes: " + attributeCount);

        int length = DataProcessor.getLength("../files/heart_2020_cleaned.csv");
        int rowCount = (int) Math.floor(length * percentDataPoints);
        System.out.println("Number of data points: " + rowCount);


        Matrix data = new Matrix(Lab7.process("../files/data.txt"));

        ArrayList<PatientData> patientData = DataProcessor.processHeartDiseaseData("../files/heart_2020_cleaned.csv");
        



        for (String attribute : allAttributes){
            System.out.println(attribute);
        }


        for (int i = 0; i < numTrees; i++) {
            ArrayList<Integer> randomAttributes = new ArrayList<>();
            for (int j = 0; j < attributeCount; j++) {
                randomAttributes.add((int) Math.floor(Math.random() * allAttributes.length));
            }
            System.out.println("Random attributes: " + randomAttributes);
        }

        for (int i = 0; i < numTrees; i++) {
            ArrayList<Integer> randomDataPoints = new ArrayList<>();
            for (int j = 0; j < (int) Math.floor(data.getMatrix().size() * percentDataPoints); j++) {
                randomDataPoints.add((int) Math.floor(Math.random() * data.getMatrix().size()));
            }
            System.out.println("Random data points: " + randomDataPoints);
        }


        Tree decisionTree = Lab7.buildDecisionTree(data, Lab7.getAttributes(data), Lab7.getAllRows(data), 0, 100);
        System.out.println("");
        //decisionTree.printWholeTree();

    }

}
