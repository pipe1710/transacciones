package com.example.transacciones.config;

import com.example.transacciones.domain.Transaction;
import com.example.transacciones.service.IProcessRestClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {


    @Bean
    public WebClient buildWebClient(WebClient.Builder webClient){
        return webClient
                .baseUrl("http://localhost:8090")
                .defaultStatusHandler(HttpStatusCode::is4xxClientError, clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new Exception400(errorBody)));
                })
               // .defaultStatusHandler(HttpStatusCode::is5xxServerError, clientResponse -> {})
                .build();
    }




    @Bean
    public IProcessRestClient buildProcessRestClient(WebClient client){

        HttpServiceProxyFactory factory =  HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(client)).build();

        return factory.createClient(IProcessRestClient.class);


    }
}
