package org.sample.filters.conditional;

import org.sample.function.ReadFormDataFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;

@Component
public class SampleReadResponsesGatewayFilterFactory
    extends ModifyResponseBodyGatewayFilterFactory {

  @Autowired private ReadFormDataFunction readFormDataFunction;

  @Autowired private ServerCodecConfigurer codecConfigurer;

  public SampleReadResponsesGatewayFilterFactory(ServerCodecConfigurer codecConfigurer) {
    super(codecConfigurer);
  }

  @Override
  public GatewayFilter apply(Config config) {
    Config readResponseConfig =
        new Config();
    readResponseConfig.setInClass(String.class);
    readResponseConfig.setOutClass(String.class);
    readResponseConfig.setRewriteFunction(readFormDataFunction);
    return new ModifyResponseGatewayFilter(readResponseConfig);
  }
}
