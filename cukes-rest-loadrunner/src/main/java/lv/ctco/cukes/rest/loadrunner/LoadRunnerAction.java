package lv.ctco.cukes.rest.loadrunner;

import java.util.*;

public class LoadRunnerAction {

    private List<LoadRunnerTransaction> transactions = new ArrayList<LoadRunnerTransaction>();

    public List<LoadRunnerTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<LoadRunnerTransaction> transactions) {
        this.transactions = transactions;
    }

    public void addTransaction(LoadRunnerTransaction trx) {
        transactions.add(trx);
    }

    public String format() {
        StringBuilder result = new StringBuilder().append("Action() {\n" +
            "int HttpRetCode;\n" +
            "int transactionStatus;\n" +
            "int actionStatus = LR_PASS;\n" +
            "lr_continue_on_error(1);\n");
        for (LoadRunnerTransaction transaction : transactions) {
            result.append(transaction.format());
        }
        return result.append("lr_exit(LR_EXIT_ACTION_AND_CONTINUE , actionStatus);\n" + "return 0;\n}\n\n").toString();
    }
}
