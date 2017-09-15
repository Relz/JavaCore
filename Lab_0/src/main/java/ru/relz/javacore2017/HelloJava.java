package ru.relz.javacore2017;

import java.io.File;
import java.util.Arrays;

class HelloJava {
	public static void main(String[] args) {
		String jrePath = System.getProperty("java.home");
		String jdkPath = (new File(jrePath)).getParentFile().getAbsolutePath();
		System.out.print("Hello, Java from ");
		Arrays.stream(args).forEach(arg -> System.out.print(String.format("%s ", arg)));
		System.out.println(
			String.format("%s %s %s %s",
				System.getProperty("os.name"),
				System.getProperty("os.version"),
				System.getProperty("java.version"),
				jdkPath
			)
		);
	}
}

