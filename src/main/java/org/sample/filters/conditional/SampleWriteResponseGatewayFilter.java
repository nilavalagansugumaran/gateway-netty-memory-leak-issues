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

    public static String responseString(){
        return  "{\n" +
                "    \"jsonapi\": {\n" +
                "        \"version\": \"1.0\"\n" +
                "    },\n" +
                "    \"data\": [\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwQU0xNTExMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTEtMTVUMDk6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTEtMTVUMTM6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-11-15T09:00:00Z\",\n" +
                "                \"end-time\": \"2018-11-15T13:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwUE0xNTExMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTEtMTVUMTM6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTEtMTVUMTc6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-11-15T13:00:00Z\",\n" +
                "                \"end-time\": \"2018-11-15T17:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwRVYxNTExMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTEtMTVUMTc6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTEtMTVUMjE6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-11-15T17:00:00Z\",\n" +
                "                \"end-time\": \"2018-11-15T21:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwQU0xNjExMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTEtMTZUMDk6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTEtMTZUMTM6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-11-16T09:00:00Z\",\n" +
                "                \"end-time\": \"2018-11-16T13:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwUE0xNjExMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTEtMTZUMTM6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTEtMTZUMTc6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-11-16T13:00:00Z\",\n" +
                "                \"end-time\": \"2018-11-16T17:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwQU0xNzExMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTEtMTdUMDk6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTEtMTdUMTM6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-11-17T09:00:00Z\",\n" +
                "                \"end-time\": \"2018-11-17T13:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwUE0xNzExMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTEtMTdUMTM6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTEtMTdUMTc6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-11-17T13:00:00Z\",\n" +
                "                \"end-time\": \"2018-11-17T17:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwQU0xOTExMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTEtMTlUMDk6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTEtMTlUMTM6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-11-19T09:00:00Z\",\n" +
                "                \"end-time\": \"2018-11-19T13:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwUE0xOTExMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTEtMTlUMTM6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTEtMTlUMTc6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-11-19T13:00:00Z\",\n" +
                "                \"end-time\": \"2018-11-19T17:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwRVYxOTExMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTEtMTlUMTc6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTEtMTlUMjE6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-11-19T17:00:00Z\",\n" +
                "                \"end-time\": \"2018-11-19T21:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwQU0yMDExMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTEtMjBUMDk6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTEtMjBUMTM6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-11-20T09:00:00Z\",\n" +
                "                \"end-time\": \"2018-11-20T13:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwUE0yMDExMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTEtMjBUMTM6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTEtMjBUMTc6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-11-20T13:00:00Z\",\n" +
                "                \"end-time\": \"2018-11-20T17:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwRVYyMDExMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTEtMjBUMTc6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTEtMjBUMjE6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-11-20T17:00:00Z\",\n" +
                "                \"end-time\": \"2018-11-20T21:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwQU0yMTExMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTEtMjFUMDk6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTEtMjFUMTM6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-11-21T09:00:00Z\",\n" +
                "                \"end-time\": \"2018-11-21T13:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwUE0yMTExMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTEtMjFUMTM6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTEtMjFUMTc6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-11-21T13:00:00Z\",\n" +
                "                \"end-time\": \"2018-11-21T17:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwRVYyMTExMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTEtMjFUMTc6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTEtMjFUMjE6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-11-21T17:00:00Z\",\n" +
                "                \"end-time\": \"2018-11-21T21:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwQU0yMjExMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTEtMjJUMDk6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTEtMjJUMTM6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-11-22T09:00:00Z\",\n" +
                "                \"end-time\": \"2018-11-22T13:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwUE0yMjExMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTEtMjJUMTM6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTEtMjJUMTc6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-11-22T13:00:00Z\",\n" +
                "                \"end-time\": \"2018-11-22T17:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwRVYyMjExMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTEtMjJUMTc6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTEtMjJUMjE6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-11-22T17:00:00Z\",\n" +
                "                \"end-time\": \"2018-11-22T21:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwQU0yMzExMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTEtMjNUMDk6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTEtMjNUMTM6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-11-23T09:00:00Z\",\n" +
                "                \"end-time\": \"2018-11-23T13:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwUE0yMzExMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTEtMjNUMTM6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTEtMjNUMTc6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-11-23T13:00:00Z\",\n" +
                "                \"end-time\": \"2018-11-23T17:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwQU0yNDExMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTEtMjRUMDk6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTEtMjRUMTM6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-11-24T09:00:00Z\",\n" +
                "                \"end-time\": \"2018-11-24T13:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwUE0yNDExMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTEtMjRUMTM6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTEtMjRUMTc6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-11-24T13:00:00Z\",\n" +
                "                \"end-time\": \"2018-11-24T17:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwQU0yNjExMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTEtMjZUMDk6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTEtMjZUMTM6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-11-26T09:00:00Z\",\n" +
                "                \"end-time\": \"2018-11-26T13:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwUE0yNjExMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTEtMjZUMTM6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTEtMjZUMTc6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-11-26T13:00:00Z\",\n" +
                "                \"end-time\": \"2018-11-26T17:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwRVYyNjExMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTEtMjZUMTc6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTEtMjZUMjE6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-11-26T17:00:00Z\",\n" +
                "                \"end-time\": \"2018-11-26T21:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwQU0yNzExMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTEtMjdUMDk6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTEtMjdUMTM6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-11-27T09:00:00Z\",\n" +
                "                \"end-time\": \"2018-11-27T13:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwUE0yNzExMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTEtMjdUMTM6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTEtMjdUMTc6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-11-27T13:00:00Z\",\n" +
                "                \"end-time\": \"2018-11-27T17:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwRVYyNzExMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTEtMjdUMTc6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTEtMjdUMjE6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-11-27T17:00:00Z\",\n" +
                "                \"end-time\": \"2018-11-27T21:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwQU0yODExMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTEtMjhUMDk6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTEtMjhUMTM6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-11-28T09:00:00Z\",\n" +
                "                \"end-time\": \"2018-11-28T13:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwUE0yODExMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTEtMjhUMTM6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTEtMjhUMTc6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-11-28T13:00:00Z\",\n" +
                "                \"end-time\": \"2018-11-28T17:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwRVYyODExMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTEtMjhUMTc6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTEtMjhUMjE6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-11-28T17:00:00Z\",\n" +
                "                \"end-time\": \"2018-11-28T21:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwQU0yOTExMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTEtMjlUMDk6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTEtMjlUMTM6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-11-29T09:00:00Z\",\n" +
                "                \"end-time\": \"2018-11-29T13:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwUE0yOTExMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTEtMjlUMTM6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTEtMjlUMTc6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-11-29T13:00:00Z\",\n" +
                "                \"end-time\": \"2018-11-29T17:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwRVYyOTExMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTEtMjlUMTc6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTEtMjlUMjE6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-11-29T17:00:00Z\",\n" +
                "                \"end-time\": \"2018-11-29T21:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwQU0zMDExMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTEtMzBUMDk6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTEtMzBUMTM6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-11-30T09:00:00Z\",\n" +
                "                \"end-time\": \"2018-11-30T13:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwUE0zMDExMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTEtMzBUMTM6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTEtMzBUMTc6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-11-30T13:00:00Z\",\n" +
                "                \"end-time\": \"2018-11-30T17:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwQU0wMTEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMDFUMDk6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMDFUMTM6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-01T09:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-01T13:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwUE0wMTEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMDFUMTM6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMDFUMTc6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-01T13:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-01T17:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwQU0wMzEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMDNUMDk6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMDNUMTM6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-03T09:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-03T13:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwUE0wMzEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMDNUMTM6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMDNUMTc6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-03T13:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-03T17:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwRVYwMzEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMDNUMTc6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMDNUMjE6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-03T17:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-03T21:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwQU0wNDEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMDRUMDk6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMDRUMTM6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-04T09:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-04T13:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwUE0wNDEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMDRUMTM6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMDRUMTc6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-04T13:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-04T17:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwRVYwNDEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMDRUMTc6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMDRUMjE6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-04T17:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-04T21:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwQU0wNTEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMDVUMDk6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMDVUMTM6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-05T09:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-05T13:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwUE0wNTEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMDVUMTM6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMDVUMTc6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-05T13:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-05T17:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwRVYwNTEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMDVUMTc6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMDVUMjE6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-05T17:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-05T21:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwQU0wNjEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMDZUMDk6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMDZUMTM6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-06T09:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-06T13:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwUE0wNjEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMDZUMTM6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMDZUMTc6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-06T13:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-06T17:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwRVYwNjEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMDZUMTc6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMDZUMjE6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-06T17:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-06T21:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwQU0wNzEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMDdUMDk6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMDdUMTM6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-07T09:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-07T13:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwUE0wNzEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMDdUMTM6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMDdUMTc6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-07T13:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-07T17:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwQU0wODEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMDhUMDk6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMDhUMTM6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-08T09:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-08T13:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwUE0wODEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMDhUMTM6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMDhUMTc6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-08T13:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-08T17:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwQU0xMDEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMTBUMDk6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMTBUMTM6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-10T09:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-10T13:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwUE0xMDEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMTBUMTM6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMTBUMTc6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-10T13:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-10T17:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwRVYxMDEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMTBUMTc6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMTBUMjE6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-10T17:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-10T21:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwQU0xMTEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMTFUMDk6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMTFUMTM6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-11T09:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-11T13:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwUE0xMTEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMTFUMTM6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMTFUMTc6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-11T13:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-11T17:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwRVYxMTEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMTFUMTc6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMTFUMjE6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-11T17:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-11T21:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwQU0xMjEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMTJUMDk6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMTJUMTM6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-12T09:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-12T13:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwUE0xMjEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMTJUMTM6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMTJUMTc6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-12T13:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-12T17:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwRVYxMjEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMTJUMTc6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMTJUMjE6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-12T17:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-12T21:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwQU0xMzEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMTNUMDk6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMTNUMTM6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-13T09:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-13T13:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwUE0xMzEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMTNUMTM6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMTNUMTc6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-13T13:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-13T17:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwRVYxMzEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMTNUMTc6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMTNUMjE6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-13T17:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-13T21:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwQU0xNDEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMTRUMDk6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMTRUMTM6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-14T09:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-14T13:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwUE0xNDEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMTRUMTM6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMTRUMTc6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-14T13:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-14T17:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwQU0xNTEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMTVUMDk6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMTVUMTM6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-15T09:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-15T13:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwUE0xNTEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMTVUMTM6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMTVUMTc6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-15T13:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-15T17:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwQU0xNzEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMTdUMDk6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMTdUMTM6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-17T09:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-17T13:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwUE0xNzEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMTdUMTM6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMTdUMTc6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-17T13:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-17T17:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwRVYxNzEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMTdUMTc6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMTdUMjE6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-17T17:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-17T21:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwQU0xODEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMThUMDk6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMThUMTM6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-18T09:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-18T13:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwUE0xODEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMThUMTM6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMThUMTc6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-18T13:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-18T17:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwRVYxODEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMThUMTc6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMThUMjE6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-18T17:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-18T21:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwQU0xOTEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMTlUMDk6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMTlUMTM6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-19T09:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-19T13:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwUE0xOTEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMTlUMTM6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMTlUMTc6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-19T13:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-19T17:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwRVYxOTEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMTlUMTc6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMTlUMjE6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-19T17:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-19T21:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwQU0yMDEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMjBUMDk6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMjBUMTM6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-20T09:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-20T13:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwUE0yMDEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMjBUMTM6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMjBUMTc6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-20T13:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-20T17:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwRVYyMDEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMjBUMTc6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMjBUMjE6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-20T17:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-20T21:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwQU0yMTEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMjFUMDk6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMjFUMTM6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-21T09:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-21T13:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwUE0yMTEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMjFUMTM6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMjFUMTc6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-21T13:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-21T17:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwQU0yMjEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMjJUMDk6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMjJUMTM6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-22T09:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-22T13:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwUE0yMjEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMjJUMTM6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMjJUMTc6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-22T13:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-22T17:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwQU0yNDEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMjRUMDk6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMjRUMTM6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-24T09:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-24T13:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwUE0yNDEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMjRUMTM6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMjRUMTc6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-24T13:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-24T17:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwRVYyNDEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMjRUMTc6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMjRUMjE6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-24T17:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-24T21:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwQU0yNzEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMjdUMDk6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMjdUMTM6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-27T09:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-27T13:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwUE0yNzEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMjdUMTM6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMjdUMTc6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-27T13:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-27T17:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwRVYyNzEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMjdUMTc6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMjdUMjE6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-27T17:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-27T21:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwQU0yODEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMjhUMDk6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMjhUMTM6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-28T09:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-28T13:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwUE0yODEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMjhUMTM6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMjhUMTc6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-28T13:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-28T17:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwQU0yOTEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMjlUMDk6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMjlUMTM6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-29T09:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-29T13:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwUE0yOTEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMjlUMTM6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMjlUMTc6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-29T13:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-29T17:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwQU0zMTEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMzFUMDk6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMzFUMTM6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-31T09:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-31T13:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwUE0zMTEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMzFUMTM6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMzFUMTc6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-31T13:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-31T17:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwRVYzMTEyMjAxOCIsInN0YXJ0VGltZSI6IjIwMTgtMTItMzFUMTc6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTgtMTItMzFUMjE6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2018-12-31T17:00:00Z\",\n" +
                "                \"end-time\": \"2018-12-31T21:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwQU0wMjAxMjAxOSIsInN0YXJ0VGltZSI6IjIwMTktMDEtMDJUMDk6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTktMDEtMDJUMTM6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2019-01-02T09:00:00Z\",\n" +
                "                \"end-time\": \"2019-01-02T13:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwUE0wMjAxMjAxOSIsInN0YXJ0VGltZSI6IjIwMTktMDEtMDJUMTM6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTktMDEtMDJUMTc6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2019-01-02T13:00:00Z\",\n" +
                "                \"end-time\": \"2019-01-02T17:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwRVYwMjAxMjAxOSIsInN0YXJ0VGltZSI6IjIwMTktMDEtMDJUMTc6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTktMDEtMDJUMjE6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2019-01-02T17:00:00Z\",\n" +
                "                \"end-time\": \"2019-01-02T21:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwQU0wMzAxMjAxOSIsInN0YXJ0VGltZSI6IjIwMTktMDEtMDNUMDk6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTktMDEtMDNUMTM6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2019-01-03T09:00:00Z\",\n" +
                "                \"end-time\": \"2019-01-03T13:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwUE0wMzAxMjAxOSIsInN0YXJ0VGltZSI6IjIwMTktMDEtMDNUMTM6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTktMDEtMDNUMTc6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2019-01-03T13:00:00Z\",\n" +
                "                \"end-time\": \"2019-01-03T17:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwRVYwMzAxMjAxOSIsInN0YXJ0VGltZSI6IjIwMTktMDEtMDNUMTc6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTktMDEtMDNUMjE6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2019-01-03T17:00:00Z\",\n" +
                "                \"end-time\": \"2019-01-03T21:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwQU0wNDAxMjAxOSIsInN0YXJ0VGltZSI6IjIwMTktMDEtMDRUMDk6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTktMDEtMDRUMTM6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2019-01-04T09:00:00Z\",\n" +
                "                \"end-time\": \"2019-01-04T13:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwUE0wNDAxMjAxOSIsInN0YXJ0VGltZSI6IjIwMTktMDEtMDRUMTM6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTktMDEtMDRUMTc6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2019-01-04T13:00:00Z\",\n" +
                "                \"end-time\": \"2019-01-04T17:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwQU0wNTAxMjAxOSIsInN0YXJ0VGltZSI6IjIwMTktMDEtMDVUMDk6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTktMDEtMDVUMTM6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2019-01-05T09:00:00Z\",\n" +
                "                \"end-time\": \"2019-01-05T13:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwUE0wNTAxMjAxOSIsInN0YXJ0VGltZSI6IjIwMTktMDEtMDVUMTM6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTktMDEtMDVUMTc6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2019-01-05T13:00:00Z\",\n" +
                "                \"end-time\": \"2019-01-05T17:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwQU0wNzAxMjAxOSIsInN0YXJ0VGltZSI6IjIwMTktMDEtMDdUMDk6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTktMDEtMDdUMTM6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2019-01-07T09:00:00Z\",\n" +
                "                \"end-time\": \"2019-01-07T13:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwUE0wNzAxMjAxOSIsInN0YXJ0VGltZSI6IjIwMTktMDEtMDdUMTM6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTktMDEtMDdUMTc6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2019-01-07T13:00:00Z\",\n" +
                "                \"end-time\": \"2019-01-07T17:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Y2hp_eyJpZCI6ImEwQXcwMDAwRVYwNzAxMjAxOSIsInN0YXJ0VGltZSI6IjIwMTktMDEtMDdUMTc6MDA6MDBaIiwiZW5kVGltZSI6IjIwMTktMDEtMDdUMjE6MDA6MDBaIiwiYXZhaWxhYmxlIjp0cnVlLCJjbGFzc2lmaWNhdGlvbiI6ImZpeGVkLXNsb3QifQ--\",\n" +
                "            \"type\": \"appointment-slots\",\n" +
                "            \"attributes\": {\n" +
                "                \"start-time\": \"2019-01-07T17:00:00Z\",\n" +
                "                \"end-time\": \"2019-01-07T21:00:00Z\",\n" +
                "                \"available\": true,\n" +
                "                \"classification\": \"fixed-slot\"\n" +
                "            }\n" +
                "        }\n" +
                "    ]\n" +
                "}";
    }
}
