package com.cawstudios.clouddefence;

import com.cawstudios.clouddefence.handlers.AppHandler;
import io.micronaut.context.ApplicationContext;
import io.micronaut.http.annotation.Get;
import io.micronaut.runtime.Micronaut;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
//        ApplicationContext applicationContext = Micronaut.build(args)
//                .eagerInitSingletons(true)
//                .mainClass(Application.class)
//                .build();
//
//        log.info("\n\n-----------------Main application started--------");
//        applicationContext.start();
//        AppHandler appHandler = applicationContext.getBean(AppHandler.class);
//        appHandler.run();
    }
}
