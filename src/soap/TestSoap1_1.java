package soap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestSoap1_1 {

	public static void main(String[] args) throws Exception {
		String urlString = "http://www.webxml.com.cn/WebServices/WeatherWebService.asmx";
		String xmlFile = "soap1.1.xml";// 要发送的soap格式文件
		 String soapActionString = "http://WebXml.com.cn/getWeatherbyCityName";//Soap 1.1中使用
		URL url = new URL(urlString);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		File fileToSend = new File(xmlFile);
		byte[] buf = new byte[(int) fileToSend.length()];// 用于存放文件数据的数组
		new FileInputStream(xmlFile).read(buf);
//		httpConn.setRequestProperty("Content-Length",
//				String.valueOf(buf.length));//这句话可以不用写，即使是随便写
		//根据我的测试，过去的请求头中的Content-Length长度也是正确的，也就是说它会自动进行计算
		httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
		 httpConn.setRequestProperty("soapActionString",soapActionString);//Soap
		httpConn.setRequestMethod("POST");
		httpConn.setDoOutput(true);
		httpConn.setDoInput(true);
		OutputStream out = httpConn.getOutputStream();
		out.write(buf);
		out.close();
		InputStreamReader is = new InputStreamReader(httpConn.getInputStream(),
				"utf-8");
		BufferedReader in = new BufferedReader(is);
		String inputLine;
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream("result1.xml")));// 将结果存放的位置
		while ((inputLine = in.readLine()) != null) {
			System.out.println(inputLine);
			bw.write(inputLine);
			bw.newLine();
		}
		bw.close();
		in.close();
		httpConn.disconnect();
	}
}