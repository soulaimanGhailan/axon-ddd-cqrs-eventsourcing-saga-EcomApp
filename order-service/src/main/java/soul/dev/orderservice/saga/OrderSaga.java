package soul.dev.orderservice.saga;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.deadline.DeadlineManager;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;
import soul.dev.common.commands.CancelProductReservationCommand;
import soul.dev.common.commands.ProcessPaymentCommand;
import soul.dev.common.commands.ReserveProductCommand;
import soul.dev.common.events.PaymentProcessedEvent;
import soul.dev.common.events.ProductReservationCanceledEvent;
import soul.dev.common.events.ProductReservedEvent;
import soul.dev.common.model.User;
import soul.dev.common.queries.FetchUserPaymentDetailsQuery;
import soul.dev.orderservice.command.commands.ApproveOrderCommand;
import soul.dev.orderservice.command.commands.RejectOrderCommand;
import soul.dev.orderservice.common.events.OrderApprovedEvent;
import soul.dev.orderservice.common.events.OrderCreatedEvent;
import soul.dev.orderservice.common.events.OrderRejectedEvent;

import java.util.UUID;

@Saga
@Slf4j
public class OrderSaga {
    @Autowired
    private transient CommandGateway commandGateway;
    @Autowired
    private transient QueryGateway queryGateway;
    @Autowired
    private transient DeadlineManager deadlineManager ;


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
        log.info("Order saga -----> orderCreatedEvent for orderId : {} , productId {} ", event.getOrderId() , event.getProductId());
        commandGateway.send(reserveProductCommand , (commandMessage, commandResultMessage) -> {
            if(commandResultMessage.isExceptional()){
                // start compensating transaction CancelProductReservationCommand
                log.info("Order saga rollback -----> product reservation can not be processed , productId : {} ", event.getProductId());
                RejectOrderCommand command = RejectOrderCommand.builder()
                        .orderId(event.getOrderId())
                        .reason("can not reserve product").build();
                commandGateway.send(command) ;
            }
        });
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(ProductReservedEvent event){

        /** get user payment details */
        log.info("Order saga -----> productReservedEvent for orderId : {} ", event.getOrderId());
        FetchUserPaymentDetailsQuery query = FetchUserPaymentDetailsQuery.builder()
                .userId(event.getUserId()).build();
        User userPaymentDetails = null ;
        try {
            userPaymentDetails = queryGateway.query(query , ResponseTypes.instanceOf(User.class)).join()  ;
        }catch (Exception e){
            log.error("Error while fetching user payment details ", e.getMessage());
            // start compensating transaction
            cancelProductReservationCommand(event , e.getMessage());
            return;
        }
        if(userPaymentDetails == null){
            // start compensating transaction
            cancelProductReservationCommand(event , "the userPayment details is empty");
        }else{

            /** process payment **/
            log.info("Order saga -----> payment details for user {} is fetched successfully", event.getUserId());
            ProcessPaymentCommand command = ProcessPaymentCommand.builder()
                    .orderId(event.getOrderId())
                    .paymentId(UUID.randomUUID().toString())
                    .paymentDetails(userPaymentDetails.getPaymentDetails()).build();
            commandGateway.send(command , (commandMessage, commandResultMessage) -> {
                if(commandResultMessage.isExceptional()){
                    // rollout transaction
                    cancelProductReservationCommand(event , "payment process failed");
                }
            });
        }

    }
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(PaymentProcessedEvent event){
        log.info("Order saga -----> paymentProcessedEvent for orderId : {} ", event.getOrderId());
        commandGateway.send(new ApproveOrderCommand(event.getOrderId()));
    }

    /** saga happy path **/
    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderApprovedEvent event){
        log.info("Order saga -----> order saga has ended happily for orderId : {} ", event.getOrderId());
    }
    /** error path **/
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(ProductReservationCanceledEvent event){
        log.info("Order saga rollback -----> product reservation canceled , productId : {} ", event.getProductId());
        RejectOrderCommand command = RejectOrderCommand.builder()
                .orderId(event.getOrderId())
                .reason(event.getReason()).build();
        commandGateway.send(command) ;
    }
    @SagaEventHandler(associationProperty = "orderId")
    @EndSaga
    public void handle(OrderRejectedEvent event){
        log.info("Order saga rollback -----> order rejected , orderId : {} ", event.getOrderId());

    }


    private void cancelProductReservationCommand(ProductReservedEvent event , String reason){
        CancelProductReservationCommand cancelCommand = CancelProductReservationCommand.builder()
                .orderId(event.getOrderId())
                .productId(event.getProductId())
                .userId(event.getUserId())
                .quantity(event.getQuantity())
                .reason(reason)
                .build();
        commandGateway.send(cancelCommand);
    }
}
