package soul.dev.productservice.command.dataLockUp.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import soul.dev.productservice.command.dataLockUp.entites.ProductLockupEntity;

import java.util.Optional;

public interface ProductLockUpRepo extends JpaRepository<ProductLockupEntity, String> {
    Optional<ProductLockupEntity> findByProductTitle(String productTitle);
}
