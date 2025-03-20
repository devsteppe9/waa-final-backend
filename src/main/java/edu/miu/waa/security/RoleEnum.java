package edu.miu.waa.security;

public enum RoleEnum {
  ADMIN("ADMIN"), OWNER("OWNER"), CUSTOMER("CUSTOMER");
  
  private final String role;
  public String getRole() {
    return role;
  }
  RoleEnum(String role) {
    this.role = role;
  }
  
  public static RoleEnum fromString(String role) {
    for (RoleEnum r : RoleEnum.values()) {
      if (r.getRole().equalsIgnoreCase(role)) {
        return r;
      }
    }
    return null;
  }
  
  @Override
  public String toString() {
    return role;
  }
  
  
}
