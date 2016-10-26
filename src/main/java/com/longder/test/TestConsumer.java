package com.longder.test;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * JMS消费者
 * Created by Longder on 2016/10/18.
 */
public class TestConsumer {
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";
    private static final String BROKENURL = "tcp://localhost:61616";
    public static void main(String[] args) {
        //连接工厂
        ConnectionFactory connectionFactory;
        //连接
        Connection connection;
        //会话
        Session session;
        //目的地
        Destination destination;
        //消费者
        MessageConsumer messageConsumer;
        //实例化连接工厂
        connectionFactory = new ActiveMQConnectionFactory(USERNAME,PASSWORD,BROKENURL);
        try {
            //通过连接工厂获取连接
            connection = connectionFactory.createConnection();
            //启动连接
            connection.start();
            session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            //创建一格连接HelloWorld的消息队列
            destination = session.createQueue("HelloWorld");
            //创建消息消费者
            messageConsumer = session.createConsumer(destination);

            //接收消息
            while(true){
                TextMessage textMessage = (TextMessage) messageConsumer.receive(100000);
                if(textMessage!=null){
                    System.out.println("收到的消息："+textMessage.getText());
                }else{
                    break;
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
