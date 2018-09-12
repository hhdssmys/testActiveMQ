package com.generalichina.testactivemq.springjms.queueredelivery.consumer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.activemq.command.MessageAck;

public class ConsumerMessageListener implements MessageListener{
	//接收消息
	@Override
	public void onMessage(Message message) {
		TextMessage textMessage = (TextMessage) message;
		try {
			System.out.println("接收消息"+textMessage.getText());
			int car = 1/0;
		} catch (Exception e) {
			//e.printStackTrace();
			try {
				MessageAck messageAck = new MessageAck();
				System.out.println("处理失败"+textMessage.getText());
			} catch (JMSException e1) {
				
			}
		}
		
	}

}
