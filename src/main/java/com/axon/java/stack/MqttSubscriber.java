package  com.axon.java.stack;

import org.eclipse.paho.client.mqttv3.*;

public class MqttSubscriber {
    public static void main(String[] args) {
        String brokerUrl = "tcp://mqtt.lianok.com:1883"; // MQTT代理的地址
        String clientId = "1886CA8M0471"; // 客户端ID，可以自定义
        String topic = "/pos_pay/message/1886CA8M0471"; // 订阅的主题，/pos_pay/message/liandi/  这是固定的。 4022232110000001 这个编号是动态的，即当前pos机设备编号。

        try {
            IMqttClient client = new MqttClient(brokerUrl, clientId);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            options.setUserName("pos");
            options.setPassword("pos".toCharArray());
            client.connect(options);

            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    System.out.println("Connection lost: " + cause.getMessage());
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    System.out.println("Received message on topic: " + topic);
                    System.out.println("Message: " + new String(message.getPayload()));
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    // 消息传递完成回调
                }
            });

            client.subscribe(topic);

            // 阻塞程序，以保持订阅活动
            while (true) {
                Thread.sleep(1000);
            }
        } catch (MqttException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
