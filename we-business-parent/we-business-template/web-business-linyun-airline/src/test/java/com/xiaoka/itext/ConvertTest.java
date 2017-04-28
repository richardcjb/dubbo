/**
 * ConvertTest.java
 * com.xiaoka.itext
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.xiaoka.itext;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   朱晓川
 * @Date	 2017年3月30日 	 
 */
public class ConvertTest {
	public static void fillTemplate() {//利用模板生成pdf
		//模板路径
		String templatePath = "E:/德国中英签证表15.pdf";
		//生成的新文件路径
		String newPDFPath = "E:/样板1.pdf";
		PdfReader reader;
		FileOutputStream out;
		ByteArrayOutputStream bos;
		PdfStamper stamper;
		try {
			out = new FileOutputStream(newPDFPath);//输出流
			reader = new PdfReader(templatePath);//读取pdf模板
			bos = new ByteArrayOutputStream();
			stamper = new PdfStamper(reader, bos);
			AcroFields s = stamper.getAcroFields();

			/*String[] str = { "123456789", "小鲁", "男", "1994-00-00", "130222111133338888", "河北省唐山市" };
			int i = 0;
			java.util.Iterator<String> it = form.getFields().keySet().iterator();
			while (it.hasNext()) {
				String name = it.next().toString();
				System.out.println(name);
				form.setField(name, str[i++]);
			}*/

			s.setField("xing", "柳");
			s.setField("birthxing", "柳");
			s.setField("name", "柳晗儿");
			s.setField("birthname", "晗儿");
			s.setField("birthtime", "1997-12-25");
			s.setField("birtharea", "台湾");
			s.setField("birthcountry", "中国");
			s.setField("nowcountry", "中国");
			s.setField("sexwomen", "On");
			/*s.setField("toggle_1", "On");//x
			*/
			s.setField("marriage1", "On");
			s.setField("vormundnameandaddress", "柳大郞   台湾省高雄市");
			s.setField("identitycard", "121211 1997 1225 2222");
			s.setField("travel1", "On");
			s.setField("travelnum", "T00342399");
			s.setField("issuetime", "2017-3-30");
			s.setField("effectivetime", "2017-5-30");
			s.setField("issueoffice", "北京市直立人");
			s.setField("applicantaddressandemail", "北京市海淀区学知园   123455689@qq.com");
			s.setField("phonenumber", "010-74747412");
			s.setField("permitnumber", "G12345678");
			s.setField("effectivetimeofcountry", "2017-5-30");
			s.setField("applicanttime", "2017-3-30");
			s.setField("a1", "On");
			s.setField("b1", "On");
			s.setField("v1", "On");
			s.setField("people1", "On");
			s.setField("country1", "On");
			s.setField("daynumber", "60");

			Document document = new Document();
			document.open();
			Image gif = Image.getInstance("e:/13.jpg");
			gif.setDpi(100, 100);
			gif.setBorderWidth(200);
			gif.scaleAbsolute(80, 100);
			gif.setAbsolutePosition(493, 735);
			PdfContentByte over = stamper.getOverContent(1);
			over.addImage(gif);

			stamper.setFormFlattening(true);//如果为false那么生成的PDF文件还能编辑，一定要设为true
			stamper.close();

			Document doc = new Document();
			PdfCopy copy = new PdfCopy(doc, out);
			doc.open();
			PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
			copy.addPage(importPage);
			doc.close();

		} catch (IOException e) {
			System.out.println(1);
		} catch (DocumentException e) {
			System.out.println(2);
		}

	}

	public static void main(String[] args) {
		ConvertTest.fillTemplate();
	}
}
