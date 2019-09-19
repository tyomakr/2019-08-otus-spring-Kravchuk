package ru.otus.spring.spring04.questionnaire.springshell.service;

import java.util.Scanner;

public class ConsoleService {

    public void printMsg(String s) {
        System.out.println(s);
    }

    String readMsg() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
