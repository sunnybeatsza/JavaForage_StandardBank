package com.example.Auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AuthController {
    Boolean isAuthenticated = false;

    @GetMapping("/")
    public String home(){
        return "Welcome to the home page!";
    }

    @GetMapping("/login")
    public String login(){
        AuthJWT auth = new AuthJWT();
        List<String> credentials = auth.authenticateUser();
        if (auth.isAuthenticated){
            try {
                KeyPair keyPair = KeyGen.generateRsaKeyPair();
                RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
                RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
                Algorithm algorithm = Algorithm.RSA256(rsaPublicKey, rsaPrivateKey);
                String token = JWT.create()
                        .withIssuer("auth0")
                        .withClaim("username", credentials.getFirst())
                        .withClaim("password", credentials.getLast())
                        .sign(algorithm);
                System.out.println(token);
                return "You are authenticated" + token;
            } catch (JWTCreationException exception){
                // Invalid Signing configuration / Couldn't convert Claims.
                System.out.println("Invalid Signing configuration / Couldn't convert Claims.");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
        return "Authentication failed!";
    }
}
