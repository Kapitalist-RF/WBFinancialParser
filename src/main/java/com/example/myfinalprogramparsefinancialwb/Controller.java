package com.example.myfinalprogramparsefinancialwb;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import myParseFinancialExcel.Main;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller {

    private Path pathParseExcel;
    private Path pathCostPrice;

    private List<File> fileListExcel = new ArrayList<>();

    private BigDecimal acceptanceFeeBD = new BigDecimal(0);
    private BigDecimal otherDeductionsBD = new BigDecimal(0);
    private BigDecimal storageCostBD = new BigDecimal(0);
    private BigDecimal surchargesBD = new BigDecimal(0);
    private BigDecimal taxBD = new BigDecimal(6);
    private String nameFinalFileExcelStr = "Temp";

    @FXML
    private TextField acceptanceFeeNum;
    @FXML
    private TextField nameFinalFileExcel;

    @FXML
    private TextField otherDeductionsNum;

    @FXML
    private Button parseExcel;

    @FXML
    private TextArea processingText;

    @FXML
    private Button selectFileForParsingExcel;

    @FXML
    private Button selectFileForParsingExcelCostPrice;

    @FXML
    private Label showSelectFileForParsingExcel;

    @FXML
    private TextArea showSelectFileForParsingExcelCostPrice;

    @FXML
    private TextField storageCost;

    @FXML
    private TextField surchargesNum;

    @FXML
    private Text errorTextSurchargesNum;

    @FXML
    private Text errorTextStorageCost;

    @FXML
    private Text errorTextAcceptanceFeeNum;

    @FXML
    private Text errorTextOtherDeductionsNum;

    @FXML
    private Text errorTextTax;

    @FXML
    private TextField taxField;

    @FXML
    private TextArea filesText;

    @FXML
    private Button resetFilesExcelFinance;

    @FXML
    private Button costPriceSample;





    @FXML
    void initialize() {
//        surchargesNum.setOnAction(actionEvent -> {
////            String surchargesNumStr = surchargesNum.getCharacters().toString();
////            System.out.println(surchargesNumStr);
//            surchargesNum.textProperty().addListener(new ChangeListener<String>() {
//                @Override
//                public void changed(ObservableValue<? extends String> observable, String oldValue,
//                                    String newValue) {
//                    if (!newValue.matches("\\d*")) {
//                        surchargesNum.setText(newValue.replaceAll("[^\\d]", ""));
//                    }
//                }
//            });
//        });
    }

    public void loadCostPriceSample () {

        File dest = new File("CostPriceSample.xlsx");

        while (true) {
            if(Files.exists(dest.toPath())) {
                String nameFile = dest.getName().split("\\.")[0];
                String surName = "Copy";
                String endName = ".xlsx";
                dest = new File(nameFile + surName + endName) ;
            } else {
                break;
            }
        }
        copyExcelFile(dest);
    }

    public void clearResetFilesExcelFinance () {
        fileListExcel.clear();
        filesText.clear();
        showSelectFileForParsingExcelCostPrice.clear();
        errorTextSurchargesNum.setText("");
        errorTextStorageCost.setText("");
        errorTextAcceptanceFeeNum.setText("");
        errorTextOtherDeductionsNum.setText("");
        errorTextTax.setText("");
        pathCostPrice = null;
        surchargesNum.setText("0");
        storageCost.setText("0");
        acceptanceFeeNum.setText("0");
        otherDeductionsNum.setText("0");
        nameFinalFileExcel.setText("");
        taxField.setText("0");


    }

    public void selectFileForParsingExcelCostPrice(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel File", "*.xlsx"));
        File file = fileChooser.showOpenDialog(null);
        if(file != null) {
            showSelectFileForParsingExcelCostPrice.setText(file.getAbsolutePath());
            pathCostPrice = file.toPath();
        }
    }

    public void selectFileForParsingExcel(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel File", "*.xlsx"));
        File file = fileChooser.showOpenDialog(null);
        if(file != null) {
            filesText.setText(file.getAbsolutePath());
            pathParseExcel = file.toPath();
        }
    }

    public void selectMultiFileForParsingExcel(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel File", "*.xlsx"));
        List<File> fileList = fileChooser.showOpenMultipleDialog(null);
        System.out.println(fileListExcel.size());
        if(fileList != null) {
            fileListExcel.clear();
            filesText.clear();
            for (File file : fileList){
                if(file != null) {
                    filesText.appendText(file.getAbsolutePath() + "\n");
                    fileListExcel.add(file);
                }
            }
        }
    }


    public void waitText() {
        processingText.setText("Подождите... 5 сек Файл обрабатывается!");
    }

    public void fileProcessingExcelToFinancialWB () {
        waitText();

        errorTextSurchargesNum.setText("");
        errorTextStorageCost.setText("");
        errorTextAcceptanceFeeNum.setText("");
        errorTextOtherDeductionsNum.setText("");
        errorTextTax.setText("");

        if(pathParseExcel == null && fileListExcel.isEmpty()) {
            processingText.setText("Выберете файл!");
            return;
        }




        String surchargesNumStr = surchargesNum.getCharacters().toString().replace(" ", "").replace(",", ".");
        String storageCostStr = storageCost.getCharacters().toString().replace(" ", "").replace(",", ".");
        String acceptanceFeeNumStr = acceptanceFeeNum.getCharacters().toString().replace(" ", "").replace(",", ".");
        String otherDeductionsNumStr = otherDeductionsNum.getCharacters().toString().replace(" ", "").replace(",", ".");
        nameFinalFileExcelStr = nameFinalFileExcel.getCharacters().toString().trim();
        String tax = taxField.getCharacters().toString().replace(" ", "").replace(",", ".");

        try {
            surchargesBD = new BigDecimal(surchargesNumStr);
        } catch (Exception e) {
            errorTextSurchargesNum.setText("Ошибка!");
            e.printStackTrace();
            processingText.setText("Ошибка... Введите в поле правильное число (213443,234) дробная часть пишется после запятой (,)");
            return;
        }

        try {
            storageCostBD = new BigDecimal(storageCostStr);
        } catch (Exception e) {
            errorTextStorageCost.setText("Ошибка!");
            e.printStackTrace();
            processingText.setText("Ошибка... Введите в поле правильное число (213443,234) дробная часть пишется после запятой (,)");
            return;
        }

        try {
            acceptanceFeeBD = new BigDecimal(acceptanceFeeNumStr);
        } catch (Exception e) {
            errorTextAcceptanceFeeNum.setText("Ошибка!");
            e.printStackTrace();
            processingText.setText("Ошибка... Введите в поле правильное число (213443,234) дробная часть пишется после запятой (,)");
            return;
        }

        try {
            otherDeductionsBD = new BigDecimal(otherDeductionsNumStr);
        } catch (Exception e) {
            errorTextOtherDeductionsNum.setText("Ошибка!");
            e.printStackTrace();
            processingText.setText("Ошибка... Введите в поле правильное число (213443,234) дробная часть пишется после запятой (,)");
            return;
        }

        try {
            taxBD = new BigDecimal(tax);
        } catch (Exception e) {
            errorTextTax.setText("Ошибка!");
            e.printStackTrace();
            processingText.setText("Ошибка... Введите в поле правильное число (213443,234) дробная часть пишется после запятой (,)");
            return;
        }






        try {
            Main.startParseExcelMulti(fileListExcel, pathCostPrice, surchargesBD, storageCostBD, acceptanceFeeBD, otherDeductionsBD
                    , nameFinalFileExcelStr, taxBD);
//            Main.startParseExcel(pathParseExcel, pathCostPrice, surchargesBD, storageCostBD, acceptanceFeeBD, otherDeductionsBD
//                    , nameFinalFileExcelStr, taxBD);
            processingText.setText("Файл Успешно обработан!");
        } catch (Exception e) {
            processingText.setText("Ошибка при обработке");
            e.printStackTrace();
            System.out.println("Ошибка");
        }


    }

//    private void copyFileUsingChannel(File dest) {
//        try(FileChannel sourceChannel = new FileInputStream(getClass().getClassLoader().getResourceAsStream("/resources/com/example/myfinalprogramparsefinancialwb/CostPriceSample.xlsx")).getChannel();
//            FileChannel destChannel = new FileOutputStream(dest).getChannel();) {
//            destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    private void copyExcelFile(File dest) {
        try (InputStream is = getClass().getResourceAsStream("/com/example/myfinalprogramparsefinancialwb/CostPriceSample.xlsx");
             Workbook workbook = new XSSFWorkbook(is);
             FileOutputStream fos = new FileOutputStream(dest)){

            workbook.write(fos);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }







}
