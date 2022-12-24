package resources;

import pojo.FT_tag18_Test;

public class FT_18_TestData {

	FT_tag18_Test tt = new FT_tag18_Test();
	
	public FT_tag18_Test TestTag18(String f8,String f11,String name ) {
		
		tt.setF8(f8);
		tt.setF11(f11);
		tt.setName(name);
		
		return tt;
		
		
		}
}
