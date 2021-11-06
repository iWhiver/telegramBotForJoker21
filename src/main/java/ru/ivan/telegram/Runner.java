package ru.ivan.telegram;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Runner {

	private final static YamlVariables messages = new YamlVariables("messages.yaml");

	public static void main(String[] args) {
		try {
			TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
			botsApi.registerBot(new JokerBot());
			System.out.println(messages.getVariable("l-start"));
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
}