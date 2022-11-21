package stepDefination;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import javax.json.JsonObject;

import org.apache.juneau.json.JsonParser;
import org.apache.juneau.json.JsonSerializer;
import org.apache.juneau.parser.ParseException;
import org.apache.juneau.serializer.SerializeException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.path.json.JsonPath;
import pojo.FixClass;
import resources.TestDataBuild;

import org.junit.Test;
import org.junit.Assert;

public class Connect_to_Unix_Server 
{
	static String fixmessage;
	static String complianceID="ABCD1";
	static PrintStream ps;
	static Channel channel;
	static Session session;
	static OutputStream ops;
	static String output;
	static HashMap<String, Object> map;
	static HashMap<String,Object> map1;
	static String[] result;
	static String jsonstring;
	static JsonPath js;
	static String key;
	static String value;
	static FixClass f1;
	static FixClass fixoutcome;
	
	TestDataBuild Fixdata = new TestDataBuild();
	
	@Given("unix terminal connection using putty")
	public void unix_terminal_connection_using_putty() {
		System.out.println("in Given method... ");
		String host="3.144.74.169";
	    String user="ec2-user";
	    String password="Vips*1818";
	    String privateKeyPath = "C:\\DevOps\\Jenkins_Server_2022.pem";
	    
	    try 
	    {
		    	Properties config = new Properties(); 
		    	config.put("StrictHostKeyChecking", "no");
		    	JSch jsch = new JSch();
		    	jsch.addIdentity(privateKeyPath);
		    	session=jsch.getSession(user, host, 22);
		    	session.setPassword(password);
		    	session.setConfig(config);
		    	session.connect();
		    		    	
		    	channel = session.openChannel("shell");
			    ops = channel.getOutputStream();
			    ps = new PrintStream(ops, true);
			    channel.connect();
			    
			    if(channel.isConnected()==true)
			    {
		    		System.out.println("Connected to server ...");
		    	}else {
		    		System.out.println("unix server is down");
		    	}
	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	}
	
	@Given("based on given Compliance ID {string} extract the FI logs")
	public void based_on_given_compliance_id_extract_the_fi_logs(String complianceid) throws IOException 
	{
		// send the commands on unix console
		
				System.out.println(" extract data for this compliance id "+ complianceid);
				ps.println("sudo su -");
			    ps.println("cd /var/lib/jenkins/workspace/BankAPI/src/main/java");
			    ps.println("grep -ai "+complianceid+" apiLogs");
			   
			    InputStream in=channel.getInputStream();
			    byte[] tmp=new byte[1024];
			    while(true)
		        {
		        	while(in.available()>0)
		          {
			   		int i=in.read(tmp, 0, 1024);
		            if(i<0)
		            break;
		            System.out.print(new String(tmp, 0, i));
	
		          }
		            
		          if(channel.isClosed())
		          {
		            System.out.println("exit-status: "+channel.getExitStatus());
		            channel.disconnect();
		            break;
		          }
		          //try{Thread.sleep(1000);}catch(Exception ee){}
		        }
			  
			    session.disconnect();
			    in.close();
	}

	@Given("extract Fix logs only")
	public void extract_fix_logs_only() {
		//fix output will be stored in test.json file
		try {
			Path files = Path.of("C:\\eclipse_workspace_2022\\Siri\\target\\extractedFix_logs.txt");
		    String fixfile = Files.readString(files);
			Pattern p = Pattern.compile("8=FIX(.+)(?<=\\|)18=(.+?)(?=\\|)", Pattern.MULTILINE);
			Matcher m = p.matcher(fixfile);
			// we can use the List or Map to store the String outcome
			//List<String> list = new ArrayList();
			while (m.find()) {
			fixmessage = m.group();
			
			System.out.println( "Fix Log for Compliance ID---> " + Connect_to_Unix_Server.complianceID +"" +  fixmessage);
		
			}
		}catch(Exception e) {
			
		}
	}
	@Given("convert the fix message into JSON and parse it for validation")
	public void fixtoJSON() throws SerializeException, JsonProcessingException, ParseException {
	    
		List<HashMap<String,Object>> list = new ArrayList<HashMap<String, Object>>();
		map = new HashMap<String, Object>();
		map1 = new HashMap<String,Object>();
		
		result = fixmessage.split("\\|");
		
    	// iterate the result and add them to a HashMap 
        for (String fixoutcome : result) { 
        	
            String[] fix1 = fixoutcome.split("="); 
            //System.out.println(" this is fix1 "+fix1);
            
            String key = fix1[0].trim().toString(); 
            String value = fix1[1].trim().toString(); 
            map.put(key, value);
           
        }
	        map1.put("Object", map);
	   	 	list.add(map1);
        	 
            ObjectMapper mapper = new ObjectMapper();
            jsonstring =  mapper.writeValueAsString(list);
            System.out.println("Fix message in JSON format for compliance id "+complianceID+"  " + jsonstring);
                      
            // js obejct has the knowledge of fix message came from unix server which is extracted based on comp id
            js = new JsonPath(jsonstring);
            
            int FixTagCount = js.getInt("Object.size()");
            System.out.println(js.getString("Object.18"));
            System.out.println(js.getString("Object.17"));
            System.out.println(js.getString("Object.11"));
            System.out.println(FixTagCount);
             
            // POJO to JSON
            JsonSerializer js1 = JsonSerializer.DEFAULT_READABLE;
            
           f1=  Fixdata.SetFixTags("FIX1.1", "449", "AE", "ABCD1", "20140402-11:38:34", "TR_UAT_VENDOR", "8", "GBP", "XYZ", "Price", "RPM");
            String jsonserialistring = js1.serialize(f1);
            System.out.println(" POJO to JSON "+ jsonserialistring);
            
            //JSON to POJO
       
            JsonParser jsonparser = JsonParser.DEFAULT;
            FixClass fixoutcome = jsonparser.parse(jsonserialistring, FixClass.class);
            System.out.println("JSON to POJO " + fixoutcome);
            //System.out.println(fixoutcome.getT18());
            System.out.println("tag 18 val is " + fixoutcome.getT18());
            
	}
	
	public void validate_fixmessages(String key,String value) {
		js = new JsonPath(jsonstring);
		Assert.assertEquals(js.getString(key), fixoutcome);
		//Assert.assertEquals(js.getString("Object.18"),fixoutcome.getT18());
        //Assert.assertEquals(js.getString("Object.15"),fixoutcome.getT15());
        //Assert.assertEquals(js.getString("Object.17"),fixoutcome.getT17());
	}
}

	
	
	

