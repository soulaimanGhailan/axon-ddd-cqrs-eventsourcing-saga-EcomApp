package soul.dev.orderservice.query.quriesHandlere;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
import soul.dev.orderservice.query.entities.OrderEntity;
import soul.dev.orderservice.query.queries.FindOrderQuery;
import soul.dev.orderservice.query.repos.OrdersRepo;

@Component
public class OrderQueriesHandler {

    private OrdersRepo ordersRepo;

    public OrderQueriesHandler(OrdersRepo ordersRepo) {
        this.ordersRepo = ordersRepo;
    }

    @QueryHandler
    public OrderEntity handle(FindOrderQuery query){
        return ordersRepo.findById(query.getOrderId()).orElse(null) ;

    }
}
