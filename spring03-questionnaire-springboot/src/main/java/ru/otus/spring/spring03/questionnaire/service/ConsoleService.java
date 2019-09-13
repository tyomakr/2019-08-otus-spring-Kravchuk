package ru.otus.spring.spring03.questionnaire.service;

import java.util.Scanner;

class ConsoleService {

    void printMsg(String s) {
        System.out.println(s);
    }

    String readMsg() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
