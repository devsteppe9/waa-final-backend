package edu.miu.waa.repo;


import edu.miu.waa.model.Favourite;
import edu.miu.waa.model.Property;
import edu.miu.waa.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavouriteRepo extends JpaRepository<Favourite, Long> {
  List<Favourite> findByUser(User user);
  List<Favourite> findByProperty(Property property);
  Favourite findByUserAndPropertyId(User user, long propertyId);
  void deleteByUserAndPropertyId(User user, long propertyId);
}
