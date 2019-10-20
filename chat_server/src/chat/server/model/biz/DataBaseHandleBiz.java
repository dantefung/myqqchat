package chat.server.model.biz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import chat.server.controller.MessageTypeEnum;
import chat.server.controller.ServerThread;

public class DataBaseHandleBiz {
	private Statement statement;
	private String se = "\"";
	private String com = ",";
	private Connection connect;
	private static DataBaseHandleBiz dataBaseHandleBiz = new DataBaseHandleBiz();

	public DataBaseHandleBiz() {
		try {
//			Connection connect = DriverManager.getConnection(
//					"jdbc:mysql://localhost:3306/user", "root", "");
			connect = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/qq?useUnicode=true&characterEncoding=utf8", "root", "123456");
			statement = connect.createStatement();		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ȡDataBaseHandleBiz�ĵ���
	 * 
	 * @return DataBaseHandleBiz�ĵ���
	 */
	public static DataBaseHandleBiz getDataBaseHandleBiz() {
		return dataBaseHandleBiz;
	}

	/**
	 * ����ע���¼�
	 * 
	 * @param st
	 *            ��ǰͨ���еĶ������ڷ���ע����
	 * @param msg
	 *            �ӿͻ��˽��յ���ע������
	 */
	public void signUp(ServerThread st, String msg) {
		String[] values = msg.split("\r");

		// signup(����) :::(���ͷָ���) ��ǰid��Ҳ���û����˺�
		st.sendData(MessageTypeEnum.SignUp.toString()
				+ ":::"
				+ insertUser(values[0], values[1], values[2], values[3],
						values[4]));
	}

	public int setLogin(int id, boolean log) {
		String sql = "update user set login = " + log + " where id = " + id;
		try {
			int result = statement.executeUpdate(sql);
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public boolean IsLogin(int id) {
		String sql = "select login from user where id = " + id;
		try {
			ResultSet result = statement.executeQuery(sql);
			result.next();
			return result.getBoolean("login");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * ����û���Ϣ
	 * 
	 * @param name
	 *            �û���
	 * @param password
	 *            ����
	 * @param sex
	 *            �Ա�
	 * @param birthday
	 *            ����
	 * @param live
	 *            ���ڵ�
	 * @return ��������������-1�����򷵻ص�ǰ����
	 */
	public int insertUser(String name, String password, String sex,
			String birthday, String live) {
		try {
			int id = getLastId();
			if (id == -1) {
				return -1;
			}
//			String sql = "insert " + "into " + "user " + "values " + "(" + ++id
//					+ com + se + name + se + com + se + password + se + com
//					+ se + sex + se + com + se + birthday + se + com + se
//					+ live + se + com + "false" + com + se + "0;" + se + ")";
			System.out.println("DataBaseHandleBiz.insertUser():birthday " + birthday);
			String sql = "insert into user values(null,?,?,?,?,?)";//��һ��Ϊ�Զ��������������null
			PreparedStatement pStmt = connect.prepareStatement(sql);
			pStmt.setString(1,name);
			pStmt.setString(2,password);
			pStmt.setString(3,sex);
			pStmt.setString(4,birthday);
			pStmt.setString(5,live);
			int result = pStmt.executeUpdate();
//			int result = statement.executeUpdate(sql);
			if (result == -1) {
				return -1;
			}
			return id;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public boolean isUserExist(int id) {
		String sql = "select password from user where id = " + id;
		try {
			ResultSet result = statement.executeQuery(sql);
			return result.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean isId_E_Password(int id, String password) {
		String sql = "select password from user where id = " + id;
		try {
			ResultSet result = statement.executeQuery(sql);
			result.next();
			return result.getString("password").equals(password);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * ��ѯ�ǳ�
	 * 
	 * @param name
	 *            �ǳ�
	 * @return ��������ĵ�һ��ֵΪ-1������󣬷��򷵻����з�������������
	 */
	public int[] getId_F_Name(String name) {
		String sql = "select * from user where name = " + name;
		int i = -1;
		if (getLastId() == -1) {
			int[] error = new int[0];
			error[0] = -1;
		}
		int[] id = new int[getLastId()];
		try {
			ResultSet result = statement.executeQuery(sql);
			while (result.next()) {
				if (result.getString("name").equals(name)) {
					id[++i] = Integer.valueOf(result.getString("id"));
				}
			}
			return id;
		} catch (SQLException e) {
			e.printStackTrace();
			int[] error = new int[0];
			error[0] = -1;
			return error;
		}
	}

	public String getName_F_Id(int id) {
		String sql = "select name from user where id = " + id;
		try {
			ResultSet result = statement.executeQuery(sql);
			result.next();
			return result.getString("name");
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public int addFriend_F_Id(int id, int fid) {
		String sql = "select friend from user where id = " + id;
		try {
			ResultSet result = statement.executeQuery(sql);
			result.next();
			String friends = result.getString("friend");

			sql = "update user set friend = " + "'" + friends + ";" + fid + "'"
					+ " where id = " + id;

			return statement.executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public int removeFriend_F_Id(int mid, int fid) {
		String sql = "select friend from user where id = " + mid;
		try {
			ResultSet result = statement.executeQuery(sql);
			result.next();
			String friends = result.getString("friend");

			if (friends.endsWith(String.valueOf(fid))) {
				friends = friends.substring(0, friends.length() - String.valueOf(fid).length() - 1);
				sql = "update user set friend = " + "'" + friends + "'"
						+ " where id = " + mid;
			} else {
				String value = String.valueOf(fid) + ";";
				int index = friends.indexOf(value);

				friends = friends.substring(0, index)
						+ friends.substring(index + value.length());

				sql = "update user set friend = " + "'" + friends + "'"
						+ " where id = " + mid;
			}

			return statement.executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public boolean isFriend(int mid, int fid) {
		int[] ids = getFriend_F_Id(mid);
		for (int i = 0; i < ids.length; i++) {
			if (ids[i] == fid) {
				return true;
			}
		}
		return false;
	}

	public int[] getFriend_F_Id(int mid) {
		String sql = "select friend from user where id = " + mid;
		try {
			ResultSet result = statement.executeQuery(sql);
			result.next();
			String friends = result.getString("friend");
			if (friends.equals("0")) {
				int[] id = { 0 };
				return id;
			}
			String[] friend = friends.split(";");
			int[] id = new int[friend.length];
			for (int i = 0; i < friend.length; i++) {
				id[i] = Integer.valueOf(friend[i]);
			}
			return id;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getSex_F_Id(int id) {
		String sql = "select sex from user where id = " + id;
		try {
			ResultSet result = statement.executeQuery(sql);
			result.next();
			return result.getString("sex");
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public int[] getAllId() {
		String sql = "select count(*) from user";
		try {
			ResultSet result = statement.executeQuery(sql);
			result.next();
			int nums = result.getInt("id");

			int[] ids = new int[nums];
			int i = 0;
			while (result.next()) {
				ids[i++] = result.getInt("id");
			}
			return ids;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * ��ѯ���һԪ�ص�id
	 * 
	 * @return ��������򷵻�-1�����򷵻����һ��Ԫ�ص�id
	 */
	private int getLastId() {
		try {
			ResultSet result = statement
					.executeQuery("select id from user order by id");
			int id = -1;
			if (result.last()) {
				id = Integer.valueOf(result.getString("id"));
				return id;
			} else {
				return 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
}
