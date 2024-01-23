package edu.miu.demoinclass.entity.dto.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenRequest {
    private String accessToken;
    private String refreshToken;
}
