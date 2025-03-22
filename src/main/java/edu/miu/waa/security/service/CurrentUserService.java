package edu.miu.waa.security.service;

import edu.miu.waa.model.User;
import edu.miu.waa.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CurrentUserService {

  private final UserRepo userRepo;
  
  @Transactional(readOnly = true)
  public User getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication != null && !authentication.getPrincipal().equals("anonymousUser") && authentication.isAuthenticated()) {
      return userRepo.findUserByUsername(authentication.getName());
    }

    return userRepo.findUserByUsername("guest");
  }
}
