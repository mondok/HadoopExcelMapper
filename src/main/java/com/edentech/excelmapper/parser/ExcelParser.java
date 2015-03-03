/*
 * Copyright 2014 Sreejith Pillai
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * 2015 modifications for XLSX support and better List<T> passing by Matt Mondok
*/


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
