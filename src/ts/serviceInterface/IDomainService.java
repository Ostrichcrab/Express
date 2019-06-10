package ts.serviceInterface;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ts.model.Count;
import ts.model.ExpressSheet;
import ts.model.Posion;
import ts.model.TransHistory;
import ts.model.TransPackage;
import ts.model.UserInfo;

@Path("/Domain")	//ҵ�����
public interface IDomainService {
    //����������ʽӿ�=======================================================================
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getExpressList/{Property}/{Restrictions}/{Value}") 
	public List<ExpressSheet> getExpressList(@PathParam("Property")String property, @PathParam("Restrictions")String restrictions, @PathParam("Value")String value);

    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getExpressListInPackage/PackageId/{PackageId}/{State}") 
	public List<ExpressSheet> getExpressListInPackage(@PathParam("PackageId")String packageId,@PathParam("State")String state);

    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getExpressSheet/{id}") 
	public Response getExpressSheet(@PathParam("id")String id);

    
    @GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/getExpressSheet")
	public List <ExpressSheet> getExpressSheet();
    
    @GET
   	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
   	@Path("/getUserInfo")
   	public List <UserInfo> getUserInfo();
       
    @GET
   	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
   	@Path("/getTransPackage")
   	public List <TransPackage> getTransPackage();
       
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/newExpressSheet/id/{id}/uid/{uid}") 
	public Response newExpressSheet(@PathParam("id")String id, @PathParam("uid")int uid);
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/saveExpressSheet") 
	public Response saveExpressSheet(ExpressSheet obj);
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/receiveExpressSheetId/id/{id}/uid/{uid}") 
	public Response ReceiveExpressSheetId(@PathParam("id")String id, @PathParam("uid")int uid);
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/dispatchExpressSheetId/id/{id}/uid/{uid}") 
	public Response DispatchExpressSheet(@PathParam("id")String id, @PathParam("uid")int uid);
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/deliveryExpressSheetId/id/{id}/uid/{uid}") 
	public Response DeliveryExpressSheetId(@PathParam("id")String id, @PathParam("uid")int uid);

    //�����������ʽӿ�=======================================================================
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getTransPackageList/{Property}/{Restrictions}/{Value}") 
	public List<TransPackage> getTransPackageList(@PathParam("Property")String property, @PathParam("Restrictions")String restrictions, @PathParam("Value")String value);

    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getTransPackage/{id}") 
	public Response getTransPackage(@PathParam("id")String id);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/newTransPackage") 
    public Response newTransPackage(String id, int uid);
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/newUser") 
    public Response newUser(UserInfo userInfo);


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/saveTransPackage") 
	public Response saveTransPackage(TransPackage obj);
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/savePosion/{X}/{Y}/{pid}") 
	public Response savePosion(@PathParam("X") double X,@PathParam("Y") double Y,@PathParam("pid") String pid);
    
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getPosition/{expressId}")
    public List<Posion> getPosition(@PathParam("expressId") String expressId);
	
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getTransHistory/{expressId}")
	public List<TransPackage> getTransHistory(@PathParam("expressId") String expressId) ;
	
	@GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/pack/{uid}/{eid}/{pid}") 
	public Response pack(@PathParam("uid")int uid, @PathParam("eid")String eid,@PathParam("pid")String pid);
    
	@GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/pack2/{eid}/{pid}") 
	public Response pack2(@PathParam("eid")String eid,@PathParam("pid")String pid);
    
	@GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/pack3/{eid}/{pid}/{snode}/{tnode}") 
	public Response pack3(@PathParam("eid")String eid,@PathParam("pid")String pid,@PathParam("snode")String snode,@PathParam("tnode")String tnode);
    
	
	@GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/unpack/{eid}/{pid}") 
	public Response unpack(@PathParam("eid")String eid,@PathParam("pid")String pid);
    
	@GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/transport/{uid}/{pid}") 
	public Response transport(@PathParam("uid")int uid,@PathParam("pid")String pid);
    
	@GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/deliver/{eid}/{did}") 
	public Response deliver(@PathParam("eid")String eid,@PathParam("did")String did);
    
	@GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/finish/{eid}/{did}") 
	public Response finish(@PathParam("eid")String eid,@PathParam("did")String did);
    
	
	@GET
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/countByUid/{uid}") 
	public List<ExpressSheet> countByUid(@PathParam("uid")String uid);
	
	@GET
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/countAll") 
	public List<Count> countAll();
    
	
}
