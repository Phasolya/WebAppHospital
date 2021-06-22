package ua.kiev.mvovnianko.hospital.entity;

/**
 * Created by M.Vovnianko on 13.06.2021.
 */
public class EntityDoctor {

    private User user;
    private String doctorType;
    private int patientsAmount;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDoctorType() {
        return doctorType;
    }

    public void setDoctorType(String doctorType) {
        this.doctorType = doctorType;
    }

    public int getPatientsAmount() {
        return patientsAmount;
    }

    public void setPatientsAmount(int patientsAmount) {
        this.patientsAmount = patientsAmount;
    }
}
