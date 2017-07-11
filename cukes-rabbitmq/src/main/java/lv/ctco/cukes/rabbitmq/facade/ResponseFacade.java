package lv.ctco.cukes.rabbitmq.facade;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.SneakyThrows;
import lv.ctco.cukes.core.internal.context.GlobalWorldFacade;
import lv.ctco.cukes.rabbitmq.internal.CustomJsonMatchers;
import lv.ctco.cukes.rabbitmq.internal.MessageService;
import lv.ctco.cukes.rabbitmq.internal.MessageWrapper;

import java.util.Optional;

import static lv.ctco.cukes.core.internal.matchers.ArrayWithSizeMatcher.arrayWithSize;
import static lv.ctco.cukes.core.internal.matchers.ContainsPattern.containsPattern;
import static lv.ctco.cukes.core.internal.matchers.EqualToIgnoringTypeMatcher.equalToIgnoringType;
import static lv.ctco.cukes.core.internal.matchers.EqualToIgnoringTypeMatcher.notEqualToIgnoringType;
import static lv.ctco.cukes.core.internal.matchers.OfTypeMatcher.ofType;
import static lv.ctco.cukes.rabbitmq.ConfigurationParameters.DEFAULT_READ_TIMEOUT;
import static lv.ctco.cukes.rabbitmq.internal.CustomJsonMatchers.containsValueByPath;
import static lv.ctco.cukes.rabbitmq.internal.CustomJsonMatchers.containsValueByPathInArray;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

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

    private CustomJsonMatchers.ContentProvider<MessageWrapper> getContentProvider() {
        return new CustomJsonMatchers.ContentProvider<MessageWrapper>() {
            @Override
            public String getValue(Object o) {
                return ((MessageWrapper) o).getBody();
            }

            @Override
            public String getContentType(Object o) {
                return ((MessageWrapper) o).getContentType();
            }
        };
    }

    public void assertMessageBodyEqualsTo(String body) {
        assertThat(message.getBody(), is(body));
    }

    public void assertMessageBodyDoesNotEqualTo(String body) {
        assertThat(message.getBody(), not(body));
    }

    public void assertMessageContainsPropertyWithPhrase(String path, String phrase) {
        assertThat(message, containsValueByPath(getContentProvider(), path, containsString(phrase)));
    }

    public void assertMessageContainsPropertyWithValue(String path, String value) {
        boolean caseInsensitive = globalWorldFacade.getBoolean("case-insensitive");
        assertThat(message, containsValueByPath(getContentProvider(), path, equalToIgnoringType(value, caseInsensitive)));
    }

    public void assertMessageBodyIsEmpty() {
        assertThat(message.getBody(), isEmptyString());
    }

    public void assertMessageBodyIsNotEmpty() {
        assertThat(message.getBody(), not(isEmptyString()));
    }

    public void assertMessageBodyContains(String body) {
        assertThat(message.getBody(), containsString(body));
    }

    public void assertMessageBodyDoesNotContain(String body) {
        assertThat(message.getBody(), not(containsString(body)));
    }

    public void assertMessageBodyContainsPathWithOtherValue(String path, String value) {
        assertThat(message, containsValueByPath(getContentProvider(), path, notEqualToIgnoringType(value)));
    }

    public void assertMessageBodyContainsPathOfType(String path, String type) {
        assertThat(message, containsValueByPath(getContentProvider(), path, ofType(type)));
    }

    public void assertMessageBodyContainsArrayWithSize(String path, String operator, Integer size) {
        assertThat(message, containsValueByPath(getContentProvider(), path, arrayWithSize(operator + size)));
    }

    public void assertMessageBodyContainsArrayWithSize(String path, Integer size) {
        assertMessageBodyContainsArrayWithSize(path, "=", size);
    }

    public void assertMessageBodyContainsArrayWithEntryHavingValue(String path, String value) {
        boolean caseInsensitive = globalWorldFacade.getBoolean("case-insensitive");
        assertThat(message, containsValueByPathInArray(getContentProvider(), path, equalToIgnoringType(value, caseInsensitive)));
    }

    public void assertMessageBodyDoesNotContainPath(String path) {
        assertThat(message, containsValueByPath(getContentProvider(), path, nullValue()));
    }

    public void assertMessageBodyContainsPathMatchingPattern(String path, String pattern) {
        assertThat(message, containsValueByPath(getContentProvider(), path, containsPattern(pattern)));
    }

    public void assertMessageBodyContainsPathNotMatchingPattern(String path, String pattern) {
        assertThat(message, containsValueByPath(getContentProvider(), path, not(containsPattern(pattern))));
    }
}
