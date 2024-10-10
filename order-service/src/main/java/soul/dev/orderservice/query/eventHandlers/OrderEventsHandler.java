package soul.dev.orderservice.query.eventHandlers;
import jakarta.persistence.criteria.Order;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import soul.dev.orderservice.common.enums.OrderStatus;
import soul.dev.orderservice.common.events.OrderApprovedEvent;
import soul.dev.orderservice.common.events.OrderCreatedEvent;
import soul.dev.orderservice.query.entities.OrderEntity;
import soul.dev.orderservice.query.repos.OrdersRepo;

import java.util.Optional;

@Component
@ProcessingGroup("order-group")
public class OrderEventsHandler {
    
    private OrdersRepo ordersRepo;
    
    public OrderEventsHandler(OrdersRepo ordersRepo) {
        this.ordersRepo = ordersRepo;
    }

    @EventHandler
    public void on(OrderCreatedEvent event) throws Exception {
        OrderEntity orderEntity = new OrderEntity();
        BeanUtils.copyProperties(event, orderEntity);
        this.ordersRepo.save(orderEntity);
    }

    @EventHandler
    public void on(OrderApprovedEvent event) throws Exception {
        OrderEntity order = this.ordersRepo.findById(event.getOrderId()).orElse(null);
        System.out.println(order);
        if(order == null){
            // need to do something
            return;
        }
        order.setOrderStatus(OrderStatus.APPROVED);
        this.ordersRepo.save(order);
    }
    
}
