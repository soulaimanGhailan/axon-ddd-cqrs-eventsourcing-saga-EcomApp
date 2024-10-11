package soul.dev.productservice.command.aggregates;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import soul.dev.common.commands.CancelProductReservationCommand;
import soul.dev.common.commands.ReserveProductCommand;
import soul.dev.common.events.ProductReservationCanceledEvent;
import soul.dev.common.events.ProductReservedEvent;
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

        ProductCreatedEvent event = ProductCreatedEvent.builder()
                .id(command.getId())
                .price(command.getPrice())
                .title(command.getTitle())
                .quantity(command.getQuantity()).build();
        AggregateLifecycle.apply(event) ;
    }

    @CommandHandler
    public void handle(ReserveProductCommand command) {

        // it is recommended to implement a command interceptor here { proxy }
        System.out.println(this);
        if(quantity < command.getQuantity()) { throw new IllegalArgumentException(" quantity required is less the the provided quantity " +quantity + "  / " + command.getQuantity()) ;}

        ProductReservedEvent event = ProductReservedEvent.builder()
                .productId(command.getProductId())
                .orderId(command.getOrderId())
                .userId(command.getUserId())
                .quantity(command.getQuantity()).build();
        AggregateLifecycle.apply(event) ;
    }
    @CommandHandler
    public void handle(CancelProductReservationCommand command){
        ProductReservationCanceledEvent event = ProductReservationCanceledEvent.builder()
                .productId(command.getProductId())
                .orderId(command.getOrderId())
                .userId(command.getUserId())
                .quantity(command.getQuantity())
                .reason(command.getReason()).build();
        AggregateLifecycle.apply(event) ;
    }


    @EventSourcingHandler
    public void on(ProductCreatedEvent event){
        System.out.println(this);
        this.id = event.getId() ;
        this.price = event.getPrice() ;
        this.quantity = event.getQuantity() ;
        this.title = event.getTitle() ;
    }

    @EventSourcingHandler
    public void on(ProductReservedEvent event){
        this.quantity = this.quantity - event.getQuantity() ;
    }

    @EventSourcingHandler
    public void on(ProductReservationCanceledEvent event){
        this.quantity = this.quantity + event.getQuantity() ;
    }

    @Override
    public String toString() {
        return "ProductAggregate{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
