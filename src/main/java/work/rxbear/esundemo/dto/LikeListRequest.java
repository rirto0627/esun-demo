package work.rxbear.esundemo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeListRequest {
    @NotNull(message = "userId 不可為空")
    private String userId;

    @NotNull(message = "productNo 不可為空")
    private Integer productNo;

//    @NotNull(message = "price 不可為空")
//    private BigDecimal price;
//
//    @NotNull(message = "feeRate 不可為空")
//    private BigDecimal feeRate;

    @NotNull(message = "account 不可為空")
    private String account;

    @NotNull(message = "orderNum 不可為空")
    private Integer orderNum;
}
