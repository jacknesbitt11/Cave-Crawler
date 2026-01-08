package com.nesbot.cavecrawler;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReadCSVFile {
    int width = 0;
    int height = 0;
    int[][] map;

    public ReadCSVFile(String file_name){
        this.map = load_array(file_name);
    }
    //Used to read in the map csv files, the csv is read in column by column so that x and y can be used uniformly


    public int[][] load_array(String file_name) {
        List<List<Integer>> columns = new ArrayList<>();
        int expected_column_count = 0;
        int row_index = 0;
        InputStream input = getClass().getClassLoader().getResourceAsStream(file_name);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(input))) {
            String line;
            while ((line = br.readLine()) != null) {
                int[] row_values = parse(line);

                if (row_index == 0) {
                    expected_column_count = row_values.length;
                    this.width = expected_column_count;
                    for (int i = 0; i < expected_column_count; i++) {
                        columns.add(new ArrayList<>());
                    }
                }
                for (int col_index = 0; col_index < row_values.length; col_index++) {
                    if (col_index < expected_column_count) {
                        columns.get(col_index).add(row_values[col_index]);
                    }
                }
                row_index++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.height = row_index;
        int[][] result_columns_array = new int[columns.size()][];
        for (int i = 0; i < columns.size(); i++) {
            List<Integer> column_list = columns.get(i);
            result_columns_array[i] = column_list.stream()
                    .mapToInt(Integer::intValue)
                    .toArray();
        }

        return result_columns_array;
    }
    private static int[] parse(String line){
        String[] stringValues = line.split(",");
        try {
            return Arrays.stream(stringValues)
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .mapToInt(Integer::parseInt)
                    .toArray();
        } catch (NumberFormatException e) {
            return new int[0];
        }



    }




}
