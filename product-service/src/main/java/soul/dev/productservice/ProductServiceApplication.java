package soul.dev.productservice;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.config.EventProcessingConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import soul.dev.productservice.command.CommandInterceptors.CreateProductCommandInterceptor;
import soul.dev.productservice.common.errorHandlers.ProductEventErrorHandler;
import soul.dev.productservice.common.config.AxonXstreamConfig;

@SpringBootApplication
@Import({ AxonXstreamConfig.class })
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

    @Autowired
    public void registerCreateProductCommandInterceptors(ApplicationContext applicationContext , CommandBus commandBus) {
        CreateProductCommandInterceptor bean = applicationContext.getBean(CreateProductCommandInterceptor.class);
        commandBus.registerDispatchInterceptor(bean) ;
    }


    @Autowired
    public void configure(EventProcessingConfigurer eventProcessingConfigurer){
        eventProcessingConfigurer.registerListenerInvocationErrorHandler("product-group" , configuration ->
            new ProductEventErrorHandler()) ;
    }

}


