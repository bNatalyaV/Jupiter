package id.bnv.jupiter.interceptor;

import id.bnv.jupiter.authentication.Authentication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Interceptor extends HandlerInterceptorAdapter {
    private final Authentication authentication;
    private static final Logger logger = LogManager.getLogger(Interceptor.class);

    public Interceptor(Authentication authentication) {
        this.authentication = authentication;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        super.preHandle(request, response, handler);

        String servletPath = request.getRequestURL().toString();

        logger.info("============NEW REQUEST=============");
        logger.info(("[preHandle][" + "\n[" + request.getMethod()
                + "]\n" + request.getRequestURI() +"\n token: "+ request.getHeader("token")
                +"\n userid:"+ request.getHeader("userid")));

        if (servletPath.contains("/authentication")) return true;
        if (servletPath.contains("/country/")) return true;
        if (servletPath.contains("/region/providersForRegion/")) return true;
        if (servletPath.contains("/tarif/price")) return true;
        if (servletPath.contains("/tariffoffers/")) return true;
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

            logger.info("NOT AUTHORIZED!");
            logger.info("============REQUEST ENDED=============");
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);

        logger.info(("[postHandle][" + response + "]" + "[" + response.getStatus() + "]"));
        logger.info("============REQUEST ENDED=============");
    }
}
