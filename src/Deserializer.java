import java.lang.reflect.*;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;
import org.jdom2.*;

public class Deserializer {
	
	Attribute attribute; 
	int originalObjectId = 0;
	IdentityHashMap<Integer, Object> map = new IdentityHashMap<Integer, Object>();
	
	public Object deserialize(Document document) {
		
		Element root = document.getRootElement();
		List<Element> objectElementList = root.getChildren();
		Object object = null; 
		deserializeObject(objectElementList);
		deserializeField(objectElementList);
		return map.get(originalObjectId);	
	}

	public void deserializeObject(List<Element> objectElementList) {
		Object object = null;
		Class classObject = null;
		for (Element objectElement: objectElementList) {
			attribute = objectElement.getAttribute("class");
			String className = attribute.getValue();
			
			try {
				classObject = Class.forName(className);
			} catch (ClassNotFoundException e) {
				System.out.println(e);
			}
			
			try {
				if (!classObject.isArray()) {
					Constructor constructor = classObject.getConstructor();
					constructor.setAccessible(true);
					object = constructor.newInstance();
					
				} else {
					attribute = objectElement.getAttribute("length"); 
					int length = Integer.parseInt(objectElement.getAttributeValue(attribute.getName()));
					object = Array.newInstance(classObject.getComponentType(), length);
				}
			} catch (Exception e) {
				System.out.println(e);
			}
			
			attribute = objectElement.getAttribute("id"); 
			int id = Integer.parseInt(objectElement.getAttributeValue(attribute.getName()));
			map.put(id, object);
		}
	}
	
	public void deserializeField (List<Element> objectElementList) {
		//Set<Integer> keySet = map.keySet();
		
		Class declaringClassObject = null;
		Field field = null;
		Object object = null;
		Class classObject = null;
		
		for (Element objectElement: objectElementList) {
			
			attribute = objectElement.getAttribute("id"); 
			int id = Integer.parseInt(objectElement.getAttributeValue(attribute.getName()));
			object = map.get(id);
			classObject = object.getClass();
		
			if (!classObject.isArray()) {
				List<Element> objectFieldList = objectElement.getChildren();
				
				for (Element fieldElement: objectFieldList) {
					attribute = fieldElement.getAttribute("declaringclass"); 
					String fieldDeclaringClass = fieldElement.getAttributeValue(attribute.getName());
					try {
						declaringClassObject = Class.forName(fieldDeclaringClass);
						//System.out.println("declaringClassObject" + declaringClassObject);
					} catch (ClassNotFoundException e) {
						System.out.println(e);
					}
					
					attribute = fieldElement.getAttribute("name"); 
					String fieldName = fieldElement.getAttributeValue(attribute.getName());
					
					try {
						field = declaringClassObject.getDeclaredField(fieldName);
					} catch (Exception e) {
						System.out.println(e);
					}
					
					field.setAccessible(true);
					
					if (Modifier.isFinal(field.getModifiers())) {
						try {
							Field modifiersField = Field.class.getDeclaredField("modifiers");
							modifiersField.setAccessible(true);
							modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
						} catch (Exception e) {
							System.out.println(e);
						}
					} 
					
					if (field.getType().isPrimitive()) {
						Element fieldValueElement = fieldElement.getChildren().get(0);
						String fieldValue = fieldValueElement.getText();
						
						setField(field, object, fieldValue);
						
					} else {
						Element reference = fieldElement.getChildren().get(0);
						int referenceId = Integer.valueOf(reference.getText());
						//TO DO: check in the table to find corresponding instance in the table 
						Object referenceObject = map.get(referenceId);
						try {
							if (referenceObject != null) {
								field.set(object, referenceObject);
							}
						} catch (Exception e) {
							System.out.println(e);
						}
					}	
				}
			} else {
				List<Element> objectArrayComponents = objectElement.getChildren();
				//Class arrayType = classObject.getComponentType();
				
				if (classObject.getComponentType().isPrimitive()) {
					for (int i=0; i<objectArrayComponents.size(); i++) {
						Element component = objectArrayComponents.get(i);
						String componentValue = component.getText(); 
						setArrayComponent(object, classObject, i, componentValue);
					}
				} else {
					for (int i=0; i<objectArrayComponents.size(); i++) {
						
						Element component = objectArrayComponents.get(i);
						int componentReferenceID = Integer.parseInt(component.getText()); 
						//int componentReferenceID = Integer.parseInt(component.getAttributeValue(attribute.getName()));
						
						Object referenceObject = map.get(componentReferenceID);
						if (referenceObject != null) {
							Array.set(object, i, referenceObject);
						}
						
					}
				}
			}
		}	
	}

	public void setArrayComponent(Object object, Class classObject, int i, String componentValue) {
		try {
			if (classObject.getComponentType().equals(byte.class)) {
				Array.setByte(object, i, Byte.valueOf(componentValue));
			} else if (classObject.getComponentType().equals(char.class)){
				Array.setChar(object, i, new Character(componentValue.charAt(0)));
			} else if (classObject.getComponentType().equals(double.class)){
				Array.setDouble(object, i, Double.valueOf(componentValue));
			} else if (classObject.getComponentType().equals(float.class)){
				Array.setFloat(object, i, Float.valueOf(componentValue));
			} else if (classObject.getComponentType().equals(int.class)){
				Array.setInt(object, i, Integer.valueOf(componentValue));
			} else if (classObject.getComponentType().equals(long.class)){
				Array.setLong(object, i, Long.valueOf(componentValue));
			} else if (classObject.getComponentType().equals(short.class)){
				Array.setShort(object, i, Short.valueOf(componentValue));
			} else if (classObject.getComponentType().equals(boolean.class)){
				Array.setBoolean(object, i, Boolean.valueOf(componentValue));
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void setField(Field field, Object object, String fieldValue) {
		try {
			if (field.getType().equals(byte.class)) {
				field.setByte(object, Byte.valueOf(fieldValue));
			} else if (field.getType().equals(char.class)){
				field.setChar(object, new Character(fieldValue.charAt(0)));
			} else if (field.getType().equals(double.class)){
				field.setDouble(object, Double.valueOf(fieldValue));
			} else if (field.getType().equals(float.class)){
				field.setFloat(object, Float.valueOf(fieldValue));
			} else if (field.getType().equals(int.class)){
				field.setInt(object, Integer.valueOf(fieldValue));
			} else if (field.getType().equals(long.class)){
				field.setLong(object, Long.valueOf(fieldValue));
			} else if (field.getType().equals(short.class)){
				field.setShort(object, Short.valueOf(fieldValue));
			} else if (field.getType().equals(boolean.class)){
				field.setBoolean(object, Boolean.valueOf(fieldValue));
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
