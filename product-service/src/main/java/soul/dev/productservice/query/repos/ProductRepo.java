package soul.dev.productservice.query.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import soul.dev.productservice.query.entities.Product;

public interface ProductRepo extends JpaRepository<Product, String> {
}
