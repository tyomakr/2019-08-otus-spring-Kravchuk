package ru.otus.spring.migration.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.migration.service.IOService;

@Service
@RequiredArgsConstructor
public class ConsoleIOService implements IOService {

    public void printMsg(String s) {
        System.out.println(s);
    }

}
