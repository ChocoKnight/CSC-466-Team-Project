import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class DataProcessor {

    public static boolean parseBooleans(String bool) {
        return bool.equals("Yes");
    }

    public static ArrayList<PatientData> processHeartDiseaseData(String fileName) {

        ArrayList<PatientData> patientDataArrayList = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File(fileName));

            String[] attributes = scanner.nextLine().split(",");
            System.out.println(attributes);

            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] lineSplit = line.split(",");

                PatientData patientData = new PatientData();

                String smokes = lineSplit[2];

                patientData.setHasHeartDisease(parseBooleans(lineSplit[0]));
                patientData.setBMI(Double.parseDouble(lineSplit[1]));
                patientData.setSmoking(parseBooleans(lineSplit[2]));
                patientData.setDrinksAlcohol(parseBooleans(lineSplit[3]));
                patientData.setHasHadStroke(parseBooleans(lineSplit[4]));
                patientData.setBadPhysicalHealthLast30Days(Double.parseDouble(lineSplit[5]));
                patientData.setBadMentalHealthLast30Days(Double.parseDouble(lineSplit[6]));
                patientData.setHasDifficultyWalking(parseBooleans(lineSplit[7]));
                patientData.setSex(lineSplit[8]);
                patientData.setAgeCategory(lineSplit[9]);
                patientData.setRace(lineSplit[10]);

                if((lineSplit[11] + lineSplit[12]).equals("\"No borderline diabetes\"")) {

                    patientData.setDiabetic(1);

                    patientData.setDoesPhysicalActivity(parseBooleans(lineSplit[13]));
                    patientData.setGeneralHealth(lineSplit[14]);
                    patientData.setHoursSlept(Double.parseDouble(lineSplit[15]));
                    patientData.setHasAsthma(parseBooleans(lineSplit[16]));
                    patientData.setHasKidneyDisease(parseBooleans(lineSplit[17]));
                    patientData.setHasSkinCancer(parseBooleans(lineSplit[18]));
                } else {

                    if(lineSplit[11].equals("No")) {
                        patientData.setDiabetic(0);
                    } else if(lineSplit[11].equals("Yes")){
                        patientData.setDiabetic(2);
                    } else {
                        patientData.setDiabetic(3);
                    }

                    patientData.setDoesPhysicalActivity(parseBooleans(lineSplit[12]));
                    patientData.setGeneralHealth(lineSplit[13]);
                    patientData.setHoursSlept(Double.parseDouble(lineSplit[14]));
                    patientData.setHasAsthma(parseBooleans(lineSplit[15]));
                    patientData.setHasKidneyDisease(parseBooleans(lineSplit[16]));
                    patientData.setHasSkinCancer(parseBooleans(lineSplit[17]));
                }

                patientDataArrayList.add(patientData);
            }

            scanner.close();

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        return patientDataArrayList;
    }

    public static Matrix turnPatientDataIntoMatrix(ArrayList<PatientData> patientDataArrayList, String[] attributes) {
        ArrayList<ArrayList<String>> patientDataArrayListMatrix = new ArrayList<>();

        for(PatientData patientData : patientDataArrayList) {
            patientDataArrayListMatrix.add(PatientData.getPatientDataArrayList(patientData));
        }

        return new Matrix(patientDataArrayListMatrix, attributes);
    }

    

    public static String[] getDataAttributess(String fileName) {

        String[] attributes = new String[0];

        try {
            Scanner scanner = new Scanner(new File(fileName));

            attributes = scanner.nextLine().split(",");
            System.out.println(attributes);

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        return attributes;
    }

    public static int getLength(String fileName) {
        int length = 0;

        try {
            Scanner scanner = new Scanner(new File(fileName));

            while(scanner.hasNextLine()) {
                scanner.nextLine();
                length++;
            }

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        return length;
    }
}
