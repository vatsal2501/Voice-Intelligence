package com.amazon.ask.questionnaire.handlers;

import static com.amazon.ask.request.Predicates.intentName;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Request;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import com.amazon.ask.questionnaire.dao.AnswerDAO;
import com.google.gson.Gson;


public class AnswerIntentHandler implements RequestHandler {
	
	public static final String ANSWER_SLOT = "Answer";

	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(intentName("AnswerIntent"));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		
		System.out.println("Customized Log :: in AnswerIntentHandler");
		
		AnswerDAO answerDAO = new AnswerDAO();

		Request request = input.getRequestEnvelope().getRequest();
		IntentRequest intentRequest = (IntentRequest) request;
		Intent intent = intentRequest.getIntent();
		Map<String, Slot> slots = intent.getSlots();

		Slot answerSlot = slots.get(ANSWER_SLOT);
		
		String speechText = "";
		String repromptText = "";
		
		/* Printing the session Json */
		System.out.println("Skill IntentHandlerB - EFORE ::: " + new Gson().toJson(input.getAttributesManager().getSessionAttributes()));
		
		int questionId= (int)input.getAttributesManager().getSessionAttributes().get("questionId");
//		input.getAttributesManager().setSessionAttributes(Collections.singletonMap("questionId", questionId));
		
//		input.getAttributesManager().getSessionAttributes().put("questionId",questionId);
		
		System.out.println("Customized Log :: questionId :"+questionId);
		
		/* Printing the session Json */
		System.out.println("Skill IntentHandlerB - EFORE ::: " + new Gson().toJson(input.getAttributesManager().getSessionAttributes()));

		if (answerSlot != null) {
			
			String answerSlotValue = answerSlot.getValue();
			
			System.out.println("Customized Log :: answerSlotValue :: " + answerSlotValue);
			
			String answer = answerDAO.getAnswer(questionId);
			
			String answerValue = answer.toLowerCase();
			 
			if((answerSlotValue.toLowerCase()).equals(answerValue)) {
				
				speechText = "Excellent, the answer is right";
				
			}else {
				speechText = "oops, its wrong answer";
			}
		
		}else {
			
			speechText = "sorry, i can't understand what you are saying";
			System.out.println("Customized Log :: answerSlot is null");
			
		}
		
		return input.getResponseBuilder().withSimpleCard("StateSession", speechText).withSpeech(speechText)
				.withReprompt(repromptText).withShouldEndSession(false).build();
	}

}

