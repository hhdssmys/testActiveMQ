package com.generalichina.testactivemq.springjms.queue.producer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage; 

public class ProducerServiceImpl implements ProducerService{
	@Autowired
	JmsTemplate jmsTemplate;
	@Resource(name="queueDestination")
	Destination destination;
	
	@Override
	public void sendMessage(String message) {
		//目的地（queue，topic）与创建者（）
		//发送消息
		jmsTemplate.send(destination, new MessageCreator() {
			//创建消息
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage(message);
				return textMessage;
			}
		});
		System.out.println("发送消息:"+message);
		
	}

}
