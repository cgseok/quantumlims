package lims.core.common.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class SessionDto {
    String requestId;
    String sessionTest;
}
