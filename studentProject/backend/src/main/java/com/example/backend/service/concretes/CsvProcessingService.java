package com.example.backend.service.concretes;

import com.example.backend.entities.Student;
import com.example.backend.dataAccess.StudentsRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CsvProcessingService {

    private final StudentsRepository studentsRepository;

    @Value("${csv.watch.directory:./csv-files}")
    private String csvWatchDirectory;

    public void processCsvFiles() {
        log.info("CSV dosyaları işleniyor...");
        
        try {
            // CSV dosyalarının bulunduğu klasörü kontrol et
            Path directory = Paths.get(csvWatchDirectory);
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
                log.info("CSV klasörü oluşturuldu: {}", csvWatchDirectory);
                return;
            }

            // .csv ile biten dosyaları bul
            File[] csvFiles = directory.toFile().listFiles((dir, name) -> 
                name.toLowerCase().endsWith(".csv") && !name.toLowerCase().endsWith(".done.csv"));

            if (csvFiles == null || csvFiles.length == 0) {
                log.info("İşlenecek CSV dosyası bulunamadı.");
                return;
            }

            for (File csvFile : csvFiles) {
                processCsvFile(csvFile);
            }

        } catch (Exception e) {
            log.error("CSV dosyaları işlenirken hata oluştu: {}", e.getMessage(), e);
        }
    }

    private void processCsvFile(File csvFile) {
        log.info("CSV dosyası işleniyor: {}", csvFile.getName());
        
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(csvFile));
            String[] header = reader.readNext(); // Header satırını oku
            if (header == null) {
                log.warn("CSV dosyası boş: {}", csvFile.getName());
                return;
            }

            List<Student> students = new ArrayList<>();
            String[] line;
            int lineNumber = 1;

            while ((line = reader.readNext()) != null) {
                lineNumber++;
                try {
                    Student student = parseStudentFromCsvLine(line, header);
                    if (student != null) {
                        students.add(student);
                    }
                } catch (Exception e) {
                    log.error("Satır {} işlenirken hata: {} - Dosya: {}", lineNumber, e.getMessage(), csvFile.getName());
                }
            }

            // Öğrencileri veritabanına kaydet (duplicate kontrolü ile)
            if (!students.isEmpty()) {
                int savedCount = 0;
                for (Student student : students) {
                    try {
                        // Öğrenci numarası zaten var mı kontrol et
                        if (studentsRepository.findByNumber(student.getNumber()).isEmpty()) {
                            studentsRepository.save(student);
                            savedCount++;
                        } else {
                            log.warn("Öğrenci numarası zaten mevcut: {}", student.getNumber());
                        }
                    } catch (Exception e) {
                        log.error("Öğrenci kaydedilirken hata: {} - Numara: {}", e.getMessage(), student.getNumber());
                    }
                }
                log.info("{} öğrenci kaydı başarıyla kaydedildi. Dosya: {}", savedCount, csvFile.getName());
            }

        } catch (IOException | CsvValidationException e) {
            log.error("CSV dosyası okunurken hata: {} - Dosya: {}", e.getMessage(), csvFile.getName(), e);
        } finally {
            // CSV reader'ı kapat
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error("CSV reader kapatılırken hata: {}", e.getMessage());
                }
            }
            
            // Dosya ismini güncelle (.done ekle) - reader kapandıktan sonra
            renameFileToDone(csvFile);
        }
    }

    private Student parseStudentFromCsvLine(String[] line, String[] header) {
        if (line.length < 3) {
            log.warn("Geçersiz satır formatı. En az 3 sütun gerekli.");
            return null;
        }

        try {
            Student student = new Student();
            
            // CSV sütunlarını header'a göre eşleştir
            for (int i = 0; i < header.length && i < line.length; i++) {
                String columnName = header[i].toLowerCase().trim();
                String value = line[i].trim();
                
                switch (columnName) {
                    case "name":
                    case "ad":
                    case "isim":
                        student.setName(value);
                        break;
                    case "surname":
                    case "soyad":
                    case "lastname":
                        student.setSurname(value);
                        break;
                    case "number":
                    case "numara":
                    case "student_number":
                    case "ogrenci_no":
                        student.setNumber(value);
                        break;
                }
            }

            // Gerekli alanların kontrolü
            if (student.getName() == null || student.getName().isEmpty() ||
                student.getSurname() == null || student.getSurname().isEmpty() ||
                student.getNumber() == null || student.getNumber().isEmpty()) {
                log.warn("Eksik öğrenci bilgileri. Satır atlandı.");
                return null;
            }

            return student;

        } catch (Exception e) {
            log.error("Öğrenci verisi parse edilirken hata: {}", e.getMessage());
            return null;
        }
    }

    private void renameFileToDone(File originalFile) {
        try {
            String originalName = originalFile.getName();
            String newName = originalName.replace(".csv", ".done.csv");
            Path newPath = originalFile.toPath().resolveSibling(newName);
            
            Files.move(originalFile.toPath(), newPath);
            log.info("Dosya yeniden adlandırıldı: {} -> {}", originalName, newName);
            
        } catch (IOException e) {
            log.error("Dosya yeniden adlandırılırken hata: {}", e.getMessage(), e);
        }
    }
} 