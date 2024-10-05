package soul.dev.productservice.common.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductCreatedEvent {
    private final String id ;
    private final String title ;
    private final int price ;
    private final int quantity ;
}
