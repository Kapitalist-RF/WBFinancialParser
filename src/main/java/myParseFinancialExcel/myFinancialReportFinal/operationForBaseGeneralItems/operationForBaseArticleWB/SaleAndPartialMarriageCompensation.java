package myParseFinancialExcel.myFinancialReportFinal.operationForBaseGeneralItems.operationForBaseArticleWB;

import myParseFinancialExcel.annotations.DocumentType;
import myParseFinancialExcel.annotations.NameField;
import myParseFinancialExcel.annotations.ReasonForPayment;
import myParseFinancialExcel.interfaceOperationForArticleWB.OperationForBaseGeneralMyReport;
import myParseFinancialExcel.myArticleWB.BaseArticleWB;
import myParseFinancialExcel.myFinancialReportBaseWB.Thing;
import myParseFinancialExcel.myFinancialReportFinal.BaseGeneralItems;
import myParseFinancialExcel.myFinancialReportFinal.GeneralInfoItems;

import java.math.BigDecimal;
import java.util.List;

public class SaleAndPartialMarriageCompensation implements OperationForBaseGeneralMyReport {


    @Override
    @NameField("+")
    @DocumentType("Продажа")
    @ReasonForPayment("Частичная компенсация брака")
    public boolean operationForBaseGeneralMyReport(BaseArticleWB baseArticleWB, BaseGeneralItems baseGeneralItems) {
        List<Thing> baseArticleWBthingsList = baseArticleWB.getSaleAndPartialMarriageCompensation();
        List<GeneralInfoItems> baseGeneralItemsList = baseGeneralItems.getBaseGeneralItems();
        GeneralInfoItems generalInfoItems = new GeneralInfoItems();

        if(baseArticleWBthingsList == null) {
            return false;
        }

        for(Thing thing : baseArticleWBthingsList) {
            generalInfoItems = generalInfoItems.thingToGeneralInfoItems(thing);
            if(baseGeneralItemsList.contains(generalInfoItems)) {
                generalInfoItems = baseGeneralItemsList.get(baseGeneralItemsList.indexOf(generalInfoItems));
                BigDecimal transferredToSellerSoldGoods = thing.getTransferredToSellerSoldGoods();
                BigDecimal result = generalInfoItems.getTransferredSeller().add(transferredToSellerSoldGoods);
                generalInfoItems.setTransferredSeller(result);
                generalInfoItems.setAmountForMarriage(generalInfoItems.getAmountForMarriage().add(transferredToSellerSoldGoods));
            } else {
                baseGeneralItemsList.add(generalInfoItems);
                BigDecimal transferredToSellerSoldGoods = thing.getTransferredToSellerSoldGoods();
                BigDecimal result = new BigDecimal(0).add(transferredToSellerSoldGoods);
                generalInfoItems.setTransferredSeller(result);
                generalInfoItems.setAmountForMarriage(new BigDecimal(0).add(transferredToSellerSoldGoods));
            }
        }
        return true;
    }
}