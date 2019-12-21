package ru.otus.spring.library.webmvc.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "users")
public class User implements UserDetails {

    @Id
    private String id;

    @Indexed(unique = true)
    @Field("userName")
    private String username;

    @Field("password")
    private String password;

    @Field("roles")
    private List<GrantedAuthority> roles = new ArrayList<>();

    @Field("isAccountNonExpired")
    private boolean isAccountNonExpired;

    @Field("isAccountNonLocked")
    private boolean isAccountNonLocked;

    @Field("isCredentialsNonExpired")
    private boolean isCredentialsNonExpired;

    @Field("isEnabled")
    private boolean isEnabled;


    public User(String username, String password, List<String> userRoles) {
        this.username = username;
        this.password = password;
        this.roles = getGrantedAuthoritiesList(userRoles);
        this.isAccountNonExpired = true;
        this.isAccountNonLocked = true;
        this.isCredentialsNonExpired = true;
        this.isEnabled = true;
    }


    private List<GrantedAuthority> getGrantedAuthoritiesList(List<String> userRoles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : userRoles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}