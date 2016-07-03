import java.io.File;
import java.io.FileOutputStream;
import java.net.URLDecoder;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;


public class Test {

	public static void main(String[] args) throws Exception {
		new Test().parseXml();
	}
	
	/**
	 * 解析Xml
	 * @throws Exception 
	 */
	public void parseXml() throws Exception {
		SAXReader reader = new SAXReader();
		Document doc = reader.read(this.getClass().getResourceAsStream("/users.xml"));
		Element rootElt = doc.getRootElement();
		List<Element> elts = rootElt.elements("user");
		for (Element elt : elts) {
			Element idElt = elt.element("id");
			Element nameElt = elt.element("name");
			Element pwdElt = elt.element("password");
			Element sexElt = elt.element("sex");
			
			System.out.println(idElt.getText() + "#" + nameElt.getText() 
					+ "#" + pwdElt.getText() + "#" + sexElt.getText() + "#");
		}
	}
	
	/**
	 * 序列化对象到xml文件中保存
	 * @throws Exception
	 */
	public void serXml() throws Exception {
		//创建根节点
		Element rootElt = DocumentHelper.createElement("users");
		Document doc = DocumentHelper.createDocument(rootElt);
		
		Element userElt = DocumentHelper.createElement("user");
		Element idElt = DocumentHelper.createElement("id");
		Element nameElt = DocumentHelper.createElement("name");
		Element passwordElt = DocumentHelper.createElement("password");
		Element sexElt = DocumentHelper.createElement("sex");
		
		idElt.addText("001");
		nameElt.addText("还是");
		passwordElt.addText("123123");
		sexElt.addText("n");
		
		userElt.add(idElt);
		userElt.add(nameElt);
		userElt.add(passwordElt);
		userElt.add(sexElt);
		
		rootElt.add(userElt);
		
		// path不以’/'开头时，默认是从此类所在的包下取资源；  
		//	path  以’/'开头时，则是从ClassPath根下获取；
		//并且路径带中文，需要用URLDecoder.decode来根据编码的码表来解码
		String path = this.getClass().getResource("users.xml").getPath();
		path = URLDecoder.decode(path, "UTF-8");
		FileOutputStream fos = new FileOutputStream(new File(path));
		 XMLWriter xw = new XMLWriter(fos, OutputFormat.createPrettyPrint());
		 xw.write(doc);
		 xw.close();
	}
}
