package ts.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;

import ts.daoBase.BaseDao;
import ts.model.TransPackage;
import ts.model.UserInfo;


public class UserInfoDao extends BaseDao<UserInfo, Integer> {
	public UserInfoDao(){
		super(UserInfo.class);
	}
	
	 //用户登录
		public UserInfo login(int id, String passwd) {
			List<UserInfo> users = findBy("UID", true, Restrictions.eq("PWD", passwd));
			for (UserInfo userInfo : users) {
				if (userInfo.getUID() == id) return userInfo;
			}
			return null;
		}
		//根据id查询用户
		public UserInfo get( String Id){
			List<UserInfo> list  = new ArrayList<UserInfo>();
			list = super.findBy("ID", true, 
					Restrictions.sqlRestriction("ID = '"+ Id  +"'"));
			if(list.size() == 0)
				return null;
			return list.get(0);
		}
		
//		public boolean login(int id, String passwd) {
//			List<UserInfo> users = findBy("UID", true, Restrictions.eq("PWD", passwd));
//			for (UserInfo userInfo : users) {
//				if (userInfo.getUID() == id) return true;
//			}
//			return false;
//		}
}
