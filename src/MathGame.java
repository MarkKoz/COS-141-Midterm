import java.io.*;
import java.util.*;

public class MathGame {

	public static void main(String[] args) {
		credits();
		String name = getName(); // If only I could use global vars...
		Stats.save(name, new String[]{});
		menu(name);
    }

	private static void credits() {
		System.out.println("▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓" +
				"▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓" +
				"▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓\n" +
				"▓   _____ _           ___  ___      _   _       _____        " +
				"                ▓\n" +
				"▓  |_   _| |          |  \\/  |     | | | |     |  __ \\     " +
				"                  ▓\n" +
				"▓    | | | |__   ___  | .  . | __ _| |_| |__   | |  \\/ __ _ " +
				"_ __ ___   ___   ▓\n" +
				"▓    | | | '_ \\ / _ \\ | |\\/| |/ _` | __| '_ \\  | | __ / " +
				"_` | '_ ` _ \\ / _ \\  ▓\n" +
				"▓    | | | | | |  __/ | |  | | (_| | |_| | | | | |_\\ \\ (_| " +
				"| | | | | |  __/  ▓\n" +
				"▓    \\_/ |_| |_|\\___| \\_|  |_/\\__,_|\\__|_| |_|  " +
				"\\____/\\__,_|_| |_| |_|\\___|  ▓\n" +
				"▓                                                            " +
				"                ▓\n" +
				"▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓" +
				"▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓" +
				"▓▓▓▓▓▓▓▓\n" +
				"▓▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓" +
				" By Mark Kozlov " +
				"▓▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓\n" +
				"▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓" +
				"▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓" +
				"▓▓▓▓▓▓▓▓\n");
    }

    private static void menu(String name) {

	    printMessage(new String[] {"Make a selection by typing a " +
			    "character in the left column", "and press [ENTER]."}, "bold");

	    System.out.println("                       " +
			    "╔═════╦════════════════════════╗\n" +
			    "                       " +
			    "║  1  ║ Addition Problem       ║\n" +
			    "                       " +
			    "╠═════╬════════════════════════╣\n" +
			    "                       " +
			    "║  2  ║ Subtraction Problem    ║\n" +
			    "                       " +
			    "╠═════╬════════════════════════╣\n" +
			    "                       " +
			    "║  3  ║ Multiplication Problem ║\n" +
			    "                       " +
			    "╠═════╬════════════════════════╣\n" +
			    "                       " +
			    "║  4  ║ Division Problem       ║\n" +
			    "                       " +
			    "╠═════╬════════════════════════╣\n" +
			    "                       " +
			    "║  5  ║ Statistics             ║\n" +
			    "                       " +
			    "╠═════╬════════════════════════╣\n" +
			    "                       " +
			    "║ q/Q ║ Quit                   ║\n" +
			    "                       " +
			    "╚═════╩════════════════════════╝\n");

	    menuSelection(name);
    }

	private static void menuSelection(String name) {
		Scanner scanner = new Scanner(System.in).useLocale(Locale.getDefault());
		boolean retry;
		int answer;

		do {
			switch (scanner.nextLine()) {
				case "q":
				case "Q":
					Stats.save(name, Stats.get(name, true));
					retry = false;
					break;
				case "1":
					answer = Problem.genAdd();
					Problem.checkAnswer(Problem.getAnswer(), answer, name);
					retry = false;
					break;
				case "2":
					answer = Problem.genSub();
					Problem.checkAnswer(Problem.getAnswer(), answer, name);
					retry = false;
					break;
				case "3":
					answer = Problem.genMult();
					Problem.checkAnswer(Problem.getAnswer(), answer, name);
					retry = false;
					break;
				case "4":
					answer = Problem.genDiv();
					Problem.checkAnswer(Problem.getAnswer(), answer, name);
					retry = false;
					break;
				case "5":
					Stats.print(name);
					retry = false;
					break;
				default:
					printMessage("That selection is invalid, please try again" +
							".", "light");
					retry = true;
					break;
			}
		} while (retry);
	}

	private static String getName() {
		Scanner scanner = new Scanner(System.in).useLocale(Locale.getDefault());
		String inputName;

		printMessage("Type in your name and press [ENTER].", "bold");
		do {
			inputName = scanner.nextLine();
		} while (!checkInput(inputName, "name"));

		return inputName;
	}

	public static boolean checkInput(String input, String type) {
		switch (type) {
			case "name":
				if (input.isEmpty()) {
					printMessage("The name given is blank, please try again" +
							".", "light");
					return false;
				}
				if (input.contains(" ")) {
					printMessage("Spaces are not allowed, please try again" +
							".", "light");
					return false;
				}
				for (int index = 0; index < input.length(); index ++) {
					if(!Character.isLetter(input.charAt(index))) {
						printMessage("The name given contains invalid " +
								"characters, please try again.", "light");
						return false;
					}
				}
				return true;
			case "answer":
				if (input.isEmpty()) {
					printMessage("The answer given is blank, please try again" +
							".", "light");
					return false;
				}
				if (input.contains(" ")) {
					printMessage("Spaces are not allowed, please try again" +
							".", "light");
					return false;
				}
				if (input.contains("-")) {
					printMessage("All answers are POSITIVE integers, please " +
							"try again.", "light");
					return false;
				}

				for (int index = 0; index < input.length(); index ++) {
					if(!Character.isDigit(input.charAt(index))) {
						printMessage("The answer given is not an integer, " +
								"please try again.", "light");
						return false;
					}
				}
				return true;
			default:
				printMessage("Something went wrong, please try again.", "light");
				return false;
		}
	}

	public static void Continue(String name, boolean toMenu) {
		Scanner scanner = new Scanner(System.in).useLocale(Locale.getDefault());

		printMessage("Type [y/Y] to continue or any other key to exit. Then " +
				"press [ENTER].", "light");

		String input = scanner.nextLine().toLowerCase();

		if (input.equals("y") && toMenu) {
			menu(name);
		} else if (!input.equals("y") && !toMenu) {
			System.exit(0);
		}
	}

	public static void printMessage(String string, String style) {
		char blockPrimary = '▓';
		char blockSecondary = '▒';

		final int max = 78;
		int length = string.length();
		int appendNum = (max - length - 6);
		int index = 1;

		StringBuilder output = new StringBuilder(max);
		StringBuilder bar = new StringBuilder(max);

		if (style.equals("light")) { // Better if I could use optional param
			blockPrimary = '▒';
			blockSecondary = '░';
		}

		output.append(blockPrimary);

		while (index < (appendNum / 2) + 1) {
			output.insert(index, blockSecondary);
			index ++;
		}

		output.insert(index, blockPrimary + " ");
		index += 2;

		output.insert(index, string);
		index += length;

		output.insert(index, " " + blockPrimary);
		index += 2;

		while (index < (max - 1)) {
			output.insert(index, blockSecondary);
			index ++;
		}

		output.insert(index, blockPrimary);

		for(int count = 0; count < 78; count ++) {
			bar.append(blockPrimary);
		}
		System.out.println("\n" + bar);
		System.out.println(output);
		System.out.println(bar + "\n");
	}

	public static void printMessage(String[] strings, String style) {
		final int max = 78;
		StringBuilder bar = new StringBuilder(max);
		char blockPrimary = '▓';
		char blockSecondary = '▒';
		int length = 0;

		if (style.equals("light")) { // Better if I could use optional param
			blockPrimary = '▒';
			blockSecondary = '░';
		}

		for(int count = 0; count < max; count ++) {
			bar.append(blockPrimary);
		}

		System.out.println("\n" + bar);

		for (String line : strings) {
			if (line.length() > length) {
				length = line.length();
			}
		}

		for (String line : strings) {
			int length2 = line.length();
			int appendNum = (max - length - 6);
			int appendNum2 = (max - length2 - 6);
			int appendNum3;
			int index = 1;

			StringBuilder output = new StringBuilder(max);

			output.append(blockPrimary);

			while (index < (appendNum / 2) + 1) {
				output.insert(index, blockSecondary);
				index ++;
			}

			if (length % 2 == 0) {
				appendNum3 = index - 1;
			} else {
				appendNum3 = index;
			}

			output.insert(index, blockPrimary + " ");
			index += 2;

			if (length2 < length) {
				while (index < (appendNum2 / 2) + 3) {
					output.insert(index, " ");
					index ++;
				}
			}

			output.insert(index, line);
			if (length2 < length) {
				index += length2;
			} else {
				index += length;
			}

			if (length2 < length) {
				while (index < (max - appendNum3 - 3)) {
					output.insert(index, " ");
					index ++;
				}
			}

			output.insert(index, " " + blockPrimary);
			index += 2;

			while (index < (max - 1)) {
				output.insert(index, blockSecondary);
				index ++;
			}

			output.insert(index, blockPrimary);

			System.out.println(output);
		}

		System.out.println(bar + "\n");
	}

	public static void printErrors(String name, List<String> errors) {
		errors.add(0, "Error parsing the statistics file!");
		String errorsArr[] = errors.toArray(new String[errors.size()]);
		printMessage(errorsArr, "light");
		Continue(name, false);
	}

	public static void printTable(String[][] rows) {
		final int max = 78;

		for (String[] row:rows) {
			/*for (String element:row) {
				System.out.println(element);
			}*/
		}
	}
}

class Stats {

	public static String[] get(String name, boolean fileExists) {
		File statsFile = new File (name + ".txt");
		StringBuilder builderName;
		StringBuilder builderCorrect;
		StringBuilder builderWrong;
		StringBuilder builderEarnings;
		String statName = name;
		String statCorrect = "0";
		String statWrong = "0";
		String statEarnings = "0";
		List<String> errors = new ArrayList<>();

		try (Scanner inputFile = new Scanner(statsFile)) {

			if (inputFile.hasNextLine()) {
				builderName = new StringBuilder(inputFile.nextLine());

				if (builderName.substring(0, 5).equals("NAME=")) {
					builderName.replace(0, 5, "");
					statName = builderName.toString();
				} else {
					errors.add("The NAME field is invalid and will be " +
							"reset to the default of:");
					errors.add(statName);
				}
			} else {
				errors.add("The NAME field is invalid and will be " +
						"reset to the default of:");
				errors.add(statName);
			}

			if (inputFile.hasNextLine()) {
				builderCorrect = new StringBuilder(inputFile.nextLine());

				if (builderCorrect.substring(0, 8).equals("CORRECT=")) {
					builderCorrect.replace(0, 8, "");
					statCorrect = builderCorrect.toString();
				} else {
					errors.add("The CORRECT field is invalid and will be " +
							"reset to the default of: " + statCorrect);
				}
			} else {
				errors.add("The CORRECT field is invalid and will be " +
						"reset to the default of: " + statCorrect);
			}

			if (inputFile.hasNextLine()) {
				builderWrong = new StringBuilder(inputFile.nextLine());

				if (builderWrong.substring(0, 6).equals("WRONG=")) {
					builderWrong.replace(0, 6, "");
					statWrong = builderWrong.toString();
				} else {
					errors.add("The WRONG field is invalid and will be " +
							"reset to the default of: " + statWrong);
				}
			} else {
				errors.add("The WRONG field is invalid and will be " +
						"reset to the default of: " + statWrong);
			}

			if (inputFile.hasNextLine()) {
				builderEarnings = new StringBuilder(inputFile.nextLine());

				if (builderEarnings.substring(0, 9).equals("EARNINGS=")) {
					builderEarnings.replace(0, 9, "");
					statEarnings = builderEarnings.toString();
				} else {
					errors.add("The EARNINGS field is invalid and will be " +
							"reset to the default of: " + statEarnings);
				}
			} else {
				errors.add("The EARNINGS field is invalid and will be " +
						"reset to the default of: " + statEarnings);
			}

			if (!errors.isEmpty() && fileExists) {
				MathGame.printErrors(name, errors);
			}

			inputFile.close();

			return new String[]{statName, statCorrect, statWrong, statEarnings};
		} catch (FileNotFoundException e) {
			MathGame.printMessage(new String[]{"The statistics file could " +
					"not be accessed.", "Try restarting the game to recreate " +
					"the file", "or launching the game in another directory."},
					"bold");
			MathGame.Continue(name, true);
			// e.printStackTrace();
		}
		return null;
	}

	public static String[] update(String name, boolean correct) {
		String statName = name;
		int statCorrect = 0;
		int statWrong = 0;
		double statEarnings = 0;
		String[] stats = get(name, true);
		List<String> errors = new ArrayList<>();

		if (stats != null) {
			statName = stats[0];
			try {
				statCorrect = Integer.parseInt(stats[1]);
			} catch (NumberFormatException e) {
				errors.add("The value for field CORRECT is invalid.");
			}
			try {
				statWrong = Integer.parseInt(stats[2]);
			} catch (NumberFormatException e) {
				errors.add("The value for field WRONG is invalid.");
			}
			try {
				statEarnings = Double.parseDouble(stats[3]);
			} catch (NumberFormatException e) {
				errors.add("The value for field EARNINGS is invalid.");

			}
			if (!errors.isEmpty()) {
				errors.add("Correct the value(s) manually or delete the file " +
						"so that it can be remade.");
				MathGame.printErrors(name, errors);
			}
		}

		if (correct) {
			statCorrect ++;
			statEarnings += 0.05;
		} else {
			statWrong ++;
			statEarnings -= 0.03;
			/*if (statEarnings < 0) { // Disallow negative earnings
				statEarnings = 0;
			}*/
		}
		return new String[]{statName, Integer.toString(statCorrect), Integer
			.toString(statWrong), Double.toString(statEarnings)};
	}

	public static void save(String name, String[] stats) {
		String fileName = name + ".txt";

		File statsFile = new File (fileName);
		PrintWriter pw;

		try {
			if (!statsFile.exists()) {
				pw = new PrintWriter(fileName);
				stats = get(name, false);
			} else {
				if (stats.length == 0) {
					stats = get(name, true);
				}
				FileWriter fw = new FileWriter(fileName);
				pw = new PrintWriter(fw);
			}

			if (stats !=null) {
				pw.println("NAME=" + stats[0]);
				pw.println("CORRECT=" + stats[1]);
				pw.println("WRONG=" + stats[2]);
				pw.println("EARNINGS=" + stats[3]);

				pw.close();
			}
		} catch (FileNotFoundException e) {
			MathGame.printMessage(new String[]{"The statistics file could" +
					" not be accessed.", "Try restarting the game to " +
					"recreate the file", "or launching the game in another" +
					" directory."}, "bold");
			MathGame.Continue(name, true);
			// e.printStackTrace();
		} catch (IOException e) {
			MathGame.printMessage(new String[]{"The statistics file could" +
				" not be accessed or created.", "Try launching the game in " +
				"another directory."}, "bold");
			MathGame.Continue(name, true);
			// e.printStackTrace();
		}
	}

	public static void print(String name) {
		String[] stats = get(name, true);

		if (stats != null) {
			/*MathGame.printMessage(new String[]{
							"Name: " + stats[0],
							"Correct: " + stats[1],
							"Wrong: " + stats[2],
							"Earnings: " + stats[3]},
					"bold");*/
			String[] row0 = {"Name", stats[0]};
			String[] row1 = {"Correct", stats[1]};
			String[] row2 = {"Wrong", stats[2]};
			String[] row3 = {"Earnings", stats[3]};
			String[][] rows = {row0, row1, row2, row3};

			MathGame.printTable(rows);
			MathGame.Continue(name, true);
		}
	}
}

class Problem {
	public static int genAdd() {
		Random rand = new Random();

		int first = rand.nextInt(11);
		int second = rand.nextInt(11);
		String problemStr = first + " + " + second + " = ?";

		MathGame.printMessage(problemStr, "bold");

		return first + second;
	}

	public static int genSub() {
		Random rand = new Random();

		int first =  rand.nextInt(11);
		int second = rand.nextInt(first - 1) + 1;
		String problemStr = first + " - " + second + " = ?";

		MathGame.printMessage(problemStr, "bold");

		return first - second;
	}

	public static int genMult() {
		Random rand = new Random();

		int first = rand.nextInt(11);
		int second = rand.nextInt(11);
		String problemStr = first + " × " + second + " = ?";

		MathGame.printMessage(problemStr, "bold");

		return first * second;
	}

	public static int genDiv() {
		Random rand = new Random();

		int first = rand.nextInt(101);
		int second;

		do {
			second = rand.nextInt(10) + 1;
		} while (first % second != 0);

		String problemStr = first + " ÷ " + second + " = ?";

		MathGame.printMessage(problemStr, "bold");

		return first / second;
	}

	public static int getAnswer() {
		Scanner scanner = new Scanner(System.in).useLocale(Locale.getDefault());
		String input;

		do {
			input = scanner.nextLine();
		} while (!MathGame.checkInput(input, "answer"));

		return Integer.parseInt(input);
	}

	public static void checkAnswer(int input, int answer, String name) {
		if (input == answer) {
			MathGame.printMessage("Correct!", "bold");
			Stats.save(name, Stats.update(name, true));
		} else {
			MathGame.printMessage("Wrong!", "bold");
			Stats.save(name, Stats.update(name, false));
		}

		MathGame.Continue(name, true);
	}
}