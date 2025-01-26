package org.unibl.etf.rest_api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.unibl.etf.rest_api.model.APIKey;
import org.unibl.etf.rest_api.model.db.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginResponseDto {
    private APIKey xApiKey;
    private User user;
}
