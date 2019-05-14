package id.bnv.jupiter.interceptor;

import id.bnv.jupiter.authentication.Authentication;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Interceptor extends HandlerInterceptorAdapter {
    private final Authentication authentication;

    public Interceptor(Authentication authentication) {
        this.authentication = authentication;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        super.preHandle(request, response, handler);

        String servletPath = request.getRequestURL().toString();

        if (servletPath.contains("/authentication")) {
            return true;
        }

        if (servletPath.contains("swagger")) {
            return true;
        }

        String token = request.getHeader("token");
        String userid = request.getHeader("userid");

        try {
            boolean isAuthenticated = authentication.identifyUserByToken(token, Integer.parseInt(userid));
            addResponse(isAuthenticated, response);
            return isAuthenticated;
        } catch (Exception e) {
            addResponse(false, response);
            return false;
        }
    }

    private void addResponse(boolean isAuthenticated, HttpServletResponse response) throws Exception {
        if (!isAuthenticated) {
            String responseToClient = "Not authenticated";

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(responseToClient);
            response.getWriter().flush();
            response.getWriter().close();
        }
    }
}
