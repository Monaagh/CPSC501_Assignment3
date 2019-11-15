import java.util.ArrayList;

public class CollectionObject {
	private ArrayList arrayList = new ArrayList();
	
	
	public CollectionObject() {
		arrayList.add(new SimpleObject());
		arrayList.add(new ArrayObject());
	}
	
	public CollectionObject(ArrayList param1){
		arrayList = param1;
	}
	
	
}
