package com.example.pokedex_demo.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@ApiModel(description = "User Model")
public class User {

    @ApiModelProperty(notes = "ID of the User", name = "id", required = true, value = "1")
    @Id
    private String id;

    @ApiModelProperty(notes = "Name of the User", name = "name", required = true, value = "Bob")
    @NotNull
    private String name;

    @ApiModelProperty(notes = "Date of birth for the User", name = "birthdate", required = true, value = "1901-03-29")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd", shape=JsonFormat.Shape.STRING)
    private LocalDate birthdate;

    @ApiModelProperty(notes = "Username of the User", name = "username", required = true, value = "BobTheBuilder")
    @NotEmpty
    private String username;

    @ApiModelProperty(notes = "Password of the User", name = "password", required = true, value = "secret")
    @NotEmpty @Size(min = 8, max = 24)
    private String password;

    @ApiModelProperty(notes = "Roles of the User", name = "roles", required = true, value = "ROLE_USER")
    private List<String> roles;

    public User() {

    }

    public User(String name, LocalDate birthdate, String username, String password, List<String> roles) {
        this.name = name;
        this.birthdate = birthdate;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore // ignores password
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

}
