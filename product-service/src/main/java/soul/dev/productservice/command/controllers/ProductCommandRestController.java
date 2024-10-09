package soul.dev.productservice.command.controllers;

import jakarta.validation.Valid;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soul.dev.productservice.command.commands.CreateProductCommand;
import soul.dev.productservice.command.reqDtos.CreateProductReqDto;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/command/v1/products")
public class ProductCommandRestController {

    private CommandGateway commandGateway;

    public ProductCommandRestController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public CompletableFuture<String> create(@Valid @RequestBody CreateProductReqDto reqDto) {
        CreateProductCommand command = CreateProductCommand.builder()
                .id(UUID.randomUUID().toString())
                .price(reqDto.getPrice())
                .title(reqDto.getTitle())
                .quantity(reqDto.getQuantity()).build();
        return commandGateway.send(command);
    }


}
