/*
 * Copyright(c) Huawei Technologies Co, Ltd 2018-2019. All rights reserved
 */

package com.sct.springcloud.control;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Notes AddAnnotatedUtil.class
 * @author chenyang
 * @since 2019年11月14日 22:02:03
 */
public class AddAnnotatedUtil {

	private static final Logger LOG = LoggerFactory.getLogger(AddAnnotatedUtil.class);
	
	private static final String OBJECT_TYPE_CONSTANT = "STRING,OBJECT,BOOLEAN,INT,DATE,CRITERIA,STRINGBUILDER";

	private static final String CHANGE_ROW = System.lineSeparator();

	private static String FILE_PATH = "D:\\AddAnnotatedUtil.java";

	private static String className = null;	

	/**
	 * Notes main
	 * @author chenyang
	 * @param args args
	 * @return void
	 * @Date 2019年11月14日 22:02:03
	 */
	public static void main(String[] args) {
		BufferedReader br = null;
		try {
			File file = new File(FILE_PATH);
			if (file.isFile()) {
				br= new BufferedReader(new FileReader(FILE_PATH));
				readAndWriteData(br);
			} else {
				File[] fs = file.listFiles();
				for (File f : fs) {
					br= new BufferedReader(new FileReader(f.getPath()));
					readAndWriteData(br);
				}
			}
			LOG.info("Adding comments succeeded! ");
		}catch(IOException e) {
			LOG.info("An exception occurred when adding a comment: {}" + e.getMessage());
		}finally {
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					br = null;
				}
			}
		}
	}

	/**
	 * Notes readAndWriteData
	 * @author chenyang
	 * @param br br
	 * @return void
	 * @Date 2019年11月14日 22:02:03
	 */
	public static void readAndWriteData(BufferedReader br) throws IOException {
		String line = null;
		int i = 1;
		StringBuffer writeData = new StringBuffer();
		while ((line = br.readLine()) != null) {
			String str = line.trim();
			if (i<5) {
				i ++;
				writeData.append(line).append(CHANGE_ROW);
				continue;
			}
			//去掉原有注释
			if (str.startsWith ("/*") || str.startsWith ("*") || str.startsWith ("*/")){
				continue;
			}
			if (str.startsWith("public") || str.startsWith("protected")) {
				writeData.append(createAnnotated(line)).append(CHANGE_ROW);
			}
			writeData.append(line).append(CHANGE_ROW);
		}
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(FILE_PATH).getAbsoluteFile()));
		bw.write(writeData.toString());
		bw.close();
	}

	/**
	 * Notes createAnnotated
	 * @author chenyang
	 * @param line line
	 * @return Object
	 * @Date 2019年11月14日 22:02:03
	 */
	public static Object createAnnotated(String line) {
		String[] str1 = line.split(" ");
		List<String> list = new ArrayList<String>();
		for (String str : str1) {
			if(str !=null && !"".equals(str)) {
				list.add(str);
			}
		}
		StringBuilder sb = new StringBuilder();
		String blanks = line.contains("public") ? line.split("public")[0] : line.split("protected")[0];
		if (list.contains("class")) {
			createClassAnnotated(list, sb, blanks);
		} else if (className!=null && list.get(1).startsWith(className)){
			constructorAnnotated(list, sb, blanks);
		} else if (line.trim().startsWith("protected") && !line.trim().endsWith("{")) {
			constantAnnotated(list, sb, blanks);
		} else {
			methodAnnotated(list, sb, blanks);
		}
		return sb.toString();
	}


	/**
	 * Notes constructorAnnotated
	 * @author chenyang
	 * @param list list
	 * @param sb sb
	 * @param blanks blanks
	 * @return void
	 * @Date 2019年11月14日 22:02:03
	 */
	public static void constructorAnnotated(List<String> list, StringBuilder sb, String blanks) {
		List<String> parameters = list.subList(2, list.size() - 1);
		sb.append(blanks).append("/**").append(CHANGE_ROW);
		sb.append(blanks).append(" * Notes 构造器").append(CHANGE_ROW);
		sb.append(blanks).append(" * @author ").append("chenyang").append(CHANGE_ROW);
		if (parameters != null && parameters.size() != 0) {
			for (String parameter : parameters) {
				parameter = parameter.replace(",", "").replace(")", "").replace("{", "");
				if (parameter == null || "".equals(parameter) || OBJECT_TYPE_CONSTANT.contains(parameter.toUpperCase(Locale.ROOT))) {
					continue;
				}
				sb.append(blanks).append(" * @param ").append(parameter).append(" ").append(parameter).append(CHANGE_ROW);
			}
		}
		sb.append(blanks).append(" * @return ").append(list.get(1)).append(CHANGE_ROW);
		sb.append(blanks).append(" * @Date ").append(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date())).append(CHANGE_ROW);
		sb.append(blanks).append(" */");
	}

	/**
	 * Notes constantAnnotated
	 * @author chenyang
	 * @param list list
	 * @param sb sb
	 * @param blanks blanks
	 * @return void
	 * @Date 2019年11月14日 22:02:03
	 */
	public static void constantAnnotated(List<String> list, StringBuilder sb, String blanks) {
		sb.append(blanks).append("/**").append(CHANGE_ROW);
		sb.append(blanks).append(" *").append(list.get(list.size() - 1).replace(";", "")).append(CHANGE_ROW);
		sb.append(blanks).append(" */");

	}

	/**
	 * Notes methodAnnotated
	 * @author chenyang
	 * @param list list
	 * @param sb sb
	 * @param blanks blanks
	 * @return void
	 * @Date 2019年11月14日 22:02:03
	 */
	public static void methodAnnotated(List<String> list, StringBuilder sb, String blanks) {
		String methodName = "";
		int startIndex = 0;
		int endIndex = 0;
		boolean flag = true;
		for (int ii = 0; ii < list.size(); ii++) {
			String str = list.get(ii);
			if (str.contains("(") && flag) {
				methodName = str.split("\\(")[0];
				startIndex = ii;
				flag = false;
			}
			if (str.contains(")")) {
				endIndex = ii;
				break;
			}
		}
		List<String> parameters = list.get(startIndex).contains("()") ? new ArrayList<String>() : list.subList(startIndex + 1, endIndex + 1);
		sb.append(blanks).append("/**").append(CHANGE_ROW);
		sb.append(blanks).append(" * Notes ").append(methodName).append(CHANGE_ROW);
		sb.append(blanks).append(" * @author ").append("chenyang").append(CHANGE_ROW);
		if (parameters != null && parameters.size() != 0) {
			for (String parameter : parameters) {
				parameter = parameter.replace(",", "").replace(")", "").replace("{", "");
				if (parameter == null || "".equals(parameter) || OBJECT_TYPE_CONSTANT.contains(parameter.toUpperCase(Locale.ROOT))) {
					continue;
				}
				sb.append(blanks).append(" * @param ").append(parameter).append(" ").append(parameter).append(CHANGE_ROW);
			}
		}
		sb.append(blanks).append(" * @return ").append(list.get(startIndex - 1)).append(CHANGE_ROW);
		sb.append(blanks).append(" * @Date ").append(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date())).append(CHANGE_ROW);
		sb.append(blanks).append(" */");
	}

	/**
	 * Notes createClassAnnotated
	 * @author chenyang
	 * @param sb sb
	 * @param blanks blanks
	 * @return void
	 * @Date 2019年11月14日 22:02:03
	 */
	public static void createClassAnnotated(List<String>list, StringBuilder sb, String blanks) {
		int index = list.indexOf("class");
		className = list.get(index + 1);
		sb.append(blanks).append("/**").append(CHANGE_ROW);
		sb.append(blanks).append(" * Notes ").append(className).append(".class").append(CHANGE_ROW);
		sb.append(blanks).append(" * @author ").append("chenyang").append(CHANGE_ROW);
		sb.append(blanks).append(" * @since ").append(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date())).append(CHANGE_ROW);
		sb.append(blanks).append(" */");
	}

}
