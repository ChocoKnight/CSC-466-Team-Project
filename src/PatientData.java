public class PatientData {

    private boolean isHasHeartDisease;
    private double BMI;
    private boolean isSmoking;
    private boolean isDrinksAlcohol;
    private boolean isHasHadStroke;
    private double physicalHealthDays;
    private double mentalHealthDays;
    private boolean isHasDifficultyWalking;
    private String sex;
    private String ageCategory;
    private String race;
    private boolean isDiabetic;
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
        this.physicalHealthDays = 0;
        this.mentalHealthDays = 0;
        this.isHasDifficultyWalking = false;
        this.sex = "";
        this.ageCategory = "";
        this.race = "";
        this.isDiabetic = false;
        this.isDoesPhysicalActivity = false;
        this.generalHealth = "";
        this.hoursSlept = 0;
        this.isHasAsthma = false;
        this.isHasKidneyDisease = false;
        this.isHasSkinCancer = false;
    }

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

    public double getPhysicalHealthDays() {
        return physicalHealthDays;
    }

    public void setPhysicalHealthDays(double physicalHealthDays) {
        this.physicalHealthDays = physicalHealthDays;
    }

    public double getMentalHealthDays() {
        return mentalHealthDays;
    }

    public void setMentalHealthDays(double mentalHealthDays) {
        this.mentalHealthDays = mentalHealthDays;
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

    public boolean isDiabetic() {
        return isDiabetic;
    }

    public void setDiabetic(boolean diabetic) {
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
}
