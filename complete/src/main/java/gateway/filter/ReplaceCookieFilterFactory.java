package gateway.filter;


import gateway.filter.model.CookieStoreConfig;
import java.util.List;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class ReplaceCookieFilterFactory extends
    AbstractGatewayFilterFactory<CookieStoreConfig> {

  @Override
  public GatewayFilter apply(CookieStoreConfig config) {
    return (exchange, chain) -> {
      exchange.getRequest().mutate().headers(
          (httpHeaders) -> {
            List<String> cookie = httpHeaders.get("Cookie");
            String replacedCookie = cookie.get(0)
                .replaceAll("(.*)" + config.getCookieKey() + "=.*(.*)", // (.*)mycookie=.*(.*)
                    "$1" + config.getCookieStore().getCookies()
                        .get(config.getCookieKey()) + ";$2");
            httpHeaders.set("Cookie", replacedCookie);
          });
      return chain.filter(exchange);
    };
  }
}
