package edu.miu.waa.config;

import edu.miu.waa.dto.response.UserOutDto;
import edu.miu.waa.model.User;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();

        Converter<User, String> roleConverter = ctx ->
                ctx.getSource().getRoles().size() > 0 ? ctx.getSource().getRoles().get(0).getRole() : "";

        modelMapper.addMappings(new PropertyMap<User, UserOutDto>() {
            @Override
            protected void configure() {
                using(roleConverter).map(source, destination.getRole());
            }
        });

        return modelMapper;
    }
}