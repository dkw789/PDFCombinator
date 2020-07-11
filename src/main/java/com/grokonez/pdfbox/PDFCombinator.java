package com.grokonez.pdfbox;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
@Configuration
@PropertySource("classpath:application.yml")
public class PDFCombinator {

    @Value("${origin.folder}")
    private static String originFolder;

    @Value("${output.folder}")
    private static String outputFolder;
    @Value("${keyword.searchFor}")
    private static String searchFor;

    private static void walkDirTreeAndSearchAndCombine(String rootFolder, String search) throws Exception {
        PDFMergerUtility ut = new PDFMergerUtility();

        Files.walk(Paths.get(rootFolder), FileVisitOption.FOLLOW_LINKS)
                .filter(t -> {
                    return t.toString().contains(search);
                })
                .forEach(path -> {
                    log.info("Filtered search : {}", path);
                    log.info("path : {} ", path.toAbsolutePath().toString());

                    try {
                        ut.addSource(path.toAbsolutePath().toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                    }
                });

        ut.setDestinationFileName(outputFolder);
        ut.mergeDocuments();
    }

    public void setOriginFolderAndRun() throws Exception {
//    public static void main(String[] args) throws Exception {


        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(PDFCombinator.class);
        ConfigurableEnvironment env = context.getEnvironment();
        System.out.println("originFolder value is " + originFolder);

        //printing all sources
        System.out.println(env.getPropertySources());
//        Properties prop = new Properties();
//        prop.load(PDFCombinator.class.getResourceAsStream("application.yml"));

        log.info("originFolder {}", originFolder);
        Path currentRelativePath = Paths.get(originFolder);
        String rootFolder = currentRelativePath.toAbsolutePath().toString();
        log.info("Current relative path is: {}", rootFolder);

        walkDirTreeAndSearchAndCombine(rootFolder, searchFor);
    }

}
