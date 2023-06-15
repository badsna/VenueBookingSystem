package com.example.apigateway.filter;


import com.example.apigateway.customexception.LoginException;
import com.example.apigateway.customexception.RoleNotMatchedException;
import com.example.apigateway.util.JwtUtil;
import org.apache.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    @Autowired
    private RouteValidator routeValidator;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (routeValidator.isSecured.test(exchange.getRequest())) {
                //header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new LoginException("Missing Authorization Header");
                }
                String authHeaders = exchange.getRequest().getHeaders().get(org.springframework.http.HttpHeaders.AUTHORIZATION).get(0);
                if (authHeaders != null && authHeaders.startsWith("Bearer ")) {
                    authHeaders = authHeaders.substring(7);
                }

                jwtUtil.validateToken(authHeaders);
                String role = jwtUtil.extractRole(authHeaders);

                if (routeValidator.isSuperAdminAccess.test(exchange.getRequest())) {
                    if (role.contains("ROLE_SUPER_ADMIN")) {
                        logger.info("User With Role " + role + " Is Accessing The Api " + exchange.getRequest().getURI());
                    } else {
                        logger.error("User With Role " + role + "  Dont Have Access To This Api " + exchange.getRequest().getURI());
                        throw new RoleNotMatchedException("Only Super Admin Can access " + exchange.getRequest().getURI());
                    }
                }

                if (routeValidator.isAdminAccess.test(exchange.getRequest())) {
                    if (role.contains("ROLE_ADMIN")) {
                        logger.info("User With Role " + role + " Is Accessing The Api " + exchange.getRequest().getURI());
                    } else {
                        logger.error("User With Role " + role + "  Dont Have Access To This Api " + exchange.getRequest().getURI());
                        throw new RoleNotMatchedException("Only Admin Can access " + exchange.getRequest().getURI() + "api");
                    }
                }

                if (routeValidator.isvenueOwnerAccess.test(exchange.getRequest())) {
                    if (role.contains("ROLE_VENUE_OWNER")) {
                        logger.info("User With Role " + role + " Is Accessing The Api " + exchange.getRequest().getURI());
                    } else {
                        logger.error("User With Role " + role + "  Dont Have Access To This Api " + exchange.getRequest().getURI());
                        throw new RoleNotMatchedException("Only HotelOwner Can access " + exchange.getRequest().getURI() + "api");
                    }
                }

                if (routeValidator.isUserAccess.test(exchange.getRequest())) {
                    if (role.contains("ROLE_USER")) {
                        logger.info("User With Role " + role + " Is Accessing The Api " + exchange.getRequest().getURI());
                    } else {
                        logger.error("User With Role " + role + "  Dont Have Access To This Api " + exchange.getRequest().getURI());
                        throw new RoleNotMatchedException("Only User Can Access " + exchange.getRequest().getURI());
                    }
                }

            }

            return chain.filter(exchange);
        });
    }

    public static class Config {

    }

}
