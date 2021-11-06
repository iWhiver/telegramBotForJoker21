package ru.ivan.telegram;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class JokerBot extends TelegramLongPollingBot {

	private final YamlVariables privateVar = new YamlVariables("private.yaml");
	private final YamlVariables messages = new YamlVariables("messages.yaml");
	private final String DATE_FORMAT = "HH:mm:ss dd-MM-yyyy";

	@Override
	public void onUpdateReceived(Update update) {
		if (update.hasMessage() && update.getMessage().hasText()) {
			try {
				Message incomingMessage = update.getMessage();
				SendMessage sendMessage = handleIncomingMessage(incomingMessage);

				System.out.println(messages.getVariable("l-update") + getCurrentTime());

				execute(sendMessage);
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		}
	}

	private String getCurrentTime() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		return formatter.format(date);
	}

	private SendMessage handleIncomingMessage(Message incomingMessage) {
		String chatIdWithUser = incomingMessage.getChatId().toString();
		String messageFromUser = incomingMessage.getText();
		String textMessage = getAnswer(messageFromUser);
		return new SendMessage(chatIdWithUser, textMessage);
	}

	private String getAnswer(String messageFromUser) {
		switch (messageFromUser) {
			case "/start":
				return messages.getVariable("c-start");
			case "/schedule":
				return messages.getVariable("c-schedule");
			case "/currentPresentation":
				return messages.getVariable("c-currentPresentation");
			case "/getInformation":
				return messages.getVariable("c-getInformation");
			case "/help":
				return messages.getVariable("c-help");
			default:
				return messages.getVariable("c-error");
		}
	}

	@Override
	public String getBotUsername() {
		return privateVar.getVariable("username");
	}

	@Override
	public String getBotToken() {
		return privateVar.getVariable("token");
	}
}
