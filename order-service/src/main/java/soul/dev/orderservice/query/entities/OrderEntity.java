package soul.dev.orderservice.query.entities;


import jakarta.persistence.*;
import lombok.Data;
import soul.dev.orderservice.common.enums.OrderStatus;

@Data
@Entity
public class OrderEntity{
    @Id
    public String orderId;
    private String productId;
    private String userId;
    private int quantity;
    private String addressId;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}
