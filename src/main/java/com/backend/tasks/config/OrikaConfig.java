package com.backend.tasks.config;

import com.backend.tasks.domain.dto.view.UserViewDto;
import com.backend.tasks.domain.entity.User;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class OrikaConfig {

    @Bean
    public MapperFacade mapperFacade() {
        return mapperFactory().getMapperFacade();
    }

    @Bean
    public MapperFactory mapperFactory() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().mapNulls(false).build();
        mapperFactory.classMap(String.class, Date.class).register();
        mapperFactory.classMap(Long.class, Date.class).register();

        mapperFactory.classMap(User.class, UserViewDto.class)
                .field("organization.id", "organizationId")
                .byDefault()
                .register();

        return mapperFactory;
    }

}
