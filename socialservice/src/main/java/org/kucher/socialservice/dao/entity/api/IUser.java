package org.kucher.socialservice.dao.entity.api;


import java.util.UUID;

public interface IUser {

    UUID getUuid();
    String getEmail();
    String getName();
    String getSurname();

}
