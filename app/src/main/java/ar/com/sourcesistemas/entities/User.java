package ar.com.sourcesistemas.entities;

/**
 * Created by H4te on 1/23/2017.
 */

public class User {


    private long id;
    private String name;
    private String last_name;
    private String facebook;

    public User(long id){
        this.id = id;
    }


    public User( String name, String last_name, String facebook) {
        this.name = name;
        this.last_name = last_name;
        this.facebook = facebook;
    }

    public User(long id, String name, String last_name, String facebook) {
        this.id = id;
        this.name = name;
        this.last_name = last_name;
        this.facebook = facebook;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }
}
