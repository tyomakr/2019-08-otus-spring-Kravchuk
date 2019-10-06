package ru.otus.spring.library.springjdbc.service;

public interface IOService {

    void printMsg(String s);

    void printItemsList(String format, Object... args);
}
