package soul.dev.productservice.command.reqDtos;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class CreateProductReqDto {
    private String title ;
    private int price ;
    private int quantity ;
}
