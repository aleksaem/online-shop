package dao.jdbc.mapper;

import entity.Product;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductRowMapperTest {
    @Test
    public void testMapRow() throws SQLException {
        ProductRowMapper productRowMapper = new ProductRowMapper();
        LocalDate localDate = LocalDate.of(2021,12,17);
        ResultSet resultSetMock = mock(ResultSet.class);
        when(resultSetMock.getString("name")).thenReturn("CHANEL LIPSTICK");
        when(resultSetMock.getInt("id")).thenReturn(1);
        when(resultSetMock.getDouble("price")).thenReturn(699.0);
        when(resultSetMock.getString("description")).thenReturn("MATTE, RED");
        when(resultSetMock.getDate("date")).thenReturn(Date.valueOf(localDate));

        Product actual = productRowMapper.mapRow(resultSetMock);
        assertEquals(1, actual.getId());
        assertEquals("CHANEL LIPSTICK", actual.getName());
        assertEquals(699.0, actual.getPrice());
        assertEquals("MATTE, RED", actual.getDescription());
        assertEquals(localDate, actual.getCreationDate());

    }

}