package edu.miu.waa.service.impl;

import java.util.List;
import java.util.Optional;

import edu.miu.waa.dto.request.OfferPatchRequestDto;
import edu.miu.waa.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.annotation.JsonAppend.Prop;

import edu.miu.waa.dto.request.OfferRequestDto;
import edu.miu.waa.dto.response.OfferResponseDto;
import edu.miu.waa.repo.OfferRepo;
import edu.miu.waa.repo.PropertyRepo;
import edu.miu.waa.repo.UserRepo;
import edu.miu.waa.service.OfferService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {

    private final OfferRepo offerRepo;
    private final UserRepo userRepo;
    private final PropertyRepo propertyRepo;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<OfferResponseDto> findAllOffers(Long userId) {
        List<Offer> offers = offerRepo.findByUserId(userId);

        return offers.stream()
                .sorted((o1,o2) -> o2.getId().compareTo(o1.getId()))
                .map(
                offer -> modelMapper.map(offer, OfferResponseDto.class)).toList();

    }

    @Override
    public OfferResponseDto findOfferById(Long userId, Long id) {
        Offer offer = offerRepo.findByUserIdAndId(userId, id)
                .orElseThrow(() -> new RuntimeException("Offer not found"));
        return modelMapper.map(offer, OfferResponseDto.class);
    }

    @Override
    public Long create(Long userId, OfferRequestDto offerRequestDto) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Property property = propertyRepo.findById(offerRequestDto.getPropertyId())
                .orElseThrow(() -> new RuntimeException("Property not found"));
        Offer offer = new Offer();
        offer.setProperty(property);
        offer.setOfferAmount(offerRequestDto.getOfferAmount());
        offer.setMessage(offerRequestDto.getMessage());
        offer.setUser(user);

        return offerRepo.save(offer).getId();
    }

    @Override
    public void update(Long userId, long id, OfferPatchRequestDto offerRequestDto) {
        Offer offer = offerRepo.findByUserIdAndId(userId, id)
                .orElseThrow(() -> new RuntimeException("Offer not found"));
        OfferStatusEnum status = OfferStatusEnum.fromString(offerRequestDto.getStatus());

        offer.setStatus(status);
        offerRepo.save(offer);
    }

    @Override
    public void delete(Long userId, long id) {
        userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        Offer offer = offerRepo.findByUserIdAndId(userId, id)
                .orElseThrow(() -> new RuntimeException("Offer not found"));

        offerRepo.delete(offer);
    }

    @Override
    public List<OfferResponseDto> findAllOffersByPropertyId(Long userId, Long propertyId) {
        List<Offer> offers = offerRepo.findByUserIdAndPropertyId(userId, propertyId);

        return offers.stream().map(
                offer -> modelMapper.map(offer, OfferResponseDto.class)).toList();
    }

    @Override
    public OfferResponseDto setOfferStatus(Long propertyId, Long offerId, String s) {
        OfferStatusEnum status = OfferStatusEnum.fromString(s);
        switch (status) {
            case ACCEPTED:
//                - 1. Check if there is no other accepted offers for this property
//                - 2. Changes offer status to Accepted
//                - 3. Property status to Pending
                Property property = propertyRepo.findById(propertyId).orElseThrow(() -> new RuntimeException("Property not found"));
                if (property.getStatus() != PropertyStatus.AVAILABLE) {
                    throw new RuntimeException("Property is not available");
                }
                property.setStatus(PropertyStatus.PENDING);
                propertyRepo.save(property);

                Offer offer = offerRepo.findById(offerId).orElseThrow(() -> new RuntimeException("Offer not found"));
                offer.setStatus(OfferStatusEnum.ACCEPTED);
                offerRepo.save(offer);
                return modelMapper.map(offer, OfferResponseDto.class);
            case REJECTED:
//                - 1. Changes offer status to REJECTED
//                - 2. Set Property status to Available if needed
                Offer offer1 = offerRepo.findById(offerId).orElseThrow(() -> new RuntimeException("Offer not found"));
                offer1.setStatus(OfferStatusEnum.REJECTED);
                offerRepo.save(offer1);

                Property property1 = propertyRepo.findById(propertyId).orElseThrow(() -> new RuntimeException("Property not found"));
                property1.setStatus(PropertyStatus.AVAILABLE);
                propertyRepo.save(property1);
                return modelMapper.map(offer1, OfferResponseDto.class);
            default:
                throw new RuntimeException("Invalid status");

        }
    }

}
