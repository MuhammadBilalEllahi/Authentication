package MyApp.Authentication.SMS_Sender;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.Say;
import com.twilio.type.PhoneNumber;

import java.net.URI;
import java.net.URISyntaxException;

public class SMSSender {

    // Twilio account information
    private static final String ACCOUNT_SID = "ACcd*****e0f5";
    private static final String AUTH_TOKEN = "6cba*******287";
    private static final String FROM_NUMBER = "+44******699";

    public static void sendSMS(String toNumber, String messageBody) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(
                        new PhoneNumber(toNumber),
                        new PhoneNumber(FROM_NUMBER),
                        messageBody)
                .create();

        System.out.println("Message SID: " + message.getSid());


    }
    public static void callPerson(String toNumber,String messageBody) throws URISyntaxException {
        Twilio.init(ACCOUNT_SID,AUTH_TOKEN);

        Say say = new Say.Builder("Hello, this is Bilal's Company Speaking. Your Otp is "+ messageBody).build();
        VoiceResponse voiceResponse = new VoiceResponse.Builder().say(say).build();


        Call call = Call.creator(
                new PhoneNumber(toNumber),
                new PhoneNumber(FROM_NUMBER),
                new URI("http://demo.twilio.com/docs/voice.xml")
                //voiceResponse.toXml()
                //Account not authorized to call +92*****20. Perhaps you need to enable some international permissions: https://www.twilio.com/console/voice/calls/geo-permissions/low-risk


        ).create();

        System.out.println(call.getSid());
    }
}
