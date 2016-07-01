package com.shangpin.uaas.util;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * author : Administrator date : 2014-8-12.
 */
public class CommonExcelImport<T> {

	private List<String> properties = null;

	public CommonExcelImport(InputStream input, List<String> properties) {
		// super.read(input);
		this.properties = properties;
	}

	@SuppressWarnings("unused")
	private boolean isExistProp(String property) {
		for (String p : this.properties) {
			if (p == property)
				return true;
		}
		return false;
	}

	public List<T> getModelList(Class<T> modelClass) {
		return null;
		/*
		 * Sheet sheet = workbook.getSheetAt(workbook.getActiveSheetIndex()) Row
		 * row = sheet.getRow(sheet.getFirstRowNum()) for (int cn =
		 * row.firstCellNum ; cn < row.lastCellNum ; cn++){ Cell cell =
		 * row.getCell(cn , Row.RETURN_BLANK_AS_NULL) if(cell != null){ def
		 * property = cell.richStringCellValue.string.trim()
		 * if(isExistProp(property)){ sheetMeta.put(
		 * CellReference.convertNumToColString(cell.getColumnIndex()), property)
		 * } } }
		 * 
		 * def columnMap = [ sheet : sheet.getSheetName(), startRow :
		 * sheet.getFirstRowNum() + 1, columnMap : sheetMeta, configMap :[
		 * id:[expectedType: ExpectedPropertyType.StringType],
		 * jobLevel:[expectedType: ExpectedPropertyType.StringType,
		 * defaultValue: "9"], parentId:[expectedType:
		 * ExpectedPropertyType.StringType, defaultValue:"1"] ] ] def rowList =
		 * excelImportService.columns(workbook,columnMap) return
		 * convertModel(modelClass , rowList)
		 */
	}

	@SuppressWarnings("unused")
	private List<T> convertModel(Class<T> modelClass, List<Map> rowList) {
		/*
		 * List<T> modelList = new ArrayList<T>() rowList.each {Map row -> T
		 * model = modelClass.newInstance() for(key in row.keySet()){ model[key]
		 * = row[key] } modelList << model } return modelList
		 */
		return null;
	}
}
