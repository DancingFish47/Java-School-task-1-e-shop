package com.rychkov.eshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class EshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(EshopApplication.class, args);
	}

}

//TODO MySQL security/injections
//TODO Сменить ддл на валидейт только в конце? При валидейте структура базы данных не изменяется.