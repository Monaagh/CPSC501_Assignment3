
public class ArrayRefObject {
	
	private SimpleObject[] arrObject = new SimpleObject[2];
	
	public ArrayRefObject() {
		arrObject[0] = new SimpleObject();
		arrObject[1] = new SimpleObject(4, 67.5, false);
	}
	
	public ArrayRefObject(SimpleObject[] object) {
		arrObject = object;
	}

}
