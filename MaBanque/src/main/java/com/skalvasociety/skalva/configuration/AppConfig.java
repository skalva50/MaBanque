package com.skalvasociety.skalva.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.skalvasociety.skalva")
public class AppConfig extends WebMvcConfigurerAdapter {
	
	
	  @Bean
	  public InternalResourceViewResolver getViewResolver() {
	    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
	    viewResolver.setPrefix("/");
	    viewResolver.setSuffix(".xhtml");
	    return viewResolver;
	  }
	  
	  public void addViewControllers(ViewControllerRegistry registry) {
		    registry.addViewController("/").setViewName("accueil");
	  }
  
	    @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	    }

}
