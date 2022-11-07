package com.kpoma.film.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class User implements UserDetails {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "username", nullable = true, length = 45)
    private String username;
    @Basic
    @Column(name = "password", nullable = true, length = 45)
    private String password;
    @Basic
    @Column(name = "roles", nullable = true, length = 255)
    private String roles;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Collection<Film> filmsById;

    @JsonIgnore
    @OneToMany(mappedBy = "userByUserId")
    private Collection<Playlist> playlistsById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if(roles==null){
            return Collections.emptyList();
        }

        HashSet<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();

        Arrays.stream(roles.split(","))
                .collect(Collectors.toList())
                .forEach(role->grantedAuthorities.add(new SimpleGrantedAuthority(role.trim())));

        return grantedAuthorities;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, roles);
    }

    public Collection<Film> getFilmsById() {
        return filmsById;
    }

    public void setFilmsById(Collection<Film> filmsById) {
        this.filmsById = filmsById;
    }

    public Collection<Playlist> getPlaylistsById() {
        return playlistsById;
    }

    public void setPlaylistsById(Collection<Playlist> playlistsById) {
        this.playlistsById = playlistsById;
    }
}
