package ts.serviceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;

import ts.daoImpl.ExpressSheetDao;
import ts.daoImpl.PosionDao;
import ts.daoImpl.TransHistoryDao;
import ts.daoImpl.TransPackageContentDao;
import ts.daoImpl.TransPackageDao;
import ts.daoImpl.UserInfoDao;
import ts.model.ExpressSheet;
import ts.model.Posion;
import ts.model.TransHistory;
import ts.model.TransPackage;
import ts.model.TransPackageContent;
import ts.model.UserInfo;
import ts.serviceInterface.IDomainService;

public class DomainService implements IDomainService {
	
	private ExpressSheetDao expressSheetDao;
	private TransPackageDao transPackageDao;
	private TransHistoryDao transHistoryDao;
	private TransPackageContentDao transPackageContentDao;
	
	private UserInfoDao userInfoDao;
	
	private PosionDao posionDao;
	
	public ExpressSheetDao getExpressSheetDao() {
		return expressSheetDao;
	}

	public void setExpressSheetDao(ExpressSheetDao dao) {
		this.expressSheetDao = dao;
	}

	public TransPackageDao getTransPackageDao() {
		return transPackageDao;
	}

	public void setTransPackageDao(TransPackageDao dao) {
		this.transPackageDao = dao;
	}

	public TransHistoryDao getTransHistoryDao() {
		return transHistoryDao;
	}

	public void setTransHistoryDao(TransHistoryDao dao) {
		this.transHistoryDao = dao;
	}

	public TransPackageContentDao getTransPackageContentDao() {
		return transPackageContentDao;
	}

	public void setTransPackageContentDao(TransPackageContentDao dao) {
		this.transPackageContentDao = dao;
	}

	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}

	public void setUserInfoDao(UserInfoDao dao) {
		this.userInfoDao = dao;
	}

	public Date getCurrentDate() {
		//����һ�����������ʱ��,��Ȼ,SQLʱ���JAVAʱ���ʽ��һ��
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date tm = new Date();
		try {
			tm= sdf.parse(sdf.format(new Date()));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return tm;
	}

	@Override
	public List<ExpressSheet> getExpressList(String property,
			String restrictions, String value) {
		List<ExpressSheet> list = new ArrayList<ExpressSheet>();
		switch(restrictions.toLowerCase()){
		case "eq":
			list = expressSheetDao.findBy(property, value, "ID", true);
			break;
		case "like":
			list = expressSheetDao.findLike(property, value+"%", "ID", true);
			break;
		}
		return list;
	}
//	@Override
//	public List<ExpressSheet> getExpressList(String property,
//			String restrictions, String value) {
//		Criterion cr1;
//		Criterion cr2 = Restrictions.eq("Status", 0);
//
//		List<ExpressSheet> list = new ArrayList<ExpressSheet>();
//		switch(restrictions.toLowerCase()){
//		case "eq":
//			cr1 = Restrictions.eq(property, value);
//			break;
//		case "like":
//			cr1 = Restrictions.like(property, value);
//			break;
//		default:
//			cr1 = Restrictions.like(property, value);
//			break;
//		}
//		list = expressSheetDao.findBy("ID", true,cr1,cr2);		
//		return list;
//	}

	@Override
	public List<ExpressSheet> getExpressListInPackage(String packageId,String state){
		List<ExpressSheet> list = new ArrayList<ExpressSheet>();
		list = expressSheetDao.getListInPackage(packageId,state);
		return list;		
	}

	public PosionDao getPosionDao() {
		return posionDao;
	}

	public void setPosionDao(PosionDao posionDao) {
		this.posionDao = posionDao;
	}

	@Override
	public Response getExpressSheet(String id) {
		ExpressSheet es = expressSheetDao.get(id);
		return Response.ok(es).header("EntityClass", "ExpressSheet").build(); 
	}
	
	@Override
	public List <ExpressSheet> getExpressSheet() {
		List <ExpressSheet> list = expressSheetDao.findBy();
		return list; 
	}	
	@Override
	public Response newExpressSheet(String id, int uid) {
		ExpressSheet es = null;
		try{
			es = expressSheetDao.get(id);
		} catch (Exception e1) {}

		if(es != null){
//			if(es.getStatus() != 0)
//				return Response.ok(es).header("EntityClass", "L_ExpressSheet").build(); //�Ѿ�����,�Ҳ��ܸ���
//			else
				return Response.ok("����˵���Ϣ�Ѿ�����!\n�޷�����!").header("EntityClass", "E_ExpressSheet").build(); //�Ѿ�����
		}
		try{
			String pkgId = userInfoDao.get(uid).getReceivePackageID();
			ExpressSheet nes = new ExpressSheet();
			nes.setID(id);
			nes.setType(0);
			nes.setAccepter(String.valueOf(uid));
			nes.setAccepteTime(getCurrentDate());
			nes.setStatus(ExpressSheet.STATUS.STATUS_CREATE);
//			TransPackageContent pkg_add = new TransPackageContent();
//			pkg_add.setPkg(transPackageDao.get(pkgId));
//			pkg_add.setExpress(nes);
//			nes.getTransPackageContent().add(pkg_add);
			expressSheetDao.save(nes);
			//�ŵ��ռ�������
			MoveExpressIntoPackage(nes.getID(),pkgId);
			return Response.ok(nes).header("EntityClass", "ExpressSheet").build(); 
		}
		catch(Exception e)
		{
			return Response.serverError().entity(e.getMessage()).build(); 
		}
	}

	@Override
	public Response saveExpressSheet(ExpressSheet obj) {
		try{
			//ExpressSheet nes = expressSheetDao.get(obj.getID());
			if(obj.getStatus() != ExpressSheet.STATUS.STATUS_CREATE){
				return Response.ok("����˵��Ѹ���!�޷��������!").header("EntityClass", "E_ExpressSheet").build(); 
			}
			expressSheetDao.save(obj);			
			return Response.ok(obj).header("EntityClass", "R_ExpressSheet").build(); 
		}
		catch(Exception e)
		{
			return Response.serverError().entity(e.getMessage()).build(); 
		}
	}

	@Override
	public Response ReceiveExpressSheetId(String id, int uid) {
		try{
			ExpressSheet nes = expressSheetDao.get(id);
			if(nes.getStatus() != ExpressSheet.STATUS.STATUS_CREATE){
				return Response.ok("����˵�״̬����!�޷��ռ�!").header("EntityClass", "E_ExpressSheet").build(); 
			}
			nes.setAccepter(String.valueOf(uid));
			nes.setAccepteTime(getCurrentDate());
			nes.setStatus(ExpressSheet.STATUS.STATUS_TRANSPORT);
			expressSheetDao.save(nes);
			return Response.ok(nes).header("EntityClass", "ExpressSheet").build(); 
		}
		catch(Exception e)
		{
			return Response.serverError().entity(e.getMessage()).build(); 
		}
	}

	@Override
	public Response DispatchExpressSheet(String id, int uid) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
// ������ŵ�������ȥ
	public boolean MoveExpressIntoPackage(String id, String targetPkgId) {
		TransPackage targetPkg = transPackageDao.get(targetPkgId);
		if((targetPkg.getStatus() > 0) && (targetPkg.getStatus() < 3)){	//������״̬��㶨��,�򿪵İ������߻������ܲ���==================================================================
			return false;
		}

		TransPackageContent pkg_add = new TransPackageContent();
		pkg_add.setPkg(targetPkg);
		pkg_add.setExpress(expressSheetDao.get(id));
		pkg_add.setStatus(TransPackageContent.STATUS.STAUS_UNPACK);
		transPackageContentDao.save(pkg_add); 
		return true;
	}

	public boolean MoveExpressFromPackage(String id, String sourcePkgId) {
		TransPackage sourcePkg = transPackageDao.get(sourcePkgId);
		if((sourcePkg.getStatus() > 0) && (sourcePkg.getStatus() < 3)){
			return false;
		}

		TransPackageContent pkg_add = transPackageContentDao.get(id, sourcePkgId);
		pkg_add.setStatus(TransPackageContent.STATUS.STATUS_PACK);
		transPackageContentDao.save(pkg_add); 
		return true;
	}

	public boolean MoveExpressBetweenPackage(String id, String sourcePkgId, String targetPkgId) {
		//��Ҫ�����������
		MoveExpressFromPackage(id,sourcePkgId);
		MoveExpressIntoPackage(id,targetPkgId);
		return true;
	}

	@Override
	public Response DeliveryExpressSheetId(String id, int uid) {
		try{
			String pkgId = userInfoDao.get(uid).getDelivePackageID();
			ExpressSheet nes = expressSheetDao.get(id);
			if(nes.getStatus() != ExpressSheet.STATUS.STATUS_TRANSPORT){
				return Response.ok("����˵�״̬����!�޷�����").header("EntityClass", "E_ExpressSheet").build(); 
			}
			
			if(transPackageContentDao.getSn(id, pkgId) == 0){
				//��ʱ��һ������ʽ,��·�˰����Ĵ��ݹ���,�Լ��Ļ�������һ��
				MoveExpressBetweenPackage(id, userInfoDao.get(uid).getReceivePackageID(),pkgId);
				return Response.ok("����˵�״̬����!\n�����Ϣû�������ɼ�������!").header("EntityClass", "E_ExpressSheet").build(); 
			}
				
			nes.setDeliver(String.valueOf(uid));
			nes.setDeliveTime(getCurrentDate());
			nes.setStatus(ExpressSheet.STATUS.STATUS_DELIVERIED);
			expressSheetDao.save(nes);
			//���ɼ�������ɾ��
			MoveExpressFromPackage(nes.getID(),pkgId);
			//���û����ʷ��¼,���Ѹ����ռ��ͽ����ļ�¼
			return Response.ok(nes).header("EntityClass", "ExpressSheet").build(); 
		}
		catch(Exception e)
		{
			return Response.serverError().entity(e.getMessage()).build(); 
		}
	}

	@Override
	public List<TransPackage> getTransPackageList(String property,
			String restrictions, String value) {
		List<TransPackage> list = new ArrayList<TransPackage>();
		switch(restrictions.toLowerCase()){
		case "eq":
			list = transPackageDao.findBy(property, value, "ID", true);
			break;
		case "like":
			list = transPackageDao.findLike(property, value+"%", "ID", true);
			break;
		}
		return list;
	}

	@Override
	public Response getTransPackage(String id) {
		TransPackage es = transPackageDao.get(id);
		return Response.ok(es).header("EntityClass", "TransPackage").build(); 
	}

	@Override
	public Response newTransPackage(String id, int uid) {
		try{
			TransPackage npk = new TransPackage();
			npk.setID(id);
			//npk.setStatus(value);
			npk.setCreateTime(new Date());
			transPackageDao.save(npk);
			return Response.ok(npk).header("EntityClass", "TransPackage").build(); 
		}
		catch(Exception e)
		{
			return Response.serverError().entity(e.getMessage()).build(); 
		}
	}

	@Override
	public Response saveTransPackage(TransPackage obj) {
		try{
			transPackageDao.save(obj);			
			return Response.ok(obj).header("EntityClass", "R_TransPackage").build(); 
		}
		catch(Exception e)
		{
			return Response.serverError().entity(e.getMessage()).build(); 
		}
	}

	@Override
	public Response savePosion(Posion obj) {
		try {
			posionDao.save(obj);
			return Response.ok(obj).header("EntityClass", "Posion").build();
		}catch(Exception e)
		{
			return Response.serverError().entity(e.getMessage()).build(); 
		}
	}

	@Override
	public List<Posion> getPosition(String expressId)  {
		// TODO Auto-generated method stub
		List<Posion> ans = posionDao.findBy("packageId",expressId, "posCode", true);
		return ans;
	}

	@Override
	public List<TransPackage> getTransHistory(String expressId)  {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<>();
				
		list =	transPackageContentDao.getPackageID(expressId);
		System.out.println("llllllllllllist"+list);
		
		List<ExpressSheet> list2 = new ArrayList<ExpressSheet>();
		List<TransPackage> list3 = new ArrayList<TransPackage>();
		for(int i=0;i<list.size();i++)
		{	
			TransPackage t= transPackageDao.get(list.get(i));
			list3.add(t);
			
			ExpressSheet e = new ExpressSheet();
			e.setID(list.get(i));
			list2.add(e);
		}
		System.out.println("ttttttttttttttttt"+list3.toString());
		return list3;
		//return null;
	}

	@Override
	public List<UserInfo> getUserInfo() {
		// TODO Auto-generated method stub
		List <UserInfo> list = userInfoDao.findBy();
		return list; 
	}

	@Override
	public List<TransPackage> getTransPackage() {
		// TODO Auto-generated method stub
		List <TransPackage> list = transPackageDao.findBy();
		return list; 
	}

	@Override
	public Response newUser(UserInfo userInfo) {
		// TODO Auto-generated method stub
		try{
			userInfoDao.save(userInfo);
			return Response.ok(userInfo).header("EntityClass", "UserInfo").build(); 
		}
		catch(Exception e)
		{
			return Response.serverError().entity(e.getMessage()).build(); 
		}
	}

	@Override
	public Response pack(int uid, String eid, String pid) {
		// TODO Auto-generated method stub
		TransPackageContent tpc = transPackageContentDao.get(eid,pid);
		try {
			//����״̬
			tpc.setStatus(TransPackageContent.STATUS.STATUS_PACK);
			transPackageContentDao.save(tpc);
			return Response.ok().header("EntityClass", "TransPackageContent").build();
		}catch(Exception e)
		{
			return Response.serverError().entity(e.getMessage()).build(); 
		}
		

	}

	@Override
	public Response pack2(String eid, String pid) {
		// TODO Auto-generated method stub
		TransPackageContent tpc = transPackageContentDao.get(eid,pid);
		try {
			//����״̬
			tpc.setStatus(TransPackageContent.STATUS.STATUS_PACK);
			transPackageContentDao.save(tpc);
			return Response.ok().header("EntityClass", "TransPackageContent").build();
		}catch(Exception e)
		{
			return Response.serverError().entity(e.getMessage()).build(); 
		}
	}

	@Override
	public Response unpack(String eid, String pid) {
		// TODO Auto-generated method stub
		TransPackageContent tpc = transPackageContentDao.get(eid,pid);
		try {
			//����״̬
			tpc.setStatus(TransPackageContent.STATUS.STAUS_UNPACK);
			transPackageContentDao.save(tpc);
			return Response.ok().header("EntityClass", "TransPackageContent").build();
		}catch(Exception e)
		{
			return Response.serverError().entity(e.getMessage()).build(); 
		}
	}

	@Override
	public Response pack3(String eid, String pid, String snode, String tnode) {
		// TODO Auto-generated method stub
		
		TransPackage t= transPackageDao.get(pid);
		ExpressSheet e = expressSheetDao.get(eid);
		
		/*
		 * if( t!=null)System.out.println("isexist"+t.toString()); else
		 * System.out.println("nullllllllllllllllllllllllll");
		 */
		//System.out.println(new Date());
		SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
		System.out.println(ft.format(new Date()));
		String time = ft.format(new Date());
	
		//�ж�traspackage�Ƿ����
		if(t==null)
		{
			TransPackage tp = new TransPackage();
			tp.setCreateTime(getCurrentDate());
		    tp.setID(pid);
			tp.setSourceNode(snode); tp.setTargetNode(tnode);
			tp.setStatus(TransPackage.STATUS.STATUS_PACK);
			System.out.println("tpppppppppp"+tp.toString());
			transPackageDao.save(tp);
			
			TransPackageContent tpc = new TransPackageContent();
			tpc.setPkg(tp);
			tpc.setExpress(e);
			tpc.setStatus(TransPackageContent.STATUS.STATUS_PACK);
			transPackageContentDao.save(tpc);
		}else {
			TransPackageContent tpc = new TransPackageContent();
			tpc.setPkg(t);
			tpc.setExpress(e);
			tpc.setStatus(TransPackageContent.STATUS.STATUS_PACK);
			transPackageContentDao.save(tpc);
		}
		//return null;
		return Response.ok().header("EntityClass", "TransPackageContent").build();
	}

	@Override
	public Response transport(int uid, String pid) {
		// TODO Auto-generated method stub
		UserInfo u = userInfoDao.get(uid);
		//System.out.println("userInfo:"+u.toString());
		u.setTransPackageID(pid);
		userInfoDao.save(u);
		//return null;
		return Response.ok().header("EntityClass", "UserInfo").build();
	}

	@Override
	public Response deliver(String eid, String did) {
		// TODO Auto-generated method stub
		TransPackageContent tpc = new TransPackageContent();
		TransPackage t= transPackageDao.get(did);
		ExpressSheet e = expressSheetDao.get(eid);
		tpc.setPkg(t);
		tpc.setExpress(e);
		tpc.setStatus(TransPackageContent.STATUS.STATUS_DELIVERIED);
		System.out.println("deliver::: "+tpc.toString());
		
		TransPackageContent ttpc = transPackageContentDao.get(eid,did);
		if(ttpc==null) {
			System.out.println("saving����");
			transPackageContentDao.save(tpc);
		}
		else {
			System.out.println("updating����");
			ttpc.setStatus(TransPackageContent.STATUS.STATUS_DELIVERIED);
			transPackageContentDao.update(ttpc);
		}
		return Response.ok().header("EntityClass", "TransPackageContent").build();
	}

	@Override
	public Response finish(String eid, String did) {
		// TODO Auto-generated method stub
		TransPackageContent tpc = new TransPackageContent();
		TransPackage t= transPackageDao.get(did);
		ExpressSheet e = expressSheetDao.get(eid);
		tpc.setPkg(t);
		tpc.setExpress(e);
		tpc.setStatus(TransPackageContent.STATUS.STATUS_FINISH);
		
		TransPackageContent ttpc = transPackageContentDao.get(eid,did);
		if(ttpc==null) {
			System.out.println("saving����");
			transPackageContentDao.save(tpc);
		}
		else {
			System.out.println("updating����");
			ttpc.setStatus(TransPackageContent.STATUS.STATUS_FINISH);
			transPackageContentDao.update(ttpc);
		}
		return Response.ok().header("EntityClass", "TransPackageContent").build();
	}
	
}
