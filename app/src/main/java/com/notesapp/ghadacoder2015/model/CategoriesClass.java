package com.notesapp.ghadacoder2015.model;

public class CategoriesClass {

    String Uiu,name;

    public CategoriesClass() {
    }

    public CategoriesClass(String uiu, String name) {
        Uiu = uiu;
        this.name = name;
    }


    public String getUiu() {
        return Uiu;
    }

    public void setUiu(String uiu) {
        Uiu = uiu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
