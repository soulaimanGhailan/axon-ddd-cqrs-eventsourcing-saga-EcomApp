package soul.dev.common.events;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductReservationCanceledEvent {
    private String productId ;
    private int quantity ;
    private String orderId ;
    private String userId ;
    private String reason ;
}
