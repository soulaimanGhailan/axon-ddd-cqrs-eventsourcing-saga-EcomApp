package soul.dev.common.queries;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class FetchUserPaymentDetailsQuery {
    private String userId;
}
