package edu.miu.waa.service;

import java.util.List;

import edu.miu.waa.dto.request.OfferPatchRequestDto;
import edu.miu.waa.dto.request.OfferRequestDto;
import edu.miu.waa.dto.response.OfferResponseDto;

public interface OfferService {
    List<OfferResponseDto> findAllOffers(Long userId);

    OfferResponseDto findOfferById(Long userId, Long id);

    Long create(Long userId, OfferRequestDto offerRequestDto);

    void update(Long userId, long id, OfferPatchRequestDto offerRequestDto);

    void delete(Long userId, long id);

    List<OfferResponseDto> findAllOffersByPropertyId(Long userId, Long propertyId);
}