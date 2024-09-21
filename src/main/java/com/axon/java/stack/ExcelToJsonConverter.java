package com.axon.java.stack;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class ExcelToJsonConverter {

    public static void main(String[] args) {
        String excelFilePath = "/Users/lingxiao/Downloads/hu294lxue0oxuxlh1wdybwk72gs9jdgy.xlsx"; // Excel文件路径

        try {
            // 读取Excel文件数据
            List<Map<String, String>> excelData = readExcelData(excelFilePath);

            // 将数据转换为动态JSON结构
            JSONObject jsonResult = convertToJSON2(excelData);

            // 输出结果
            System.out.println(jsonResult);  // 格式化输出

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 读取Excel文件数据
    private static List<Map<String, String>> readExcelData(String excelFilePath) throws IOException {
        List<Map<String, String>> data = new ArrayList<>();

        // 打开Excel文件
        FileInputStream fileInputStream = new FileInputStream(excelFilePath);
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);  // 假设读取的是第一个Sheet

        // 遍历行（跳过标题行）
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                Map<String, String> rowData = new HashMap<>();
                rowData.put("regionLevel", getCellValue(row.getCell(0)));   // 地区级别
                rowData.put("province", getCellValue(row.getCell(1)));     // 省份
                rowData.put("city", getCellValue(row.getCell(2)));         // 城市
                rowData.put("county", getCellValue(row.getCell(3)));       // 县区
                rowData.put("provinceCode", getCellValue(row.getCell(4))); // 省份地区码
                rowData.put("cityCode", getCellValue(row.getCell(5)));     // 城市地区码
                rowData.put("countyCode", getCellValue(row.getCell(6)));   // 县区地区码

                data.add(rowData);
            }
        }

        workbook.close();
        fileInputStream.close();

        return data;
    }

    // 获取单元格中的值
    private static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue());
            default:
                return "";
        }
    }


    // 将Excel数据转换为基于代码的动态JSON结构
    private static JSONObject convertToJSON2(List<Map<String, String>> excelData) {
        // 顶级结构体：省 -> 市 -> 区
        Map<String, JSONObject> provinceMap = new HashMap<>();
        Map<String, JSONObject> cityMap = new HashMap<>();

        for (Map<String, String> row : excelData) {
            String provinceName = row.get("province");
            String cityName = row.get("city");
            String countyName = row.get("county");
            String provinceCode = row.get("provinceCode");
            String cityCode = row.get("cityCode");
            String countyCode = row.get("countyCode");

            // 处理省份
            if (!provinceCode.isEmpty()) {
                // 如果省份不存在，创建省份节点
                if (!provinceMap.containsKey(provinceCode)) {
                    JSONObject provinceObj = new JSONObject();
                    provinceObj.put("adcode", provinceCode);
                    provinceObj.put("level", "province");
                    provinceObj.put("name", provinceName);
                    provinceObj.put("parentCode", "0");
                    provinceObj.put("children", new JSONArray());
                    provinceMap.put(provinceCode, provinceObj);
                }
            }

            // 处理城市
            if (!cityCode.isEmpty() && !provinceCode.isEmpty()) {
                // 如果城市不存在，创建城市节点
                if (!cityMap.containsKey(cityCode)) {
                    JSONObject cityObj = new JSONObject();
                    cityObj.put("adcode", cityCode);
                    cityObj.put("level", "city");
                    cityObj.put("name", cityName);
                    cityObj.put("parentCode", provinceCode);
                    cityObj.put("children", new JSONArray());

                    // 将城市节点添加到对应的省份下
                    provinceMap.get(provinceCode).getJSONArray("children").put(cityObj);
                    cityMap.put(cityCode, cityObj);
                }
            }

            // 处理区县
            if (!countyCode.isEmpty() && !cityCode.isEmpty()) {
                // 创建区县节点
                JSONObject countyObj = new JSONObject();
                countyObj.put("adcode", countyCode);
                countyObj.put("level", "county");
                countyObj.put("name", countyName);
                countyObj.put("parentCode", cityCode);

                // 将区县节点添加到对应的城市下
                if (cityMap.containsKey(cityCode)) {
                    cityMap.get(cityCode).getJSONArray("children").put(countyObj);
                }
            }
        }

        // 创建最终的JSON结构，包含所有省份
        JSONArray provinceArray = new JSONArray();
        for (JSONObject provinceObj : provinceMap.values()) {
            provinceArray.put(provinceObj);
        }

        // 将省份数组作为顶层JSON对象的children
        JSONObject root = new JSONObject();
        root.put("children", provinceArray);

        return root;
    }


    // 将Excel数据转换为动态的JSON结构
    private static JSONObject convertToJSON(List<Map<String, String>> excelData) {
        // 顶级结构体：省 -> 市 -> 区
        Map<String, JSONObject> provinceMap = new HashMap<>();
        Map<String, JSONObject> cityMap = new HashMap<>();

        for (Map<String, String> row : excelData) {
            String regionLevel = row.get("regionLevel");
            String provinceName = row.get("province");
            String cityName = row.get("city");
            String countyName = row.get("county");
            String provinceCode = row.get("provinceCode");
            String cityCode = row.get("cityCode");
            String countyCode = row.get("countyCode");

            // 处理省份
            if ("1".equals(regionLevel)) {
                // 如果省份不存在，创建省份节点
                if (!provinceMap.containsKey(provinceCode)) {
                    JSONObject provinceObj = new JSONObject();
                    provinceObj.put("adcode", provinceCode);
                    provinceObj.put("level", "province");
                    provinceObj.put("name", provinceName);
                    provinceObj.put("parentCode", "0");
                    provinceObj.put("children", new JSONArray());
                    provinceMap.put(provinceCode, provinceObj);
                }
            }

            // 处理城市
            if ("2".equals(regionLevel) && !cityCode.isEmpty()) {
                // 如果城市不存在，创建城市节点
                if (!cityMap.containsKey(cityCode)) {
                    JSONObject cityObj = new JSONObject();
                    cityObj.put("adcode", cityCode);
                    cityObj.put("level", "city");
                    cityObj.put("name", cityName);
                    cityObj.put("parentCode", provinceCode);
                    cityObj.put("children", new JSONArray());

                    // 将城市节点添加到对应的省份下
                    provinceMap.get(provinceCode).getJSONArray("children").put(cityObj);
                    cityMap.put(cityCode, cityObj);
                }
            }

            // 处理区县
            if ("3".equals(regionLevel) && !countyCode.isEmpty()) {
                // 创建区县节点
                JSONObject countyObj = new JSONObject();
                countyObj.put("adcode", countyCode);
                countyObj.put("level", "county");
                countyObj.put("name", countyName);
                countyObj.put("parentCode", cityCode);

                // 将区县节点添加到对应的城市下
                if (cityMap.containsKey(cityCode)) {
                    cityMap.get(cityCode).getJSONArray("children").put(countyObj);
                }
            }
        }

        // 创建最终的JSON结构，包含所有省份
        JSONArray provinceArray = new JSONArray();
        for (JSONObject provinceObj : provinceMap.values()) {
            provinceArray.put(provinceObj);
        }

        // 将省份数组作为顶层JSON对象的children
        JSONObject root = new JSONObject();
        root.put("children", provinceArray);

        return root;
    }
}