package user.com.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.user.model.User;
import com.user.model.User.Gender;
import com.user.util.DButil;

public class UserService {
	
	static TreeMap<String, User> tree = new TreeMap<String, User>();
                   
	public List<User> SortedFirstName() throws Exception {
		fetchAll();
		List<User> tempList1 = new ArrayList<User>(tree.values());
		System.out.println("sorting");
		System.out.println(tree.toString());
		System.out.println(tempList1.toString());
		// returning Sorted with respect to FirstNames
		return tempList1;
	}

	public List<User> fetchAll() throws Exception {

		List<User> UserList = new ArrayList<User>();
		Connection con = DButil.getConnection();
		System.out.println("i was called");
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			ps = con.prepareStatement("Select * from user_db.user");
			rs = ps.executeQuery();

			while (rs.next()) {
				User User = new User();
				Gender gen = null;

				User.setId(rs.getInt("ID"));
				User.setFirstName(rs.getString("FIRST_NAME"));
				User.setMiddleName(rs.getString("MIDDLE_NAME"));
				User.setLastName(rs.getString("LAST_NAME"));
				User.setAge(rs.getInt("AGE"));

				String str = rs.getString("GENDER");

				str = str.toUpperCase();

				if (str.equals("MALE"))
					gen = gen.MALE;
				else
					gen = gen.FEMALE;

				User.setGender(gen);
				User.setPhone(rs.getString("PHONE"));
				User.setZip(rs.getInt("ZIP"));
				tree.put((User.getFirstName()).toUpperCase(), User);
				UserList.add(User);
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(" Error in Returant DAO " + e);
			// throw new AppException(e.getMessage(), e.getCause());
		} finally {
			DButil.closeResource(ps, rs, con);
		}

		for (User u : UserList) {

			System.out.println("name " + u.getFirstName());
		}

		return UserList;

	}

	public User fetchOne(int ID) throws Exception {

		Connection con = DButil.getConnection();
		User User = new User();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			ps = con.prepareStatement("Select * from user_db.user where ID=?");
			ps.setLong(1, ID);
			rs = ps.executeQuery();

			while (rs.next()) {

				Gender gen = null;
				User.setId(rs.getInt("ID"));
				User.setFirstName(rs.getString("FIRST_NAME"));
				User.setMiddleName(rs.getString("MIDDLE_NAME"));
				User.setLastName(rs.getString("LAST_NAME"));
				User.setAge(rs.getInt("AGE"));

				String str = rs.getString("GENDER");
				str.toUpperCase();

				if (str.equals("MALE"))
					gen = gen.MALE;
				else
					gen = gen.FEMALE;

				User.setGender(gen);
				User.setPhone(rs.getString("PHONE"));
				User.setZip(rs.getInt("ZIP"));
			}

		} catch (Exception e) {

			System.out.println(" Error in Returant DAO " + e);
			// throw new AppException(e.getMessage(), e.getCause());
		} finally {
			DButil.closeResource(ps, rs, con);
		}

		return User;

	}

	public User create(User user) throws Exception {
		Connection con = DButil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String str = null;
		Gender gen = null;

		try {

			ps = con.prepareStatement(
					"INSERT INTO user_db.user (FIRST_NAME,MIDDLE_NAME,LAST_NAME,AGE,GENDER,PHONE,ZIP) VALUES (?,?,?,?,?,?,?)",
					PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getMiddleName());
			ps.setString(3, user.getLastName());
			ps.setInt(4, user.getAge());

			gen = user.getGender();

			if (gen == gen.MALE)
				str = "MALE";
			else
				str = "FEMALE";
			ps.setString(5, str);
			ps.setString(6, user.getPhone());
			ps.setInt(7, user.getZip());

			ps.executeUpdate();

			rs = ps.getGeneratedKeys();

			if (rs.next()) {

				System.out.println(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e.getCause());
		} finally {
			DButil.closeResource(ps, rs, con);
		}

		return user;
	}

	public User update(int ID, User user) throws Exception {

		Connection con = DButil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String str = null;
		Gender gen = null;

		try {

			ps = con.prepareStatement(
					"UPDATE user_db.user SET FIRST_NAME = ?,MIDDLE_NAME = ?,LAST_NAME = ?,AGE = ?,GENDER = ?,PHONE = ?,ZIP = ? WHERE ID=?;",
					PreparedStatement.RETURN_GENERATED_KEYS);

			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getMiddleName());
			ps.setString(3, user.getLastName());
			ps.setInt(4, user.getAge());

			gen = user.getGender();

			if (gen == gen.MALE)
				str = "MALE";
			else
				str = "FEMALE";

			ps.setString(5, str);
			ps.setString(6, user.getPhone());
			ps.setInt(7, user.getZip());
			ps.setInt(8, user.getId());

			ps.executeUpdate();

			rs = ps.getGeneratedKeys();

			if (rs.next()) {

				System.out.println(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// throw new AppException(e.getMessage(), e.getCause());
		} finally {
			DButil.closeResource(ps, rs, con);
		}

		return user;

	}

	public List<User> delete(int id) throws Exception {

		Connection con = DButil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<User> list1 = new ArrayList<User>();

		try {
			ps = con.prepareStatement(" DELETE FROM user_db.user where id = ? ",
					PreparedStatement.RETURN_GENERATED_KEYS);

			ps.setInt(1, id);
			ps.executeUpdate();

			rs = ps.getGeneratedKeys();

			if (rs.next()) {
				// res.setCON_id(rs.getInt(1));
				System.out.println(rs.getInt(1));
			}

		}

		catch (SQLException e) {
			e.printStackTrace();
			// throw new AppException(e.getMessage(), e.getCause());
		} finally {
			DButil.closeResource(ps, rs, con);
		}

		// update list after deleting an element
		List<User> list = new ArrayList<User>();
		list = fetchAll();

		return list;

	}

}
