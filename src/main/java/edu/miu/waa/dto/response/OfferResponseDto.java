package edu.miu.waa.dto.response;

import java.time.LocalDate;

import edu.miu.waa.dto.UserOutDto;
import edu.miu.waa.model.OfferStatusEnum;
import edu.miu.waa.model.Property;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferResponseDto {
    private long id;
    private LocalDate createdDate;
    private Double offerAmount;
    private String message;

    private PropertyResponseDto property;
    private UserOutDto user;
    private OfferStatusEnum status;
}
