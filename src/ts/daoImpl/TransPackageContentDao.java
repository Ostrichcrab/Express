package ts.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;

import ts.daoBase.BaseDao;
import ts.model.TransPackageContent;

public class TransPackageContentDao extends BaseDao<TransPackageContent,Integer> {
	public TransPackageContentDao(){
		super(TransPackageContent.class);
	}
	
	public TransPackageContent get(String expressId, String packageId){
		List<TransPackageContent> list  = new ArrayList<TransPackageContent>();
		list = super.findBy("SN", true, 
				Restrictions.sqlRestriction("ExpressID = '"+ expressId + "' and PackageID = '" + packageId +"'"));
		if(list.size() == 0)
			return null;
		return list.get(0);
	}

	public int getSn(String expressId, String packageId){
		TransPackageContent cn = get(expressId,packageId);
		if(cn == null){
			return 0;
		}
		return get(expressId,packageId).getSN();
	}

	public void delete(String expressId, String packageId){
		List<TransPackageContent> list  = new ArrayList<TransPackageContent>();
		list = super.findBy("SN", true, 
				Restrictions.eq("ExpressID", expressId),
				Restrictions.eq("PackageID",packageId));
		for(TransPackageContent pc : list)
			super.remove(pc);
		return ;
	}
	
	/*
	 * 根据expressSheetID，得到其待过的TransPackage的ID
	 * 
	 * */
	public List<String> getPackageID(String expressSheetID){
		List<TransPackageContent> list= super.findBy();
		System.out.println("hhhhhhhhhhhistory:    "+expressSheetID);
		List<String> ans = new ArrayList<>();
		for(TransPackageContent item : list) {
			System.out.println("history:    "+item.getExpress().getID());
			if(item.getExpress().getID().equals(expressSheetID) ) {
				System.out.println("here:  "+item.getPkg().getID());
				ans.add(item.getPkg().getID());
			}
		}
		System.out.println("historyyyyyyyyyyyyy:    "+ans);
		return ans;
	}
}
