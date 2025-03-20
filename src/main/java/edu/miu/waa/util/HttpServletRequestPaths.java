package edu.miu.waa.util;


import edu.miu.waa.service.StaticPropertyHolder;
import jakarta.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpServletRequestPaths {
  public static final Pattern API_VERSION = Pattern.compile("(/api/v(\\d+)?/)");
  
  public static String generateFileResourceLink(String storageKey, HttpServletRequest request) {
    
    return String.format(
        "%s/%s/%s", getApiPath(request), "file-resources", storageKey);
  }

  public static String getApiPath(HttpServletRequest request) {
    Matcher matcher = API_VERSION.matcher(request.getRequestURI());
    String version = "";

    if (matcher.find()) {
      version = "v" + matcher.group(2);
    }
    String domain = StaticPropertyHolder.getBackendDomain();
    String domainPath = domain != null && !domain.equals("null") && domain.length() > 0 ? "/" + domain : "";
    return String.format("%s%s/api/%s", getContextPath(request), domainPath, version);
  }

  public static String getServletPath(HttpServletRequest request) {
    return getContextPath(request) + request.getServletPath();
  }

  public static String getContextPath(HttpServletRequest request) {
    StringBuilder builder = new StringBuilder();
    String xForwardedProto = request.getHeader("X-Forwarded-Proto");
    String xForwardedPort = request.getHeader("X-Forwarded-Port");

    if (xForwardedProto != null
        && (xForwardedProto.equalsIgnoreCase("http")
            || xForwardedProto.equalsIgnoreCase("https"))) {
      builder.append(xForwardedProto);
    } else {
      builder.append(request.getScheme());
    }

    builder.append("://").append(request.getServerName());

    int port;

    try {
      port = Integer.parseInt(xForwardedPort);
    } catch (NumberFormatException e) {
      port = request.getServerPort();
    }

    if (port != 80 && port != 443) {
      builder.append(":").append(port);
    }

    builder.append(request.getContextPath());

    return builder.toString();
  }
}
