package edu.miu.waa.dto.response;

import edu.miu.waa.model.OfferStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferForPropertyResponseDto {
    private long id;
    private LocalDate createdDate;
    private Double offerAmount;
    private String message;
    private UserOutDto user;
    private OfferStatusEnum status;
}
