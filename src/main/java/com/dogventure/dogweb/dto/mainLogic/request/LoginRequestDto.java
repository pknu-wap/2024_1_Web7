package com.dogventure.dogweb.dto.mainLogic.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginRequestDto {

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Column(length = 30)
    private String password;
}
