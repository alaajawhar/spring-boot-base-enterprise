package com.amdose.base.devportal.logSaver;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.core.spi.FilterReply;
import ch.qos.logback.core.spi.LifeCycle;
import com.amdose.base.devportal.cachdata.ApiModelCircularMap;
import com.amdose.base.devportal.models.ApiModel;

import java.util.List;

public class MyCustomAppender extends ContextAwareBase implements Appender<ILoggingEvent>, LifeCycle {

    private static final String PATTERN = "%d{yyyy-MM-dd HH:mm:ss} %-5level %logger{40}.%M\\(%line\\): %msg%n";
    private static PatternLayout layout = new PatternLayout();

    static {
        layout.setPattern(PATTERN);
        layout.setContext(new LoggerContext());
        layout.start();
    }


    private boolean started = false;

    @Override
    public void start() {
        started = true;
    }

    @Override
    public void stop() {
        started = false;
    }

    @Override
    public boolean isStarted() {
        return started;
    }

    @Override
    public String getName() {
        return "customAppender";
    }

    @Override
    public void doAppend(ILoggingEvent event) {
        ApiModel apiModel = ApiModelCircularMap.getCurrentApiModel();
        if (apiModel != null) {
            apiModel.appendLog(layout.doLayout(event));
        }
    }

    @Override
    public void setName(String name) {
    }

    @Override
    public void addFilter(Filter<ILoggingEvent> newFilter) {

    }

    @Override
    public void clearAllFilters() {

    }

    @Override
    public List<Filter<ILoggingEvent>> getCopyOfAttachedFiltersList() {
        return null;
    }

    @Override
    public FilterReply getFilterChainDecision(ILoggingEvent event) {
        return null;
    }
}