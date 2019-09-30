package ru.otus.spring.spring04.questionnaire.springshell.service;

import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class ConsoleService implements IOService {

    public void printMsg(String s) {
        System.out.println(s);
    }

    public String readMsg() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
