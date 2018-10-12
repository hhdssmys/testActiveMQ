package com.generalichina.testactivemq.basicjms.testackmode.pcshared.queue;

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
				Session session = connection.createSession(true,Session.AUTO_ACKNOWLEDGE);
				//5.创建一个目标
				Destination destination = session.createQueue(queueName);
				//6.创建一个生产者
				MessageProducer producer = session.createProducer(destination);
				session.getTransacted();
				for(int i=0;i<5;i++){
					//7.创建消息
					TextMessage textMessage = session.createTextMessage("test"+i);
					//8.发送消息
					producer.send(textMessage);
					System.out.println("发送消息"+textMessage.getText());
				}
				session.commit();//此处先提5条消息

				System.out.println("**********大山的分界线************");
				for(int i=5;i<11;i++){
					
					//7.创建消息
					TextMessage textMessage = session.createTextMessage("test"+i);
					
					//8.发送消息
					producer.send(textMessage);
					System.out.println("发送消息"+textMessage.getText());
					if(i==9){
						//i=5-9，此处再提5条消息
						try {
							session.commit();
							Thread.sleep(30000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				session.commit();//此处提i=10的数据，最后一条数据，总共要发11个消息
				//9.关闭链接
				connection.close();
	}
	
}
