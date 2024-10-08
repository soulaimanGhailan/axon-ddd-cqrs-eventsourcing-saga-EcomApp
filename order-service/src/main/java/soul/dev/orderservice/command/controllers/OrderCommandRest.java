package soul.dev.orderservice.command.controllers;

import jakarta.validation.Valid;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soul.dev.orderservice.command.commands.CreateOrderCommand;
import soul.dev.orderservice.command.reqsDtos.CreateOrderReqDto;
import soul.dev.orderservice.common.enums.OrderStatus;

import java.util.UUID;

@RestController
@RequestMapping("/api/command/v1/orders")
public class OrderCommandRest {

    private final CommandGateway commandGateway;


    public OrderCommandRest(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public String createOrder(@Valid @RequestBody CreateOrderReqDto order) {

        String userId = "27b95829-4f3f-4ddf-8983-151ba010e35b";

        CreateOrderCommand createOrderCommand = CreateOrderCommand.builder()
                .addressId(order.getAddressId())
                .productId(order.getProductId())
                .userId(userId)
                .quantity(order.getQuantity())
                .orderId(UUID.randomUUID().toString())
                .orderStatus(OrderStatus.CREATED)
                .build();

        return commandGateway.sendAndWait(createOrderCommand);

    }

}
