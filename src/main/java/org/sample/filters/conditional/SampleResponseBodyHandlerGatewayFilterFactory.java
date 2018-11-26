package org.sample.filters.conditional;

import org.reactivestreams.Publisher;
import org.sample.function.ReadFormDataFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteFunction;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.cloud.gateway.support.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.DefaultClientResponse;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.client.reactive.ClientHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR;

@Component
public class SampleResponseBodyHandlerGatewayFilterFactory extends AbstractNameConfigGatewayFilterFactory {

    @Autowired
    private ReadFormDataFunction readFormDataFunction;

    @Override
    public GatewayFilter apply(NameConfig config) {
        SampleResponseBodyHandlerGatewayFilterFactory.Config config1 =  new SampleResponseBodyHandlerGatewayFilterFactory.Config();
        config1.setInClass(String.class);
        config1.setOutClass(String.class);
        config1.setRewriteFunction(readFormDataFunction);
        return new SampleResponseBodyHandlerGatewayFilter(config1);
    }

    @Component
    public class SampleResponseBodyHandlerGatewayFilter implements GatewayFilter, Ordered {
        private Config config;

        public SampleResponseBodyHandlerGatewayFilter(Config config) {
            this.config = config;
        }

        @Override
        @SuppressWarnings("unchecked")
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

            ServerHttpResponseDecorator responseDecorator = new ServerHttpResponseDecorator(exchange.getResponse()) {

                @Override
                public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {

                    Class inClass = config.getInClass();
                    Class outClass = config.getOutClass();

                    String originalResponseContentType = exchange.getAttribute(ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR);
                    HttpHeaders httpHeaders = new HttpHeaders();
                    //explicitly add it in this way instead of 'httpHeaders.setContentType(originalResponseContentType)'
                    //this will prevent exception in case of using non-standard media types like "Content-Type: image"
                    httpHeaders.add(HttpHeaders.CONTENT_TYPE, originalResponseContentType);
                    ResponseAdapter responseAdapter = new ResponseAdapter(body, httpHeaders);
                    DefaultClientResponse clientResponse = new DefaultClientResponse(responseAdapter, ExchangeStrategies.withDefaults());

                    //TODO: flux or mono
                    Mono modifiedBody = clientResponse.bodyToMono(inClass)
                            .flatMap(originalBody -> config.rewriteFunction.apply(exchange, originalBody));

                    BodyInserter bodyInserter = BodyInserters.fromPublisher(modifiedBody, outClass);
                    CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, exchange.getResponse().getHeaders());
                    return bodyInserter.insert(outputMessage, new BodyInserterContext())
                            .then(Mono.defer(() -> {
                                Flux<DataBuffer> messageBody = outputMessage.getBody();
                                HttpHeaders headers = getDelegate().getHeaders();
                                if (!headers.containsKey(HttpHeaders.TRANSFER_ENCODING)) {
                                    messageBody = messageBody.doOnNext(data -> headers.setContentLength(data.readableByteCount()));
                                }
                                //TODO: use isStreamingMediaType?
                                return getDelegate().writeWith(messageBody);
                            }));
                }

                @Override
                public Mono<Void> writeAndFlushWith(Publisher<? extends Publisher<? extends DataBuffer>> body) {
                    return writeWith(Flux.from(body)
                            .flatMapSequential(p -> p));
                }
            };

            return chain.filter(exchange.mutate().response(responseDecorator).build());
        }

        @Override
        public int getOrder() {
            return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 1;
        }

    }

    public class ResponseAdapter implements ClientHttpResponse {

        private final Flux<DataBuffer> flux;
        private final HttpHeaders headers;

        public ResponseAdapter(Publisher<? extends DataBuffer> body, HttpHeaders headers) {
            this.headers = headers;
            if (body instanceof Flux) {
                flux = (Flux) body;
            } else {
                flux = ((Mono)body).flux();
            }
        }

        @Override
        public Flux<DataBuffer> getBody() {
            return flux;
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }

        @Override
        public HttpStatus getStatusCode() {
            return null;
        }

        @Override
        public int getRawStatusCode() {
            return 0;
        }

        @Override
        public MultiValueMap<String, ResponseCookie> getCookies() {
            return null;
        }
    }

    public static class Config {
        private Class inClass;
        private Class outClass;
        private Map<String, Object> inHints;
        private Map<String, Object> outHints;
        private String newContentType;

        private RewriteFunction rewriteFunction;

        public Class getInClass() {
            return inClass;
        }

        public Config setInClass(Class inClass) {
            this.inClass = inClass;
            return this;
        }

        public Class getOutClass() {
            return outClass;
        }

        public Config setOutClass(Class outClass) {
            this.outClass = outClass;
            return this;
        }

        public Map<String, Object> getInHints() {
            return inHints;
        }

        public Config setInHints(Map<String, Object> inHints) {
            this.inHints = inHints;
            return this;
        }

        public Map<String, Object> getOutHints() {
            return outHints;
        }

        public Config setOutHints(Map<String, Object> outHints) {
            this.outHints = outHints;
            return this;
        }

        public String getNewContentType() {
            return newContentType;
        }

        public Config setNewContentType(String newContentType) {
            this.newContentType = newContentType;
            return this;
        }

        public RewriteFunction getRewriteFunction() {
            return rewriteFunction;
        }

        public <T, R> Config setRewriteFunction(Class<T> inClass, Class<R> outClass,
                                                RewriteFunction<T, R> rewriteFunction) {
            setInClass(inClass);
            setOutClass(outClass);
            setRewriteFunction(rewriteFunction);
            return this;
        }

        public Config setRewriteFunction(RewriteFunction rewriteFunction) {
            this.rewriteFunction = rewriteFunction;
            return this;
        }
    }


}

