package soul.dev.productservice.query.eventHandlers;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import soul.dev.common.events.ProductReservationCanceledEvent;
import soul.dev.common.events.ProductReservedEvent;
import soul.dev.productservice.common.events.ProductCreatedEvent;
import soul.dev.productservice.query.entities.Product;
import soul.dev.productservice.query.repos.ProductRepo;

import java.util.Optional;

@Component
@ProcessingGroup("product-group")
@Slf4j
public class ProductEventsHandler {
    private ProductRepo productRepo ;

    public ProductEventsHandler(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @ExceptionHandler(resultType = IllegalArgumentException.class)
    public void handleException(IllegalArgumentException exception) throws Exception {
        throw exception;
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

    @EventHandler
    public void handle(ProductReservedEvent event) {
        log.info("Reserved product: {}", event.getProductId());
        Product product = productRepo.findById(event.getProductId()).orElse(null);
        product.setQuantity(product.getQuantity() - event.getQuantity());
        productRepo.save(product);
    }

    @EventHandler
    public void handle(ProductReservationCanceledEvent event){
        Product product = productRepo.findById(event.getProductId()).orElse(null);
        if(product == null) return;
        product.setQuantity(product.getQuantity() + event.getQuantity());
        productRepo.save(product);
    }

}
