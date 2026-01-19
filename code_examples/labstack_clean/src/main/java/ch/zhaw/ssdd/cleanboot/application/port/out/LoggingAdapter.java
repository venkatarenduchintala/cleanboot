package ch.zhaw.ssdd.cleanboot.application.port.out;

public interface LoggingAdapter {
    void debug(String message);
    void info(String message);
    void warn(String message);
    void error(String message);
}
