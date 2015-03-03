package  com.edentech.excelmapper.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class ExcelParser {

	private static final Log LOG = LogFactory.getLog(ExcelParser.class);
	private long bytesRead = 0;

	public ArrayList<ArrayList<String>>  parseExcelData(InputStream is) {
        ArrayList<ArrayList<String>> results = new ArrayList<ArrayList<String>>();
        try {
			XSSFWorkbook workbook = new XSSFWorkbook(is);
			XSSFSheet sheet = workbook.getSheetAt(0);

			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
                ArrayList<String> rowItems = new ArrayList<String>();
				Row row = rowIterator.next();

				Iterator<Cell> cellIterator = row.cellIterator();

				while (cellIterator.hasNext()) {

					Cell cell = cellIterator.next();

					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_BOOLEAN:
						bytesRead++;
                        rowItems.add(String.valueOf(cell.getBooleanCellValue()));
						break;

					case Cell.CELL_TYPE_NUMERIC:
						bytesRead++;
                        rowItems.add(String.valueOf(cell.getNumericCellValue()));
						break;

					case Cell.CELL_TYPE_STRING:
						bytesRead++;
                        rowItems.add(cell.getStringCellValue() );
						break;

					}
				}
                results.add(rowItems);
			}
			is.close();
		} catch (IOException e) {
			LOG.error("IO Exception : File not found " + e);
		}
		return results;

	}

	public long getBytesRead() {
		return bytesRead;
	}

}
