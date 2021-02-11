package com.khouloud.gestion_prod.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.khouloud.gestion_prod.dto.TokenDto;
import com.khouloud.gestion_prod.model.Category;
import com.khouloud.gestion_prod.model.Fournisseur;
import com.khouloud.gestion_prod.repository.CategoryRepository;
import com.khouloud.gestion_prod.repository.FournisseurRepository;
//import com.khouloud.gestion_prod.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.web.bind.annotation.*;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/oauth")
@CrossOrigin
public class OauthController {

    @Value("${google.clientId}")
    String googleClientId;

    /*@Autowired
    private FournisseurRepository fournisseurRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;*/

    /*@Value("${secretPsw}")
    String secretPsw;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    FournisseurRepository fournisseurRepository;

    @Autowired
    CategoryRepository categoryRepository;*/

    /*public OauthController(FournisseurRepository fournisseurRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.fournisseurRepository = fournisseurRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody Fournisseur fournisseur) {
        fournisseur.setPassword(bCryptPasswordEncoder.encode(fournisseur.getPassword()));
        fournisseurRepository.save(fournisseur);
    }*/

    @PostMapping("/google")
    public ResponseEntity<?> google(@RequestBody TokenDto tokenDto) throws IOException {
        final NetHttpTransport transport = new NetHttpTransport();
        final JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();

        GoogleIdTokenVerifier.Builder verifier =
                new GoogleIdTokenVerifier.Builder(transport, jacksonFactory)
                        .setAudience(Collections.singletonList(googleClientId));
        final GoogleIdToken googleIdToken = GoogleIdToken.parse(verifier.getJsonFactory(), tokenDto.getValue());
        final GoogleIdToken.Payload payload = googleIdToken.getPayload();

        return new ResponseEntity(payload, HttpStatus.OK);

       /* Fournisseur fournisseur = new Fournisseur();
        if(fournisseurRepository.existsByEmailId(fournisseur.getEmailId()))
            fournisseur = fournisseurRepository.findByEmailId(fournisseur.getEmailId()).get();
        else
            fournisseur = saveFournisseur(fournisseur.getEmailId());
        TokenDto tokenRes = login(fournisseur);
        return new ResponseEntity(tokenRes, HttpStatus.OK);*/
    }

    @PostMapping("/facebook")
    public ResponseEntity<?> facebook(@RequestBody TokenDto tokenDto) {
        Facebook facebook = new FacebookTemplate(tokenDto.getValue());
        final String [] fields = {"email", "picture"};
        User user = facebook.fetchObject("me", User.class /*, fields*/);

        return new ResponseEntity(user, HttpStatus.OK);

        /*Fournisseur fournisseur = new Fournisseur();
        if(fournisseurRepository.existsByEmailId(fournisseur.getEmailId()))
            fournisseur = fournisseurRepository.findByEmailId(fournisseur.getEmailId()).get();
        else
            fournisseur = saveFournisseur(fournisseur.getEmailId());
        TokenDto tokenRes = login(fournisseur);
        return new ResponseEntity(tokenRes, HttpStatus.OK);*/
    }

    /*private TokenDto login(Fournisseur fournisseur){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(fournisseur.getEmailId(), secretPsw)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        TokenDto tokenDto = new TokenDto();
        tokenDto.setValue(jwt);
        return tokenDto;
    }

    private Fournisseur saveFournisseur(String emailId){
        Fournisseur fournisseur = new Fournisseur(emailId, passwordEncoder.encode(secretPsw));
        return fournisseurRepository.save(fournisseur);
    }*/

}
