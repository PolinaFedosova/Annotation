package org.example;

@Table(title="Students")
public class Students {
    @Column
    private int id;
    @Column
    private String name;
    @Column
    private int numberStud;

    public Students() {
    }

    public Students(int id, String name, int numberStud) {
        this.id = id;
        this.name = name;
        this.numberStud = numberStud;
    }
}
