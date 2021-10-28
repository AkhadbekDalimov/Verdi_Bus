package uz.asbt.digid.crmservice.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ModelMapperConfig {

  public static void configMapper(final ModelMapper modelMapper) {
    modelMapper.getConfiguration()
      .setFieldAccessLevel(AccessLevel.PUBLIC)
      .setMethodAccessLevel(AccessLevel.PUBLIC)
      .setMatchingStrategy(MatchingStrategies.STRICT)
      .setFieldMatchingEnabled(Boolean.TRUE)
      .setSkipNullEnabled(Boolean.TRUE)
      .setFieldAccessLevel(AccessLevel.PRIVATE);
  }

  @Bean
  @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
  public ModelMapper modelMapper() {

    final ModelMapper mapper = new ModelMapper();

    mapper.validate();
    configMapper(mapper);

    return mapper;
  }

}
