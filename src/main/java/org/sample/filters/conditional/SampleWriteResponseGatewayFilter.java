package org.sample.filters.conditional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Component
@Slf4j
public class SampleWriteResponseGatewayFilter implements GatewayFilter, Ordered {

  @Override
  public int getOrder() {
    return 10;
  }

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    return chain
        .filter(exchange)
        .then(
            Mono.defer(
                () -> {
                  log.debug(">>>");
                  DataBuffer buffer =
                      exchange.getResponse().bufferFactory().wrap(responseString().getBytes());
                  ServerHttpResponse response = exchange.getResponse();
                    log.debug("return the response");
                    return response
                        .writeAndFlushWith(Flux.just(Flux.just(buffer)))
                        .then(Mono.fromRunnable(() -> log.debug("<<<")));

                }));
  }

    @Component
    public class SampleWriteResponseGatewayFilterFactory extends AbstractNameConfigGatewayFilterFactory {

        @Autowired
        private SampleWriteResponseGatewayFilter gatewayFilter;

        @Override
        public GatewayFilter apply(NameConfig config) {
            return gatewayFilter;
        }
    }

    private String responseString(){
      return  "{\n" +
              "  \"jsonapi\": {\n" +
              "    \"version\": \"1.0\"\n" +
              "  },\n" +
              "  \"data\": {\n" +
              "    \"id\": \"abc\",\n" +
              "    \"type\": \"modified\",\n" +
              "    \"attributes\": {\n" +
              "      \"value\": \"abc\",\n" +
              "      \"status\": false\n" +
              "    }\n" +
              "  }\n" +
              "}";
    }
}
