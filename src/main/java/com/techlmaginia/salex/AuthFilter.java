//package com.techlmaginia.salex;
//
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.Ordered;
//import org.springframework.http.HttpHeaders;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//import static com.techlmaginia.salex.AuthController.authToken;
//
//@Configuration
//public class AuthFilter implements GlobalFilter, Ordered {
//    @Bean
//    public GlobalFilter customFilter() {
//        return new AuthFilter();
//    }
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
//        if(requestHeaders.get("user_token")!=null
//        && requestHeaders.get("user_token").equals(authToken)){
////            String key = "SALEX_KEY_FOR_ENCRYPT_DECRYPT"; // 128 bit key
////            // Create key and cipher
////            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
////            Cipher cipher = null;
////            try {
////                cipher = Cipher.getInstance("AES");
////                // decrypt the text
////                cipher.init(Cipher.DECRYPT_MODE, aesKey);
////                String decrypted = new String(cipher.doFinal(String.valueOf(requestHeaders.get("user_token")).getBytes()));
////            } catch (NoSuchAlgorithmException e) {
////                throw new RuntimeException(e);
////            } catch (NoSuchPaddingException e) {
////                throw new RuntimeException(e);
////            } catch (IllegalBlockSizeException e) {
////                throw new RuntimeException(e);
////            } catch (BadPaddingException e) {
////                throw new RuntimeException(e);
////            } catch (InvalidKeyException e) {
////                throw new RuntimeException(e);
////            }
//
//        }else{
//            throw new RuntimeException("Token Invalid");
//        }
//        return chain.filter(exchange);
//    }
//
//    @Override
//    public int getOrder() {
//        return -1;
//    }
//}