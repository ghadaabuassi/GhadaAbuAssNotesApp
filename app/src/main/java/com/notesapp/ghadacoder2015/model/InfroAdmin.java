package com.notesapp.ghadacoder2015.model;

public class InfroAdmin {

    String pasword,email,id;

    public InfroAdmin(String pasword, String email, String id) {
        this.pasword = pasword;
        this.email = email;
        this.id = id;
    }

    public InfroAdmin() {

    }

    public String getPasword() {
        return pasword;
    }

    public void setPasword(String pasword) {
        this.pasword = pasword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
