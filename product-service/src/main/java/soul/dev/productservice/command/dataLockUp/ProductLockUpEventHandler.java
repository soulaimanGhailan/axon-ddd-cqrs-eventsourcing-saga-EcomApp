package soul.dev.productservice.command.dataLockUp;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import soul.dev.productservice.command.dataLockUp.entites.ProductLockupEntity;
import soul.dev.productservice.command.dataLockUp.repos.ProductLockUpRepo;
import soul.dev.productservice.common.events.ProductCreatedEvent;

@Component
@ProcessingGroup("product-group")
public class ProductLockUpEventHandler {
    private ProductLockUpRepo productLockUpRepo ;

    public ProductLockUpEventHandler(ProductLockUpRepo productLockUpRepo) {
        this.productLockUpRepo = productLockUpRepo;
    }
    @EventHandler
    public void handle(ProductCreatedEvent event){
        ProductLockupEntity productLockup = ProductLockupEntity.builder()
                .productId(event.getId())
                .productTitle(event.getTitle()).build();
        productLockUpRepo.save(productLockup) ;
    }
}
