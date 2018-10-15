package com.generalichina.testactivemq.basicjms.testasyncproducer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.AsyncCallback;

public class AppProducerNoTrxPersistent {
	private static final String url = "tcp://10.0.61.210:61616";
	private static final String queueName="queue-test";
	
	public static void main(String[] args) throws JMSException {
				//1.创建ConnectionFactory
				ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
				//2.创建Connection
				Connection connection = connectionFactory.createConnection();
				//异步发送模式
				((ActiveMQConnection)connection).setUseAsyncSend(true);
				//3.启动连接
				connection.start();
				//4.创建会话   非事务非持久化
				Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
				//5.创建一个目标
				Destination destination = session.createQueue(queueName);
				//6.创建一个生产者
				ActiveMQMessageProducer producer =  (ActiveMQMessageProducer) session.createProducer(destination);
				
				// 非事务持久化
				producer.setDeliveryMode(DeliveryMode.PERSISTENT);
				//7.创建消息
				TextMessage textMessage = session.createTextMessage("test2");
				//8.发送消息
				producer.send(textMessage);
				System.out.println("发送消息"+textMessage.getText());
				//9.关闭链接
				connection.close();
	}
	
}
