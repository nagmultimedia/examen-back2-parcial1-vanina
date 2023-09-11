package com.example.msusers.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KeyCloakJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
  private final JwtGrantedAuthoritiesConverter defaultGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

  private static Collection<? extends GrantedAuthority> extractResourceRoles(final Jwt jwt) throws JsonProcessingException {
    Set<GrantedAuthority> resourcesRoles = new HashSet();
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    resourcesRoles.addAll(extractRoles("resource_access", objectMapper.readTree(objectMapper.writeValueAsString(jwt)).get("claims")));
    resourcesRoles.addAll(extractRolesRealmAccess("realm_access", objectMapper.readTree(objectMapper.writeValueAsString(jwt)).get("claims")));
    resourcesRoles.addAll(extractAud("aud", objectMapper.readTree(objectMapper.writeValueAsString(jwt)).get("claims")));
    return resourcesRoles;
  }


  private static List<GrantedAuthority> extractRoles(String route, JsonNode jwt) {
    Set<String> rolesWithPrefix = new HashSet<>();

    jwt.path(route)
        .elements()
        .forEachRemaining(e -> e.path("roles")
            .elements()
            .forEachRemaining(r -> rolesWithPrefix.add("ROLE_" + r.asText())));

    final List<GrantedAuthority> authorityList =
        AuthorityUtils.createAuthorityList(rolesWithPrefix.toArray(new String[0]));

    return authorityList;
  }
  private static List<GrantedAuthority> extractRolesRealmAccess(String route, JsonNode jwt) {
    Set<String> rolesWithPrefix = new HashSet<>();

    jwt.path(route)
            .path("roles")
                    .elements()
                    .forEachRemaining(r -> rolesWithPrefix.add("ROLE_" + r.asText()));

    final List<GrantedAuthority> authorityList =
            AuthorityUtils.createAuthorityList(rolesWithPrefix.toArray(new String[0]));

    return authorityList;
  }
  private static List<GrantedAuthority> extractAud(String route, JsonNode jwt) {
    Set<String> rolesWithPrefix = new HashSet<>();

    jwt.path(route)
        .elements()
        .forEachRemaining(e ->rolesWithPrefix.add("AUD_" + e.asText()));

    final List<GrantedAuthority> authorityList =
        AuthorityUtils.createAuthorityList(rolesWithPrefix.toArray(new String[0]));

    return authorityList;
  }



  public KeyCloakJwtAuthenticationConverter() {
  }

  public AbstractAuthenticationToken convert(final Jwt source) {
    Collection<GrantedAuthority> authorities = null;
    try {
      authorities = this.getGrantedAuthorities(source);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return new JwtAuthenticationToken(source, authorities);
  }

  public Collection<GrantedAuthority> getGrantedAuthorities(Jwt source) throws JsonProcessingException {
    return (Collection) Stream.concat(this.defaultGrantedAuthoritiesConverter.convert(source).stream(), extractResourceRoles(source).stream()).collect(Collectors.toSet());
  }
}


/*
* package com.example.springbootkeycloak.security;

import org.springframework.core.convert.converter.Converter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;


import java.util.*;
import java.util.stream.Collectors;

public class JwtAuthConverter implements Converter<Jwt, Collection<GrantedAuthority>> {


    public Collection<GrantedAuthority> convert(Jwt source) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        Map<String, Object> realmAccessRoles = source.getClaim("realm_access");
//        Map<String, Object> realmAccessRoles = (Map<String, Object>) source.getClaims().get("realm_access");

        if (realmAccessRoles != null && !realmAccessRoles.isEmpty()) {
            authorities.addAll(extractRoles(realmAccessRoles));
        }

        return authorities;
    }

    private static Collection<GrantedAuthority> extractRoles(Map<String, Object> realmAccessRoles) {
        return ((List<String>) realmAccessRoles.get("roles"))
                .stream().map(roleMap -> "ROLE_" + roleMap)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}
*
* */

/*
* package com.example.springbootkeycloak.security;

import org.springframework.core.convert.converter.Converter;
import
        org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import
        org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import
        org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Component
public class JwtAuthConverter implements Converter<Jwt,
        AbstractAuthenticationToken> {
    private final JwtGrantedAuthoritiesConverter
            jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    private final JwtAuthConverterProperties properties;
    public JwtAuthConverter(JwtAuthConverterProperties properties) {
        this.properties = properties;
    }
    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = Stream.concat(
                jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                extractResourceRoles(jwt).stream()).collect(Collectors.toSet());
        return new JwtAuthenticationToken(jwt, authorities,
                getPrincipalClaimName(jwt));
    }
    private String getPrincipalClaimName(Jwt jwt) {
        String claimName = JwtClaimNames.SUB;
        if (properties.getPrincipalAttribute() != null) {
            claimName = properties.getPrincipalAttribute();
        }
        return jwt.getClaim(claimName);
    }
    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt
                                                                                jwt) {
        Map<String, Object> resourceAccess =
                jwt.getClaim("resource_access");
        Map<String, Object> resource;
        Collection<String> resourceRoles;
        if (resourceAccess == null
                || (resource = (Map<String, Object>)
                resourceAccess.get(properties.getResourceId())) == null
                || (resourceRoles = (Collection<String>)
                resource.get("roles")) == null) {
            return Set.of();
        }
        return resourceRoles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());
    }
}
* */