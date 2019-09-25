package com.jinm.example.ch11.partition.remote;

import java.util.Collection;

import org.springframework.batch.core.StepExecution;
import org.springframework.integration.Message;

/**
 * 
 *
 * 2014-3-23下午06:16:21
 */
public class MessageTransformer {
	@SuppressWarnings("unchecked")
	public Message<Collection<StepExecution>> extract(Message<?>  inMessage)  {
		return (Message<Collection<StepExecution>>)inMessage;
	}
}
