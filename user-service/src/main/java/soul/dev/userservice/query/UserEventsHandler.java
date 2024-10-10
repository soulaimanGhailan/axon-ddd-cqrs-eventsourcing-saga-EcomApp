package soul.dev.userservice.query;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
import soul.dev.common.model.PaymentDetails;
import soul.dev.common.model.User;
import soul.dev.common.queries.FetchUserPaymentDetailsQuery;

@Component
public class UserEventsHandler {

    @QueryHandler
    public User findUserPaymentDetails(FetchUserPaymentDetailsQuery query) {
        
        PaymentDetails paymentDetails = PaymentDetails.builder()
                .cardNumber("123Card")
                .cvv("123")
                .name("SERGEY KARGOPOLOV")
                .validUntilMonth(12)
                .validUntilYear(2030)
                .build();

        User user = User.builder()
                .firstName("soulaiman")
                .lastName("ghailan")
                .userId(query.getUserId())
                .paymentDetails(paymentDetails)
                .build();
        System.out.println(user);
        return user;
    }
    
    
}
