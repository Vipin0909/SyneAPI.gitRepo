package resources;

import pojo.FixClass;

public class TestDataBuild {
	
	public FixClass SetFixTags(String t8,String t9,String t10,String t11,String t12,String t13,String t14,String t15,String t16,String t17,String t18) {
		
		FixClass fc = new FixClass();
		
		fc.setT8(t8);
		fc.setT9(t9);
		fc.setT10(t10);
		fc.setT11(t11);
		fc.setT12(t12);
		fc.setT13(t13);
		fc.setT14(t14);
		fc.setT15(t15);
		fc.setT16(t16);
		fc.setT17(t17);
		fc.setT18(t18);
		return fc;
	}

public String getJsonData(String key) {
	
	return key;
}
}
