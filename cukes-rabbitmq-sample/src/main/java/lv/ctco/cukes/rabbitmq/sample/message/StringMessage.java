package lv.ctco.cukes.rabbitmq.sample.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StringMessage {

    private String body;

}
