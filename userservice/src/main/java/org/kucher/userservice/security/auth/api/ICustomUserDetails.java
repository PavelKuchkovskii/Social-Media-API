package org.kucher.userservice.security.auth.api;

import org.kucher.userservice.model.enums.EUserStatus;
import org.springframework.security.core.userdetails.UserDetails;

public interface ICustomUserDetails extends UserDetails {

    EUserStatus getStatus();
}
