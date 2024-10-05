package soul.dev.productservice.query.quiriesHandlers;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
import soul.dev.productservice.query.entities.Product;
import soul.dev.productservice.query.quiries.GetAllProductQuery;
import soul.dev.productservice.query.repos.ProductRepo;

import java.util.List;

@Component
public class ProductQueryHandlers {

    private ProductRepo productRepo ;


    public ProductQueryHandlers(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @QueryHandler
    public List<Product> handle(GetAllProductQuery query){
        return productRepo.findAll();
    }

}
