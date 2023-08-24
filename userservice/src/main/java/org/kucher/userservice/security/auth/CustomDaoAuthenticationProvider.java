package org.kucher.userservice.security.auth;

import org.kucher.userservice.exception.auth.EmailNotConfirmException;
import org.kucher.userservice.exception.auth.NotActivatedException;
import org.kucher.userservice.exception.auth.UserDeactivatedException;
import org.kucher.userservice.model.enums.EUserStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomDaoAuthenticationProvider extends DaoAuthenticationProvider {

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            this.logger.debug("Failed to authenticate since no credentials provided");
            throw new BadCredentialsException(this.messages
                    .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }
        String presentedPassword = authentication.getCredentials().toString();
        if (!this.getPasswordEncoder().matches(presentedPassword, userDetails.getPassword())) {
            this.logger.debug("Failed to authenticate since password does not match stored value");
            throw new BadCredentialsException(this.messages
                    .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }

        //Validate user for status
        validate((CustomUserDetails) userDetails);
    }

    private void validate(CustomUserDetails user) {
        if(user.getStatus() == EUserStatus.WAITING_VERIFICATION || user.getStatus() == EUserStatus.WAITING_CONFIRM) {
            throw new EmailNotConfirmException("Check you email and tap to link to confirm registration");
        }
        else if(user.getStatus() == EUserStatus.WAITING_ACTIVATION) {
            throw new NotActivatedException("Your account has not been activated. Contact the administrator");
        }
        else if(user.getStatus() == EUserStatus.DEACTIVATED) {
            throw new UserDeactivatedException("Your account has been deactivated. Contact the administrator");
        }
    }
}
