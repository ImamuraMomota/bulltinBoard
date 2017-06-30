package board.dao;

import static board.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import board.beans.Department;
import board.exception.SQLRuntimeException;

public class DepartmentDao {

	public List<Department> getDepartments(Connection connection) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM departments ");

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<Department> dbDepartment = toDepartmentList(rs);
			return dbDepartment;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<Department> toDepartmentList(ResultSet rs)
			throws SQLException {

		List<Department> dbDepartment = new ArrayList<>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");

				Department department = new Department();
				department.setId(id);
				department.setName(name);

				dbDepartment.add(department);
			}
			return dbDepartment;
		} finally {
			close(rs);
		}
	}

}

