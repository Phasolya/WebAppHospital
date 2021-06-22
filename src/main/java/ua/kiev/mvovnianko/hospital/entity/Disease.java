package ua.kiev.mvovnianko.hospital.entity;

/**
 * Created by M.Vovnianko on 13.06.2021.
 */
public class Disease {

    private int id;
    private String name;
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
