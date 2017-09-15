package ru.relz.javacore2017;

class GetNetAddress {
	private static int bytesBlockCount = 4;

	public static void main(String[] args) {
		if (args.length < 2) {
			printlnError("usage: GetNetAddress <ip address> <network mask>");
		}
		byte[] ipAddress = new byte[bytesBlockCount];
		getArgument(args[0], ipAddress);
		byte[] networkMask = new byte[bytesBlockCount];
		getArgument(args[1], networkMask);
		for (int i = 0; i < bytesBlockCount; ++i) {
			System.out.print((ipAddress[i] & networkMask[i] & 0xFF) + ".");
		}
	}

	private static void printlnError(String msg) {
		System.out.println(String.format("Error: %s", msg));
		System.exit(1);
	}

	private static void getArgument(String arg, byte[] result) {
		String[] bytesStrs = arg.split("[.]");
		for (int i = 0; i < bytesStrs.length; ++i) {
			result[i] = (byte) Integer.parseInt(bytesStrs[i]);
		}
	}
}

