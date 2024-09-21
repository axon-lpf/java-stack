package com.axon.java.stack;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelToMccJsonConverter {

    static class Category {
        String code;
        String name;
        String parentCode;
        List<Category> childrenData;

        public Category(String code, String name, String parentCode) {
            this.code = code;
            this.name = name;
            this.parentCode = parentCode;
            this.childrenData = new ArrayList<>();
        }
    }

    public static void main(String[] args) {
        String excelFilePath = "/Users/lingxiao/Downloads/i8tbo54chsv6fj3y9wmm9ufa2537tq6i.xlsx";

        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            Map<String, Category> categoryMap = new HashMap<>();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
/*                String level1Code = row.getCell(1).getStringCellValue(); // MCC大类(1级)
                String level2Code = row.getCell(2).getStringCellValue(); // MCC小类(2级)
                String level3Code = row.getCell(4).getStringCellValue(); // MCC（3级）
                String level1Name = row.getCell(0).getStringCellValue(); // MCC大类名称
                String level2Name = row.getCell(3).getStringCellValue(); // MCC小类名称
                String level3Name = row.getCell(5).getStringCellValue(); // MCC名称*/

                String level1Code = getCellValueAsString(row.getCell(1));
                String level2Code = getCellValueAsString(row.getCell(3));
                String level3Code = getCellValueAsString(row.getCell(5));
                String level1Name = getCellValueAsString(row.getCell(0));
                String level2Name = getCellValueAsString(row.getCell(2));
                String level3Name = getCellValueAsString(row.getCell(4));


                // Process level 1 category
                if (!categoryMap.containsKey(level1Code)) {
                    categoryMap.put(level1Code, new Category(level1Code, level1Name, "0"));
                }
                Category level1Category = categoryMap.get(level1Code);

                // Process level 2 category
                Category level2Category = findOrCreateCategory(level1Category, level2Code, level2Name, level1Code);

                // Process level 3 category
                findOrCreateCategory(level2Category, level3Code, level3Name, level2Code);
            }

            // Convert to JSON
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonOutput = gson.toJson(new ArrayList<>(categoryMap.values()));

            // Print JSON output
            System.out.println(jsonOutput);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                // 数值型单元格可能是日期或数字，这里做了简单处理
                // 可以根据实际情况决定是否需要转换为特定格式
                return String.valueOf((int) cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                // 如果单元格是公式，获取其计算结果
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    private static Category findOrCreateCategory(Category parent, String code, String name, String parentCode) {
        return parent.childrenData.stream()
                .filter(child -> child.code.equals(code))
                .findFirst()
                .orElseGet(() -> {
                    Category newCategory = new Category(code, name, parentCode);
                    parent.childrenData.add(newCategory);
                    return newCategory;
                });
    }
}