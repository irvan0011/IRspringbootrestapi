package com.juaracoding.IRspringbootrestapi.util;


import com.juaracoding.IRspringbootrestapi.model.Barang;
import com.juaracoding.IRspringbootrestapi.model.KategoriBarang;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    public static boolean isCsv(MultipartFile multipartFile)
    {
        if(!"text/csv".equals(multipartFile.getContentType()))
        {
            return false;
        }
        return true;
    }

    public static List<KategoriBarang> csvToKategoriBarangData(InputStream inputStream) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        CSVParser csvParser = new CSVParser(bufferedReader,
                CSVFormat.DEFAULT.withFirstRecordAsHeader().
                        withIgnoreHeaderCase().
                        withTrim()
        );
        List<KategoriBarang> listKategoriBarang = new ArrayList<KategoriBarang>();
        try {

            Iterable<CSVRecord> iterRecords = csvParser.getRecords();
            KategoriBarang kategoriBarang;
            for (CSVRecord record : iterRecords) {
                kategoriBarang = new KategoriBarang();
                kategoriBarang.setNamaKategoriBarang(record.get("namaKategoriBarang"));
//                kategoriBarang.setDeskripsiKategori(record.get("DeskripsiKategori"));
                listKategoriBarang.add(kategoriBarang);
            }

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {

            if (!csvParser.isClosed()) {
                csvParser.close();
            }
            return listKategoriBarang;
        }
    }
    public static List<Barang> csvToKategoriBarang(InputStream inputStream) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        CSVParser csvParser = new CSVParser(bufferedReader,
                CSVFormat.DEFAULT.withFirstRecordAsHeader().
                        withIgnoreHeaderCase().
                        withTrim()
        );
        List<Barang> listBarang = new ArrayList<Barang>();
        try {

            Iterable<CSVRecord> iterRecords = csvParser.getRecords();
            Barang barang;
            for (CSVRecord record : iterRecords) {
                barang = new Barang();
                barang.setNamaBarang(record.get("namaBarang"));
                barang.setDeskripsi(record.get("deskripsi"));
                listBarang.add(barang);
            }

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {

            if (!csvParser.isClosed()) {
                csvParser.close();
            }
            return listBarang;
        }
    }
}