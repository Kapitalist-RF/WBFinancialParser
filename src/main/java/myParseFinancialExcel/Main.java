package myParseFinancialExcel;

import myParseFinancialExcel.interfaceOperationForArticleWB.OperationForBaseGeneralMyReport;
import myParseFinancialExcel.myArticleWB.AddBaseArticleWB;
import myParseFinancialExcel.myArticleWB.BaseArticleWB;
import myParseFinancialExcel.myFinancialReportBaseWB.BaseThings;
import myParseFinancialExcel.myFinancialReportBaseWB.ParseExcelDataRead;
import myParseFinancialExcel.myFinancialReportBaseWB.ParseExcelDataWrite;
import myParseFinancialExcel.myFinancialReportBaseWB.Thing;
import myParseFinancialExcel.myFinancialReportFinal.BaseGeneralItems;
import myParseFinancialExcel.myFinancialReportFinal.operationForBaseGeneralItems.operationForBaseArticleWB.*;
import myParseFinancialExcel.myFinancialReportFinal.operationForUniversal.OperationForCostPrice;
import myParseFinancialExcel.myFinancialReportFinal.operationForUniversal.OperationForLogisticAndTransferred;
import myParseFinancialExcel.myFinancialReportFinal.operationForUniversal.OperationForTaxAndNetProfit;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {



    }

    public static void startParseExcel(Path pathExcel, Path costPricePath, BigDecimal surchargesBD,
                                       BigDecimal storageCostBD, BigDecimal acceptanceFeeBD, BigDecimal otherDeductionsBD, String nameFinalFileExcelStr, BigDecimal tax) {

        ReturnAndAdvancePaymentForGoodsWithoutMovement returnAndAdvancePaymentForGoodsWithoutMovement = new ReturnAndAdvancePaymentForGoodsWithoutMovement();
        ReturnAndPartialMarriageCompensation returnAndPartialMarriageCompensation = new ReturnAndPartialMarriageCompensation();
        ReturnAndReturn returnAndReturn = new ReturnAndReturn();
        ReturnAndReversalOfSales returnAndReversalOfSales = new ReturnAndReversalOfSales();
        SaleAndAdvancePaymentForGoodsWithoutMovement saleAndAdvancePaymentForGoodsWithoutMovement = new SaleAndAdvancePaymentForGoodsWithoutMovement();
        SaleAndCancellationOfReturns saleAndCancellationOfReturns = new SaleAndCancellationOfReturns();
        SaleAndCorrectSale saleAndCorrectSale = new SaleAndCorrectSale();
        SaleAndFines saleAndFines = new SaleAndFines();
        SaleAndLogistics saleAndLogistics = new SaleAndLogistics();
        SaleAndPartialMarriageCompensation saleAndPartialMarriageCompensation = new SaleAndPartialMarriageCompensation();
        SaleAndSale saleAndSale = new SaleAndSale();

        OperationForCostPrice operationForCostPrice = new OperationForCostPrice(costPricePath);
        OperationForLogisticAndTransferred operationForLogisticAndTransferred = new OperationForLogisticAndTransferred();
        OperationForTaxAndNetProfit operationForTaxAndNetProfit = new OperationForTaxAndNetProfit(tax);
        List<OperationForBaseGeneralMyReport> operations = new ArrayList<>();
        operations.add(returnAndAdvancePaymentForGoodsWithoutMovement);
        operations.add(returnAndPartialMarriageCompensation);
        operations.add(returnAndReturn);
        operations.add(returnAndReversalOfSales);
        operations.add(saleAndAdvancePaymentForGoodsWithoutMovement);
        operations.add(saleAndCancellationOfReturns);
        operations.add(saleAndCorrectSale);
        operations.add(saleAndFines);
        operations.add(saleAndLogistics);
        operations.add(saleAndPartialMarriageCompensation);
        operations.add(saleAndSale);
        operations.add(operationForCostPrice);
        operations.add(operationForLogisticAndTransferred);
        operations.add(operationForTaxAndNetProfit);
        ParseExcelDataRead parseExcelData = new ParseExcelDataRead();
        BaseThings baseThings = new BaseThings(parseExcelData.parseThings(pathExcel));
        BaseArticleWB baseArticleWB = new AddBaseArticleWB().addBaseArticleWB(baseThings);

        BaseGeneralItems baseGeneralItems = new BaseGeneralItems(acceptanceFeeBD, otherDeductionsBD, storageCostBD, surchargesBD, nameFinalFileExcelStr);
        for (OperationForBaseGeneralMyReport operation : operations) {
            operation.operationForBaseGeneralMyReport(baseArticleWB, baseGeneralItems);
        }
        baseGeneralItems.transferredSellerAfterDeductionAllExpensesAllSum();
        baseGeneralItems.netProfitAllSum();
        new ParseExcelDataWrite().writeGeneralItemToExcel(baseGeneralItems);
    }


    public static void startParseExcelMulti(List<File> fileList, Path costPricePath, BigDecimal surchargesBD,
                                            BigDecimal storageCostBD, BigDecimal acceptanceFeeBD, BigDecimal otherDeductionsBD, String nameFinalFileExcelStr, BigDecimal tax) {

        ReturnAndAdvancePaymentForGoodsWithoutMovement returnAndAdvancePaymentForGoodsWithoutMovement = new ReturnAndAdvancePaymentForGoodsWithoutMovement();
        ReturnAndPartialMarriageCompensation returnAndPartialMarriageCompensation = new ReturnAndPartialMarriageCompensation();
        ReturnAndReturn returnAndReturn = new ReturnAndReturn();
        ReturnAndReversalOfSales returnAndReversalOfSales = new ReturnAndReversalOfSales();
        SaleAndAdvancePaymentForGoodsWithoutMovement saleAndAdvancePaymentForGoodsWithoutMovement = new SaleAndAdvancePaymentForGoodsWithoutMovement();
        SaleAndCancellationOfReturns saleAndCancellationOfReturns = new SaleAndCancellationOfReturns();
        SaleAndCorrectSale saleAndCorrectSale = new SaleAndCorrectSale();
        SaleAndFines saleAndFines = new SaleAndFines();
        SaleAndLogistics saleAndLogistics = new SaleAndLogistics();
        SaleAndPartialMarriageCompensation saleAndPartialMarriageCompensation = new SaleAndPartialMarriageCompensation();
        SaleAndSale saleAndSale = new SaleAndSale();

        OperationForCostPrice operationForCostPrice = new OperationForCostPrice(costPricePath);
        OperationForLogisticAndTransferred operationForLogisticAndTransferred = new OperationForLogisticAndTransferred();
        OperationForTaxAndNetProfit operationForTaxAndNetProfit = new OperationForTaxAndNetProfit(tax);
        List<OperationForBaseGeneralMyReport> operations = new ArrayList<>();
        operations.add(returnAndAdvancePaymentForGoodsWithoutMovement);
        operations.add(returnAndPartialMarriageCompensation);
        operations.add(returnAndReturn);
        operations.add(returnAndReversalOfSales);
        operations.add(saleAndAdvancePaymentForGoodsWithoutMovement);
        operations.add(saleAndCancellationOfReturns);
        operations.add(saleAndCorrectSale);
        operations.add(saleAndFines);
        operations.add(saleAndLogistics);
        operations.add(saleAndPartialMarriageCompensation);
        operations.add(saleAndSale);
        operations.add(operationForCostPrice);
        operations.add(operationForLogisticAndTransferred);
        operations.add(operationForTaxAndNetProfit);
        ParseExcelDataRead parseExcelData = new ParseExcelDataRead();


        List<BaseThings> baseThingsList = new ArrayList<>();
        List<Thing> thingListMulti = new ArrayList<>();
        if(fileList != null) {
            for (File file : fileList) {
                baseThingsList.add(new BaseThings(parseExcelData.parseThings(file.toPath())));
            }
            for (BaseThings baseThing : baseThingsList) {
                for (Thing thing : baseThing.getThingsList()) {
                    thingListMulti.add(thing);
                }
            }
        }

        BaseThings baseThings = new BaseThings(thingListMulti);
        BaseArticleWB baseArticleWB = new AddBaseArticleWB().addBaseArticleWB(baseThings);

        BaseGeneralItems baseGeneralItems = new BaseGeneralItems(acceptanceFeeBD, otherDeductionsBD, storageCostBD, surchargesBD, nameFinalFileExcelStr);
        for (OperationForBaseGeneralMyReport operation : operations) {
            operation.operationForBaseGeneralMyReport(baseArticleWB, baseGeneralItems);
        }
        baseGeneralItems.transferredSellerAfterDeductionAllExpensesAllSum();
        baseGeneralItems.netProfitAllSum();
        new ParseExcelDataWrite().writeGeneralItemToExcel(baseGeneralItems);
    }


}