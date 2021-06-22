package ua.kiev.mvovnianko.hospital.entity.builder;

import ua.kiev.mvovnianko.hospital.entity.Disease;
import ua.kiev.mvovnianko.hospital.entity.User;

/**
 * Created by M.Vovnianko on 13.06.2021.
 */
public class DiseaseBuilder {

    private Disease disease;

    public DiseaseBuilder() {
        this.disease = new Disease();
    }

    public DiseaseBuilder buildId(int id) {
        disease.setId(id);
        return this;
    }

    public DiseaseBuilder buildName(String name) {
        disease.setName(name);
        return this;
    }

    public DiseaseBuilder buildUser(User user) {
        disease.setUser(user);
        return this;
    }

    public Disease build() {
        return disease;
    }
}
