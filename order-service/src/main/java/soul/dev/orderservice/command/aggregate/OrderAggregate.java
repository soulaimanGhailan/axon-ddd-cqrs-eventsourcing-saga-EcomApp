package soul.dev.orderservice.command.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;
import soul.dev.orderservice.command.commands.ApproveOrderCommand;
import soul.dev.orderservice.command.commands.CreateOrderCommand;
import soul.dev.orderservice.common.enums.OrderStatus;
import soul.dev.orderservice.common.events.OrderApprovedEvent;
import soul.dev.orderservice.common.events.OrderCreatedEvent;

@Aggregate
public class OrderAggregate {

    @AggregateIdentifier
    private String orderId;
    private String productId;
    private String userId;
    private int quantity;
    private String addressId;
    private OrderStatus orderStatus;

    public OrderAggregate() {
    }

    @CommandHandler
    public OrderAggregate(CreateOrderCommand createOrderCommand) {
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent();
        BeanUtils.copyProperties(createOrderCommand, orderCreatedEvent);
        AggregateLifecycle.apply(orderCreatedEvent);
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent orderCreatedEvent) throws Exception {
        this.orderId = orderCreatedEvent.getOrderId();
        this.productId = orderCreatedEvent.getProductId();
        this.userId = orderCreatedEvent.getUserId();
        this.addressId = orderCreatedEvent.getAddressId();
        this.quantity = orderCreatedEvent.getQuantity();
        this.orderStatus = orderCreatedEvent.getOrderStatus();
    }


    @CommandHandler
    public void handle(ApproveOrderCommand command) {
        AggregateLifecycle.apply(new OrderApprovedEvent(command.getOrderId()));
    }

    @EventSourcingHandler
    public void on(OrderApprovedEvent event){
        this.orderStatus = OrderStatus.APPROVED;
    }


}