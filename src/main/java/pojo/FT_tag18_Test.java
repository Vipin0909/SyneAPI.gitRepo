package pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FT_tag18_Test {

	//private int Vipin_Fix_tag18 = Integer.parseInt("18");
	private String f8;
	private String f11;
	private String name;
	
	
	public FT_tag18_Test() {}
	
	public String getF8() {
		return f8;
	}
	public void setF8(String f8) {
		this.f8 = f8;
	}
	public String getF11() {
		return f11;
	}
	public void setF11(String f11) {
		this.f11 = f11;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "FT_tag18_Test [f8=" + f8 + ", f11=" + f11 + ", name=" + name + "]";
	}
	
	
	
	
}
