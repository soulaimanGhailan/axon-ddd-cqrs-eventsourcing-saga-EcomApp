package soul.dev.orderservice.common.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import soul.dev.orderservice.common.enums.OrderStatus;

@Getter
@AllArgsConstructor
public class OrderApprovedEvent {
    private final String orderId ;
}
