package dev.dentech.spring_security.configuracao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
public class ConfiguracaoDeSeguranca {
    
    @Bean
    public SecurityFilterChain filtroDeSeguranca(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configure(http))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorize -> authorize
                .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                .requestMatchers(HttpMethod.POST, "/autenticacao/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/autenticacao/registrar").permitAll()
                .anyRequest().authenticated()
            ).build();
    }


    /**
     * O Bean vai gerenciar automaticamente quandoum tipo PasswordEncoder for instanciado.
     */
    @Bean
    public PasswordEncoder codificadorDeSenha() {
        return new BCryptPasswordEncoder();
    } 
}
