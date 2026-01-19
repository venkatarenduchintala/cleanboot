package ch.zhaw.ssdd.bookstack;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.AllArgsConstructor;

@SpringBootApplication
@AllArgsConstructor
public class BookStack implements CommandLineRunner {
        public static void main(String[] args) {
                SpringApplication.run(BookStack.class, args);
        }

        @Override
        public void run(String... args) throws Exception {

        }
}
