package ru.otus.spring.data.jpa.services;

public interface IOService {

    void printMsg(String s);

    String getMsg(String s);

    void printItemsList(String fromat, Object... args);

}
