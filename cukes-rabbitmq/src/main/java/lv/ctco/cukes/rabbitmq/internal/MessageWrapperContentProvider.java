package lv.ctco.cukes.rabbitmq.internal;

import lv.ctco.cukes.core.internal.matchers.JsonMatchers;

public class MessageWrapperContentProvider implements JsonMatchers.ContentProvider<MessageWrapper> {

    public static final MessageWrapperContentProvider INSTANCE = new MessageWrapperContentProvider();

    @Override
    public String getValue(Object o) {
        return ((MessageWrapper) o).getBody();
    }

    @Override
    public String getContentType(Object o) {
        return ((MessageWrapper) o).getContentType();
    }

}
