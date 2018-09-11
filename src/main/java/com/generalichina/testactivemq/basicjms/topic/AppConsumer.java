package com.generalichina.testactivemq.basicjms.topic;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
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
 * @ClassName: AppConsumer.java
 * @Description: 
 * @author QSNP241
 * @date: 2018年9月11日 下午5:49:33
 */
public class AppConsumer {
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
		
		/**
		 * 队列与主题的区别 在与目标
		 * 差别不大
		 * 
		 */
		//5.创建一个目标
		Destination destination = session.createTopic(topicName);
		//6.创建一个消费者
		MessageConsumer consumer = session.createConsumer(destination);
		//7创建一个监听器
		consumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message) {
				TextMessage textMessage = (TextMessage) message;
				try {
					System.out.println("接收消息"+textMessage.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		//8.关闭链接 消息监听是异步的，所以不能在此关闭链接,关闭要在程序结束
		//connection.close();
	}
}
