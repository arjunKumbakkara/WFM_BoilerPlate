package com.sixdee.wfm.configuration;
import javax.sql.DataSource;
import java.io.File;
import java.util.Vector;
//A Generic Poller!
/**
 * @author Arjun Kumbakkara
 * @version 1.0.0
 * @Sixdee March 5th,2017 Below Class has two implementations based on:
 *         1.Polling output Files 2.Further Processing. 3.Follow along the
 *         comments.
 * 
 * 
 * 
 */
public class PGPPoller implements Runnable {


	String findLoc = null;
	String putLoc = null;
	String keyFileDecode = null;
	String bicsKeyPGP = null;
	String locHSS = null;
	String locOTA = null;

	String bicsOutputrecievedBeforeDecryptputLocation;
	/* For HSS and OTA */
	String DecryptedFileHSS = null;
	String DecryptedFileOTA = null;

	String pollMoveDirectory = null;
	String pollMoveDirectoryDuplicate = null;
	String  movePolledInvalidLoc=null;
	

	// invalidity Check
	String desiredFileExtension = "pgp";
	String recievedFileExtension = null;

	boolean genForHSSDone = false;
	boolean genForOTADone = false;
	String jschUsername = null;
	String jschPassword = null;
	String jschHost = null;

	Vector<File> vs1 = null;
	
	//General Case
	String generalSFTPPort=null;
	
	//FailureFileCase
	String outputFailureFileLoc=null;
	String pollerDelayTime=null;
	
	//DATASOURCE
	DataSource passedDS;
	


	// Employed Constructor
	public PGPPoller() {

	}

	@Override
	public void run() {
		
		
		
	}

}