package com.sixdee.wfm.configuration;
/**
* @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0] 
*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;

@Configuration
@CacheConfig(cacheNames = "globals")
@Order(1)
public class Globals implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger logger = LogManager.getLogger(Globals.class);

	@Autowired
	GlobalCacheStacker globalCacheStacker;

	@Autowired
	ConstantsLoader con;

	@Autowired
	ConstantsLoader constantsLoader;

	public static String[] SECURITY_LAYER_CHECK_CONSTANTS;

	public static Map<String, String> ErrorCodeMapper = null;
	public static Map<String, String> AccountMapper = null;
	public static Map<String, String> EntityLocationMapper = null;
	public static Map<String, String> EntityAreaMapper = null;
	public static Map<String, String> ProductMapper = null;
	public static Map<String, String> SimTypeMapper = null;
	public static Map<String, String> DeviceTypeMapper = null;
	public static Map<String, String> findColumnNameMapper = null;
	public static Map roleMainMenuMapper = null;
	public static Map roleSubMenuMapper = null;
	public static Map countryIdToNameMapper = null;
	public static Map roleIdToNameMapper = null;
	public static Map levelIdToNameMapper = null;
	public static Map tierIdToNameMapper = null;
	public static Map OperatorIdToNameMapper = null;
	public static Map billCycleIdToNameMapper = null;
	public static Map ApplicationTypeIdToNameMapper = null;
	// public static Map stateIdToNameMapper =null;
	public static Map countryIdToCurrencyMapper = null;

	// TAKEN OUT MAPS
	/*
	 * public static Map countryIdToStateListMapper =null; public static Map countryIdToCityListMapper =null;
	 */
	public static Map parentRoleIdToChildrenListMapper = null;
	public static Map orderStatusIdToNameMapper = null;
	public static Map orderStatusNameToIdMapper = null;
	public static Map AccToParentAccMapper = null;
	public static Map menuIdToFeatureId = null;
	public static Map parentRoleToChildRole = null;
	public static Map RoleIdToRoleNameMapper = null;
	public static Map EntityIdToDescStatusMapper = null;
	public static Map featureIdToDependentFeatures = null;
	public static Map ParentAccToChildAccMapper = null;
	public static Map CustomerIdToTypeMapper = null;
	public static Map OrderIdToOrderStatusMapper = null;
	public static Map planIdToPlanNameMapper = null;

	public static Map accountIDToAccountDetailsMapper = null;
	public static Map supplieIDToSupplierDetailsMapper = null;
	public static Map supplierNameToSupplierIdDetailsMapper = null;
	public static Map accountNameToIdMapper = null;

	public static Map serviceIdToHSSTagMapper = null;

	public static Map networkIdToNameMapper = null;
	public static Map customerTypeToPrivListMapper = null;
	public static Map customerTypeToAccountMapper = null;

	public static Map TadigCodeToOperatorId = null;

	// AccountToParentAccountList
	// public static ArrayList<AccountDTO> AccToParentAccArrayList =null;

	// Write List
	public static ArrayList<String> SimEntryWriteList = new ArrayList<String>();

	// Operator Id to Details Master
	public static Map OpkoIdToDetailsArrayList = null;

	// For CBS Failover
	public static List<String> CBSFailoverTryList = null;

	// For HSS Failover
	public static List<String> HSSFailoverTryList = null;

	public static Map<String, String> restAPIFieldValidationMap = new HashMap<String, String>();
	public static Map<String, String> errorCodeMaster = new HashMap<String, String>();

	/* Globals Values */
	public static int loginMaxAttempt = 0;
	public static int loginMaxAttempt_Reset = 0;
	static int unifiedThreadPoolSize = 0;
	public static ExecutorService executorFileUpload = null;

	public static Map systemPropertiesMapper = null;

	public static synchronized ExecutorService unifiedOpsthreadPool() throws Exception {
		if (executorFileUpload == null) {
			logger.info(" :::::::::::::::::::::::::::::::::::::|| LOADING..Unified Operations thread Pool Loaded with size:" + unifiedThreadPoolSize + " ||:::::::::::::::::::::::::::::::::");
			executorFileUpload = Executors.newFixedThreadPool(unifiedThreadPoolSize);
		} else {
			return executorFileUpload;
		}
		return executorFileUpload;

	}

	public void loadGlobalMaps() throws Exception {

		try {
			unifiedThreadPoolSize = Integer.parseInt("5");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Exception While loading unifiedThreadPoolSize");
			e.printStackTrace();
		}

		globalCacheStacker.getApiFieldValidationData();
		logger.info("restAPIFieldValidationMap size  >> " + restAPIFieldValidationMap.size());

		globalCacheStacker.getErrorCodeMaster();
		logger.info("errorCodeMaster size  >> " + errorCodeMaster.size());

		executorFileUpload = unifiedOpsthreadPool();
		logger.info(" :::::::::::::::::::::::::::::::::::::|| LOADING....||:::::::::::::::::::::::::::::::::");

		/*
		 * //Error Mappings logger.info(" || LOADING...ERROR CODE MAPPINGS||"); ErrorCodeMapper = globalCacheStacker.errorCodeMapper(); logger.info("ERROR CODE MAPPER CONTAINS : " +
		 * ErrorCodeMapper.size());
		 */

		/*
		 * //Bill Cycle Meta Details Mappings. logger.info(" || LOADING...billCycleIdToNameListMapper Meta Details Mappings||"); billCycleIdToNameMapper =
		 * globalCacheStacker.getBillIdToCycleNameList(); logger.info("billCycleIdToNameListMapper  CONTAINS : " + billCycleIdToNameMapper.size());
		 */
	}

	public void loadGlobalValues() throws Exception {

		logger.info(" :::::::::::::::::::::::::::::::::::::|| LOADING GLOBAL CONSTANTS....||:::::::::::::::::::::::::::::::::");
		// Max Attempt Global
		logger.info(" || LOADING...MAX ATTEMPT GLOBAL||");
		loginMaxAttempt = 6;
		loginMaxAttempt_Reset = 5;
		SECURITY_LAYER_CHECK_CONSTANTS = Arrays.asList(("" + constantsLoader.getSecurityLayerCheckConstants()).split(","))
				.toArray(new String[Arrays.asList(("" + constantsLoader.getSecurityLayerCheckConstants()).split(",")).size()]);
		logger.info(" || Security Layer Check Constraints||" + constantsLoader.getSecurityLayerCheckConstants());
	}

	/*
	 * public boolean RefreshCache(String cases) throws Exception { boolean isCacheRefreshed=false; boolean isCacheRefreshedSelf=false;
	 * 
	 * 
	 * switch (cases) { case "S": logger.info(" :::::::::::::::::::::::::::::::::::::|| REFRESHING CACHE  TRIGGER:SELF....||:::::::::::::::::::::::::::::::::"); loadGlobalMaps(); loadGlobalValues();
	 * isCacheRefreshed=true; logger.info(" :::::::::::::::::::::::::::::::::::::|| CACHE REFRESHED |SELF| TRIGGERED....||:::::::::::::::::::::::::::::::::"); break; case "N":
	 * logger.info(" :::::::::::::::::::::::::::::::::::::|| REFRESHING CACHE TRIGGER:NEIGHBOUR....||:::::::::::::::::::::::::::::::::"); isCacheRefreshed=updateNeighboursCaches();
	 * logger.info(" :::::::::::::::::::::::::::::::::::::|| CACHE REFRESHED |NEIGHBOUR| TRIGGERED....||:::::::::::::::::::::::::::::::::"); break;
	 * 
	 * case "SN": logger.info(" :::::::::::::::::::::::::::::::::::::|| REFRESHING CACHE TRIGGER:SELF&NEIGHBOURS....||:::::::::::::::::::::::::::::::::"); loadGlobalMaps(); loadGlobalValues();
	 * isCacheRefreshedSelf=true; if(isCacheRefreshedSelf){ logger.info("REFRESH on SELF-CACHE successful.Graduating to NEIGHBOURS."); isCacheRefreshed=updateNeighboursCaches(); }
	 * logger.info(" :::::::::::::::::::::::::::::::::::::|| CACHE REFRESHED |SELF&NEIGHBOURS| TRIGGERED....||:::::::::::::::::::::::::::::::::"); break; default:
	 * logger.info(" :::::::::::::::::::::::::::::::::::::|| REFRESHING CACHE TRIGGER: NO CASE....||:::::::::::::::::::::::::::::::::");
	 * logger.info(" :::::::::::::::::::::::::::::::::::::|| REFRESHING CACHE TRIGGER: FAILURE SINCE NO CASE....||:::::::::::::::::::::::::::::::::"); break; } return isCacheRefreshed; }
	 * 
	 * /*public boolean updateNeighboursCaches() { logger.info("FURTHERING UPDATION OF NEIGHBOURING APPLICATION INSTANCES"); boolean flag=false; Client client=null; WebResource webResource=null;
	 * ClientResponse response=null; String output=null; String Bearer_Token=null; ArrayList<String> failureList = new ArrayList<String>();
	 * 
	 * try { if(con.getNeighbourCacheUpdation().size()>0){ for(int i=0;con.getNeighbourCacheUpdation()!=null && con.getNeighbourCacheUpdation().size()>0 &&
	 * i<con.getNeighbourCacheUpdation().size();i++){ //try{ logger.info("##########Updating Cache of---> Instance de :"+con.getNeighbourCacheUpdation().get(i)+"########"); client= Client.create();
	 * webResource = client.resource(con.getNeighbourCacheUpdation().get(i)+"/config/RefreshCacheFromNeighbour"); //response = webResource.type("application/json").get(ClientResponse.class); response
	 * = webResource.header("Content-Type", "application/json") .header("X-Requested-With", "XMLHttpRequest") //.header("X-Authorization", Bearer_Token) .header("Cache",
	 * "no-cache").get(ClientResponse.class);
	 * 
	 * if (response.getStatus() != 200) { //throw new RuntimeException("Failed : HTTP error code : "+ response.getStatus());
	 * logger.info("******Failure Updating Cache*******"+con.getNeighbourCacheUpdation().get(i));
	 * logger.error("CACHE UPDATION FAILURE for :::::::::"+con.getNeighbourCacheUpdation().get(i)+":::::::::::::::::::::...SYSTEM INTIMATING FAILURE!!!!");
	 * failureList.add(con.getNeighbourCacheUpdation().get(i)); continue; //throw new Exception("CACHE UPDATION FAILURE ...SYSTEM INTIMATING FAILURE"); }else{
	 * logger.info("##########Success Updation of Cache ---> Instance de :"+con.getNeighbourCacheUpdation().get(i)+"########"); } logger.info("Response from Instance post Updation \n"); output =
	 * response.getEntity(String.class); logger.info("Response from Neighbour Cache:::"+output); }catch(Exception ee){ ee.printStackTrace(); } }
	 * 
	 * //Only if all Instances are Updated Successfully.No failures. flag=true;
	 * 
	 * logger.info("DONE AWAY WITH UPDATION OF NEIGHBOURING APPLICATION INSTANCES ! with Failure Count as"+failureList.size()); logger.info("Failed URLS"+failureList);
	 * 
	 * }else{ throw new Exception("Multiple Instance Entries have not been updated.Check Propertieds file for Details."); }
	 * 
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } finally { } return flag; }
	 */

	public void kickStartPoller() throws Exception {

		logger.info(" :::::::::::::::::::::::::::::::::::::|| Starting POLLER....||:::::::::::::::::::::::::::::::::");

		/* Remember this is a single thread,In case of multiple threads,Initiate the Executor Service with @postConstruct Annotation. */
		PGPPoller pollerThread = new PGPPoller();
		Thread handlerThread = new Thread(pollerThread);
		handlerThread.start();
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		try {

			logger.info(" :::::::::::::::::::::::::::::::::::::|| LOADING APPLICATION CONFIGURATIONS [CACHED STATIC MAPPINGS]||:::::::::::::::::::::::::::::::::");
			loadGlobalMaps();
			loadGlobalValues();
			kickStartPoller();
			logger.info(" ::::::::::::::::::::::::::::::::::;;;;;;;;;;:::|| ALL APPLICATION CONFIGURATIONS AND GLOBALS LOADED||:::::::::::::::::::::::::::::::::::::::::::::");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
