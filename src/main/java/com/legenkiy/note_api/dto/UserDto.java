package com.legenkiy.note_api.dto;


import com.legenkiy.note_api.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {

    @NotBlank(message = "Username must be present!")
    @Size(min = 4, max = 25, message = "Username can`t contains less then 4 and more then 25 chars!")
    private String username;
    @NotBlank(message = "Password can`t be blank!")
    @Size(min = 8, message = "Password can`t contains less then 8 chars!")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@_.!?#]).+$",
            message = "Password must contain upper, lower, digit and special char (@_.!?#)"
    )
    private String password;
    @NotNull(message = "Role must be provided")
    private Role role;


}
