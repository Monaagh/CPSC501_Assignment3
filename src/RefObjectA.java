
public class RefObjectA {
	
	private int i;
	private RefObjectB objB; 
	
	public RefObjectA() {
		i = 2;
	}
	
	public RefObjectA(int param1) {
		i = param1;
	}
	
	public void setObjB(RefObjectB b) {
		objB = b; 
		//refObj.setCircularRefObject();
	}
	
}
