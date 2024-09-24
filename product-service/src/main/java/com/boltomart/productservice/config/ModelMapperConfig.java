package com.boltomart.productservice.config;

import com.boltomart.productservice.entity.Product;
import com.boltomart.productservice.entity.ProductVariation;
import com.boltomart.productservice.response.ProductResponse;
import com.boltomart.productservice.response.ProductVariationResponse;
import org.hibernate.collection.spi.PersistentBag;
import org.modelmapper.Converter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
