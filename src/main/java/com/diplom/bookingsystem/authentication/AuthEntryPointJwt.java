package com.diplom.bookingsystem.authentication;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException {
        final String expired = (String) httpServletRequest.getAttribute("expired");

        if (expired != null)
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, expired);
        else
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Login details");
    }
}
