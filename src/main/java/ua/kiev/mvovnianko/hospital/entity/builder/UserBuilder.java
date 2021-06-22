package ua.kiev.mvovnianko.hospital.entity.builder;

import ua.kiev.mvovnianko.hospital.entity.User;
import ua.kiev.mvovnianko.hospital.entity.UserRole;

import java.time.LocalDate;

/**
 * Created by M.Vovnianko on 13.06.2021.
 */
public class UserBuilder {

    private User user;

    public UserBuilder() {
        this.user = new User();
    }

    public UserBuilder buildId(int id) {
        user.setId(id);
        return this;
    }

    public UserBuilder buildEmail(String email) {
        user.setEmail(email);
        return this;
    }

    public UserBuilder buildPassword(String password) {
        user.setPassword(password);
        return this;
    }

    public UserBuilder buildFullName(String fullName) {
        user.setFullName(fullName);
        return this;
    }

    public UserBuilder buildBirthDate(LocalDate birthDate) {
        user.setBirthDate(birthDate);
        return this;
    }

    public UserBuilder buildUserRole(UserRole userRole) {
        user.setUserRole(userRole);
        return this;
    }

    public User build() {
        return user;
    }
}
