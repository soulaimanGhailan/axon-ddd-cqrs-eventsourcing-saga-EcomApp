package soul.dev.productservice.query.controllers;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soul.dev.productservice.query.entities.Product;
import soul.dev.productservice.query.quiries.GetAllProductQuery;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/query/v1/products")
public class ProductQueryRestController {

    private QueryGateway queryGateway ;

    public ProductQueryRestController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }
    @GetMapping
    public CompletableFuture<List<Product>> getProducts() {
        GetAllProductQuery query = GetAllProductQuery.builder().build();
       return queryGateway.query(query, ResponseTypes.multipleInstancesOf(Product.class));
    }

}
