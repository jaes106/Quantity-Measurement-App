package com.bridgelabz.repository;

import com.bridgelabz.entity.QuantityMeasurementEntity;
import com.bridgelabz.exception.DatabaseException;
import com.bridgelabz.util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class QuantityMeasurementDatabaseRepository
        implements IQuantityMeasurementRepository {

    private static final QuantityMeasurementDatabaseRepository INSTANCE =
            new QuantityMeasurementDatabaseRepository();

    private final ConnectionPool connectionPool;

    private QuantityMeasurementDatabaseRepository() {
        this.connectionPool = ConnectionPool.getInstance();
    }

    public static QuantityMeasurementDatabaseRepository getInstance() {
        return INSTANCE;
    }

    @Override
    public void save(QuantityMeasurementEntity entity) {
        String sql = """
                INSERT INTO quantity_measurements (operation, result, error, created_at)
                VALUES (?, ?, ?, ?)
                """;

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, entity.getOperation());
            preparedStatement.setString(2, entity.getResult());
            preparedStatement.setString(3, entity.getError());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(entity.getCreatedAt()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Unable to save quantity measurement", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public List<QuantityMeasurementEntity> findAll() {
        String sql = """
                SELECT id, operation, result, error, created_at
                FROM quantity_measurements
                ORDER BY id
                """;

        List<QuantityMeasurementEntity> measurements = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                measurements.add(new QuantityMeasurementEntity(
                        resultSet.getLong("id"),
                        resultSet.getString("operation"),
                        resultSet.getString("result"),
                        resultSet.getString("error"),
                        resultSet.getTimestamp("created_at").toLocalDateTime()
                ));
            }
            return measurements;
        } catch (SQLException e) {
            throw new DatabaseException("Unable to fetch quantity measurements", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    public void deleteAll() {
        String sql = "DELETE FROM quantity_measurements";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Unable to clear quantity measurements", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }
}
