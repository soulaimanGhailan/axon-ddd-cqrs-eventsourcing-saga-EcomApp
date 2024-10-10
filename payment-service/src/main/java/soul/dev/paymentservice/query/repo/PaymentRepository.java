
package soul.dev.paymentservice.query.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import soul.dev.paymentservice.query.entities.PaymentEntity;

public interface PaymentRepository extends JpaRepository <PaymentEntity, String>{
    
}

