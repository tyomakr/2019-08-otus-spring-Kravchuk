package ru.otus.spring.library.springjdbc.service;

public class ConsoleService {

    public void printMsg(String s) {
        System.out.println(s);
    }

    public void printItemsList(String format, Object... args) {System.out.printf(format, args);}
}
