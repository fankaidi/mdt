package co.kensure.io;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * excel写
 * 
 * @author fankd
 *
 */
public class WriteExcelUtils {


	public static void main(String[] args) {

		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("BankName", "BankName");
		dataMap.put("Addr", "Addr");
		dataMap.put("Phone", "Phone");
		List<Map> list = new ArrayList<Map>();
		list.add(dataMap);


	}

	

	/**
	 * 记得关闭wb
	 * @param dataList
	 * @return
	 */
	public static Workbook writeExcel(List<String[]> dataList) {
		Workbook workBook = null;
		try {
			workBook = new XSSFWorkbook();
			// sheet 对应一个工作页
			Sheet sheet = workBook.createSheet();

			/**
			 * 往Excel中写新数据
			 */
			for (int j = 0; j < dataList.size(); j++) {
				Row row = sheet.createRow(j);
				// 得到要插入的每一条记录
				String[] datafield = dataList.get(j);
				int index = 0;
				for (String fieldval : datafield) {
					Cell first = row.createCell(index);
					first.setCellValue(fieldval);
					index++;
				}
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return workBook;
	}

}