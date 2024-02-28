package br.com.josimar.kafka.genes;

public class HumchrRecord {
    private String geneName;
    private String chromosomalPosition;
    private String swissProtAccessionNumber;
    private String mimNumber;
    private String description;

    public HumchrRecord(String geneName, String chromosomalPosition, String swissProtAccessionNumber,
            String mimNumber, String description) {
        this.geneName = geneName;
        this.chromosomalPosition = chromosomalPosition;
        this.swissProtAccessionNumber = swissProtAccessionNumber;
        this.mimNumber = mimNumber;
        this.description = description;
    }

    public String getGeneName() {
        return geneName;
    }

    public void setGeneName(String geneName) {
        this.geneName = geneName;
    }

    public String getChromosomalPosition() {
        return chromosomalPosition;
    }

    public void setChromosomalPosition(String chromosomalPosition) {
        this.chromosomalPosition = chromosomalPosition;
    }

    public String getSwissProtAccessionNumber() {
        return swissProtAccessionNumber;
    }

    public void setSwissProtAccessionNumber(String swissProtAccessionNumber) {
        this.swissProtAccessionNumber = swissProtAccessionNumber;
    }

    public String getMimNumber() {
        return mimNumber;
    }

    public void setMimNumber(String mimNumber) {
        this.mimNumber = mimNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "HumchrRecord [geneName=" + geneName + ", chromosomalPosition=" + chromosomalPosition
                + ", swissProtAccessionNumber=" + swissProtAccessionNumber + ", mimNumber=" + mimNumber
                + ", description=" + description + "]";
    }

}
