import org.junit.Test;
import static org.junit.Assert.*;
import java.io.FileOutputStream;
import java.io.IOException;
import org.jdom2.Document;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.junit.Before;
import org.junit.After;

public class TestSerializer {
	XMLOutputter outputter = new XMLOutputter();
	@Before
	public void setUp() {
		outputter.setFormat(Format.getPrettyFormat());
	}
	
	@Test
	public void testSimpleObject() throws IOException{
		SimpleObject so = new SimpleObject();
		Serializer sr = new Serializer();
		Document doc = sr.serialize(so);
		outputter.output(doc, System.out);
		
	}
	
	@Test
	public void testSimpleObjectwithParameter() throws IOException{
		SimpleObject obj = new SimpleObject(45, 50.2, false);
		Serializer sr = new Serializer();
		Document doc = sr.serialize(obj);
		outputter.output(doc, System.out);
		
	}
	
	@Test
	public void testRefObject() throws IOException {
		RefObject obj = new RefObject();
		Serializer sr = new Serializer();
		Document doc = sr.serialize(obj);
		outputter.output(doc, System.out);
	}
	
	@Test
	public void testRefObjectWithParameter() throws IOException {
		RefObjectA a = new RefObjectA();
		RefObjectB b = new RefObjectB();
		RefObject obj = new RefObject(a, b);
		Serializer sr = new Serializer();
		Document doc = sr.serialize(obj);
		outputter.output(doc, System.out);
	}
	
	@Test
	public void testArrayObject() throws IOException {
		ArrayObject obj = new ArrayObject();
		Serializer sr = new Serializer();
		Document doc = sr.serialize(obj);
		outputter.output(doc, System.out);
	}

	
	@Test
	public void testCollectionObject() throws IOException {
		CollectionObject obj = new CollectionObject();
		//obj.setCircularRefObject();
		Serializer sr = new Serializer();
		Document doc = sr.serialize(obj);
		outputter.output(doc, System.out);
	}
	

	@Test
	public void testArrayRefObject() throws IOException {
		ArrayRefObject obj = new ArrayRefObject();
		Serializer sr = new Serializer();
		Document doc = sr.serialize(obj);
		outputter.output(doc, System.out);
	}
}
