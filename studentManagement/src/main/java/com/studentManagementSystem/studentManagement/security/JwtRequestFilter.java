
  package com.studentManagementSystem.studentManagement.security;

  import java.io.IOException;
  import java.util.List;

  import lombok.Setter;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.security.authentication.
  UsernamePasswordAuthenticationToken;
  import org.springframework.security.core.authority.SimpleGrantedAuthority;
  import org.springframework.security.core.context.SecurityContextHolder;
  import org.springframework.security.core.userdetails.UserDetails;
  import org.springframework.security.core.userdetails.UserDetailsService;
  import org.springframework.security.web.authentication.
  WebAuthenticationDetailsSource;
  import org.springframework.stereotype.Component;
  import org.springframework.web.filter.OncePerRequestFilter;

  import jakarta.servlet.FilterChain;
  import jakarta.servlet.ServletException;
  import jakarta.servlet.http.HttpServletRequest;
  import jakarta.servlet.http.HttpServletResponse;

  @Component
  @Setter
  public class JwtRequestFilter extends OncePerRequestFilter {

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private UserDetailsService userDetailsService;



      @Override
  protected void doFilterInternal(HttpServletRequest request,
  HttpServletResponse response, FilterChain filterChain)
  throws ServletException, IOException {
  String authHeader = request.getHeader("Authorization");
  String username = null;
  String token = null;
  if (authHeader != null && authHeader.startsWith("Bearer ")) {
  token = authHeader.substring(7);
  username = jwtUtil.extractUsername(token);

  }
  if (username != null &&
  SecurityContextHolder.getContext().getAuthentication() == null) {
  UserDetails userDetails = userDetailsService.loadUserByUsername(username);
  List<String> roles = jwtUtil.extractRoles(token);
  List<SimpleGrantedAuthority> authorities = roles.stream().map(SimpleGrantedAuthority::new)
  .toList();
  if (jwtUtil.validToken(token, userDetails.getUsername())) {
  UsernamePasswordAuthenticationToken authToken = new
  UsernamePasswordAuthenticationToken(
  userDetails, null, authorities);
  authToken.setDetails(new
  WebAuthenticationDetailsSource().buildDetails(request));
  SecurityContextHolder.getContext().setAuthentication(authToken);
  }

  }
  filterChain.doFilter(request, response);
  }
  }
