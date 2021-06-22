package ua.kiev.mvovnianko.hospital.entity;

/**
 * Created by M.Vovnianko on 13.06.2021.
 */
public class Treatment {

    private int id;
    private String name;
    private Disease disease;
    private TreatmentType treatmentType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Disease getDisease() {
        return disease;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

    public TreatmentType getTreatmentType() {
        return treatmentType;
    }

    public void setTreatmentType(TreatmentType treatmentType) {
        this.treatmentType = treatmentType;
    }
}

