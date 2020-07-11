package com.grokonez.pdfbox;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@SpringBootApplication
public class PDFCombinatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(PDFCombinatorApplication.class, args);
    }

    @Bean
    public Logger logger() {
        return log;
    }

//    @Bean
//    public PDFCombinator getPdfCombinatorBean() {
//        return new PDFCombinator();
//    }

}

@Component
class CommandLineAppStartupRunner implements CommandLineRunner {
    @Autowired
    private static PDFCombinator pdfCombinator;

    @Override
    public void run(String... args) throws Exception {
        pdfCombinator.setOriginFolderAndRun();
    }
}
