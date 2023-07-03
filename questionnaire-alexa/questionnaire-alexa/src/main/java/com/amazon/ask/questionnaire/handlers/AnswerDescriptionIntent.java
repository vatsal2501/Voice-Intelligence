package com.amazon.ask.questionnaire.handlers;

import static com.amazon.ask.request.Predicates.intentName;

import java.util.Collections;
import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.questionnaire.dao.AnswerDAO;

public class AnswerDescriptionIntent implements RequestHandler {

	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(intentName("AnswerDescriptionIntent"));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {

		AnswerDAO answerDAO = new AnswerDAO();
		
		String speechText = "";
		String repromptText = "";
		
		int skillId= (int)input.getAttributesManager().getSessionAttributes().get("skillId");
//		input.getAttributesManager().setSessionAttributes(Collections.singletonMap("skillId", skillId));
		System.out.println("Customized Log :: SkillId :"+skillId);
		
		int questionTypeId= (int)input.getAttributesManager().getSessionAttributes().get("questionTypeId");
//		input.getAttributesManager().setSessionAttributes(Collections.singletonMap("questionTypeId", questionTypeId));
		System.out.println("Customized Log :: questionTypeId :"+questionTypeId);
		
		
		int questionId= (int)input.getAttributesManager().getSessionAttributes().get("questionId");
//		input.getAttributesManager().setSessionAttributes(Collections.singletonMap("questionId", questionId));
		System.out.println("Customized Log :: questionId :"+questionId);
	
		/*
		 * String questionType=
		 * (String)input.getAttributesManager().getSessionAttributes().get(
		 * "questionType");
		 * input.getAttributesManager().setSessionAttributes(Collections.singletonMap(
		 * "questionType", questionType));
		 * System.out.println("Customized Log :: questionType :"+questionType);
		 */
		
		String answerDescription = answerDAO.getAnswerDescription(questionId,questionTypeId,skillId);
		
		speechText = "the description for this answer is , " + answerDescription;
		
		return input.getResponseBuilder().withSimpleCard("StateSession", speechText).withSpeech(speechText)
				.withReprompt(repromptText).withShouldEndSession(false).build();
	}

}
