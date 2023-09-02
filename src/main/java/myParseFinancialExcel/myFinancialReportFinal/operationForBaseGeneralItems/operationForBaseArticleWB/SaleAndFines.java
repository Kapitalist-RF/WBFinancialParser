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

public class SaleAndFines implements OperationForBaseGeneralMyReport {


    @Override
    @NameField("-")
    @DocumentType("Продажа")
    @ReasonForPayment("Штрафы")
    public boolean operationForBaseGeneralMyReport(BaseArticleWB baseArticleWB, BaseGeneralItems baseGeneralItems) {
        List<Thing> baseArticleWBthingsList = baseArticleWB.getSaleAndFines();
        List<GeneralInfoItems> baseGeneralItemsList = baseGeneralItems.getBaseGeneralItems();
        GeneralInfoItems generalInfoItems = new GeneralInfoItems();

        if(baseArticleWBthingsList == null) {
            return false;
        }

        for(Thing thing : baseArticleWBthingsList) {
            generalInfoItems = generalInfoItems.thingToGeneralInfoItems(thing);
            if(baseGeneralItemsList.contains(generalInfoItems)) {

                generalInfoItems = baseGeneralItemsList.get(baseGeneralItemsList.indexOf(generalInfoItems));
                BigDecimal totalFine = thing.getTotalFine();
                BigDecimal result = generalInfoItems.getTotalFine().add(totalFine);
                generalInfoItems.setTotalFine(result);

                BigDecimal returnPCS = thing.getReturnQuantity().add(generalInfoItems.getReturnPCS());
                generalInfoItems.setReturnPCS(returnPCS);
            } else {
                baseGeneralItemsList.add(generalInfoItems);
                BigDecimal totalFine = thing.getTotalFine();
                BigDecimal result = new BigDecimal(0).add(totalFine);
                generalInfoItems.setTotalFine(result);
                generalInfoItems.setReturnPCS(thing.getReturnQuantity());

            }
        }


        return true;
    }
}
