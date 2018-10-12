package com.generalichina.testactivemq.basicjms.testrepeatmsg.queue;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class AppProducer {
	private static final String url = "tcp://10.0.61.210:61616";
	private static final String queueName="queue-test";
	
	public static void main(String[] args) throws JMSException {
				//1.创建ConnectionFactory
				ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
				//2.创建Connection
				Connection connection = connectionFactory.createConnection();
				//3.启动连接
				connection.start();
				//4.创建会话  false事务
				Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
				//5.创建一个目标
				Destination destination = session.createQueue(queueName);
				//6.创建一个生产者
				MessageProducer producer = session.createProducer(destination);
		
				//7.创建消息 ，重复消息体存在，broker算接收几个消息，查看broker的消息个数与接收情况即可
				TextMessage textMessage = session.createTextMessage("测试发送相同消息内容，每次发送是否是两次不同的消息");
				for(int i = 0;i<2;i++){
					//8.发送消息
					producer.send(textMessage);
					System.out.println("第"+i+"次发送消息:  "+textMessage.getText());
				}
				
				//9.关闭链接
				connection.close();
	}
	
}
