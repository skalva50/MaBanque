package com.skalvasociety.skalva.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan(basePackages = "com.skalvasociety.skalva")
public class AppConfig extends WebMvcConfigurerAdapter {
	

}
