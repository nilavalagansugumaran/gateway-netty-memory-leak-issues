package org.sample.filters.conditional;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.sample.util.GatewayFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;


@Component
@Slf4j
public class SampleResponseDecoratorGatewayFilter implements GatewayFilter, Ordered {

    @Autowired private GatewayFilterService gatewayFilterService;

  @Override
  public int getOrder() {
    return -2;
  }

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
      ServerHttpResponse originalResponse = exchange.getResponse();
      DataBufferFactory bufferFactory = originalResponse.bufferFactory();
      ServerHttpResponseDecorator decoratedResponse =
              new ServerHttpResponseDecorator(originalResponse) {
                  @Override
                  public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                      if (body instanceof Flux) {
                          Flux<? extends DataBuffer> flux = (Flux<? extends DataBuffer>) body;
                          return super.writeWith(
                                  flux.buffer()
                                          .map(
                                                  dataBuffers -> {
                                                      // Need some logic to set this boolean flag
                                                      Map<String, String> headers =
                                                              exchange.getResponse().getHeaders().toSingleValueMap();
                                                      log.debug("Response Decorator Headers {}", headers);
                                                      byte[] receivedBytes = gatewayFilterService.retrieveBytes(dataBuffers);
                                                      String receivedString = new String(receivedBytes);
                                                      log.debug("Response received response {}", receivedString);
                                                      String modified = receivedString + " decorated staring";
                                                      log.debug("modified response {}", modified);
                                                      return bufferFactory.wrap(modified.getBytes());
                                                  }));
                      }

                      return super.writeWith(body); // if body is not a flux. never got there.
                  }
              };
      return chain.filter(
              exchange.mutate().response(decoratedResponse).build()); // replace response with decorator
  }

    @Component
    public class SampleResponseDecoratorGatewayFilterFactory extends AbstractNameConfigGatewayFilterFactory {

        @Autowired
        private SampleResponseDecoratorGatewayFilter gatewayFilter;

        @Override
        public GatewayFilter apply(NameConfig config) {
            return gatewayFilter;
        }
    }
}
