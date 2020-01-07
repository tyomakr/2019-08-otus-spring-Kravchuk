package integration.service.impl;

import integration.service.IOService;
import org.springframework.stereotype.Service;

@Service
public class ConsoleIOService implements IOService {

    public void printMsg(String s) {
        System.out.println(s);
    }

    public void printMsg(String format, Object... args) {System.out.printf(format, args); }

}