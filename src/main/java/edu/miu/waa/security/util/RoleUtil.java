package edu.miu.waa.security.util;

import edu.miu.waa.model.User;
import edu.miu.waa.security.RoleEnum;

public class RoleUtil {
  public static boolean isOwner(User user) {
    return user.getRoles().stream().filter(role -> RoleEnum.fromString(role.getRole()) == RoleEnum.OWNER).findFirst().isPresent();
  }
}
