package org.kucher.socialservice.service.utill;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.kucher.socialservice.config.api.Message;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;

@Component
public class CustomResponseErrorHandler implements ResponseErrorHandler {

    private final ObjectMapper objectMapper;

    public CustomResponseErrorHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return !response.getStatusCode().is2xxSuccessful();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        Message message = objectMapper.readValue(response.getBody(), Message.class);
        throw new EntityNotFoundException(message.getMessage());
    }
}