package lv.ctco.cukes.soap.sample.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "calculatorRequest")
@XmlRootElement
public class CalculatorRequest implements Serializable {
    public Integer a;
    public Integer b;
}
