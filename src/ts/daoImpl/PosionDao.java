package ts.daoImpl;

import java.util.ArrayList;
import java.util.List;

import ts.daoBase.BaseDao;
import ts.model.Posion;

public class PosionDao extends BaseDao<Posion, String> {
	public PosionDao() {
		super(Posion.class);
	}


}
