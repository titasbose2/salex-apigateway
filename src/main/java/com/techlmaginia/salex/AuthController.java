package com.techlmaginia.salex;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@CrossOrigin("*")
public class AuthController {
    public static String authToken;
    String key = "Bar12345Bar12345"; // 128 bit key

    @PostMapping(value = "/testhi",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> test()  {
        return new ResponseEntity<String>("hi", HttpStatus.OK);
    }

    @GetMapping(value = "/getAuth",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAuth() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // Create key and cipher
        Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        // encrypt the text
        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
        byte[] encrypted = cipher.doFinal(String.valueOf(System.currentTimeMillis()).getBytes());
        authToken=new String(encrypted);
        System.out.println(
                "authtoken    "
                        + authToken);
        return new ResponseEntity<String>(authToken, HttpStatus.OK);
    }


//    @Scheduled(fixedRate = 15*60*1000)
//    public void scheduleTask() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
//        SimpleDateFormat dateFormat = new SimpleDateFormat(
//                "dd-MM-yyyy HH:mm:ss.SSS");
//
//        String strDate = dateFormat.format(new Date());
//        Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
//        Cipher cipher = Cipher.getInstance("AES");
//        // encrypt the text
//        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
//        byte[] encrypted = cipher.doFinal(String.valueOf(System.currentTimeMillis()).getBytes());
//        authToken=new String(encrypted);
//        System.out.println(
//                "Cron job Scheduler: Job running at - "
//                        + strDate);
////        System.out.println(
////                "authtoken    "
////                        + authToken);
//    }
}
