package lv.ctco.cukes.rabbitmq.api.given;

import cucumber.api.java.en.Given;

public class PrepareMessageSteps {

    @Given("^prepare new message$")
    public void prepareNewMessage() {

    }

    @Given("^message body:$")
    public void setMessageBody(String body) {

    }

    @Given("^message body is \"(.+)\"$")
    public void setMessageBodyInline(String body) {

    }

    @Given("^reply-to is \"(.+)\"$")
    public void setReplyTo(String replyTo) {

    }
}
