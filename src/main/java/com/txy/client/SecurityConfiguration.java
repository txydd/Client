package com.txy.client;


import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


//拦截器
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.headers().frameOptions().disable();

        http.csrf().disable().authorizeRequests()
                .antMatchers(  "/file","/fileList").permitAll();
               // .anyRequest().access("@mySecurity.check(authentication, request)")
//                .antMatchers("/**").hasAnyRole("ADMIN", "USER")
                //.and()
                //.formLogin().loginPage("/file").defaultSuccessUrl("/index").failureUrl("/user/login?error").loginProcessingUrl("/login");
    }

    @Override
    public void configure(WebSecurity web) throws Exception
    {
        super.configure(web);
        web.ignoring().antMatchers( "/static/**"); //不拦截静态资源
    }

}
