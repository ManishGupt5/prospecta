package com.masai.prospecta;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import com.masai.CalculateMathString;

public class CSVFileReaderAndGenerator {
	public static void main(String[] args) {
		String inputFile = "input.csv";
		String outputFile = "output.csv";

		try {
			BufferedReader br = new BufferedReader(new FileReader(inputFile));
			BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile, true));

			String[] lines = br.readLine().split(",");
			LinkedHashMap<String, String> data = new LinkedHashMap<String, String>();

			for (String line : lines) {
				String[] arr = lineToArray(line);
//				System.out.println(arr[0] + "=" + arr[1] + ",");
				if (arr[1].startsWith("=")) {
					String formula = arr[1].replace("=", "");
					arr[1] = formula;
					arr[1] = formula_calculation(data, formula);
				}

				data.put(arr[0], arr[1]);
			}

			List<String> list = data.keySet().stream().toList();
			for (int i = 0; i < list.size(); i++) {
				if (i == list.size() - 1) {
					bw.write(list.get(i) + ":" + data.get(list.get(i)));

				} else {
					bw.write(list.get(i) + ":" + data.get(list.get(i)) + ",");
				}
			}
			System.out.println(data);
		

			br.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String formula_calculation(Map<String, String> data, String formula) {

		for (String key : data.keySet()) {
			if (formula.contains(key)) {
				formula = formula.replace(key, data.get(key));
			}
		}

		return String.valueOf(CalculateMathString.calculateExpression(formula));
	}

	private static String[] lineToArray(String line) {
		Function<String, String[]> function = new Function<String, String[]>() {
			@Override
			public String[] apply(String t) {
				return t.trim().split(":");
			}
		};
		String[] arr = function.apply(line);
		arr[0] = arr[0].trim();
		arr[1] = arr[1].trim();

		return arr;
	}

}
