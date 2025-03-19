package edu.miu.waa.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.miu.waa.model.Offer;

@Repository
public interface OfferRepo extends JpaRepository<Offer, Long> {

    List<Offer> findByUserId(Long userId);

    Optional<Offer> findByUserIdAndId(Long userId, Long id);

    List<Offer> findByUserIdAndPropertyId(Long userId, Long propertyId);

}
