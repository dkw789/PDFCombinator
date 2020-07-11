package com.grokonez.pdfbox;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class PDFCombinatorStandalone {

    private static final String originFolder = "/Users/yin.lam.tze.ting/Desktop/samples";
    private static final String outputFolder = "/Users/yin.lam.tze.ting/Desktop/Merged_final.pdf";
    private static final String searchFor = "456.pdf";

    public static void main(String[] args) throws Exception {
//        log.info("originFolder {}", originFolder);
        Path currentRelativePath = Paths.get(originFolder);
        String rootFolder = currentRelativePath.toAbsolutePath().toString();
        log.info("Current relative path is: {}", rootFolder);

        walkDirTreeAndSearchAndCombine(rootFolder, searchFor);
    }

    private static void walkDirTreeAndSearchAndCombine(String rootFolder, String search) throws Exception {
        PDFMergerUtility ut = new PDFMergerUtility();

        Files.walk(Paths.get(rootFolder), FileVisitOption.FOLLOW_LINKS)
                .filter(t -> {
                    return t.toString().contains(search);
                })
                .forEach(path -> {
                    log.info("Filtered search : {}", path);
//                    log.info("path : {} ", path.toAbsolutePath().toString());

                    try {
                        ut.addSource(path.toAbsolutePath().toString());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

        ut.setDestinationFileName(outputFolder);
        ut.mergeDocuments();
        log.info("Output Folder is: {}", outputFolder);
    }
}