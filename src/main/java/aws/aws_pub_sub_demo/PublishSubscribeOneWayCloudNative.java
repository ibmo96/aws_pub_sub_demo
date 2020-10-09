package aws.aws_pub_sub_demo;

import java.util.UUID;

//import software.amazon.awssdk.services.sns;
import com.amazonaws.services.sns.*;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;

public class PublishSubscribeOneWayCloudNative {

    public static void main(String... args) throws Exception {

        final AmazonSNS sns = AmazonSNSClientBuilder.standard().build();

        new Thread(new Publisher(sns, "arn:aws:iam:<region>:<account-number>:PubSubOneWayCloudNative")).start();
    }

    public static class Publisher implements Runnable {

        private AmazonSNS sns;
        private String destination;

        public Publisher(AmazonSNS sns, String destination) {
            this.sns = sns;
            this.destination = destination;
        }

        public void run() {
            long counter = 0;

            while (true) {
                sns.publish(
                    new PublishRequest()
                        .withTargetArn(destination)
                        .withSubject("PubSubOneWayCloudNative sample")
                        .withMessage("Message " + ++counter)
                        .addMessageAttributesEntry("MessageID", new MessageAttributeValue().withDataType("String").withStringValue(UUID.randomUUID().toString())));
            }
        }
    }
}
