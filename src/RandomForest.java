import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class RandomForest {
    
    public static void main(String[] args) {
        
        
        Matrix data = new Matrix(Lab7.process("../files/data.txt"));
        System.out.println(data);

        ArrayList<PatientData> patientData = DataProcessor.processHeartDiseaseData("../files/heart_2020_cleaned.csv");
        
        String[] allAttributes = DataProcessor.getDataAttibutes("../files/heart_2020_cleaned.csv");

        for (String attribute : allAttributes){
            System.out.println(attribute);
        }
        Tree decisionTree = Lab7.buildDecisionTree(data, Lab7.getAttributes(data), Lab7.getAllRows(data), 0, 100);
        System.out.println("");
        decisionTree.printWholeTree();


    }

}
