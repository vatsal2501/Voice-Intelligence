package com.amazon.ask.questionnaire.handlers;

import static com.amazon.ask.request.Predicates.intentName;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.questionnaire.dao.QuestionDAO;

public class QuestionIntent implements RequestHandler {

	public static final String ANSWER_SLOT = "Answer";

//	public static final String ANSWER_SLOT = "QuestionType";

	@Override
	public boolean canHandle(HandlerInput input) {
	
		return input.matches(intentName("QuestionIntent"));
	
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {

		QuestionDAO questionDAO = new QuestionDAO();

		String speechText = "";
		String repromptText = "";

		int skillId = (int) input.getAttributesManager().getSessionAttributes().get("skillId");
//		input.getAttributesManager().setSessionAttributes(Collections.singletonMap("skillId", skillId));
		System.out.println("Customized Log :: SkillId :" + skillId);

		int questionTypeId = (int) input.getAttributesManager().getSessionAttributes().get("questionTypeId");
//		input.getAttributesManager().setSessionAttributes(Collections.singletonMap("questionTypeId", questionTypeId));
		System.out.println("Customized Log :: questionTypeId :" + questionTypeId);

		String questionType = (String) input.getAttributesManager().getSessionAttributes().get("questionType");
//		input.getAttributesManager().setSessionAttributes(Collections.singletonMap("questionType", questionType));
		System.out.println("Customized Log :: questionType :" + questionType);

		if (questionType.equals("MCQ")) {

			Map<String, Object> questionMap = questionDAO.getQuestionByQuestionTypeIdAndSkillId(questionType,
					questionTypeId, skillId);

			int questionId = (int) questionMap.get("questionId");
			
//			input.getAttributesManager().setSessionAttributes(Collections.singletonMap("questionId", questionId));

			input.getAttributesManager().getSessionAttributes().put("questionId",questionId);

			if (questionMap.isEmpty()) {

				speechText = "there is no question available right now, you can try other skill !!";

				repromptText = "please try other skill.";

			} else {

				speechText = "okay, the question is " + questionMap.get("question")
						+ ". and the relevant options are. A," + questionMap.get("option1") + ". B,"
						+ questionMap.get("option2") + ". C," + questionMap.get("option3") + ". D,"
						+ questionMap.get("option4") + ".";

				repromptText = speechText = "okay, the question is " + questionMap.get("question")
						+ ". and the relevant options are. A," + questionMap.get("option1") + ". B,"
						+ questionMap.get("option2") + ". C," + questionMap.get("option3") + ". D,"
						+ questionMap.get("option4") + ".";

			}

		} else if (questionType.equals("1 word")) {

			Map<String, Object> questionMap = questionDAO.getQuestionByQuestionTypeIdAndSkillId(questionType,
					questionTypeId, skillId);
			
			int questionId = (int) questionMap.get("questionId");
			input.getAttributesManager().getSessionAttributes().put("questionId",questionId);

			if (questionMap.isEmpty()) {
			
				speechText = "there is no question available right now, you can try other skill !!";
				repromptText = "please try other skill.";
			
			} else {

				speechText = "okay, the question is " + questionMap.get("question");
				repromptText = "the question is" + questionMap.get("question");

			}

			repromptText = "do you want to stop or continue answering questions ?";

		}

		return input.getResponseBuilder().withSimpleCard("StateSession", speechText).withSpeech(speechText)
				.withReprompt(repromptText).withShouldEndSession(false).build();
	}

}
