package com.example.protip.rabbitMQ;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.protip.config.ProTipApplicationConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

/**
 * Class for handling RabbitMQ related actions
 */
public class RabbitMQMessaging {
    private static final String MESSAGE_KEY = "msg";
    private Thread subscribeThread;

    /**
     * Method for subscribing on RabbitMq
     *
     * @param handler {@link Handler} the handler
     */
    public void subscribe(final Handler handler)
    {
        subscribeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        final String queue = ProTipApplicationConfig.getRabbitMQQueue();
                        final ConnectionFactory factory = new ConnectionFactory();

                        factory.setHost(ProTipApplicationConfig.getRabbitMQHost());
                        factory.setPort(ProTipApplicationConfig.getRabbitMQPort());
                        factory.setUsername(ProTipApplicationConfig.getRabbitMQUsername());
                        factory.setPassword(ProTipApplicationConfig.getRabbitMQPassword());

                        final Connection connection = factory.newConnection();
                        Channel channel = connection.createChannel();
                        channel.queueDeclare(queue,false,false,false,null);
                        QueueingConsumer consumer = new QueueingConsumer(channel);
                        channel.basicConsume(queue, true, consumer);

                        while (true) {
                            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                            String message = new String(delivery.getBody());
                            Message msg = handler.obtainMessage();
                            Bundle bundle = new Bundle();
                            bundle.putString(MESSAGE_KEY, message);
                            msg.setData(bundle);
                            handler.sendMessage(msg);
                        }
                    } catch (InterruptedException e) {
                        break;
                    } catch (Exception e1) {
                        Log.d("", "Connection broken: " + e1.getClass().getName());
                        try {
                            Thread.sleep(5000); //sleep and then try again
                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                }
            }
        });

        subscribeThread.start();
    }
}
