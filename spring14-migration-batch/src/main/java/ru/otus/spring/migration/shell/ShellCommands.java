package ru.otus.spring.migration.shell;

import lombok.RequiredArgsConstructor;
import org.h2.tools.Console;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.spring.migration.service.BatchService;

import java.sql.SQLException;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {

    private final BatchService batchService;

    /*CONSOLE*/
    @ShellMethod(value = "Показать консоль H2", key = {"h2", "c", "console"})
    private void showH2Console() {
        try {
            Console.main();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @ShellMethod(value = "Запуск миграции", key = {"start", "run", "1"})
    @ShellMethodAvailability("runAvailabilityCheck")
    private void runMigration() {
        batchService.launchJob();

    }

    @ShellMethod(value = "Перезапуск миграции", key = {"restart", "rerun", "2"})
    @ShellMethodAvailability("restartAvailabilityCheck")
    private void restartMigration() {
        batchService.restartJob();
    }


    private Availability runAvailabilityCheck() {
        String msg = "Миграция уже была ранее запущена. Для перезапуска введите restart";
        return batchService.isWasLaunched() ? Availability.unavailable(msg) : Availability.available();
    }


    private Availability restartAvailabilityCheck() {
        String msg = "Миграция ранее не запускалась. Для начала миграции введите run";
        return batchService.isWasLaunched() ? Availability.available() : Availability.unavailable(msg);
    }

}
