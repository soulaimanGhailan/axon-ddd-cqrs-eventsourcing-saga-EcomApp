package soul.dev.common.commands;

import lombok.Builder;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
@Getter
@Builder
public class CancelProductReservationCommand {
    @TargetAggregateIdentifier
    private String productId ;
    private int quantity ;
    private String orderId ;
    private String userId ;
    private String reason ;
}
