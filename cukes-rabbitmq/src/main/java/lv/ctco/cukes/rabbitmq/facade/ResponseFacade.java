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

import static lv.ctco.cukes.rabbitmq.ConfigurationParameters.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@Singleton
public class ResponseFacade {

    @Inject
    GlobalWorldFacade globalWorldFacade;
    @Inject
    MessageService messageService;

    private MessageWrapper message;

    @SneakyThrows
    public void waitForMessage(String queue, Optional<Integer> timeout) {
        message = messageService.receiveMessage(queue, timeout.orElse(getTimeout()));
        assertThat(message, is(notNullValue()));
    }

    private Integer getTimeout() {
        return Integer.parseInt(globalWorldFacade.get(DEFAULT_READ_TIMEOUT, "5"));
    }

    public void assertMessageBodyEqualsTo(String body) {
        assertThat(message.getBody(), is(body));
    }

    public void assertMessageContainsPropertyWithPhrase(String path, String phrase) {
        String body = message.getBody();
        String contentType = message.getContentType();
        CustomJsonMatchers.DefaultContentProvider<String> contentProvider = new CustomJsonMatchers.DefaultContentProvider<>(body, contentType);
        assertThat(body, CustomJsonMatchers.containsValueByPath(contentProvider, path, Matchers.containsString(phrase)));
    }

    public void assertMessageContainsPropertyWithValue(String path, String value) {
        String body = message.getBody();
        String contentType = message.getContentType();
        boolean caseInsensitive = globalWorldFacade.getBoolean("case-insensitive");
        Matcher<String> matcher = EqualToIgnoringTypeMatcher.equalToIgnoringType(value, caseInsensitive);
        CustomJsonMatchers.DefaultContentProvider<String> contentProvider = new CustomJsonMatchers.DefaultContentProvider<>(body, contentType);
        assertThat(body, CustomJsonMatchers.containsValueByPath(contentProvider, path, matcher));
    }
}
