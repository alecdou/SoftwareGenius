package softwareGenius.util;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import softwareGenius.service.AccountService;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class CharNameTypeHandler extends BaseTypeHandler {

    @Override
    public Object getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        return AccountService.Category.valueOf(rs.getString(columnName).toUpperCase());
    }

    @Override
    public Object getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return null;
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        return AccountService.Category.valueOf((cs.getString(columnIndex)));
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i,
                                    Object parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, ((AccountService.Category) parameter).toString());
    }
}
