package lims.core.login.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class LoginRequestDto {
    @ApiModelProperty(value = "사용자 아이디", example = "admin", required = true)
    private String loginId;

    @ApiModelProperty(value = "사용자의 이름", example = "1234", required = false)
    private String loginPassword;
}
