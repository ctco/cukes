package lv.ctco.cukes.rabbitmq.facade;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.SneakyThrows;
import lv.ctco.cukes.rabbitmq.internal.CustomJsonMatchers;
import lv.ctco.cukes.rabbitmq.internal.MessageService;
import lv.ctco.cukes.rabbitmq.internal.MessageWrapper;
import lv.ctco.cukesrest.internal.context.GlobalWorldFacade;
import lv.ctco.cukesrest.internal.matchers.EqualToIgnoringTypeMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Singleton
public class ResponseFacade {

    @Inject
    GlobalWorldFacade globalWorldFacade;
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

    public void assertMessageContainsPropertyWithPhrase(String path, String phrase) {
        String body = message.getBody();
        CustomJsonMatchers.DefaultContentProvider<String> contentProvider = new CustomJsonMatchers.DefaultContentProvider<>(body, "application/json");
        assertThat(body, CustomJsonMatchers.containsValueByPath(contentProvider, path, Matchers.containsString(phrase)));
    }

    public void assertMessageContainsPropertyWithValue(String path, String value) {
        String body = message.getBody();
        boolean caseInsensitive = globalWorldFacade.getBoolean("case-insensitive");
        Matcher<String> matcher = EqualToIgnoringTypeMatcher.equalToIgnoringType(value, caseInsensitive);
        CustomJsonMatchers.DefaultContentProvider<String> contentProvider = new CustomJsonMatchers.DefaultContentProvider<>(body, "application/json");
        assertThat(body, CustomJsonMatchers.containsValueByPath(contentProvider, path, matcher));
    }
}
