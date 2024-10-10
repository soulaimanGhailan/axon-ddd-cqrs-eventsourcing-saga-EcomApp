package soul.dev.orderservice.saga;


import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;
import soul.dev.common.commands.ProcessPaymentCommand;
import soul.dev.common.commands.ReserveProductCommand;
import soul.dev.common.events.PaymentProcessedEvent;
import soul.dev.common.events.ProductReservedEvent;
import soul.dev.common.model.User;
import soul.dev.common.queries.FetchUserPaymentDetailsQuery;
import soul.dev.orderservice.common.events.OrderCreatedEvent;

import java.util.UUID;

@Saga
@Slf4j
public class OrderSaga {
    @Autowired
    private transient CommandGateway commandGateway;
    @Autowired
    private transient QueryGateway queryGateway;


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

        /** get user payment details */
        log.info("productReservedEvent for orderId : {} ", event.getOrderId());
        FetchUserPaymentDetailsQuery query = FetchUserPaymentDetailsQuery.builder()
                .userId(event.getUserId()).build();
        User userPaymentDetails = null ;
        try {
            userPaymentDetails = queryGateway.query(query , ResponseTypes.instanceOf(User.class)).join()  ;
        }catch (Exception e){
            log.error("Error while fetching user payment details ", e.getMessage());
            // rollback transaction
        }
        if(userPaymentDetails == null){
            // rollout transaction
        }else{

            /** process payment **/
            System.out.println(userPaymentDetails);
            log.info("payment details for user {} is fetched successfully", event.getUserId());
            ProcessPaymentCommand command = ProcessPaymentCommand.builder()
                    .orderId(event.getOrderId())
                    .paymentId(UUID.randomUUID().toString())
                    .paymentDetails(userPaymentDetails.getPaymentDetails()).build();
            commandGateway.send(command , (commandMessage, commandResultMessage) -> {
                if(commandResultMessage.isExceptional()){
                    // rollout transaction
                }
            });
        }

    }
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(PaymentProcessedEvent event){
        log.info("paymentProcessedEvent for orderId : {} ", event.getOrderId());
    }
}
