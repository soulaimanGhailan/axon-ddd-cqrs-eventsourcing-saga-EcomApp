package soul.dev.productservice;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.eventsourcing.EventCountSnapshotTriggerDefinition;
import org.axonframework.eventsourcing.SnapshotTriggerDefinition;
import org.axonframework.eventsourcing.Snapshotter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import soul.dev.common.config.AxonXstreamConfig;
import soul.dev.productservice.command.CommandInterceptors.CreateProductCommandInterceptor;
import soul.dev.productservice.common.errorHandlers.ProductEventErrorHandler;

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

    @Bean(name = "productSnapshotDefinition")
    public SnapshotTriggerDefinition snapshotTriggerDefinition(Snapshotter snapshotter){
        return new EventCountSnapshotTriggerDefinition(snapshotter , 5) ;
    }

}


