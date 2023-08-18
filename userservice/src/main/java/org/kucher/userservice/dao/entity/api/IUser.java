package org.kucher.userservice.dao.entity.api;


import org.kucher.userservice.dao.entity.enums.EUserRole;
import org.kucher.userservice.dao.entity.enums.EUserStatus;

public interface IUser extends IEssence {

    String getEmail();
    String getName();
    String getSurname();
    String getPassword();
    EUserRole getRole();
    EUserStatus getStatus();
}
