package org.kucher.socialservice.model.enums;

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
