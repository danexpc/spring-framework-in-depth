package com.frankmoley.lil.fid;

import com.frankmoley.lil.fid.config.ApplicationConfig;
import com.frankmoley.lil.fid.service.OutputService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

    public static void main(String[] args) throws Exception {
        var context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        var outputService = context.getBean(OutputService.class);

        for (int i = 0; i < 5; i++) {
            outputService.generateOutput("Frank");
            Thread.sleep(5000);
        }
    }
}
