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
	
	/*@Test
	public void testSimpleObject(){
		SimpleObject so = new SimpleObject();
		Serializer sr = new Serializer();
		Document doc = sr.serialize(so);
		new Sender().printOutput(doc);
		
	}
	
	@Test
	public void testSimpleObjectwithParameter(){
		SimpleObject obj = new SimpleObject(45, 50.2, false);
		Serializer sr = new Serializer();
		Document doc = sr.serialize(obj);
		new Sender().printOutput(doc);
		
	}
	
	@Test
	public void testRefObject() {
		RefObject obj = new RefObject();
		Serializer sr = new Serializer();
		Document doc = sr.serialize(obj);
		new Sender().printOutput(doc);
	}
	
	@Test
	public void testRefObjectWithParameter() {
		SimpleObject simpleObj = new SimpleObject(45, 50.2, false);
		RefObject obj = new RefObject(simpleObj);
		Serializer sr = new Serializer();
		Document doc = sr.serialize(obj);
		new Sender().printOutput(doc);
	}*/
	
	/*@Test
	public void testArrayObject() throws IOException {
		ArrayObject obj = new ArrayObject();
		Serializer sr = new Serializer();
		Document doc = sr.serialize(obj);
		outputter.output(doc, System.out);
	}*/
	/*@Test
	public void testRefObject() {
		RefObject obj = new RefObject();
		//obj.setCircularRefObject();
		Serializer sr = new Serializer();
		Document doc = sr.serialize(obj);
		//new Sender().printOutput(doc);
	}*/
	
	@Test
	public void testCollectionObject() throws IOException {
		CollectionObject obj = new CollectionObject();
		//obj.setCircularRefObject();
		Serializer sr = new Serializer();
		Document doc = sr.serialize(obj);
		outputter.output(doc, System.out);
	}
	
	
	/*
	@Test
	public void testArrayRefObject() {
		ArrayRefObject obj = new ArrayRefObject();
		Serializer sr = new Serializer();
		Document doc = sr.serialize(obj);
		new Sender().printOutput(doc);
	}*/
}
