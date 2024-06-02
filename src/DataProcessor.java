import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class DataProcessor {
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

                patientData.setHasHeartDisease(Boolean.parseBoolean(lineSplit[0]));
                patientData.setBMI(Double.parseDouble(lineSplit[1]));
                patientData.setSmoking(Boolean.parseBoolean(lineSplit[2]));
                patientData.setDrinksAlcohol(Boolean.parseBoolean(lineSplit[3]));
                patientData.setHasHadStroke(Boolean.parseBoolean(lineSplit[4]));
                patientData.setBadPhysicalHealthLast30Days(Double.parseDouble(lineSplit[5]));
                patientData.setBadMentalHealthLast30Days(Double.parseDouble(lineSplit[6]));
                patientData.setHasDifficultyWalking(Boolean.parseBoolean(lineSplit[7]));
                patientData.setSex(lineSplit[8]);
                patientData.setAgeCategory(lineSplit[9]);
                patientData.setRace(lineSplit[10]);

                if((lineSplit[11] + lineSplit[12]).equals("\"No borderline diabetes\"")) {

                    patientData.setDiabetic(1);

                    patientData.setDoesPhysicalActivity(Boolean.parseBoolean(lineSplit[13]));
                    patientData.setGeneralHealth(lineSplit[14]);
                    patientData.setHoursSlept(Double.parseDouble(lineSplit[15]));
                    patientData.setHasAsthma(Boolean.parseBoolean(lineSplit[16]));
                    patientData.setHasKidneyDisease(Boolean.parseBoolean(lineSplit[17]));
                    patientData.setHasSkinCancer(Boolean.parseBoolean(lineSplit[18]));
                } else {

                    if(lineSplit[11].equals("No")) {
                        patientData.setDiabetic(0);
                    } else if(lineSplit[11].equals("Yes")){
                        patientData.setDiabetic(2);
                    } else {
                        patientData.setDiabetic(3);
                    }

                    patientData.setDoesPhysicalActivity(Boolean.parseBoolean(lineSplit[12]));
                    patientData.setGeneralHealth(lineSplit[13]);
                    patientData.setHoursSlept(Double.parseDouble(lineSplit[14]));
                    patientData.setHasAsthma(Boolean.parseBoolean(lineSplit[15]));
                    patientData.setHasKidneyDisease(Boolean.parseBoolean(lineSplit[16]));
                    patientData.setHasSkinCancer(Boolean.parseBoolean(lineSplit[17]));
                }

                patientDataArrayList.add(patientData);
            }

            scanner.close();

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        return patientDataArrayList;
    }

    public static Matrix turnPatientDataIntoMatrix(ArrayList<PatientData> patientDataArrayList) {
        ArrayList<ArrayList<String>> patientDataArrayListMatrix = new ArrayList<>();

        for(PatientData patientData : patientDataArrayList) {
            patientDataArrayListMatrix.add(PatientData.getPatientDataArrayList(patientData));
        }

        return new Matrix(patientDataArrayListMatrix);
    }


    public static String[] getDataAttibutes(String fileName) {

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
}
