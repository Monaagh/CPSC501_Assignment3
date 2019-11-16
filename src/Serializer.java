import java.lang.reflect.*;
import java.util.HashMap;
import java.util.IdentityHashMap;
import org.jdom2.*;

public class Serializer {
	//Class classObject;
	int id;
	IdentityHashMap<Object, Integer> map = new IdentityHashMap<Object, Integer>();
	Document doc;
	Element root; 

	public Serializer() {
		doc = new Document();
		root = new Element("serialized");
		doc.setRootElement(root);	
	}
	public Document serialize(Object obj) {
		//classObject = obj.getClass();
		serializeObject(obj, obj.getClass());
		id = map.size();
		//Field[] fields = classObject.getDeclaredFields();
		return doc;
	}
	
	
	public void serializeObject(Object obj, Class classObject) {
		System.out.println("Object name: " + obj.getClass().getName());
		//System.out.println("Class name: " + classObject.getName());
		
		if (!map.containsKey(obj)) {
			//System.out.println("The object is not visited");
			//System.out.println(map.containsKey(id));
			//System.out.println(id);
			//serialize object
			Element objectElement = new Element("object");
		
			map.put(obj, id);

			//System.out.println("Object name: " + obj.getClass().getName());
			//System.out.println("Object id: " + id);
			//System.out.println("map: " + map.keySet());
			objectElement.setAttribute("class", classObject.getName());
			objectElement.setAttribute("id", String.valueOf(id));
		
			if (classObject.isArray()) {
				objectElement.setAttribute("length", String.valueOf(Array.getLength(obj)));
			
				if (classObject.getComponentType().isPrimitive()) {
					System.out.println("array component type: true");
					for (int i= 0; i < Array.getLength(obj); i++) {
						Object elementPrimitive = Array.get(obj,  i);	
						Element arrayElementValue = new Element("value");	
						arrayElementValue.setText(String.valueOf(elementPrimitive));
						objectElement.addContent(arrayElementValue);	
					}
				} else {
					System.out.println("array component type: " + classObject.getComponentType());
					for (int i= 0; i < Array.getLength(obj); i++) {
						Object elementReference = Array.get(obj,  i);	
						
						
						/*try {
							id = elementReference.hashCode();
						} catch( Exception e) {
							System.out.println("Object that throws exception: " + 
								classObject);
						}*/
						//map.put(id, elementReference);
						if (elementReference != null) {
							Element arrayElementReference = new Element("refrence");
							//id = map.size();
							if (!map.containsKey(elementReference)) {
								id = map.size();
							} else {
								id = map.get(elementReference);
							}
							arrayElementReference.setText(String.valueOf(id));
					
							objectElement.addContent(arrayElementReference);
							serializeObject(elementReference, elementReference.getClass());
						}
				}
			}	
		}
		
		if (classObject.getDeclaredFields().length > 0) {
			//System.out.println("object has field: true");
			serializeFields(obj, objectElement, classObject);
		}
		root.addContent(objectElement);
		}
		
		//serialize field
	}
			
	public void serializeFields(Object obj, Element objectElement, Class classObject){
		Field[] fields = classObject.getDeclaredFields();
		Element fieldElement;
		for (Field field: fields) {
			fieldElement = new Element("field");
			fieldElement.setAttribute("name", field.getName());
			fieldElement.setAttribute("declaringclass", field.getDeclaringClass().getName());
			
			field.setAccessible(true);
					
			Object fieldValueObject = null; 
			try {
				System.out.println("field name: " + field.getName());
				fieldValueObject = field.get(obj);
				System.out.println("field object: " + fieldValueObject);
			} catch (IllegalAccessException e) {
				System.out.println(e);
			}
			
			if (field.getType().isPrimitive()) {
				//System.out.println("field type: primitive");
				Element fieldValueElement = new Element("value");
				fieldValueElement.setText(String.valueOf(fieldValueObject));
				fieldElement.addContent(fieldValueElement);
				objectElement.addContent(fieldElement);
			} else {
				Element reference = new Element("reference");
				if (!map.containsKey(fieldValueObject)) {
					id = map.size();
				} else {
					id = map.get(fieldValueObject);
				}
				//map.put(id, fieldValueObject);
				reference.setText(String.valueOf(id));
				fieldElement.addContent(reference);
				objectElement.addContent(fieldElement);
				serializeObject(fieldValueObject, fieldValueObject.getClass());
				/*if (!field.getType().isArray()) {
					serializeObject(fieldValueObject);
				} else {
					serializeObject(fieldValueObject);
				}*/
				
			}
			
			
		}
	}
	
}
