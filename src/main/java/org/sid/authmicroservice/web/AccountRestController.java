package org.sid.authmicroservice.web;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.sid.authmicroservice.entities.Role;
import org.sid.authmicroservice.entities.Utilisateur;
import org.sid.authmicroservice.service.AccountService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class AccountRestController {

    private AccountService accountService;
    public AccountRestController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(path="/users")
    @PostAuthorize("hasAuthority('USER')")
    public List<Utilisateur> getAllUser(){
       return accountService.listUsers();
    }
    @PostMapping(path = "/users")
    @PostAuthorize("hasAuthority('ADMIN')")
    Utilisateur saveUser(@RequestBody Utilisateur utilisateur){
       return accountService.addutilisateur(utilisateur);
    }

    @PostMapping(path = "/roles")
    @PostAuthorize("hasAuthority('ADMIN')")
    Role saveUser(@RequestBody Role role){
        return accountService.addRole(role);
    }
    @PostMapping(path = "/addRoleToUser")
    public void  saveUser(@RequestBody RoleUserForm roleUserForm ){
         accountService.AddRoleToUtilisateur(roleUserForm.getUsername(),roleUserForm.getRoleNmame());
    }
    @GetMapping(path = "/refrechToken")
    public void refrechToken(HttpServletRequest request, HttpServletResponse response)throws Exception{
        String authToken=request.getHeader("Authorization");
        if (authToken!=null && authToken.startsWith("Bearer ")){
            try {
                String jwt=authToken.substring(7);
                Algorithm algorithm=Algorithm.HMAC256("mySecret1234");
                JWTVerifier jwtVerifier= JWT.require(algorithm).build();
                DecodedJWT decodedJWT=jwtVerifier.verify(jwt);
                String username=decodedJWT.getSubject();
                Utilisateur utilisateur=accountService.loadUtilisateurByusername(username);
                String JwtAccessToken= JWT.create().withSubject(utilisateur.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis()+1*60*1000)).withIssuer(request.getRequestURL().toString())
                        .withClaim("roles",utilisateur.getRoles().stream().map(r->r.getRoleName()).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String,String> idToken=new HashMap<>();
                idToken.put("access-Token",JwtAccessToken);
                idToken.put("refrech-token",jwt);
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(),idToken);


            }catch (Exception e){

                response.setHeader("error-message",e.getMessage());
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
            }


        }else {
            throw  new RuntimeException("Refrech token required !!");
        }
    }
    @GetMapping(path = "/profile")
    public Utilisateur profile(Principal principal){
        return accountService.loadUtilisateurByusername(principal.getName());
    }

}
@Data
class RoleUserForm{
    private String username;
    private String RoleNmame;

}
