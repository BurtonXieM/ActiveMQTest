package com.longder.test;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;


/**
 * JMS生产者
 * Created by Longder on 2016/10/18.
 */
public class TestProducer {
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";
    private static final String BROKENURL = "tcp://localhost:61616";
    private static final int SEND_NUM = 10;

    public static void main(String[] args) {
        //连接工厂
        ConnectionFactory connectionFactory;
        //连接
        Connection connection;
        //会话，接收或发送消息的线程
        Session session;
        //消息的目的地
        Destination destination;
        //消息生产者
        MessageProducer messageProducer;
        //实例化连接工厂
        connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKENURL);

        try {
            //通过连接工厂获取连接
            connection = connectionFactory.createConnection();
            //启动连接
            connection.start();
            //创建session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //创建一个连接HelloWorld的消息队列
            destination = session.createQueue("HelloWorld");
            //创建消息生产者
            messageProducer = session.createProducer(destination);
            //发送消息
            sendMessage(session, messageProducer);
            session.commit();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送消息
     *
     * @param session
     * @param messageProducer
     */
    public static void sendMessage(Session session, MessageProducer messageProducer) throws JMSException {
        for (int i = 0; i < TestProducer.SEND_NUM; i++) {
            //创建一条文本消息
            TextMessage message = session.createTextMessage("ActiveMQ发送消息：" + i);
            System.out.println("发送消息：ActiveMQ发送消息：" + i);
            //通过消息生产者发出消息
            messageProducer.send(message);
        }
    }
}
