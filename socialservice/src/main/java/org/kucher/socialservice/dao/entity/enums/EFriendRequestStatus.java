package org.kucher.socialservice.dao.entity.enums;

public enum EFriendRequestStatus {

    WAITING,
    ACCEPTED,
    REJECTED;

    public static EFriendRequestStatus get(String name) {
        try {
            return EFriendRequestStatus.valueOf(name);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid status");
        }
    }

}
