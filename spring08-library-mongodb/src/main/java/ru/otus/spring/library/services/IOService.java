package ru.otus.spring.library.services;

public interface IOService {

    void printMsg(String s);

    String getMsg(String s);

    void printItemsList(String format, Object... args);
}
