package com.kenpb.sample_plugin;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @implNote Importing the ViewComponentAspect doesn't seem to work maybe I'm missing more beans?
 */
@Configuration
@ComponentScan("de.tschuehly.spring.viewcomponent.core.component")
public class SbpSpringViewComponentConfiguration {

}
