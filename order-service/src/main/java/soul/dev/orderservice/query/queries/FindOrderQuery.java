package soul.dev.orderservice.query.queries;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindOrderQuery {
    private String orderId;
}
