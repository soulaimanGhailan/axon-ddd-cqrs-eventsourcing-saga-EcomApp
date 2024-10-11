package soul.dev.orderservice.common.events;

import lombok.Builder;
import lombok.Getter;
import soul.dev.orderservice.common.enums.OrderStatus;
@Getter
@Builder
public class OrderRejectedEvent {
    private String orderId;
    private String reason;
    private OrderStatus status;
}
