package soul.dev.productservice.command.commands;

import lombok.Builder;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Builder
public class CreateProductCommand{
    @TargetAggregateIdentifier
    private final String id ;
    private final String title ;
    private final int price ;
    private final int quantity ;
}
