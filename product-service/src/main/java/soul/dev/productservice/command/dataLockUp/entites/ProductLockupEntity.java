package soul.dev.productservice.command.dataLockUp.entites;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity
@Builder
public class ProductLockupEntity {
    @Id
    private String productId;
    @Column(unique=true)
    private String productTitle;
}
