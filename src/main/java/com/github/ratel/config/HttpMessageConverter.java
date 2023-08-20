package com.github.ratel.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class HttpMessageConverter implements WebMvcConfigurer {

    @Override
    public void extendMessageConverters(List<org.springframework.http.converter.HttpMessageConverter<?>> converters) {
        converters.add(mappingJackson2HttpMessageConverter());

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter() {
            @Override
            public boolean canRead(Type type, Class<?> contextClass, MediaType mediaType) {
                RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
                if (requestAttributes == null) {
                    return false;
                }
                HandlerMethod handlerMethod = (HandlerMethod) requestAttributes
                        .getAttribute(HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
                if (handlerMethod == null) {
                    return false;
                }
                RequestMapping requestMapping = handlerMethod.getMethodAnnotation(RequestMapping.class);
                if (requestMapping == null) {
                    return false;
                }
                if (requestMapping.consumes().length != 1
                        || !MediaType.MULTIPART_FORM_DATA_VALUE.equals(requestMapping.consumes()[0])) {
                    return false;
                }
                return super.canRead(type, contextClass, mediaType);
            }

            @Override
            protected boolean canWrite(MediaType mediaType) {
                return false;
            }
        };
        List<MediaType> supportedMediaTypes = new ArrayList<>(converter.getSupportedMediaTypes());
        supportedMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
        converter.setSupportedMediaTypes(supportedMediaTypes);

        converters.add(converter);
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        SimpleModule module = new SimpleModule();
        module.addDeserializer(String.class, new StdDeserializer(String.class) {
            @Override
            public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                return jsonParser.getText() != null ? jsonParser.getText().trim() : null;
            }
        });
        mapper.registerModule(module);

        converter.setObjectMapper(mapper);

        return converter;
    }

}
