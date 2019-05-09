package ts.daoImpl;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import ts.daoBase.BaseDao;
import ts.model.UserInfo;


public class UserInfoDao extends BaseDao<UserInfo, Integer> {
	public UserInfoDao(){
		super(UserInfo.class);
	}
	
	 //ÓÃ»§µÇÂ¼
		public UserInfo login(int id, String passwd) {
			List<UserInfo> users = findBy("UID", true, Restrictions.eq("PWD", passwd));
			for (UserInfo userInfo : users) {
				if (userInfo.getUID() == id) return userInfo;
			}
			return null;
		}
		
//		public boolean login(int id, String passwd) {
//			List<UserInfo> users = findBy("UID", true, Restrictions.eq("PWD", passwd));
//			for (UserInfo userInfo : users) {
//				if (userInfo.getUID() == id) return true;
//			}
//			return false;
//		}
}
