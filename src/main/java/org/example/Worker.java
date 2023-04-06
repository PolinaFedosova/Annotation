package org.example;
@Table(title="Worker")
public class Worker {
    @Column
    private int id;
    @Column
    private String name;
    @Column
    private int tabNumber;

    public Worker() {
    }

    public Worker(int id, String name, int tabNumber) {
        this.id = id;
        this.name = name;
        this.tabNumber = tabNumber;
    }
}
