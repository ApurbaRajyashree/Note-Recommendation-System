package com.note.NoteRecommender.security;

import com.note.NoteRecommender.dto.RoleDto;
import com.note.NoteRecommender.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class UserPrincipal implements UserDetails {
    @Autowired
    private UserDto userDto;

    public UserPrincipal(UserDto userDto){
        super();
        this.userDto=userDto;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<RoleDto> roleDtos=this.userDto.getRoleDtoSet();
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        for (RoleDto role : roleDtos) {
            authorities.addAll(role.getAuthoritySet().stream().map(a -> new SimpleGrantedAuthority(a.getName()))
                    .collect(Collectors.toSet()));
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return userDto.getPassword();
    }

    @Override
    public String getUsername() {
        return this.userDto.getEmail();
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
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
