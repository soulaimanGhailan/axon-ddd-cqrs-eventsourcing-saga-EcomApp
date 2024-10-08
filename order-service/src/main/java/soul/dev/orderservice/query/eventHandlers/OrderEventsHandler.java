package soul.dev.orderservice.query.eventHandlers;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import soul.dev.orderservice.common.events.OrderCreatedEvent;
import soul.dev.orderservice.query.entities.OrderEntity;
import soul.dev.orderservice.query.repos.OrdersRepo;

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
    
}
