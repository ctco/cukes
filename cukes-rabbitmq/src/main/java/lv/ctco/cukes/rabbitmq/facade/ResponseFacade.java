package lv.ctco.cukes.rabbitmq.facade;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.SneakyThrows;
import lv.ctco.cukes.core.internal.context.GlobalWorldFacade;
import lv.ctco.cukes.rabbitmq.internal.MessageService;
import lv.ctco.cukes.rabbitmq.internal.MessageWrapper;
import lv.ctco.cukes.rabbitmq.internal.MessageWrapperContentProvider;

import java.util.Optional;

import static lv.ctco.cukes.core.internal.matchers.ArrayWithSizeMatcher.arrayWithSize;
import static lv.ctco.cukes.core.internal.matchers.ContainsPattern.containsPattern;
import static lv.ctco.cukes.core.internal.matchers.EqualToIgnoringTypeMatcher.equalToIgnoringType;
import static lv.ctco.cukes.core.internal.matchers.EqualToIgnoringTypeMatcher.notEqualToIgnoringType;
import static lv.ctco.cukes.core.internal.matchers.JsonMatchers.containsValueByPath;
import static lv.ctco.cukes.core.internal.matchers.JsonMatchers.containsValueByPathInArray;
import static lv.ctco.cukes.core.internal.matchers.OfTypeMatcher.ofType;
import static lv.ctco.cukes.rabbitmq.ConfigurationParameters.DEFAULT_READ_TIMEOUT;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.core.IsNot.not;

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

    public void assertMessageBodyDoesNotEqualTo(String body) {
        assertThat(message.getBody(), not(body));
    }

    public void assertMessageContainsPropertyWithPhrase(String path, String phrase) {
        assertThat(message, containsValueByPath(MessageWrapperContentProvider.INSTANCE, path, containsString(phrase)));
    }

    public void assertMessageContainsPropertyWithValue(String path, String value) {
        boolean caseInsensitive = globalWorldFacade.getBoolean("case-insensitive");
        assertThat(message, containsValueByPath(MessageWrapperContentProvider.INSTANCE, path, equalToIgnoringType(value, caseInsensitive)));
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
        assertThat(message, containsValueByPath(MessageWrapperContentProvider.INSTANCE, path, notEqualToIgnoringType(value)));
    }

    public void assertMessageBodyContainsPathOfType(String path, String type) {
        assertThat(message, containsValueByPath(MessageWrapperContentProvider.INSTANCE, path, ofType(type)));
    }

    public void assertMessageBodyContainsArrayWithSize(String path, String operator, Integer size) {
        assertThat(message, containsValueByPath(MessageWrapperContentProvider.INSTANCE, path, arrayWithSize(operator + size)));
    }

    public void assertMessageBodyContainsArrayWithSize(String path, Integer size) {
        assertMessageBodyContainsArrayWithSize(path, "=", size);
    }

    public void assertMessageBodyContainsArrayWithEntryHavingValue(String path, String value) {
        boolean caseInsensitive = globalWorldFacade.getBoolean("case-insensitive");
        assertThat(message, containsValueByPathInArray(MessageWrapperContentProvider.INSTANCE, path, equalToIgnoringType(value, caseInsensitive)));
    }

    public void assertMessageBodyDoesNotContainPath(String path) {
        assertThat(message, containsValueByPath(MessageWrapperContentProvider.INSTANCE, path, nullValue()));
    }

    public void assertMessageBodyContainsPathMatchingPattern(String path, String pattern) {
        assertThat(message, containsValueByPath(MessageWrapperContentProvider.INSTANCE, path, containsPattern(pattern)));
    }

    public void assertMessageBodyContainsPathNotMatchingPattern(String path, String pattern) {
        assertThat(message, containsValueByPath(MessageWrapperContentProvider.INSTANCE, path, not(containsPattern(pattern))));
    }
}
