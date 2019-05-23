package ts.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;

import ts.daoBase.BaseDao;
import ts.model.TransPackage;
import ts.model.TransPackageContent;

public class TransPackageDao extends BaseDao<TransPackage,String> {
	public TransPackageDao(){
		super(TransPackage.class);
	}
	public TransPackage get( String Id){
		List<TransPackage> list  = new ArrayList<TransPackage>();
		list = super.findBy("ID", true, 
				Restrictions.sqlRestriction("ID = '"+ Id  +"'"));
		if(list.size() == 0)
			return null;
		return list.get(0);
	}
}
