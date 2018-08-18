package com.backend.tasks.domain.dto.save;

import com.backend.tasks.domain.entity.Organization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSaveDto {

    private String username;

    private String password;

    private Organization organizationId;

}
