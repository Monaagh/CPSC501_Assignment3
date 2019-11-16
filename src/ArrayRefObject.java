
public class ArrayRefObject {
	
	private SimpleObject[] arrObject;
	
	public ArrayRefObject() {
		arrObject = new SimpleObject[2];
		arrObject[0] = new SimpleObject();
		arrObject[1] = new SimpleObject(4, 67.5, false);
	}
	
	public ArrayRefObject(SimpleObject[] object) {
		arrObject = object;
	}

}
