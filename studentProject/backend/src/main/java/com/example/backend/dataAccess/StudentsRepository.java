
package com.example.backend.dataAccess;

import com.example.backend.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface StudentsRepository extends JpaRepository<Student, Integer> {

    /**
     * Öğrenci ismi, soyismi veya numarasına göre BAŞLANGICI eşleşen arama yapar
     * Büyük/küçük harf duyarsız, baştaki boşlukları temizler
     * @param searchTerm Arama terimi
     * @return Öğrenci listesi
     */
    @Query("SELECT s FROM students s WHERE " +
       "LOWER(TRIM(s.name)) LIKE LOWER(CONCAT(TRIM(:searchTerm), '%')) OR " +
       "LOWER(TRIM(s.surname)) LIKE LOWER(CONCAT(TRIM(:searchTerm), '%')) OR " +
       "TRIM(CAST(s.number AS string)) LIKE CONCAT(TRIM(:searchTerm), '%')")
    List<Student> findByNameOrSurnameOrNumberStartsWithIgnoreCase(@Param("searchTerm") String searchTerm);

    /**
     * Öğrenci numarasına göre arama yapar
     * @param number Öğrenci numarası
     * @return Öğrenci listesi (boş liste döner eğer bulunamazsa)
     */
    List<Student> findByNumber(String number);

}