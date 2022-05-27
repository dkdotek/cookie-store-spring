package gateway;

import gateway.filter.ReplaceCookieFilterFactory;
import gateway.filter.StoreCookieFilterFactory;
import gateway.filter.model.CookieStoreConfig;
import gateway.model.CookieStore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

// tag::code[]
@SpringBootApplication
@EnableConfigurationProperties(UriConfiguration.class)
@RestController
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public CookieStore cookieStore() {
    return new CookieStore();
  }

  // tag::route-locator[]
  @Bean
  public RouteLocator myRoutes(RouteLocatorBuilder builder,
      StoreCookieFilterFactory storeCookieFilterFactory,
      ReplaceCookieFilterFactory replaceCookieFilterFactory, CookieStore cookieStore) {
    return builder.routes()
        .route(p -> p
            .path("/setcookie")
            .filters(f -> f.filter(
                storeCookieFilterFactory.apply(new CookieStoreConfig("mycookie", cookieStore))))
            .uri("http://localhost:8080"))
        .route(p -> p
            .path("/requirecookie")
            .filters(f -> f.filter(
                replaceCookieFilterFactory.apply(new CookieStoreConfig("mycookie", cookieStore))
            ))
            .uri("http://localhost:8080"))
        .build();
  }
  // end::route-locator[]

  // tag::fallback[]
  @RequestMapping("/fallback")
  public Mono<String> fallback() {
    return Mono.just("fallback");
  }
  // end::fallback[]
}

// tag::uri-configuration[]
@ConfigurationProperties
class UriConfiguration {

  private String httpbin = "http://httpbin.org:80";

  public String getHttpbin() {
    return httpbin;
  }

  public void setHttpbin(String httpbin) {
    this.httpbin = httpbin;
  }
}
// end::uri-configuration[]
// end::code[]