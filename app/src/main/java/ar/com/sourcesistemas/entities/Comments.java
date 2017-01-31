package ar.com.sourcesistemas.entities;

/**
 * Created by H4te on 1/23/2017.
 */

public class Comments {


    private long id;
    private String text;
    private User commented_by;
    private ATM from;
    private int raiting;


    public Comments(long id,String text, User commented_by, ATM from, int raiting) {
        this.id = id;
        this.text = text;
        this.commented_by = commented_by;
        this.from = from;
        this.raiting = raiting;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getCommented_by() {
        return commented_by;
    }

    public void setCommented_by(User commented_by) {
        this.commented_by = commented_by;
    }

    public ATM getFrom() {
        return from;
    }

    public void setFrom(ATM from) {
        this.from = from;
    }

    public int getRaiting() {
        return raiting;
    }

    public void setRaiting(int raiting) {
        this.raiting = raiting;
    }
}
