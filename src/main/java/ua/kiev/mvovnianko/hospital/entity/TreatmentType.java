package ua.kiev.mvovnianko.hospital.entity;

/**
 * Created by M.Vovnianko on 13.06.2021.
 */
public enum TreatmentType {
    PROCEDURE, OPERATION, MEDICINE;

    public static TreatmentType getTreatmentType(int id) {
        return TreatmentType.values()[id];
    }

    public String getName() {
        return name().toLowerCase();
    }

    public int getId() {
        return this.ordinal();
    }
}