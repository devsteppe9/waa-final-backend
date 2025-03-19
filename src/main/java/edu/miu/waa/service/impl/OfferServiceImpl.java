package edu.miu.waa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.miu.waa.dto.request.OfferRequestDto;
import edu.miu.waa.dto.response.OfferResponseDto;
import edu.miu.waa.repo.OfferRepo;
import edu.miu.waa.service.OfferService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {

    private final OfferRepo offerRepo;

    @Override
    public List<OfferResponseDto> findAllOffers(Long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllOffers'");
    }

    @Override
    public OfferResponseDto findOfferById(Long userId, Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findOfferById'");
    }

    @Override
    public Long create(OfferRequestDto offerRequestDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public void update(Long userId, long id, OfferRequestDto offerRequestDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Long userId, long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

}
