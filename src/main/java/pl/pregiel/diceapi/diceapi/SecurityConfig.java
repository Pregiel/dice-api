package pl.pregiel.diceapi.diceapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("SELECT username, password, enabled FROM user WHERE username = ?")
                .authoritiesByUsernameQuery("SELECT username, name FROM role, user " +
                        "WHERE user.role_id = role.id AND user.username = ?")
                .passwordEncoder(bCryptPasswordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();

        http.csrf().disable()

                .authorizeRequests()
// .anyRequest().permitAll()
                .antMatchers(HttpMethod.POST, "/registration/").permitAll()
                .antMatchers(HttpMethod.GET, "/welcome").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .and()
                .logout()
                    .permitAll()
        ;

//        http.authorizeRequests()
//                .anyRequest().fullyAuthenticated()
//                .and()
//                .formLogin().loginPage("/login").failureUrl("/login?error").permitAll()
//                .and()
//                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login");

        http.httpBasic().authenticationEntryPoint(authenticationEntryPoint);
    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        String password = "123";
//
//        String encrytedPassword = bCryptPasswordEncoder().encode(password);
//        System.out.println("Encoded password of 123=" + encrytedPassword);
//
//
//        InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder>
//                managerConfigurer = auth.inMemoryAuthentication();
//
//        UserDetails u1 = User.withUsername("tom").password(encrytedPassword).roles("USER").build();
//
//        managerConfigurer.withUser(u1);
//    }
}
