package com.language.java.file.infosys;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import com.sun.xml.internal.xsom.impl.scd.Iterators.Map;

public class DataOpertion {

	@SuppressWarnings("unused")
	private static HashMap<String, String> readFileContent() {
		long startTime=System.currentTimeMillis();
		
		HashMap<String, String> map = new HashMap<String, String>();

		final String methodName = "readFileContent";
		final StringBuffer buffer = new StringBuffer();
		try {
			final FileInputStream inputStream = new FileInputStream(
					"C:\\Users\\zhangkeh\\Desktop\\Person\\Coding\\proData.csv");
			final BufferedReader reader = new BufferedReader(
					new InputStreamReader(inputStream));
			String line = reader.readLine();
			int i = 1;
			while (line != null) {
				String lineData = line;
				System.out.println(i + "=" + line);
				String[] userPhone = lineData.split(",");
				if (userPhone.length > 1) {
					map.put(userPhone[0], userPhone[1]);
				}

				buffer.append(line);
				line = reader.readLine();
				i++;
			}
			inputStream.close();
		} catch (final FileNotFoundException e) {
			System.out.println("{}(): FileNotFoundException caught: {}"
					+ methodName);
		} catch (final IOException e) {
			System.out.println("{}(): IOException caught: {}" + methodName);
		}

		final String deviceDetail = buffer.toString();
		// System.out.println("result: " + deviceDetail);
		long endTime=System.currentTimeMillis();
		System.out.println("user time:"+((endTime-startTime)/1000.0)+"s");
		return map;
	}

	public static void main(String[] args) {
		
		HashMap<String, String> map = readFileContent();
		System.out.println(map.size());
		System.out.println("phone " + map.get("ab"));
		
		// testMap1();

		// loopMap();
	}

	public static void testMap1() {
		HashMap<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("abc", "123");
		dataMap.put("abcd", "123");
		dataMap.put("abcde", "1233");
		dataMap.put("abcdee", "1233");
		dataMap.put("abcdeegh", "1233");
		dataMap.put("abcdeeghi", "1233");
		dataMap.put("abcdeeghie", "1233");
		dataMap.put("abcdef", "1233456");
		dataMap.put("abcdefg", "12334567");

		TreeMap<String, String> result = new TreeMap<String, String>();

		/**
		 * map order by/map sort
		 */
		Object[] unsort_key = dataMap.keySet().toArray();
		Arrays.sort(unsort_key);

		for (int i = 0; i < unsort_key.length; i++) {
			result.put(unsort_key[i].toString(), dataMap.get(unsort_key[i]));
		}

		for (String key : result.keySet()) {
			String value = result.get(key);
			System.out.println(key + ":" + value);
		}

		HashMap<String, String> newmap = new HashMap<String, String>();
		newmap.putAll(dataMap);

		System.out.println("****** get same keys for value is same ****\n");

		Set<String> kset = dataMap.keySet();
		Set<String> newKset = newmap.keySet();

		Iterator<Entry<String, String>> it = newmap.entrySet().iterator();

		for (String key : kset) {
			int i = 1;
			String value = dataMap.get(key);
			System.out.println("value:" + value);
			System.out.println("key=" + key);
			// for (String key2 : newKset) {
			// if (value.equals(newmap.get(key2)) && !key.equals(key2)) {
			// System.out.println("key=" + key2);
			//
			// i++;
			// }
			// }

			while (it.hasNext()) {
				Entry<String, String> entry = it.next();
				String key2 = entry.getKey();
				if (value.equals(newmap.get(key2)) && !key.equals(key2)) {
					System.out.println("key=" + key2);
					// it.remove(); // OK
				}
			}
			System.out.println("same value key size is " + i + "\n");
		}
	}

	public static void loopMap() {

		HashMap<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("abc", "123");
		dataMap.put("abcd", "123");
		dataMap.put("abcde", "1233");
		dataMap.put("abcdee", "1233");
		dataMap.put("abcdeegh", "1233");
		dataMap.put("abcdeeghi", "1233");
		dataMap.put("abcdeeghie", "1233");
		dataMap.put("abcdef", "1233456");
		dataMap.put("abcdefg", "12334567");
		Iterator<Entry<String, String>> it = dataMap.entrySet().iterator();

		Iterator<Entry<String, String>> it2 = dataMap.entrySet().iterator();

		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			String key = entry.getKey();
			String value = dataMap.get(key);
			System.out.println("value:" + value);
			System.out.println("key:" + key);

			it2 = dataMap.entrySet().iterator();
			while (it2.hasNext()) {
				Entry<String, String> entry2 = it2.next();
				String key2 = entry2.getKey();
				if (value.equals(dataMap.get(key2)) && !key.equals(key2)) {
					System.out.println("key2:" + key2);
				}
				it2.remove();
			}

			System.out.println("");
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	public static void testMap2() {
		HashMap map = new HashMap();
		map.put("1", "11");
		map.put("2", "22");
		map.put("3", "33");
		map.put("4", "44");
		map.put("5", "55");
		map.put("6", "66");
		map.put("7", "77");
		int num = 0;
		String key;
		String value;
		Iterator it1 = map.keySet().iterator();
		Iterator it2 = map.entrySet().iterator();

		while (it2.hasNext()) {
			num++;
			value = ((Object) it2.next()).toString();
			if ("55".equals(value))
				break;
		}

		System.out.println("num = " + num);
		while (it1.hasNext()) {
			num--;
			value = (String) it1.next();
			if (num == 0) {
				System.out.println("value = " + value);
				break;
			}
		}
	}
}
