package com.amdose.base.devportal.logSaver;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Alaa Jawhar
 */
@Configuration
public class LoggingConfig {

    @Autowired
    private ApplicationContext context;

    @Bean
    public MyCustomAppender myCustomAppender() {
        MyCustomAppender appender = new MyCustomAppender();
        ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(this.getCompanyName());
        appender.setContext((Context) logger.getLoggerContext());
        appender.start();
        return appender;
    }

    @Bean
    public Appender<ILoggingEvent> combinedAppender() {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

        Appender<ILoggingEvent> consoleAppender = new ConsoleAppender<>();
        consoleAppender.setContext(loggerContext);
        consoleAppender.start();

        // Add the custom appender to the existing appenders
        ch.qos.logback.classic.Logger rootLogger = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.addAppender(myCustomAppender());

        return consoleAppender;
    }

    private String getCompanyName() {
        String packageName = this.getClass().getPackage().getName();
        String[] packageParts = packageName.split("\\.");
        String companyName = packageParts[0] + "." + packageParts[1];
        System.out.println("Package Name Scanned for logging is: [" + companyName + "]");
        return companyName;
    }

}
