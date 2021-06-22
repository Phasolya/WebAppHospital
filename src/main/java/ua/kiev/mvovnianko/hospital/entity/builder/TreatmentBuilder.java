package ua.kiev.mvovnianko.hospital.entity.builder;

import ua.kiev.mvovnianko.hospital.entity.Disease;
import ua.kiev.mvovnianko.hospital.entity.Treatment;
import ua.kiev.mvovnianko.hospital.entity.TreatmentType;

/**
 * Created by M.Vovnianko on 13.06.2021.
 */
public class TreatmentBuilder {

    private Treatment treatment;

    public TreatmentBuilder() {
        this.treatment = new Treatment();
    }

    public TreatmentBuilder buildId(int id) {
        treatment.setId(id);
        return this;
    }

    public TreatmentBuilder buildName(String name) {
        treatment.setName(name);
        return this;
    }

    public TreatmentBuilder buildDisease(Disease disease) {
        treatment.setDisease(disease);
        return this;
    }

    public TreatmentBuilder buildDTreatmentType(TreatmentType treatmentType) {
        treatment.setTreatmentType(treatmentType);
        return this;
    }

    public Treatment build() {
        return treatment;
    }
}