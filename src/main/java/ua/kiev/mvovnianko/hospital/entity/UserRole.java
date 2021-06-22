package ua.kiev.mvovnianko.hospital.entity;

import java.util.List;

/**
 * Created by M.Vovnianko on 13.06.2021.
 */
public enum UserRole {
    ADMIN, DOCTOR, NURSE, PATIENT, GUEST;

    public static UserRole getRole(int id) {
        return UserRole.values()[id];
    }

    public String getName() {
        return name().toLowerCase();
    }

    public int getId() {
        return this.ordinal();
    }
}