package lims.core.common.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuantumResultDto {
    private String resultCode = "OK";
    private String resultMessage = "SUCCESS";
    private Object resultData = null;
}
