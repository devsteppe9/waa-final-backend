package edu.miu.waa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FavouriteResponseDto {
  private long id;
  private long propertyId;
  private long userId;
}
