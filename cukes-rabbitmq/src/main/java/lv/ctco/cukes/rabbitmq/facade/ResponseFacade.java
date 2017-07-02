package lv.ctco.cukes.rabbitmq.facade;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.SneakyThrows;
import lv.ctco.cukes.rabbitmq.internal.ConnectionService;
import lv.ctco.cukes.rabbitmq.internal.MessageService;
import lv.ctco.cukes.rabbitmq.internal.MessageWrapper;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Singleton
public class ResponseFacade {

    @Inject
    ConnectionService connectionService;
    @Inject
    MessageService messageService;

    private MessageWrapper message;

    //TODO seems that exchange is not needed here
    @SneakyThrows
    public void waitForMessage(String queue, Optional<String> exchange, Optional<Integer> timeout) {
        message = messageService.receiveMessage(queue, timeout.orElse(5));
    }

    public void assertMessageBodyEqualsTo(String body) {
        assertThat(message.getBody(), is(body));
    }
}
