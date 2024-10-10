package soul.dev.orderservice.command.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Builder @AllArgsConstructor
public class ApproveOrderCommand {
    @TargetAggregateIdentifier
    private String orderId;
}
