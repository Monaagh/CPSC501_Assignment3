
public class RefObject {
	private RefObjectA objA;
	private RefObjectB objB;
	
	public RefObject() {
		objA = new RefObjectA();
		objB = new RefObjectB();
		objB.setObjA(objA);
		objA.setObjB(objB);

	}
	
	public RefObject(RefObjectA a, RefObjectB b) {
		objA = a;
		objB = b;
		objA.setObjB(objB);
		objB.setObjA(objA);
	}

}
