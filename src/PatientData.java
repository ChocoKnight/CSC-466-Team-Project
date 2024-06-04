import java.util.ArrayList;
import java.util.Arrays;

public class PatientData {

    private boolean isHasHeartDisease;
    private double BMI;
    private boolean isSmoking;
    private boolean isDrinksAlcohol;
    private boolean isHasHadStroke;
    private double badPhysicalHealthLast30Days;
    private double badMentalHealthLast30Days;
    private boolean isHasDifficultyWalking;
    private String sex;
    private String ageCategory;
    private String race;
    private int isDiabetic;
    private boolean isDoesPhysicalActivity;
    private String generalHealth;
    private double hoursSlept;
    private boolean isHasAsthma;
    private boolean isHasKidneyDisease;
    private boolean isHasSkinCancer;

    public PatientData() {
        this.isHasHeartDisease = false;
        this.BMI = 0;
        this.isSmoking = false;
        this.isDrinksAlcohol = false;
        this.isHasHadStroke = false;
        this.badPhysicalHealthLast30Days = 0;
        this.badMentalHealthLast30Days = 0;
        this.isHasDifficultyWalking = false;
        this.sex = "";
        this.ageCategory = "";
        this.race = "";
        this.isDiabetic = 0; //0 - no, 1 - almost diabetic, 2 - diabetic, 3 - diabetic during pregnancy
        this.isDoesPhysicalActivity = false;
        this.generalHealth = "";
        this.hoursSlept = 0;
        this.isHasAsthma = false;
        this.isHasKidneyDisease = false;
        this.isHasSkinCancer = false;
    }

    public String getBMICategory() {
        if(this.BMI < 18.5) {
            return "Underweight (< 18.5)";
        } else if (this.BMI < 25) {
            return "Optimum Range (18.5 - 24.9)";
        } else if (this.BMI < 30) {
            return "Overweight (25 - 29.9)";
        } else if (this.BMI < 35) {
            return "Class I Obesity (30 - 34.9)";
        } else if (this.BMI < 40) {
            return "Class II Obesity (35 - 39.9)";
        } else {
            return "Class III Obesity (> 40)";
        }
    }

    public String getBadPhysicalHealthLast30DaysCategory() {
        if(this.badPhysicalHealthLast30Days == 0) {
            return "No Days";
        } else if (this.badPhysicalHealthLast30Days > 0 && this.badPhysicalHealthLast30Days < 3) {
            return "Couple Days (0 - 2)";
        } else if (this.badPhysicalHealthLast30Days >= 3 && this.badPhysicalHealthLast30Days < 8) {
            return "Few Days (3 - 7)";
        } else if (this.badPhysicalHealthLast30Days >= 8 && this.badPhysicalHealthLast30Days < 15) {
            return "Many Days (8 - 14)";
        } else {
            return "Most Days (15 - 30)";
        }
    }

    public String getBadMentalHealthLast30DaysCategory() {
        if(this.badMentalHealthLast30Days == 0) {
            return "No Days";
        } else if (this.badMentalHealthLast30Days > 0 && this.badMentalHealthLast30Days < 3) {
            return "Couple Days (0 - 2)";
        } else if (this.badMentalHealthLast30Days >= 3 && this.badMentalHealthLast30Days < 8) {
            return "Few Days (3 - 7)";
        } else if (this.badMentalHealthLast30Days >= 8 && this.badMentalHealthLast30Days < 15) {
            return "Many Days (8 - 14)";
        } else {
            return "Most Days (15 - 30)";
        }
    }

    public String getDiabeticCategory() {
        if(this.isDiabetic == 0) {
            return "Not Diabetic";
        } else if (this.isDiabetic == 1) {
            return "Almost Diabetic";
        } else if (this.isDiabetic == 2) {
            return "Is Diabetic";
        } else {
            return "Is Diabetic (During Pregnancy)";
        }
    }

    public String getHoursSleptCategory() {
        if(this.hoursSlept < 5) {
            return "Less than 5 Hours";
        } else if (this.hoursSlept >= 5 && this.hoursSlept < 7) {
            return "Between 5 and 7 Hours";
        } else if (this.hoursSlept >= 7 && this.hoursSlept < 9) {
            return "Between 7 and 9 Hours";
        } else if (this.hoursSlept >= 9 && this.hoursSlept < 11) {
            return "Between 9 and 11 Hours";
        } else {
            return "11 or More Hours";
        }
    }

    public static ArrayList<String> getPatientDataArrayList(PatientData patientData) {
        ArrayList<String> patientDataArrayList = new ArrayList<>();

        patientDataArrayList.add(patientData.getBMICategory());
        patientDataArrayList.add(Boolean.toString(patientData.isSmoking()));
        patientDataArrayList.add(Boolean.toString(patientData.isDrinksAlcohol()));
        patientDataArrayList.add(Boolean.toString(patientData.isHasHadStroke()));
        patientDataArrayList.add(patientData.getBadPhysicalHealthLast30DaysCategory());
        patientDataArrayList.add(patientData.getBadMentalHealthLast30DaysCategory());
        patientDataArrayList.add(Boolean.toString(patientData.isHasDifficultyWalking()));
        patientDataArrayList.add(patientData.getSex());
        patientDataArrayList.add(patientData.getAgeCategory());
        patientDataArrayList.add(patientData.getRace());
        patientDataArrayList.add(patientData.getDiabeticCategory());
        patientDataArrayList.add(Boolean.toString(patientData.isDoesPhysicalActivity()));
        patientDataArrayList.add(patientData.getGeneralHealth());
        patientDataArrayList.add(patientData.getHoursSleptCategory());
        patientDataArrayList.add(Boolean.toString(patientData.isHasAsthma()));
        patientDataArrayList.add(Boolean.toString(patientData.isHasKidneyDisease()));
        patientDataArrayList.add(Boolean.toString(patientData.isHasSkinCancer()));
        patientDataArrayList.add(Boolean.toString(patientData.isHasHeartDisease()));

        return patientDataArrayList;
    }

    public static String[] attributes() {
        return new String[]{"BMI", "Smokes", "Drinks Alcohol",
                "Has a Stroke", "Over the Last 30 Days had bad Physical Health",
                "Over the Last 30 Days had bad Mental Health", "Has Difficulty Walking",
                "Sex", "Age Range", "Race", "Is Diabetic", "Does Physical Activity",
                "General Health", "Hours Slept", "Has Asthma", "Has Kidney Disease",
                "Has Skin Cancer", "Has Heart Disease"};
    }

    public static int getAttributeIdx(String attributeName){
        String[] allAttributes = attributes();
        return Arrays.asList(allAttributes).indexOf(attributeName);
    }

    public static String getPatientDataArrayListIndexCategoryName(int index) {
        if(index < 0 || index > 17) {
            return "Invalid Index";
        } else if (index == 0) {
            return "BMI";
        } else if (index == 1) {
            return "Smokes";
        } else if (index == 2) {
            return "Drinks Alcohol";
        } else if (index == 3) {
            return "Had a Stroke";
        } else if (index == 4) {
            return "Over the Last 30 Days had bad Physical Health";
        } else if (index == 5) {
            return "Over the Last 30 Days had bad Mental Health";
        } else if (index == 6) {
            return "Has Difficulty Walking";
        } else if (index == 7) {
            return "Sex";
        } else if (index == 8) {
            return "Age Range";
        } else if (index == 9) {
            return "Race";
        } else if (index == 10) {
            return "Is Diabetic";
        } else if (index == 11) {
            return "Does Physical Activity";
        } else if (index == 12) {
            return "General Health";
        } else if (index == 13) {
            return "Hours Slept";
        } else if (index == 14) {
            return "Has Asthma";
        } else if (index == 15) {
            return "Has Kidney Disease";
        } else if (index == 16) {
            return "Has Skin Cancer";
        } else { // index 17
            return "Has Heart Disease";
        }
    }

    //region Getters and Setters
    public boolean isHasHeartDisease() {
        return isHasHeartDisease;
    }

    public void setHasHeartDisease(boolean hasHeartDisease) {
        isHasHeartDisease = hasHeartDisease;
    }

    public double getBMI() {
        return BMI;
    }

    public void setBMI(double BMI) {
        this.BMI = BMI;
    }

    public boolean isSmoking() {
        return isSmoking;
    }

    public void setSmoking(boolean smoking) {
        isSmoking = smoking;
    }

    public boolean isDrinksAlcohol() {
        return isDrinksAlcohol;
    }

    public void setDrinksAlcohol(boolean drinksAlcohol) {
        isDrinksAlcohol = drinksAlcohol;
    }

    public boolean isHasHadStroke() {
        return isHasHadStroke;
    }

    public void setHasHadStroke(boolean hasHadStroke) {
        isHasHadStroke = hasHadStroke;
    }

    public double getBadPhysicalHealthLast30Days() {
        return badPhysicalHealthLast30Days;
    }

    public void setBadPhysicalHealthLast30Days(double badPhysicalHealthLast30Days) {
        this.badPhysicalHealthLast30Days = badPhysicalHealthLast30Days;
    }

    public double getBadMentalHealthLast30Days() {
        return badMentalHealthLast30Days;
    }

    public void setBadMentalHealthLast30Days(double badMentalHealthLast30Days) {
        this.badMentalHealthLast30Days = badMentalHealthLast30Days;
    }

    public boolean isHasDifficultyWalking() {
        return isHasDifficultyWalking;
    }

    public void setHasDifficultyWalking(boolean hasDifficultyWalking) {
        isHasDifficultyWalking = hasDifficultyWalking;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAgeCategory() {
        return ageCategory;
    }

    public void setAgeCategory(String ageCategory) {
        this.ageCategory = ageCategory;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public int isDiabetic() {
        return isDiabetic;
    }

    public void setDiabetic(int diabetic) {
        isDiabetic = diabetic;
    }

    public boolean isDoesPhysicalActivity() {
        return isDoesPhysicalActivity;
    }

    public void setDoesPhysicalActivity(boolean doesPhysicalActivity) {
        isDoesPhysicalActivity = doesPhysicalActivity;
    }

    public String getGeneralHealth() {
        return generalHealth;
    }

    public void setGeneralHealth(String generalHealth) {
        this.generalHealth = generalHealth;
    }

    public double getHoursSlept() {
        return hoursSlept;
    }

    public void setHoursSlept(double hoursSlept) {
        this.hoursSlept = hoursSlept;
    }

    public boolean isHasAsthma() {
        return isHasAsthma;
    }

    public void setHasAsthma(boolean hasAsthma) {
        isHasAsthma = hasAsthma;
    }

    public boolean isHasKidneyDisease() {
        return isHasKidneyDisease;
    }

    public void setHasKidneyDisease(boolean hasKidneyDisease) {
        isHasKidneyDisease = hasKidneyDisease;
    }

    public boolean isHasSkinCancer() {
        return isHasSkinCancer;
    }

    public void setHasSkinCancer(boolean hasSkinCancer) {
        isHasSkinCancer = hasSkinCancer;
    }
    //endregion
}
