package integration.gateway;


import integration.domain.AssembledOrder;
import integration.domain.Order;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface Cafe {

    @Gateway(requestChannel = "orders.input")
    AssembledOrder placeOrder(Order order);



}
