package com.example.auth_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

//ユーザー登録DTO
@Data
@Builder
public class RegisterRequestDto {

    @NotBlank
    private String username;

    @Email
    @NotBlank
    private String email;

    //パスワードは最低6文字
    @NotBlank
    @Size(min = 6)
    private String password;
}
