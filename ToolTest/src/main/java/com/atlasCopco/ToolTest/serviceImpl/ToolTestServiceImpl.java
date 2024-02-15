package com.atlasCopco.ToolTest.serviceImpl;

import java.io.*;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ToolTestServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(ToolTestServiceImpl.class);

    @Value("${input.directory.path}") // Typo: should be @Value("${input.directory.path}")
    private String inputDirectoryPath;

    @Value("${output.directory.path}") // Typo: should be @Value("${output.directory.path}")
    private String outputDirectoryPath;

    // Method to encrypt Excel files in the input directory
    public void encryptExcelFiles(String username, String password) {
        try (Stream<Path> paths = Files.walk(Paths.get(inputDirectoryPath))) {
            // Filter and process each regular file with ".xlsx" extension
            paths.filter(Files::isRegularFile)
            .filter(path -> path.toString().endsWith(".xlsx"))
                    .forEach(path -> encryptAndMoveFile(path, username, password));
        } catch (IOException e) {
            logger.error("Error reading files from directory: {}", e.getMessage(), e);
        }
    }

    // Method to encrypt and move a specific Excel file
    private void encryptAndMoveFile(Path inputFile, String username, String password) {
        File outputFile = new File(outputDirectoryPath + "/" + inputFile.getFileName().toString());
        FileLock lock = null;

        try (RandomAccessFile raf = new RandomAccessFile(inputFile.toFile(), "rw")) {
            // Acquire a file lock to prevent concurrent access issues
            lock = raf.getChannel().lock();

            try (InputStream fis = new FileInputStream(raf.getFD());
                 Workbook workbook = new XSSFWorkbook(fis);
                 POIFSFileSystem fs = new POIFSFileSystem();
                 OutputStream fos = new FileOutputStream(outputFile)) {

                // Add a delay to avoid potential race conditions
                Thread.sleep(1000);

                // Set up encryption information
                EncryptionInfo info = new EncryptionInfo(EncryptionMode.agile);
                Encryptor encryptor = info.getEncryptor();
                encryptor.confirmPassword(username + password);

                try (OutputStream os = encryptor.getDataStream(fs)) {
                    workbook.write(os);
                }

                // Close streams
                fis.close();
                fs.writeFilesystem(fos);
                fos.close();

            } catch (FileNotFoundException e) {
                logger.error("File not found exception: {}", e.getMessage(), e);
            } catch (IOException e) {
                logger.info("Error reading or writing file: {}", e.getMessage(), e);
            } catch (InterruptedException e) {
                logger.info("Thread interrupted: {}", e.getMessage(), e);
            } catch (Exception e) {
                logger.info("Error encrypting file: {}", e.getMessage(), e);
            }

            // Release the lock and delete the input file after processing
            if (lock != null && lock.isValid()) {
                lock.release();
            }
            Files.delete(inputFile);

        } catch (IOException e) {
            logger.error("Error locking or deleting files: {}", e.getMessage(), e);
        }
    }
}
