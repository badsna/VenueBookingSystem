package com.example.apigateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.List;
import java.util.function.Predicate;


@Component
public class RouteValidator {

    public static final List<String> openApiEndpoints = java.util.List.of(
            "/api/users/add_new_user",
            "/api/login/authenticate",
            "/eureka",
            "/api/venue/get_venue_by_venueId/**",
            "/api/venue/search"
    );
    /*
    If the request URI path does not match any of these endpoints, it returns true, indicating that the request is secured.
     */

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> new AntPathMatcher().match(uri, request.getURI().getPath()));
    // .noneMatch(uri->request.getURI().getPath().contains(uri));

    public static final List<String> superAdminApiEndpoints = java.util.List.of(
            "/api/users/add_new_admin"
    );

    // If the request URI path matches any of these endpoints, it returns true
    public Predicate<ServerHttpRequest> isSuperAdminAccess =
            request -> superAdminApiEndpoints
                    .stream()
                    .anyMatch(uri -> new AntPathMatcher().match(uri, request.getURI().getPath()));

    public static final List<String> adminApiEndpoints = java.util.List.of(
            "/api/venue/add_new_venue"
    );
    public Predicate<ServerHttpRequest> isAdminAccess =
            request -> adminApiEndpoints
                    .stream()
                    .anyMatch(uri -> new AntPathMatcher().match(uri, request.getURI().getPath()));

    public static final List<String> venueOwnerApiEndpoints = java.util.List.of(
            "/api/venue/update_venue_information/**"
    );
    public Predicate<ServerHttpRequest> isvenueOwnerAccess =
            request -> venueOwnerApiEndpoints
                    .stream()
                    .anyMatch(uri -> new AntPathMatcher().match(uri, request.getURI().getPath()));


    public static final List<String> userApiEndpoints = java.util.List.of(
            "/api/booking/reserve_venue"
    );
    public Predicate<ServerHttpRequest> isUserAccess =
            request -> userApiEndpoints
                    .stream()
                    .anyMatch(uri -> new AntPathMatcher().match(uri, request.getURI().getPath()));


}
