
package com.engelhard.todoapp.models.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLoginDTO {
    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;
}
