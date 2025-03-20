package edu.miu.waa.service.impl;

import edu.miu.waa.model.Favourite;
import edu.miu.waa.model.Property;
import edu.miu.waa.model.User;
import edu.miu.waa.repo.FavouriteRepo;
import edu.miu.waa.service.FavouriteService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FavouriteServiceImpl implements FavouriteService {

  private final FavouriteRepo favouriteRepo;
  
  @Override
  public List<Favourite> findByUser(User user) {
    return favouriteRepo.findByUser(user);
  }

  @Override
  public List<Favourite> findByProperty(Property property) {
    return favouriteRepo.findByProperty(property);
  }

  @Override
  public Favourite findByUserAndPropertyId(User user, long propertyId) {
    return favouriteRepo.findByUserAndPropertyId(user, propertyId);
  }

  @Override
  public void deleteByUserAndPropertyId(User user, long propertyId) {
    favouriteRepo.deleteByUserAndPropertyId(user, propertyId);
  }

  @Override
  public void deleteById(long id) {
    favouriteRepo.deleteById(id);
  }

  @Override
  public Optional<Favourite> findById(long id) {
    return favouriteRepo.findById(id);
  }
}
