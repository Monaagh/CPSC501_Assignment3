
public class RefObjectB {
	
	private double d;
	private RefObjectA objA; 
	
	public RefObjectB() {
		d = 10.5;
	}
	
	public RefObjectB(double param1) {
		d = param1;
	}
	
	public void setObjA(RefObjectA a) {
		objA = a; 
		//refObj.setCircularRefObject();
	}
	
}
