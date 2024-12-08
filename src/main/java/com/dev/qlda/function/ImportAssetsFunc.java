package com.dev.qlda.function;

import com.dev.qlda.constant.AssetType;
import com.dev.qlda.entity.AssetQuantity;
import com.dev.qlda.entity.Assets;
import com.dev.qlda.repo.AssetQuantityRepo;
import com.dev.qlda.repo.AssetRepo;
import com.dev.qlda.request.ImportAssetRequest;
import com.dev.qlda.response.ImportAssetResponse;
import com.dev.qlda.response.WrapResponse;
import com.dev.qlda.utils.CodeGenerators;
import com.dev.qlda.utils.DateTimesUtils;
import com.dev.qlda.utils.ExcelUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
@Component
public class ImportAssetsFunc {

    private final AssetRepo assetRepo;
    private final CodeGenerators codeGenerators;
    private final AssetQuantityRepo assetQuantityRepo;

    public WrapResponse<?> importAssets(byte[] bytes) throws IOException {
        int sheetNumber = 0;
        int totalColumn = 7;
        ByteArrayInputStream ips = new ByteArrayInputStream(bytes);
        List<ImportAssetRequest> rows = new LinkedList<>();
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(ips);
        } catch (Exception e) {
            throw new ServiceException("Error on import");
        }
        if (workbook.getNumberOfSheets() == 0) {
            throw new ServiceException("Error on import");
        }
        XSSFSheet worksheet1 = workbook.getSheetAt(sheetNumber);
        int rowCount = worksheet1.getLastRowNum() + 1;
        rowCount = Math.min(rowCount, worksheet1.getPhysicalNumberOfRows());
        int colCount = 0;
        if (rowCount > 0) {
            colCount = worksheet1.getRow(0).getPhysicalNumberOfCells();
        }
        if (rowCount == 0 || colCount < totalColumn) {
            throw new ServiceException("Error on import");
        }

        List<ImportAssetResponse> returnValue = new ArrayList<>();
        boolean isSuccess = true;
        try {
            for (int i = 1; i < rowCount; i++) {
                XSSFRow row = worksheet1.getRow(i);
                if (row == null || ExcelUtils.isRowEmpty(row, totalColumn)) {
                    continue;
                }
                String assetName = ExcelUtils.getValue(row.getCell(0));
                String assetType = ExcelUtils.getValue(row.getCell(1));
                String manufacturer = ExcelUtils.getValue(row.getCell(2));
                String valueStr = ExcelUtils.getValue(row.getCell(3));
                String quantityStr = ExcelUtils.getValue(row.getCell(4));
                String boughtDateStr = ExcelUtils.getValue(row.getCell(5));
                String description = ExcelUtils.getValue(row.getCell(6));

                Double value = convertStringToDouble(valueStr);
                Long quantity = convertStringToInteger(quantityStr);
                Date boughtDate = DateTimesUtils.convertStringToDate(boughtDateStr, "MM/dd/yyyy");

                ImportAssetRequest request = ImportAssetRequest.builder()
                        .index(i)
                        .name(assetName)
                        .type(assetType)
                        .manufacturer(manufacturer)
                        .value(value)
                        .boughtDate(boughtDate)
                        .description(description)
                        .quantity(quantity)
                        .build();
                rows.add(request);
                ImportAssetResponse response = updateAssetInfo(request);
                if (!response.isSuccess()){
                    isSuccess = false;
                }
                returnValue.add(response);
            }
        } catch (Exception e) {
            throw new ServiceException("Error on import");
        }

        List<Assets> newAssets = new ArrayList<>();
        long number = assetRepo.count();
        for (ImportAssetRequest request : rows) {
            if (request == null){
                continue;
            }
            newAssets.add(Assets.builder()
                    .id(UUID.randomUUID().toString())
                            .name(request.getName())
                            .quantity(request.getQuantity())
                            .type(request.getType())
                            .boughtDate(request.getBoughtDate())
                            .manufacturer(request.getManufacturer())
                            .value(request.getValue())
                            .description(request.getDescription())
                            .code(codeGenerators.generateAssetCode(request.getType(), number))
                    .build());
            number++;
        }

        if (isSuccess && CollectionUtils.isNotEmpty(newAssets)){
            List<Assets> savedRecord = assetRepo.saveAll(newAssets);
            createAssetQuantity(savedRecord);
            return WrapResponse.builder()
                    .isSuccess(true)
                    .status(HttpStatus.OK)
                    .message("import thành công")
                    .build();
        }

        ByteArrayOutputStream excelFile;
        try {
            excelFile = exportImportAssetResponseToExcel(returnValue);
        } catch (IOException e) {
            throw new ServiceException("Error generating Excel file");
        }

        return WrapResponse.builder()
                .isSuccess(false)
                .status(HttpStatus.BAD_REQUEST)
                .message("Có  " + returnValue.stream().filter(x -> !x.isSuccess()).count() + "/" + returnValue.size() + " dòng lỗi, vui lòng tải file xuống để xem chi tiết. Lưu ý: file chỉ tồn tại trong vòng 5 phút")
                .data(excelFile.toByteArray())
                .build();
    }

    private ImportAssetResponse updateAssetInfo(ImportAssetRequest request) {
        try {
            updateImportAssetRequest(request);
            return ImportAssetResponse.builder()
                    .index(request.getIndex())
                    .success(true)
                    .build();
        } catch (Exception e) {
            return ImportAssetResponse.builder()
                    .index(request.getIndex())
                    .success(false)
                    .errorDesc(e.getMessage())
                    .build();
        }
    }

    public static ByteArrayOutputStream exportImportAssetResponseToExcel(List<ImportAssetResponse> responses) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Import Results");

        XSSFRow headerRow = sheet.createRow(0);
        String[] headers = {"Name", "Type", "Manufacturer", "Value", "Quantity", "Bought Date", "Description", "Message"};
        for (int i = 0; i < headers.length; i++) {
            headerRow.createCell(i).setCellValue(headers[i]);
        }

        for (int i = 0; i < responses.size(); i++) {
            ImportAssetResponse response = responses.get(i);
            XSSFRow row = sheet.createRow(i + 1);

            row.createCell(0).setCellValue(response.getName());
            row.createCell(1).setCellValue(response.getType());
            row.createCell(2).setCellValue(response.getManufacturer());
            row.createCell(3).setCellValue(response.getValue() != null ? response.getValue().toString() : "");
            row.createCell(4).setCellValue(response.getQuantity() != null ? response.getQuantity().toString() : "");
            row.createCell(5).setCellValue(response.getBoughtDate() != null ? response.getBoughtDate().toString() : "");
            row.createCell(6).setCellValue(response.getDescription());
            row.createCell(7).setCellValue(response.getErrorDesc());
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        workbook.close();
        return baos;
    }

    public void updateImportAssetRequest(ImportAssetRequest request) {
        if (StringUtils.isBlank(request.getName())){
            throw new ServiceException("Ten tai san dang bi trong");
        }
        if (StringUtils.isBlank(request.getType())){
            throw new ServiceException("Loai tai san dang bi trong");
        }
        if (AssetType.isValueInConstants(request.getType())){
            throw new ServiceException("Loai san pham khong hop le");
        }
        if (StringUtils.isBlank(request.getManufacturer())){
            throw new ServiceException("Nha san xuat dang bi de trong");
        }
        if (request.getQuantity() == null){
            throw new ServiceException("so luong đang bị trống");
        }
        if (request.getQuantity() == 0){
            throw new ServiceException("so luong phai lon hon 0");
        }
        if (request.getBoughtDate() == null){
            throw new ServiceException("Ngay mua đang bị trống");
        }
    }

    private Double convertStringToDouble(String input) {
        try {
            if (StringUtils.isNotBlank(input)) {
                return null;
            }
            return Double.parseDouble(input.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Chuỗi không thể chuyển đổi sang Double: " + input, e);
        }
    }


    private Long convertStringToInteger(String input) {
        try {
            if (StringUtils.isNotBlank(input)) {
                return null;
            }
            return Long.parseLong(input.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Chuỗi không thể chuyển đổi sang Long: " + input, e);
        }
    }

    public void createAssetQuantity(List<Assets> assets){
        List<AssetQuantity> saveList = new ArrayList<>();
        for (String department : List.of("NHA K", "NHA B", "NHA HIEU BO", "NHA C", "NHA D", "NHA V", "SAN VAN DONG")){
            for (Assets asset : assets){
                saveList.add(AssetQuantity.builder()
                        .id(UUID.randomUUID().toString())
                        .assetId(asset.getId())
                        .assetCode(asset.getCode())
                        .assetName(asset.getName())
                        .assetType(asset.getType())
                        .manufacturer(asset.getManufacturer())
                        .quantity(0L)
                        .location(department)
                        .build());
            }
        }
        assetQuantityRepo.saveAll(saveList);
    }
}
