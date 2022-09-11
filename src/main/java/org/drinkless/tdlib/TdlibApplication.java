package org.drinkless.tdlib;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * TdlibApplication class for TDLib usage from Java.
 */
@SpringBootApplication
@EnableScheduling
@ComponentScan
public class TdlibApplication {
	
    static {
		System.loadLibrary("tdjni");
    }
    
    public static void main(String[] args){
    	
    	SpringApplication.run(TdlibApplication.class, args);
    }
}
