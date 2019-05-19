package id.bnv.jupiter.interceptor;

import id.bnv.jupiter.authentication.Authentication;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// TODO: add logging
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

        if (servletPath.contains("/authentication")) return true;
        if (servletPath.contains("/country/")) return true;
        if (servletPath.contains("/region/providersForRegion")) return true;
        if (servletPath.contains("/tarif/price")) return true;
        if (servletPath.contains("/tariffoffers")) return true;
        if (servletPath.contains("swagger")) return true;
        if (request.getMethod().equals("OPTIONS")) return true;

        String token = request.getHeader("token");
        String userid = request.getHeader("userid");

        try {
            boolean isAuthenticated = authentication.identifyUserByToken(token, Integer.parseInt(userid));
            addResponseIfNeeded(isAuthenticated, response);
            return isAuthenticated;
        } catch (Exception e) {
            addResponseIfNeeded(false, response);
            return false;
        }
    }

    private void addResponseIfNeeded(boolean isAuthenticated, HttpServletResponse response) throws Exception {
        if (!isAuthenticated) {
            String responseToClient = "{\"response\" : \"Not authenticated\"}";

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(responseToClient);
            response.getWriter().flush();
            response.getWriter().close();
        }
    }
}
