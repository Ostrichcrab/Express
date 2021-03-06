package ts.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.hibernate.criterion.Restrictions;

import ts.daoImpl.CustomerInfoDao;
import ts.daoImpl.RegionDao;
import ts.daoImpl.TransNodeDao;
import ts.model.CodeNamePair;
import ts.model.CustomerInfo;
//import ts.model.ErrorMessage;
import ts.model.ExpressSheet;
import ts.model.Region;
import ts.model.TransNode;
import ts.model.UserInfo;
import ts.serviceInterface.IMiscService;
import ts.util.JwtToken;
import ts.daoImpl.UserInfoDao;

public class MiscService implements IMiscService{
	//TransNodeCatalog nodes;	//自己做的缓存和重定向先不要了,用Hibernate缓存对付一下，以后加上去
	//RegionCatalog regions;
	private TransNodeDao transNodeDao;
	private RegionDao regionDao;
	private CustomerInfoDao customerInfoDao;
	private UserInfoDao userInfoDao;

	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}

	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

	public TransNodeDao getTransNodeDao() {
		return transNodeDao;
	}

	public void setTransNodeDao(TransNodeDao dao) {
		this.transNodeDao = dao;
	}

	public RegionDao getRegionDao() {
		return regionDao;
	}

	public void setRegionDao(RegionDao dao) {
		this.regionDao = dao;
	}

	public CustomerInfoDao getCustomerInfoDao() {
		return customerInfoDao;
	}

	public void setCustomerInfoDao(CustomerInfoDao dao) {
		this.customerInfoDao = dao;
	}

	public MiscService(){
//		nodes = new TransNodeCatalog();
//		nodes.Load();
//		regions = new RegionCatalog();
//		regions.Load();
	}

	@Override
	public TransNode getNode(String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TransNode> getNodesList(String regionCode, int type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CustomerInfo> getCustomerListByName(String name) {
//		List<CustomerInfo> listci = customerInfoDao.findByName(name);
//		List<CodeNamePair> listCN = new ArrayList<CodeNamePair>();
//		for(CustomerInfo ci : listci){
//			CodeNamePair cn = new CodeNamePair(String.valueOf(ci.getID()),ci.getName());
//			listCN.add(cn);
//		}
//		return listCN;
		return customerInfoDao.findByName(name);
	}

	@Override
	public List<CustomerInfo> getCustomerListByTelCode(String TelCode) {
//		List<CustomerInfo> listci = customerInfoDao.findByTelCode(TelCode);
//		List<CodeNamePair> listCN = new ArrayList<CodeNamePair>();
//		for(CustomerInfo ci : listci){
//			CodeNamePair cn = new CodeNamePair(String.valueOf(ci.getID()),ci.getName());
//			listCN.add(cn);
//		}
//		return listCN;
		return customerInfoDao.findByTelCode(TelCode);
	}

	@Override
	public Response getCustomerInfo(String id) {
		CustomerInfo cstm = customerInfoDao.get(Integer.parseInt(id));
//		try{
//			cstm.setRegionString(regionDao.getRegionNameByID(cstm.getRegionCode()));	//这部分功能放到DAO里去了
//		}catch(Exception e){}
		return Response.ok(cstm).header("EntityClass", "CustomerInfo").build(); 
	}
	
	@Override
	public Response deleteCustomerInfo(int id) {
		customerInfoDao.removeById(id);
		return Response.ok("Deleted").header("EntityClass", "D_CustomerInfo").build(); 
	}

	@Override
	public Response saveCustomerInfo(CustomerInfo obj) {
		try{
			customerInfoDao.save(obj);			
			return Response.ok(obj).header("EntityClass", "R_CustomerInfo").build(); 
		}
		catch(Exception e)
		{
			return Response.serverError().entity(e.getMessage()).build(); 
		}
	}

	@Override
	public List<CodeNamePair> getProvinceList() {		
		List<Region> listrg = regionDao.getProvinceList();
		List<CodeNamePair> listCN = new ArrayList<CodeNamePair>();
		for(Region rg : listrg){
			CodeNamePair cn = new CodeNamePair(rg.getORMID(),rg.getPrv());
			listCN.add(cn);
		}
		return listCN;
	}

	@Override
	public List<CodeNamePair> getCityList(String prv) {
		List<Region> listrg = regionDao.getCityList(prv);
		List<CodeNamePair> listCN = new ArrayList<CodeNamePair>();
		for(Region rg : listrg){
			CodeNamePair cn = new CodeNamePair(rg.getORMID(),rg.getCty());
			listCN.add(cn);
		}
		return listCN;
	}

	@Override
	public List<CodeNamePair> getTownList(String city) {
		List<Region> listrg = regionDao.getTownList(city);
		List<CodeNamePair> listCN = new ArrayList<CodeNamePair>();
		for(Region rg : listrg){
			CodeNamePair cn = new CodeNamePair(rg.getORMID(),rg.getTwn());
			listCN.add(cn);
		}
		return listCN;
	}

	@Override
	public String getRegionString(String code) {
		return regionDao.getRegionNameByID(code);
	}

	@Override
	public Region getRegion(String code) {
		return regionDao.getFullNameRegionByID(code);
	}

	@Override
	public void CreateWorkSession(int uid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Response doLogin(int uid, String pwd) {
		// TODO Auto-generated method stub
		
//		UserInfo userInfo = userInfoDao.login(uid, pwd);
//		if (userInfo != null) {
//			return Response.ok(userInfo).header("EntityClass", "UserInfo").build(); 
//		}
//		else
//		{
//			return Response.ok("用户名或密码错误").header("EntityClass", "UserInf").build(); 
//		}
//		
		UserInfo userInfo = userInfoDao.login(uid, pwd);
		if (userInfo != null) {
			System.out.println("logging in……");
			userInfo.setUserToken(
					JwtToken.createJWT(userInfo.getName(), userInfo.getUID() + "", userInfo.getURull() + ""));
			System.out.println("to returning……");
			return Response.ok(userInfo).header("EntityClass", "UserInfo").build();
		}
		//return Response.ok(new ErrorMessage(ErrorMessage.CODE.LOGIN_FAILED)).header("EntityClass", "ErrorMessage").build();
		return Response.ok("{\"retCode\":\"4011\",\"retMessage\":\"非准入IP地址!\"}\r\n" + 
				"\r\n" + 
				"").header("EntityClass", "UserInfo").build();
	}

	@Override
	public void doLogOut(int uid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void RefreshSessionList() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<CustomerInfo> getCustomerList() {
		// TODO Auto-generated method stub
		List <CustomerInfo> list = customerInfoDao.findBy();
		return list; 
	}

	@Override
	public List<TransNode> getTransNode(String id) {
		// TODO Auto-generated method stub
		List<TransNode> list = transNodeDao.findByRegionCode(id);
		return list;
		
	}
}
