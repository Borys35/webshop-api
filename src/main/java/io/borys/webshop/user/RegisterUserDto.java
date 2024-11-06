package io.borys.webshop.user;

import lombok.Data;

@Data
public class RegisterUserDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
