package org.example;

@Table(title= "School")
public class Schoolboy {

    @Column
    private int id;

    @Column
    private String name;

    @Column
    private int className;

    public Schoolboy() {
    }

    public Schoolboy(int id, String name, int className) {
        this.id = id;
        this.name = name;
        this.className = className;
    }


}
