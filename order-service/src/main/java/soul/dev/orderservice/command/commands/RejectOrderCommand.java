package soul.dev.orderservice.command.commands;

import lombok.Builder;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Builder
public class RejectOrderCommand {
    @TargetAggregateIdentifier
    private String orderId;
    private String reason ;
}
