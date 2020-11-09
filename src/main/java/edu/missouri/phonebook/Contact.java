package edu.missouri.phonebook;

import java.io.Serializable;

public class Contact implements Serializable {

    private String name;
    private long phoneNumber;

    public Contact() {
    }

    public Contact(String name, long phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

}
