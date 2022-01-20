package br.com.ibicos.ibicos.domain.repository;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;

@Slf4j
@RequiredArgsConstructor
public class RetryableDataSource extends AbstractDataSource {

    private final DataSource dataSource;
    
    @Override
    @Retryable(maxAttempts = 5, backoff = @Backoff(maxDelay = 5000, multiplier = 1.5))
    public Connection getConnection() throws SQLException {
        logger.info("Trying to get SQL connection");
        return dataSource.getConnection();
    }

    @Override
    public Connection getConnection(String arg0, String arg1) throws SQLException {
        logger.info("Trying to get SQL connection with args");
        return dataSource.getConnection(arg0, arg1);
    }
    
}
