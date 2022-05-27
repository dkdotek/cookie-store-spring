package gateway.filter.model;

import gateway.model.CookieStore;

public class CookieStoreConfig {

  private String cookieKey;
  private CookieStore cookieStore;

  public CookieStoreConfig(String cookieKey, CookieStore cookieStore) {
    this.cookieKey = cookieKey;
    this.cookieStore = cookieStore;
  }

  public String getCookieKey() {
    return cookieKey;
  }

  public void setCookieKey(String cookieKey) {
    this.cookieKey = cookieKey;
  }

  public CookieStore getCookieStore() {
    return cookieStore;
  }

  public void setCookieStore(CookieStore cookieStore) {
    this.cookieStore = cookieStore;
  }

}
