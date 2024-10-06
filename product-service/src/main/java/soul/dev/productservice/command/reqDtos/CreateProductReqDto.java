package soul.dev.productservice.command.reqDtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class CreateProductReqDto {
    @NotBlank(message = "title can not be empty ")
    private String title ;
//    @Min(value = 1 , message = "price can not be nor 0 nor negative")
    private int price ;
    @Min(value = 0 , message = "price can not be negative")
    @Max(value = 10 , message = "quantity can not be greater than 10 ")
    private int quantity ;
}
