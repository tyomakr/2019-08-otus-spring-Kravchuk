package ru.otus.spring.migration.shell;

import lombok.RequiredArgsConstructor;
import org.h2.tools.Console;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.migration.service.BatchService;
import ru.otus.spring.migration.service.IOService;

import java.sql.SQLException;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {

    private final BatchService batchService;
    private final IOService ioService;


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
    private void runMigration() {
        if (!batchService.isWasLaunched()) {
            batchService.launchJob();
        } else {
            ioService.printMsg("Миграция уже была ранее запущена. Для перезапуска введите restart");
        }

    }

    @ShellMethod(value = "Перезапуск миграции", key = {"restart", "rerun", "2"})
    private void restartMigration() {
        if (batchService.isWasLaunched()) {
            batchService.restartJob();
        } else {
            ioService.printMsg("Миграция ранее не запускалась. Для начала миграции введите run");
        }
    }
}
