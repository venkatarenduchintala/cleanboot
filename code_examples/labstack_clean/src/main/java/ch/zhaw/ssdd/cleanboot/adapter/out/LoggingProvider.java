package ch.zhaw.ssdd.cleanboot.adapter.out;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import ch.zhaw.ssdd.cleanboot.application.port.out.LoggingAdapter;

@Component
public class LoggingProvider implements LoggingAdapter {

    private final Logger log = Logger.getLogger("ch.zhaw.ssdd.cleanboot");

    @Override
    public void debug(String message) {
        log.log(Level.FINE, message);
    }

    @Override
    public void info(String message) {
        log.log(Level.INFO, message);
    }

    @Override
    public void warn(String message) {
        log.log(Level.WARNING, message);
    }

    @Override
    public void error(String message) {
        log.log(Level.SEVERE, message);
    }

}
