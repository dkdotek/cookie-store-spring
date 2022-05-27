package gateway.model;

import java.util.HashMap;
import java.util.Map;

public class CookieStore {

  private Map<String, String> cookies = new HashMap<>();

  public CookieStore() {
  }

  public CookieStore(Map<String, String> cookies) {
    this.cookies = cookies;
  }

  public Map<String, String> getCookies() {
    return cookies;
  }

  public void addCookie(String key, String cookie) {
    cookies.put(key, cookie);
  }

}
