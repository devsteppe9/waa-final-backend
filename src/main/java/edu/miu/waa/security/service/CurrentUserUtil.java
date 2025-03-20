package edu.miu.waa.security.service;

import edu.miu.waa.model.User;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nonnull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class CurrentUserUtil {
  private CurrentUserUtil() {
    throw new UnsupportedOperationException("Utility class");
  }

  /**
   * Get the username of the currently authenticated user
   *
   * @return the current user's username
   */
  @Nonnull
  public static String getCurrentUsername() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null
        || !authentication.isAuthenticated()
        || authentication.getPrincipal() == null) {
      throw new IllegalStateException("No authenticated user found");
    }

    Object principal = authentication.getPrincipal();

    if (principal instanceof org.springframework.security.core.userdetails.UserDetails) {
      org.springframework.security.core.userdetails.UserDetails userDetails =
          (org.springframework.security.core.userdetails.UserDetails) authentication.getPrincipal();
      return userDetails.getUsername();

    } else {
      throw new IllegalStateException(
          "Authentication principal is not supported; principal:" + principal);
    }
  }

  /**
   * Get details about the currently authenticated user
   *
   * @return CurrentUserDetails representing the authenticated user
   */
  @Nonnull
  public static UserDetails getCurrentUserDetails() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null
        || !authentication.isAuthenticated()
        || authentication.getPrincipal() == null) {
      throw new IllegalStateException("No authenticated user found");
    }

    Object principal = authentication.getPrincipal();

    if (principal instanceof UserDetails) {
      return (UserDetails) authentication.getPrincipal();
    } else {
      throw new IllegalStateException(
          "Authentication principal is not supported; principal:" + principal);
    }
  }

  /**
   * Get all authorities assigned to the current user Return an empty list if the current session is
   * anonymous
   *
   * @return list of authority names
   */
  public static List<String> getCurrentUserAuthorities() {
    if (!CurrentUserUtil.hasCurrentUser()) {
      return List.of();
    }

    UserDetails currentUserDetails = getCurrentUserDetails();
    return currentUserDetails.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .toList();
  }

  /**
   * Check if the current user has any of the passed candidate authorities
   *
   * @param candidateAuthorities a list of possible authorities to check against
   * @return true if the user has one or more of the candidateAuthorities
   */
  public static Boolean hasAnyAuthority(Collection<String> candidateAuthorities) {
    List<String> currentUserAuthorities = getCurrentUserAuthorities();
    return candidateAuthorities.stream().anyMatch(currentUserAuthorities::contains);
  }

  public static boolean hasCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication != null
           && authentication.isAuthenticated()
           && authentication.getPrincipal() != null
           && !authentication.getPrincipal().equals("anonymousUser");
  }

  public static void injectUserInSecurityContext(UserDetails actingUser) {
    Authentication authentication =
        new UsernamePasswordAuthenticationToken(actingUser, "", actingUser.getAuthorities());
    SecurityContext context = SecurityContextHolder.createEmptyContext();
    context.setAuthentication(authentication);
    SecurityContextHolder.setContext(context);
  }

  public static void clearSecurityContext() {
    SecurityContext context = SecurityContextHolder.getContext();
    if (context != null) {
      SecurityContextHolder.getContext().setAuthentication(null);
    }
    SecurityContextHolder.clearContext();
  }
  
}
