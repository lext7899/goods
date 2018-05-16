package cn.itcast.goods.user.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.goods.user.domain.User;
import cn.itcast.jdbc.TxQueryRunner;

/**
 * 用户模块持久层
 * @author qdmmy6
 *
 */
public class UserDao {
	private QueryRunner qr = new TxQueryRunner();
	
//通过激活码查询用户	
	public User findByCode(String code) throws SQLException{
		String sql="select * from t_user where activationCode=?";
		
		return qr.query(sql, new BeanHandler<User>(User.class),code);
		
	}
	
	//修改用户状态
	
	public void updateStatus(String uid, boolean status) throws SQLException{
		String sql="update t_user set status=? where uid=?";
		qr.update(sql,status,uid);
		
	}
	
	
	/*
	 * 校验用户名是否存在
	 * 
	 * 
	 * */
	public boolean ajaxValidateLoginname(String loginname)throws SQLException{
		String sql="select count(1) from t_user where loginname=?";
		Number number=(Number) qr.query(sql, new ScalarHandler(),loginname);
		return number.intValue()==0;
		
	}

	/*
	 * 校验邮箱是否存在
	 * 
	 * 
	 * */
	public boolean ajaxValidateEmail(String email)throws SQLException{
		String sql="select count(1) from t_user where email=?";
		Number number=(Number) qr.query(sql, new ScalarHandler(),email);
		return number.intValue()==0;
		
	}
	//添加用户
	public void add(User user) throws SQLException{
		String sql="insert into t_user values(?,?,?,?,?,?)";
		Object[] ps={user.getUid(), user.getLoginname(), user.getLoginpass(),
				user.getEmail(), user.isStatus(), user.getActivationCode()};
		qr.update(sql,ps);
	}
	
	//按用户名和密码查询。
	public User findByLoginnameAndLoginpass(String loginname,String loginpass) throws SQLException{
		String sql="select * from t_user where loginname=? and loginpass=?";
		return qr.query(sql, new BeanHandler<User>(User.class),loginname,loginpass);
		
	}
	//按uid和password 查询
	public boolean findByUidAndPassword(String uid,String password) throws SQLException{
		String sql="select count(*) from t_user where uid=? and loginpass=?";
		Number number=(Number) qr.query(sql, new ScalarHandler(),uid,password);
		return number.intValue()>0;
		
		
	}
	
	//修改密码
	public void updatePassword (String uid,String password) throws SQLException{
		String sql="update t_user set loginpass=? where uid=?";
		qr.update(sql,password,uid);
		
		
	}
	
	
}
