package ru.javawebinar.topjava.exceptions;

public class NotFoundException extends RuntimeException {
    private final int id;

    public NotFoundException(int id){
        super();
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
