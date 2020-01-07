package integration.shell;

import integration.service.OrdersGeneratorService;
import integration.service.impl.SleepService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {

    private final OrdersGeneratorService ordersService;

    @ShellMethod(value = "Start infinite generate orders", key = {"run", "start", "1"})
    private void runGenerator() {

       while (true) {
           ordersService.processNewOrder();
           SleepService.sleepUninterruptibly(2);
       }
    }
}
