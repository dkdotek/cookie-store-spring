package gateway.filter;

import gateway.filter.model.CookieStoreConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class StoreCookieFilterFactory extends
    AbstractGatewayFilterFactory<CookieStoreConfig> {

  final Logger logger =
      LoggerFactory.getLogger(StoreCookieFilterFactory.class);


  public StoreCookieFilterFactory() {
    super(CookieStoreConfig.class);
  }

  @Override
  public GatewayFilter apply(CookieStoreConfig config) {
    return (exchange, chain) -> chain.filter(exchange)
        .then(Mono.fromRunnable(() -> {
          ServerHttpResponse response = exchange.getResponse();
          config.getCookieStore()
              .addCookie(config.getCookieKey(), response.getHeaders().get("Set-Cookie").get(0));
          logger.info("Global Post Filter executed");
        }));
  }
}