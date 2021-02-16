import ch.qos.logback.classic.encoder.PatternLayoutEncoder

import static ch.qos.logback.classic.Level.*


def MAIN_LOG_DIR = "."
def rollingMaxHistory = 7

appender("ROLLING", RollingFileAppender) {
    encoder(PatternLayoutEncoder) {
        Pattern = "%d %level %thread %mdc %logger.%M\\(%line\\) - %m%n"
    }
    rollingPolicy(TimeBasedRollingPolicy) {
        FileNamePattern = "${MAIN_LOG_DIR}/logs/logback-%d{yyyy-MM-dd}.log.gz"
        MaxHistory = rollingMaxHistory
    }
}

root(INFO, ['ROLLING'])