package edu.miu.waa.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferRequestDto {

    private long propertyId;
    private Double offerAmount;
    private String message;

}
