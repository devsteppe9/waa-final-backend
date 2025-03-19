package edu.miu.waa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.miu.waa.model.Offer;

@Repository
public interface OfferRepo extends JpaRepository<Offer, Long> {

}
