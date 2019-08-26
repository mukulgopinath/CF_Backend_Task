package transactions;
import java.io.FileReader;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CurrencyfairTransactionsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyfairTransactionsApplication.class, args);
	}	
}
