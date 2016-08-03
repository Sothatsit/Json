package net.sothatsit.json;

import net.sothatsit.json.data.JsonObject;
import net.sothatsit.json.stream.InputReadStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class Benchmark {

    public static void main(String[] args) {
        if(args.length == 0) {
            System.out.println("Invalid arguments > Expected json files to benchmark");
            return;
        }

        for(String file : args) {
            benchmarkFile(file);
        }
    }

    public static void benchmarkFile(String fileName) {
        File file = new File(fileName);

        if(!file.exists()) {
            System.out.println("File \"" + fileName + "\" does not exist");
            return;
        }

        if(file.isDirectory()) {
            System.out.println("File \"" + fileName + "\" is a directory");
            return;
        }

        System.out.println("Benchmarking File \"" + fileName + "\"");

        BigDecimal jsonTime = BigDecimal.ZERO;
        BigDecimal gsonTime = BigDecimal.ZERO;

        for(int i=0; i < 10; i++) {
            try {
                jsonTime = jsonTime.add(benchmarkFileParse(file));
            } catch (IOException e) {
                System.out.println("Exception benchmarking Json");
                e.printStackTrace();
            }

            try {
                gsonTime = gsonTime.add(benchmarkGsonFileParse(file));
            } catch (IOException e) {
                System.out.println("Exception benchmarking Gson");
                e.printStackTrace();
            }
        }

        System.out.println("Json : " + jsonTime + "ms");
        System.out.println("Gson : " + gsonTime + "ms");
    }

    public static BigDecimal benchmarkGsonFileParse(File file) throws IOException {
        try {
            long start = System.nanoTime();

            try (FileReader fr = new FileReader(file)) {
                com.google.gson.JsonElement object = new com.google.gson.JsonParser().parse(fr);
            }

            long end = System.nanoTime();

            return new BigDecimal(BigInteger.valueOf(end - start), 6).stripTrailingZeros();
        } catch(RuntimeException e) {
            if(e.getCause() instanceof IOException) {
                throw (IOException) e.getCause();
            }

            throw e;
        }
    }

    public static BigDecimal benchmarkFileParse(File file) throws IOException {
        try {
            long start = System.nanoTime();

            try (FileInputStream fis = new FileInputStream(file)) {
                JsonObject object = new JsonParser().parseObject(new InputReadStream(fis));
            }

            long end = System.nanoTime();

            return new BigDecimal(BigInteger.valueOf(end - start), 6).stripTrailingZeros();
        } catch(RuntimeException e) {
            if(e.getCause() instanceof IOException) {
                throw (IOException) e.getCause();
            }

            throw e;
        }
    }

}
