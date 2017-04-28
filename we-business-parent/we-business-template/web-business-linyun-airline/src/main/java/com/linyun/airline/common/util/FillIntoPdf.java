/**
 * FillIntoPdf.java
 * com.linyun.airline.common.util
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.common.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
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
import com.linyun.airline.admin.fillinpdf.form.FillPdfAddForm;
import com.uxuexi.core.common.util.Util;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   孙斌
 * @Date	 2017年3月31日 	 
 */
public class FillIntoPdf {
	public static void fillTemplate(FillPdfAddForm form, String phone) {//利用模板生成pdf
		//模板路径
		String path = HtmlToPdf.class.getClassLoader().getResource("").getPath();
		String templatePath = path + "德国中英签证表15.pdf";
		//生成的新文件路径
		String str = System.getProperty("java.io.tmpdir");
		String newPDFPath = str + File.separator + "1.pdf";
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

			s.setField("xing", form.getXing());
			s.setField("birthxing", form.getBirthxing());
			//	s.setField("name", "柳晗儿");
			s.setField("birthname", form.getBirthname());
			s.setField("birthtime", form.getBirthtime());
			s.setField("birtharea", form.getBirtharea());
			s.setField("birthcountry", form.getBirthcountry());
			s.setField("nowcountry", form.getNowcountry());
			s.setField("sexwomen", "On");
			/*s.setField("toggle_1", "On");//x
			*/
			s.setField("marriage1", "On");
			s.setField("vormundnameandaddress", form.getVormundnameandaddress());
			s.setField("identitycard", form.getIdentitycard());
			s.setField("travel1", "On");
			s.setField("travelnum", form.getTravelnum());
			s.setField("issuetime", form.getIssuetime());
			s.setField("effectivetime", form.getEffectivetime());
			s.setField("issueoffice", form.getIssueoffice());
			s.setField("applicantaddressandemail", form.getApplicantaddressandemail());
			s.setField("phonenumber", form.getPhonenumber());
			s.setField("permitnumber", form.getPermitnumber());
			s.setField("effectivetimeofcountry", form.getEffectivetimeofcountry());
			//			s.setField("applicanttime", "2017-3-30");
			s.setField("a1", "On");
			s.setField("b1", "On");
			s.setField("v1", "On");
			s.setField("people1", "On");
			s.setField("country1", "On");
			//			s.setField("daynumber", "60");
			if (!Util.isEmpty(phone)) {
				Document document = new Document();
				document.open();
				Image gif = Image.getInstance(phone);
				gif.setDpi(100, 100);
				gif.setBorderWidth(200);
				gif.scaleAbsolute(80, 100);
				gif.setAbsolutePosition(493, 735);
				PdfContentByte over = stamper.getOverContent(1);
				over.addImage(gif);
			}

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

}
