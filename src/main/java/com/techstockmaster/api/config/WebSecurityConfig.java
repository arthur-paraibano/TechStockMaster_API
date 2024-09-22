package com.techstockmaster.api.config;


//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
//
//    @Bean
//    public BCryptPasswordEncoder encoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//    private static final String[] SWAGGER_WHITELIST = {
//            "/v2/api-docs",
//            "/swagger-resources",
//            "/swagger-resources/**",
//            "/configuration/ui",
//            "/configuration/security",
//            "/swagger-ui.html",
//            "/webjars/**"
//    };
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.headers(headers -> headers
//                        .addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))) // Substitui frameOptions().disable()
//                .csrf(csrf -> csrf.disable())
//                .cors(cors -> cors.disable())
//                .addFilterAfter(new JWTFilter(), UsernamePasswordAuthenticationFilter.class)
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers(SWAGGER_WHITELIST).permitAll()
//                        .requestMatchers(HttpMethod.POST,"/api/users").permitAll()
//                        .requestMatchers(HttpMethod.GET,"/api/users").hasAnyRole("USERS","MANAGERS")
//                        .requestMatchers("/user").hasAnyRole("MANAGERS")
//                        .anyRequest().authenticated()
//                )
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//        return http.build();
//    }
}