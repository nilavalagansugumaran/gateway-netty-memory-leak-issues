package org.sample.function;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteFunction;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.sample.filters.conditional.SampleWriteResponseGatewayFilter.responseString;

@Slf4j
@Component
public class ReadFormDataFunction implements RewriteFunction<String, String> {

  @Override
  public Mono<String> apply(ServerWebExchange exchange, String received) {

    log.debug("actual response {}", received);
    if (received != null) {
     // String[] formData = received.split("&");
    }
    return Mono.just(responseString());
  }
}
