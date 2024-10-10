
package soul.dev.paymentservice.query.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class PaymentEntity {
    @Id
    private String paymentId;
    public String orderId;

}
