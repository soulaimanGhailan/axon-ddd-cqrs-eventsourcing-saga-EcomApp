package soul.dev.productservice.query.eventHandlers;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import soul.dev.productservice.common.events.ProductCreatedEvent;
import soul.dev.productservice.query.entities.Product;
import soul.dev.productservice.query.repos.ProductRepo;

@Component
@ProcessingGroup("product-group")
public class ProductEventsHandler {
    private ProductRepo productRepo ;

    public ProductEventsHandler(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @EventHandler
    public void handle(ProductCreatedEvent event) {
        Product product = Product.builder()
                .id(event.getId())
                .price(event.getPrice())
                .title(event.getTitle())
                .quantity(event.getQuantity()).build();
        productRepo.save(product);
    }

}
