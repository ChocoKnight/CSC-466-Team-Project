import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class DataProcessor {
    public static ArrayList<PatientData> processHeartDiseaseData(String fileName) {

        ArrayList<PatientData> patientDataArrayList = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File(fileName));

            scanner.nextLine();

            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] lineSplit = line.split(",");

                PatientData patientData = new PatientData();

                patientData.setHasHeartDisease(Boolean.parseBoolean(lineSplit[0]));
                patientData.setBMI(Double.parseDouble(lineSplit[1]));
                patientData.setSmoking(Boolean.parseBoolean(lineSplit[2]));
                patientData.setDrinksAlcohol(Boolean.parseBoolean(lineSplit[3]));
                patientData.setHasHadStroke(Boolean.parseBoolean(lineSplit[4]));
                patientData.setPhysicalHealthDays(Double.parseDouble(lineSplit[5]));
                patientData.setMentalHealthDays(Double.parseDouble(lineSplit[6]));
                patientData.setHasDifficultyWalking(Boolean.parseBoolean(lineSplit[7]));
                patientData.setSex(lineSplit[8]);
                patientData.setAgeCategory(lineSplit[9]);
                patientData.setRace(lineSplit[10]);

                String word = lineSplit[11] + lineSplit[12];

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
                    } else {
                        patientData.setDiabetic(2);
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

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        return patientDataArrayList;
    }
}