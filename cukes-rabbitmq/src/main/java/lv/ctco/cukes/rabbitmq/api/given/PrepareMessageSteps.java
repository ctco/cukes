package lv.ctco.cukes.rabbitmq.api.given;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import cucumber.api.java.en.Given;
import lv.ctco.cukes.core.internal.context.InflateContext;
import lv.ctco.cukes.rabbitmq.facade.RequestFacade;

@Singleton
@InflateContext
public class PrepareMessageSteps {

    @Inject
    RequestFacade requestFacade;

    @Given("^prepare new message$")
    public void prepareNewMessage() {
        requestFacade.initRequestMessage();
    }

    @Given("^message body:$")
    public void setMessageBody(String body) {
        requestFacade.setBody(body);
    }

    @Given("^message body is \"(.+)\"$")
    public void setMessageBodyInline(String body) {
        requestFacade.setBody(body);
    }

    @Given("^reply-to is \"(.+)\"$")
    public void setReplyTo(String replyTo) {
        requestFacade.setReplyTo(replyTo);
    }

    @Given("^content-type is \"(.+)\"$")
    public void setContentType(String contentType) {
        requestFacade.setContentType(contentType);
    }
}
