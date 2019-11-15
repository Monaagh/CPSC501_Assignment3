import java.io.*; 

public class ObjectCreator {
	private static String input;
	BufferedReader inFromUser;
	public ObjectCreator() {
		inFromUser = 
		        new BufferedReader(new InputStreamReader(System.in)); 
		/*input = new Scanner(System.in);
		switch (choice) {
			case 1:
				simpleObject();
			case 2:
			case 3:
			case 4:
			case 5:
			default:	
		}*/
	}
	
	public SimpleObject simpleObject() throws IOException {
		SimpleObject object = null;
		System.out.println("SimpleObject(int param1, double param2, boolean param3)");
		System.out.println("Do you want to set the values for each field of the simple object? ");
		System.out.println("1. Yes ");
		System.out.println("2. No ");
		
		input = inFromUser.readLine(); 
		
		while (true) {
			if (input.equals("1")) {
				Integer param1;
				Double param2; 
				Boolean param3;
				System.out.println("Eter an integer for parameter 1:");
				input = inFromUser.readLine(); 
				while (true ){
					try {
						param1 = Integer.parseInt(input);
						//System.out.println(i);
						break;
					} catch (Exception e) {
						System.out.println("Invalid input. Enter an Integer");
						input = inFromUser.readLine(); 
					}
				}
				
				System.out.println("Eter a double for parameter 2:");
				input = inFromUser.readLine(); 
				while (true ){
					try {
						param2 = Double.parseDouble(input);
						//System.out.println(param2);
						break;
					} catch (Exception e) {
						System.out.println("Invalid input. Enter a double");
						input = inFromUser.readLine(); 
					}
				}
				
				System.out.println("Eter a boolean for parameter 3:");
				input = inFromUser.readLine(); 
				
				while (true ){					
					if (input.toLowerCase().equals("true") || input.toLowerCase().equals("false")) {
						param3 = Boolean.parseBoolean(input.toLowerCase());
						break;
					} else {
						System.out.println("Invalid input. Enter a boolean");
						input = inFromUser.readLine(); 
					}
				}
				
				object = new SimpleObject(param1, param2, param3);
				break;
			} else if (input.equals("2")) {
				object = new SimpleObject();
				break;
			} else {
				System.out.println("Invalid input!");
				input = inFromUser.readLine(); 
			}
		}
		System.out.println("A simple object with primitive fields created!");
		return object;
	}
	
	
	public RefObject refObject() throws IOException {
		RefObject refObject = null;
		System.out.println("refObject(RefObjectA param1, RefObjectB param1)");
		System.out.println("Do you want to set the values for primitive fields of the Object A and Object B? ");
		System.out.println("1. Yes ");
		System.out.println("2. No ");
		
		input = inFromUser.readLine(); 
		
		while (true) {
			if (input.equals("1")) {
				Integer param1;
				Double param2; 
				
				
				System.out.println("Eter an integer for Object A:");
				input = inFromUser.readLine(); 
				while (true ){
					try {
						param1 = Integer.parseInt(input);
						//System.out.println(i);
						break;
					} catch (Exception e) {
						System.out.println("Invalid input. Enter an Integer");
						input = inFromUser.readLine(); 
					}
				}
				
				System.out.println("Eter a double for  Object B:");
				input = inFromUser.readLine(); 
				while (true ){
					try {
						param2 = Double.parseDouble(input);
						//System.out.println(param2);
						break;
					} catch (Exception e) {
						System.out.println("Invalid input. Enter a double");
						input = inFromUser.readLine(); 
					}
				}
				
				RefObjectA a = new RefObjectA(param1);
				RefObjectB b = new RefObjectB(param2);
				
				refObject = new RefObject(a,b);
				
			} else if (input.equals("2")) {
			
				refObject = new RefObject();
				break;
			} else {
				System.out.println("Invalid input!");
				input = inFromUser.readLine(); 
			}
		}
		
		System.out.println("An object that contain reference to circular reference object !");
		return refObject;
	}
	
	
	public ArrayObject arrayObject() throws IOException {
		ArrayObject object = null;
		System.out.println("ArrayObject(int[] param1)");
		System.out.println("Do you want to set the values for array? ");
		System.out.println("1. Yes ");
		System.out.println("2. No ");
		
		input = inFromUser.readLine();
		
		while (true) {
			if (input.equals("1")) {
				int length = 0;
				int index = 0;
				int[] array;
				System.out.println("Enter a length for array");
				String input = inFromUser.readLine();
				while (true ){
					try {
						length = Integer.parseInt(input);
						array = new int[length];
						break;
					} catch (Exception e) {
						System.out.println("Invalid input. Enter an Integer");
						input = inFromUser.readLine(); 
					}
				}
			
				while (index < length) {
					System.out.println("Enter next element in array");
					input = inFromUser.readLine();
					while (true ){
						try {
							array[index] = Integer.parseInt(input);
							index++;
							break;
						} catch (Exception e) {
							System.out.println("Invalid input. Enter an Integer");
							input = inFromUser.readLine(); 
						}
					}
				}
				
				object = new ArrayObject(array);
				break;
				
			} else if (input.equals("2")) {
				object = new ArrayObject();
				break;
			} else {
				System.out.println("Invalid input!");
				input = inFromUser.readLine(); 
			}
		}
		
		System.out.println("An Object that contains an array of primitives created!");
		
		return object;
	}
	
	public ArrayRefObject arrayRefObject() throws IOException {
		ArrayRefObject object = null;
		
		
		System.out.println("ArrayRefObject(SimpleObject[] param1)");
		System.out.println("Do you want to set the values for array of SimpleObject refrences? ");
		System.out.println("1. Yes ");
		System.out.println("2. No ");
		
		input = inFromUser.readLine();
		
		while (true) {
			if (input.equals("1")) {
				int length = 0;
				int index = 0;
				SimpleObject[] simpleObject;
				System.out.println("Enter a length for array");
				String input = inFromUser.readLine();
				while (true ){
					try {
						length = Integer.parseInt(input);
						simpleObject = new SimpleObject[length];
						break;
					} catch (Exception e) {
						System.out.println("Invalid input. Enter an Integer");
						input = inFromUser.readLine(); 
					}
				}
			
				while (index < length) {
					simpleObject[index] = simpleObject();
					index++;
				}
				object = new ArrayRefObject(simpleObject);
				break;
				
			} else if (input.equals("2")) {
				object = new ArrayRefObject();
				break;
			} else {
				System.out.println("Invalid input!");
				input = inFromUser.readLine(); 
			}
		}
		
		System.out.println("An Object that contains an array of object refrences created!");
		
		return object;	
	}
	
	public CollectionObject collectionObject() throws Exception {
		CollectionObject object = null;
		object = new CollectionObject();
		System.out.println("An Object that contains an instance of Array List created!");
		return object;
	}
}
