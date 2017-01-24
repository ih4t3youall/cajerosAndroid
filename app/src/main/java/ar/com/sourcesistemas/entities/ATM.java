package ar.com.sourcesistemas.entities;

/**
 * Created by H4te on 1/23/2017.
 */

public class ATM {

    private long id;
    private String address;
    private String lat;
    private String lon;
    private String type;
    private String bank_name;
    private User added_by;

    public ATM(long id, String address, String lat, String lon, String type, String bank_name, User added_by) {
        this.id = id;
        this.address = address;
        this.lat = lat;
        this.lon = lon;
        this.type = type;
        this.bank_name = bank_name;
        this.added_by = added_by;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public User getAdded_by() {
        return added_by;
    }

    public void setAdded_by(User added_by) {
        this.added_by = added_by;
    }
}
