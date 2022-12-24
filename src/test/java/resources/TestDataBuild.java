package resources;

import pojo.FI_Order_Value;
import pojo.FT_tag18_Test;
import pojo.FixClass;

public class TestDataBuild {

	FixClass Fixdata = new FixClass();
	FI_Order_Value ordervalue = new FI_Order_Value();
	
	
	public FixClass SetFixTags(String t8,String t9,String t10,String t11,String t12,String t13,String t14,String t15,String t16,String t17,String t18, int fix_tag18) {
		
		Fixdata.setT8(t8);
		Fixdata.setT9(t9);
		Fixdata.setT10(t10);
		Fixdata.setT11(t11);
		Fixdata.setT12(t12);
		Fixdata.setT13(t13);
		Fixdata.setT14(t14);
		Fixdata.setT15(t15);
		Fixdata.setT16(t16);
		Fixdata.setT17(t17);
		Fixdata.setT18(t18);
		Fixdata.setFix_tag18(fix_tag18);
		
		return Fixdata;
	
	}
	
public FI_Order_Value SetFIOrder(String Last_Capacity,String Order_Capacity) {
	ordervalue.setLast_Capacity(Last_Capacity);
	ordervalue.setOrder_Capacity(Order_Capacity);
	return ordervalue;
}
	


}
