package com.techlmaginia.salex;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static com.techlmaginia.salex.AuthController.authToken;

@Configuration
@Slf4j
public class AuthFilter implements GlobalFilter, Ordered {

    @Bean
    public GlobalFilter customFilter() {

        return new AuthFilter();
    }
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //compareAuthKey(requestHeaders);
        return chain.filter(exchange).then((Mono.fromRunnable(() -> {
            String responseHeaders = exchange.getRequest().getHeaders().toSingleValueMap().toString();
            HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
            Object formData = exchange.getAttribute(ServerWebExchangeUtils.CACHED_REQUEST_BODY_ATTR);
            String reqHeader="RequestAddress : "+
                    exchange.getRequest().getRemoteAddress()+" ReferredBy : "+
                    exchange.getRequest().getHeaders().get("X-Requested-With")+" URI : "
                    +exchange.getRequest().getURI()+" Request : "+formData;
            log.info(reqHeader);

        })));
    }

    void compareAuthKey(HttpHeaders requestHeaders){
        if(requestHeaders.get("user_token")!=null
                && requestHeaders.get("user_token").equals(authToken)){
            String key = "SALEX_KEY_FOR_ENCRYPT_DECRYPT"; // 128 bit key
            // Create key and cipher
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = null;
            try {
                cipher = Cipher.getInstance("AES");
                // decrypt the text
                cipher.init(Cipher.DECRYPT_MODE, aesKey);
                String decrypted = new String(cipher.doFinal(String.valueOf(requestHeaders.get("user_token")).getBytes()));
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (NoSuchPaddingException e) {
                throw new RuntimeException(e);
            } catch (IllegalBlockSizeException e) {
                throw new RuntimeException(e);
            } catch (BadPaddingException e) {
                throw new RuntimeException(e);
            } catch (InvalidKeyException e) {
                throw new RuntimeException(e);
            }

        }else{
            throw new RuntimeException("Token Invalid");
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }

}