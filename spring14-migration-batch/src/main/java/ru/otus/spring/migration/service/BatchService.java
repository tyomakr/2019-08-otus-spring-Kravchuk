package ru.otus.spring.migration.service;

public interface BatchService {

    void launchJob();

    void restartJob();

    boolean isWasLaunched();
}
