package myParseFinancialExcel.myFinancialReportFinal.operationForUniversal;


import myParseFinancialExcel.interfaceOperationForArticleWB.OperationForBaseGeneralMyReport;
import myParseFinancialExcel.myArticleWB.BaseArticleWB;

import myParseFinancialExcel.myFinancialReportFinal.BaseGeneralItems;
import myParseFinancialExcel.myFinancialReportFinal.GeneralInfoItems;

public class OperationForLogisticAndTransferred implements OperationForBaseGeneralMyReport {


    @Override
    public boolean operationForBaseGeneralMyReport(BaseArticleWB baseArticleWB, BaseGeneralItems baseGeneralItems) {

        if(baseGeneralItems.getBaseGeneralItems() == null) {
            return false;
        }

        for(GeneralInfoItems generalInfoItems : baseGeneralItems.getBaseGeneralItems()) {

            generalInfoItems.setTotalAmountForLogistics(generalInfoItems.getSalesLogistics().add(generalInfoItems.getReturnLogistics()));

            generalInfoItems.setTransferredSellerAfterDeductionAllExpenses(generalInfoItems.getTransferredSeller()
                    .subtract(generalInfoItems.getTotalAmountForLogistics())
                    .subtract(generalInfoItems.getTotalFine()));
        }
        return true;
    }
}
