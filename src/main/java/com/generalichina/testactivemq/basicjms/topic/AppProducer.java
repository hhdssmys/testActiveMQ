package com.generalichina.testactivemq.basicjms.topic;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
/**
 * 
 * ━━━━━━南无阿弥陀佛━━━━━━
 *　　　┏┓　　　┏┓
 *　　┏┛┻━━━┛┻┓
 *　　┃　　　　　　　┃
 *　　┃　　　━　　　┃
 *　　┃　┳┛　┗┳　┃
 *　　┃　　　　　　　┃
 *　　┃　　　┻　　　┃
 *　　┃　　　　　　　┃
 *　　┗━┓　　　┏━┛
 *　　　　┃　　　┃stay hungry stay foolish
 *　　　　┃　　　┃Code is far away from bug with the animal protecting
 *　　　　┃　　　┗━━━┓
 *　　　　┃　　　　　　　┣┓
 *　　　　┃　　　　　　　┏┛
 *　　　　┗┓┓┏━┳┓┏┛
 *　　　　　┃┫┫　┃┫┫
 *　　　　　┗┻┛　┗┻┛
 * ━━━━━━萌萌哒━━━━━━
 * @ClassName: AppProducer
 * @Description: 
 * @author QSNP241
 * @date: 2018年9月11日 下午5:54:26
 */
public class AppProducer {
	private static final String url = "tcp://10.0.61.210:61616";
	private static final String topicName="topic-test";
	
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
				Destination destination = session.createTopic(topicName);
				//6.创建一个生产者
				MessageProducer producer = session.createProducer(destination);
				for(int i=0;i<100;i++){
					//7.创建消息
					TextMessage textMessage = session.createTextMessage("test Topic"+i);
					//8.发送消息
					producer.send(textMessage);
					System.out.println("发送消息"+textMessage.getText());
				}
				//9.关闭链接
				connection.close();
	}
	
}
