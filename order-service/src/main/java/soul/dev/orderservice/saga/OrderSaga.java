package soul.dev.orderservice.saga;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;
import soul.dev.common.commands.ReserveProductCommand;
import soul.dev.common.events.ProductReservedEvent;
import soul.dev.orderservice.common.events.OrderCreatedEvent;

@Saga
@Slf4j
public class OrderSaga {
    @Autowired
    private transient CommandGateway commandGateway;


    public OrderSaga() {

    }

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent event) {
        ReserveProductCommand reserveProductCommand = ReserveProductCommand.builder()
                .orderId(event.getOrderId()).
                productId(event.getProductId())
                .userId(event.getUserId())
                .quantity(event.getQuantity()).build();
        log.info("orderCreatedEvent for orderId : {} , productId {} ", event.getOrderId() , event.getProductId());
        commandGateway.send(reserveProductCommand , (commandMessage, commandResultMessage) -> {
            if(commandResultMessage.isExceptional()){
                // rollback
            }
        });
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(ProductReservedEvent event){
        // user payment
        log.info("productReservedEvent for orderId : {} ", event.getOrderId());
    }
}
