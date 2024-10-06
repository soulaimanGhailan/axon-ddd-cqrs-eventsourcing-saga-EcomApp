package soul.dev.productservice.command.commands.CommandInterceptors;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import soul.dev.productservice.command.commands.CreateProductCommand;


import java.util.List;
import java.util.function.BiFunction;

@Component
@Slf4j
public class CreateProductCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {
    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(List<? extends CommandMessage<?>> messages) {
        return (integer, commandMessage) -> {
            log.info("the command that has been intercepted: {}  ", commandMessage.getPayloadType());
            CreateProductCommand command = (CreateProductCommand) commandMessage.getPayload();
            if(commandMessage.getPayload() instanceof CreateProductCommand) {
                if(command.getPrice() < 0) throw new RuntimeException("Price cannot be negative");
            }
            return commandMessage ;
        };
    }
}
