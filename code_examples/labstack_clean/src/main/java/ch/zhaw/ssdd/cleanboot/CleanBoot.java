package ch.zhaw.ssdd.cleanboot;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import ch.zhaw.ssdd.cleanboot.adapter.out.csv.CSVComponentAdapter;
import ch.zhaw.ssdd.cleanboot.adapter.out.relational.RelationalComponentAdapter;
import ch.zhaw.ssdd.cleanboot.domain.model.Component;
import lombok.AllArgsConstructor;

@SpringBootApplication
@AllArgsConstructor
public class CleanBoot implements CommandLineRunner {

        private RelationalComponentAdapter componentAdapter;
        private CSVComponentAdapter csvComponentAdapter;
        public static void main(String[] args) {
                SpringApplication.run(CleanBoot.class, args);
        }

        @Override
        public void run(String... args) throws Exception {

                ClassPathResource resource = new ClassPathResource("Partlist.csv");

                List<Component> components = csvComponentAdapter.loadAll(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
                
                for(Component c : components) {
                        componentAdapter.persistNewCompnent(c);
                }
        }
}