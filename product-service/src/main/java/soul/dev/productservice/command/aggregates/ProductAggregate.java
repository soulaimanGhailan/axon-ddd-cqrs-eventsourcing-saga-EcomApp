package soul.dev.productservice.command.aggregates;

import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import soul.dev.productservice.command.commands.CreateProductCommand;
import soul.dev.productservice.common.events.ProductCreatedEvent;

@Aggregate
@NoArgsConstructor
public class ProductAggregate {
    @AggregateIdentifier
    private String id ;
    private String title ;
    private int price ;
    private int quantity ;

    @CommandHandler
    public ProductAggregate(CreateProductCommand command) {
        if(command.getPrice() < 0) throw new RuntimeException("Price cannot be negative");
        ProductCreatedEvent event = ProductCreatedEvent.builder()
                .id(command.getId())
                .price(command.getPrice())
                .title(command.getTitle())
                .quantity(command.getQuantity()).build();
        AggregateLifecycle.apply(event) ;
    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent event){
        this.id = event.getId() ;
        this.price = event.getPrice() ;
        this.quantity = event.getQuantity() ;
        this.title = event.getTitle() ;
    }
}
