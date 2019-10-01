package ru.otus.spring.library.springjdbc.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.library.springjdbc.service.IOService;

@Service
public class ConsoleService implements IOService {

    public void printMsg(String s) {
        System.out.println(s);
    }

    public void printItemsList(String format, Object... args) {System.out.printf(format, args);}
}
