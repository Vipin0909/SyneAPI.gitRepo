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

import org.apache.juneau.jso.JsoParser;

//import javax.json.JsonObject;

import org.apache.juneau.json.JsonParser;
import org.apache.juneau.json.JsonSerializer;
import org.apache.juneau.parser.ParseException;
import org.apache.juneau.serializer.SerializeException;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.JsonArray;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.path.json.JsonPath;
import pojo.FT_tag18_Test;
import pojo.FixClass;
import resources.FT_18_TestData;
import resources.TestDataBuild;

import org.junit.Test;
import org.junit.Assert;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Connect_to_Unix_Server_BKP
{
	static String fixmessage;
	static String complianceID;
	//static String complianceid;
	static PrintStream ps;
	static Channel channel;
	static Session session;
	static OutputStream ops;
	static String output;
	static HashMap<String, Object> map;
	static HashMap<String,Object> map1;
	static String[] result;
	static String jsonstring;
	static String jsonoutput;
	static JsonPath js_ActualFixMessage;
	static JsonPath js_ExpectedFixValues;
	static String key;
	static String value;
	static FixClass ExpectedFixValues;
	static FixClass fixoutcome;
	static FT_tag18_Test fixoutcome18;
	static FixClass f1;
	static JsonPath ExpectedTestData_Vipin;
	static String VipinJson;
	static String ActualFixMessageNewVipin;
	static JsonPath js_ActualFixMessagenewVIPIN;
	ObjectMapper mapper;
	ObjectMapper deserialise;
	static String newjsonString;
	
	TestDataBuild Fixdata = new TestDataBuild();
	FT_18_TestData data1 = new FT_18_TestData();
	
	
	@Given("unix terminal connection using putty")
	public void unix_terminal_connection_using_putty() {
		System.out.println("in Given method... ");
		String host="18.191.62.202";
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
	    	System.out.println("Connected to server ...");
	    	
	    	channel = session.openChannel("shell");
		    ops = channel.getOutputStream();
		    ps = new PrintStream(ops, true);

		    channel.connect();
	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	}
	
	@Given("based on given Compliance ID {string} extract the FI logs")
	public void based_on_given_compliance_id_extract_the_fi_logs(String complianceID) throws IOException 
	{
		try 
		{
			ps.println("sudo su -");
		    ps.println( "cd /var/lib/jenkins/workspace/SyneAPI/src/main/java/pojo");
		    ps.println("grep -ai "+complianceID+" apiLogs");
		    ps.close();
	
		    InputStream in=channel.getInputStream();
	        byte[] tmp=new byte[1024];
	        while(true)
	        {
	          while(in.available()>0)
	          {
	            int i=in.read(tmp, 0, 1024);
	            if(i<0)break;
	            System.out.print(new String(tmp, 0, i));
	            session.disconnect(); // this was the change made by me
	          }
	          if(channel.isClosed()){
	            System.out.println("exit-status: "+channel.getExitStatus());
	            break;
	          }
	          try{Thread.sleep(1000);}catch(Exception ee){}
	        }
	        
	       // session.disconnect();
		} 
		catch (Exception e) {
			
		}
		channel.disconnect();
		session.disconnect();
	     
	}

	@Given("extract Fix logs only")
	public void extract_fix_logs_only() {
		//fix output will be stored in fixlogs.txt file
		try {
			Path files = Path.of("C:\\eclipse_workspace_2022\\SyneAPI\\target\\fixlogs.txt");
		    String fixfile = Files.readString(files);
			Pattern p = Pattern.compile("8=FIX(.+)(?<=\\|)18=(.+?)(?=\\|)", Pattern.MULTILINE);
			Matcher m = p.matcher(fixfile);
			// we can use the List or Map to store the String outcome
			//List<String> list = new ArrayList();
			while (m.find()) {
			fixmessage = m.group();
			
			System.out.println( "Extracted only FIX message from Unix server based on comp id ---> " +  fixmessage);
			}
		}catch(Exception e) {
			
		}
	}

	@Given("convert the fix message into JSON and parse it for validation")
	public void fixtoJSON() throws SerializeException, ParseException, IOException {
	    
		List<HashMap<String,Object>> list = new ArrayList<HashMap<String, Object>>();
		map = new HashMap<String, Object>();
		map1 = new HashMap<String,Object>();
		
		result = fixmessage.split("\\|");
		
    	// iterate the result and add them to a HashMap 
        for (String fixoutcome : result) { 
            String[] fix1 = fixoutcome.split("="); 
            String key = fix1[0].trim().toString(); 
            String value = fix1[1].trim().toString(); 
            map.put(key, value);
            
        }
        
        	
        //map1.put("Fixjson1",map);
	   	//list.add(map1);
	   	 	
	   	 	//list.add((HashMap<String, Object>) map.get(array.toString()));
        	 
            mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            //jsonstring =  mapper.writeValueAsString(map);
            
            jsonstring= mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
            newjsonString = jsonstring.replaceAll("\\s+", "");
            System.out.println("this is new JSON"+ newjsonString);
            //js_ActualFixMessage = new JsonPath(jsonstring);
            //System.out.println(js_ActualFixMessage.getString("Fixjson1.18"));
            
            
          //json to pojo = temp code here
        	deserialise = new ObjectMapper();
    		deserialise.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    		FT_tag18_Test ft = deserialise.readValue(newjsonString, FT_tag18_Test.class);
    		System.out.println("json to pojo" + ft.toString());
    		
    		
            
                    
            
//            int FixMessageCount = js_ActualFixMessage.getInt("Fixjson.size()");
//            for(int i=0;i<FixMessageCount;i++) {
//            	
//            	ActualFixMessageNewVipin=js_ActualFixMessage.get("Fixjson["+i+"].18").toString();
//          	}
//            System.out.println("this is the actual Fix message converted into JSON which is extraced from Unix server" + ActualFixMessageNewVipin);
            //js_ActualFixMessagenewVIPIN = new JsonPath(ActualFixMessageNewVipin);
           
         // js_ActualFixMessage Object has the knowledge of fix message came from unix server which is extracted based on comp id
            //Json path concept -- to parse the json means to validate the values in json we have to use the JSONPATH to traverse the fields of json file, 
            //store it some varibales and validate. We have to use this during assertion
            
            
            //System.out.println(" what is the printing here ..."+js_ActualFixMessage.getString());
             
            // POJO to JSON
            //JsonSerializer js1 = JsonSerializer.DEFAULT_READABLE;
            
           // here we are using testdatabuild concept, this is expected data. Fix tag should give this result when US client trade US Broker.
           // ExpectedFixValues object has knowledge of test data.
            //ExpectedFixValues=  Fixdata.SetFixTags("FIX1.1", "449", "AE", complianceID, "20140402-11:38:34", "TR_UAT_VENDOR", "8", "GBP", "XYZ", "Price", "MULEY");
            									//SetFixTags(String t8,String t9,String t10,String t11,String t12,String t13,String t14,String t15,String t16,String t17,String t18)
            //String jsonserialistring = js1.serialize(ExpectedFixValues);
            //js_ExpectedFixValues = new JsonPath(jsonserialistring);
            
            //System.out.println("Expected tag value of Fix tag 18 is -- " + js_ExpectedFixValues.getString("t18"));
           //System.out.println("Actual tag value of Fix tag 18 is -- " + js_ActualFixMessage.getString("FixJson.18"));
            //System.out.println(" POJO to JSON "+ jsonserialistring);
            
            //JSON to POJO
//       
            //JsonParser jsonparser = JsonParser.DEFAULT;
            //fixoutcome = jsonparser.parse(jsonstring, FixClass.class);
            
            //System.out.println("print the fix outcome "+fixoutcome);
            
            //System.out.println("JSON to POJO " + fixoutcome);
            //System.out.println(fixoutcome.getT18());
            //System.out.println("+++++++tag 18 val is+++++++++++++++ " + fixoutcome.getFix_tag18());
            
    		//Assert.assertEquals(ExpectedFixValues.getT18(), js_ActualFixMessage.getString("Object.18"));
    		//Assert.assertEquals(js_ActualFixMessage.getString(key), js_ExpectedFixValues.getString(value));
    		//Connect_to_Unix_Server.validate_fixmessages(jsonserialistring, jsonserialistring);
            
	}

	@Given("Fix values {string} {string} {string} {string} {string} {string} {string} {string} {string} {string} {string} {int}")
	public void fix_values(String t8, String t9, String t10, String t11, String t12, String t13, String t14, String t15, String t16, String t17, String t18,int Fix_tag18) throws SerializeException {
		
		
		FixClass f1 = Fixdata.SetFixTags(t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, Fix_tag18);
		JsonSerializer js2 = JsonSerializer.DEFAULT_READABLE;
		String VipinJson = js2.serialize(f1); // serialise method accept Object
		
		System.out.println("Expected test data converted in to POJO to JSON . ."+ VipinJson);
		//ExpectedTestData_Vipin = new JsonPath(VipinJson);
				//System.out.println("what is this "+ExpectedTestData_Vipin);
				//System.out.println( "This is new Vipin POJO to JSON expected  --- > " + ExpectedTestData_Vipin.getString("t18"));
				//Assert.assertEquals(ExpectedTestData_Vipin.getString("t18"), js_ActualFixMessage.get("FixJson.18"));
	}
	
	@Given("Tag {string} should have value as {string}")
	public void tag_should_have_value_as(String Key, String expectedValue) {
		//ExpectedTestData_Vipin = new JsonPath(VipinJson);
		//System.out.println(ExpectedTestData_Vipin.getString("t18"));
		//System.out.println(ActualFixMessageNewVipin);
		//Assert.assertEquals(ExpectedTestData_Vipin.getString(Key), js_ActualFixMessage.get("FixJson"));
	}
	
	
	
	@Given("tag value  {string} {string} {string}")
	public void tag_value(String f8, String f11, String name) throws Exception {
		
		//pojo to json
		
		ObjectMapper maps = new ObjectMapper();
		FT_tag18_Test LL = data1.TestTag18(f8, f11, name);
		jsonoutput = maps.writeValueAsString(LL);
		System.out.println("pojo to json >> " +jsonoutput.toString() );
		
		
		
		
	}
}
	
	
	











