package ua.kiev.mvovnianko.hospital.entity.builder;

import ua.kiev.mvovnianko.hospital.entity.EntityDoctor;
import ua.kiev.mvovnianko.hospital.entity.User;

/**
 * Created by M.Vovnianko on 13.06.2021.
 */
public class EntityDoctorBuilder {

    private EntityDoctor doctor;

    public EntityDoctorBuilder() { this.doctor = new EntityDoctor(); }

    public EntityDoctorBuilder buildUser(User user) {
        doctor.setUser(user);
        return this;
    }

    public EntityDoctorBuilder buildDoctorType(String doctorType) {
        doctor.setDoctorType(doctorType);
        return this;
    }

    public EntityDoctorBuilder buildPatientsAmount(int patientsAmount) {
        doctor.setPatientsAmount(patientsAmount);
        return this;
    }

    public EntityDoctor build() {
        return doctor;
    }
}