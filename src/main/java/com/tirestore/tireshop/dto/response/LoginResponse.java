package com.tirestore.tireshop.dto.response;

import com.tirestore.tireshop.enums.Role;

public record LoginResponse (
        String token,
        Long expiresIn,
        String fullName,
        Role role
){}
