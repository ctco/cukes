package lv.ctco.cukesrest.loadrunner;

import lv.ctco.cukesrest.loadrunner.function.*;
import org.apache.commons.lang3.*;

import java.util.*;

public class LoadRunnerTransaction {

    private String name;
    private String trxFlag;
    private List<LoadRunnerFunction> functions = new ArrayList<LoadRunnerFunction>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrxFlag() {
        return trxFlag;
    }

    public void setTrxFlag(String trxFlag) {
        this.trxFlag = trxFlag;
    }

    public List<LoadRunnerFunction> getFunctions() {
        return functions;
    }

    public void addFunction(LoadRunnerFunction function) {
        functions.add(function);
    }

    public void setFunctions(List<LoadRunnerFunction> functions) {
        this.functions = functions;
    }

    public String format() {
        String escapedTransactionName = StringUtils.replace(name, " ", "_");
        StringBuilder result = new StringBuilder()
                .append("lr_think_time(1);\n\n")
                .append("web_reg_save_param(\"httpcode\",\n" +
                        "\"LB=HTTP/1.1 \",\n" +
                        "\"RB= \",\n" +
                        "\"Ord=1\",\n" +
                        "LAST);\n")
                .append("transactionStatus = LR_PASS;\n")
                .append("lr_start_transaction(\"").append(escapedTransactionName).append("\");\n\n");
        for (LoadRunnerFunction function : functions) {
            result.append(function.format());
        }
        return result.append("lr_end_transaction(\"").append(escapedTransactionName).append("\", ").append(trxFlag)
        .append(");\n\n").toString();
    }
}
