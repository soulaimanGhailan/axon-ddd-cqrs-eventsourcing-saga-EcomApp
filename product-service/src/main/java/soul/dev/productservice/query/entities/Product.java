package soul.dev.productservice.query.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    private String id ;
    private String title ;
    private int price ;
    private int quantity ;
}
