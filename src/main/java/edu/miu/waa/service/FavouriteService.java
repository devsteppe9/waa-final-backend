package edu.miu.waa.service;

import edu.miu.waa.model.Favourite;
import edu.miu.waa.model.Property;
import edu.miu.waa.model.User;
import java.util.List;
import java.util.Optional;

public interface FavouriteService {
  List<Favourite> findByUser(User user);
  List<Favourite> findByProperty(Property property);
  Favourite findByUserAndPropertyId(User user, long propertyId);
  void deleteByUserAndPropertyId(User user, long propertyId);
  void deleteById(long id);

  Optional<Favourite> findById(long id);
}
