package org.sample.filters.conditional;

import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory.NameConfig;

public abstract class AbstractNameConfigGatewayFilterFactory
        extends AbstractGatewayFilterFactory<NameConfig> {
  public AbstractNameConfigGatewayFilterFactory() {
    super(NameConfig.class);
  }
}
