package com.devtony.app.security.auth;

import com.devtony.app.security.UserDetailsServiceImpl;
import com.devtony.app.security.details.CustomUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException{
        final String authorizationHeader = request.getHeader("Authorization");

        Long userId = null; // Ahora extraemos el ID del usuario
        String jwt = null;

        try {
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                jwt = authorizationHeader.substring(7); // Extraer el token sin "Bearer "
                userId = jwtUtil.extractUserId(jwt); // Extraer el ID del token
            }

            // Si hay un ID y no hay autenticación en el contexto actual
            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                CustomUserDetails userDetails = userDetailsService.loadUserById(userId); // Cargar usuario por ID
                if (jwtUtil.isTokenValid(jwt, String.valueOf(userId))) { // Verificar validez del token
                    // Crear un token de autenticación para el usuario
                    JwtAuthenticationToken authentication = new JwtAuthenticationToken(userDetails, jwt, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication); // Establecer autenticación
                }
            }

            chain.doFilter(request, response); // Continuar con el filtro

        } catch (Exception e) {
            // Manejar errores y devolver un JSON detallado
            handleFilterException(response, e);
        }
    }

    private void handleFilterException(HttpServletResponse response, Exception e) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
        response.setContentType("application/json");

        // Crear un JSON con detalles del error
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", System.currentTimeMillis());
        errorDetails.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        errorDetails.put("error", "Unauthorized");
        errorDetails.put("message", e.getMessage());
        errorDetails.put("path", ""); // Puedes agregar aquí la URL del request si es necesario

        // Escribir la respuesta en formato JSON
        response.getWriter().write(new ObjectMapper().writeValueAsString(errorDetails));
    }
}
