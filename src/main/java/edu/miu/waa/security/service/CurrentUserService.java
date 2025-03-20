package edu.miu.waa.security.service;

import edu.miu.waa.model.User;
import edu.miu.waa.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CurrentUserService {
  
  private final UserRepo userRepo;
  
  @Transactional(readOnly = true)
    public User getCurrentUser() {
      UserDetails userDetails = CurrentUserUtil.getCurrentUserDetails();
      return userRepo.findUserByUsername(userDetails.getUsername());
    }
}
