import java.io.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

/**
 * This class is the main class of the program, concerned with providing an
 * interface for the user by means of printing messages and retrieving inputs.
 *
 * @author          Mark Kozlov
 */
class MathGame {

	/**
	 * The main method of the program. Displays the credits, gets the user's
	 * name, generates a statistics file if it doesn't exist, and then displays
	 * the main menu.
	 *
	 * @param   args    the string array of command-line arguments
	 */
	public static void main(String[] args) {
		credits();
		String name = getName(); // Ideally this would be a public class field
		Stats.save(name, new String[]{});
		menu(name);
	}

	/**
	 * Prints a centred string surrounded by characters specified by the style
	 * parametre.
	 *
	 * @param   string  the string to be printed
	 * @param   style   the style of characters with which to surround the
	 *                  string
	 * @see             Style
	 * @see             StringBuilder
	 */
	public static void printMessage(String string, Style style) {
		char[] blocks = Style.getBlocks(style);
		final int max = 78; // Maximum length of a line.
		string = " " + string + " ";
		int length = string.length();
		int indexHalf = (max - length) / 2; // Amount of columns at
		// either side of the string to print if the string was centred.
		StringBuilder output = new StringBuilder(max);
		StringBuilder bar = new StringBuilder(max);

		while (output.length() < max) {
			int index = output.length();

			if (length > 50 && index == indexHalf + length &&
					length % 2 != 0) {
				// Add a space to the end of the string if odd if the
				// longest string is over 50 chars. This way the odd
				// length is less noticeable for long strings compared to
				// having an extra block at the end.
				output.append(" ");
				output.append(blocks[0]);
			} else if (index == 0 || index == max - 1 ||
					index == indexHalf - 1 ||
					index == indexHalf + length) {
				output.append(blocks[0]);
			} else if (index == indexHalf){
				output.append(string);
			} else {
				output.append(blocks[1]);
			}
		}

		// Appends the primary block char to the string until the maximum
		// line length is reached.
		// Inefficient since the maximum length is constant, but useful
		// if a way to get the console window size is later added.
		for (int index = 0; index < max; index ++) {
			bar.append(blocks[0]);
		}

		System.out.println("\n" + bar);
		System.out.println(output);
		System.out.println(bar + "\n");
	}

	/**
	 * Prints multiple centred strings surrounded by characters specified by
	 * the style parametre. For all strings, the space between the borders and
	 * strings is that of the largest string in the array.
	 *
	 * @param   strings the array of strings to be printed
	 * @param   style   the style of the characters with which to surround the
	 *                  strings
	 * @see             Style
	 * @see             StringBuilder
	 */
	public static void printMessage(String[] strings, Style style) {
		char[] blocks = Style.getBlocks(style);
		final int max = 78; // Maximum length of a line.
		int longest = 0; // The length of the longest string in the array.
		StringBuilder bar = new StringBuilder(max);

		// Appends the primary block char to the string until the maximum
		// line length is reached.
		// Inefficient since the maximum length is constant, but useful
		// if a way to get the console window size is later added.
		for (int index = 0; index < max; index ++) {
			bar.append(blocks[0]);
		}

		System.out.println("\n" + bar);

		// Gets the length of the longest string in the array.
		for (String line : strings) {
			if (line.length() + 2 > longest) {
				longest = line.length() + 2; // Add 2 because 2 spaces will be
				// added in the next loop.
			}
		}

		for (String line : strings) {
			line = " " + line + " ";
			int length = line.length();
			int indexHalf = (max - longest) / 2; // Amount of columns at
			// either side of the longest string if centred.
			int indexHalfCurrent = (max - length) / 2; // Amount of columns at
			// either side of the current string if centred.
			StringBuilder output = new StringBuilder(max);

			while (output.length() < max) {
				int index = output.length();

				if (longest > 50 && index == indexHalf + longest &&
						longest % 2 != 0) {
					// Add a space to the end of the string if odd if the
					// longest string is over 50 chars. This way the odd
					// length is less noticeable for long strings compared to
					// having an extra block at the end.
					output.append(" ");
					output.append(blocks[0]);
				} else if (index == 0 || index == max - 1 ||
						index == indexHalf - 1 ||
						index == indexHalf + longest) {
					output.append(blocks[0]);
				} else if (index == indexHalfCurrent) {
					output.append(line);
				} else if (index >= indexHalfCurrent + length &&
						index < indexHalf + longest) {
					output.append(" ");
				} else if (index >= indexHalf && index < indexHalfCurrent) {
					output.append(" ");
				} else {
					output.append(blocks[1]);
				}
			}
			System.out.println(output);
		}
		System.out.println(bar + "\n");
	}

	/**
	 * Prints a list of errors to the console and prompts the user to
	 * continue, immediately terminating the program if the user does not
	 * wish to continue so that the statistics file is not modified.
	 *
	 * @param   name    the user's name
	 * @param   errors  the list of errors to print
	 * @see             #printMessage(String[], Style)
	 * @see             #cont(String, boolean)
	 */
	public static void printErrors(String name, List<String> errors) {
		errors.add(0, "Error parsing the statistics file!");
		String errorsArr[] = errors.toArray(new String[errors.size()]);
		printMessage(errorsArr, Style.Light);
		cont(name, true);
	}

	/**
	 * Prints a 2-dimensional string array of rows to a centred table. For
	 * all cells per column, the width of the cells is that of the cell for
	 * the largest string
	 *
	 * @param   rows    the 2-dimensional string array of rows to print to a
	 *                  table
	 * @see             #getColumns(String[][])
	 * @see             StringBuilder
	 */
	public static void printTable(String[][] rows) {
		final int max = 78;
		String[][] columns = getColumns(rows);

		for (int index = 0; index < rows.length; index ++) {
			String[] row = rows[index];
			StringBuilder topBorder = new StringBuilder();
			StringBuilder innerBorder = new StringBuilder();
			StringBuilder botBorder = new StringBuilder();
			StringBuilder rowOut = new StringBuilder();

			for (int indexCell = 0; indexCell < row.length; indexCell ++) {
				String cell = row[indexCell];
				int length = cell.length();
				int longest = 0;

				for (String indexColCell:columns[indexCell]) {
					if (indexColCell.length() > longest) {
						longest = indexColCell.length();
					}
				}

				if (indexCell == 0) {
					topBorder.append("╔═");
					innerBorder.append("╠═");
					botBorder.append("╚═");
				} else {
					topBorder.append("╦═");
					innerBorder.append("╬═");
					botBorder.append("╩═");
				}
				rowOut.append("║ ");

				rowOut.append(cell);
				while (topBorder.length() < rowOut.length()) {
					topBorder.append("═");
					innerBorder.append("═");
					botBorder.append("═");
				}

				int indexEnd = rowOut.length() + longest - length + 1;
				while (rowOut.length() < indexEnd) {
					topBorder.append("═");
					innerBorder.append("═");
					botBorder.append("═");
					rowOut.append(" ");
				}
			}

			topBorder.append("╗");
			innerBorder.append("╣");
			botBorder.append("╝");
			rowOut.append("║");

			int indexCentre = (max - rowOut.length()) / 2;
			while (rowOut.length() + indexCentre < max) {
				topBorder.insert(0, " ");
				innerBorder.insert(0, " ");
				botBorder.insert(0, " ");
				rowOut.insert(0, " ");
			}

			if (index == 0) {
				System.out.println(topBorder.toString());
				System.out.println(rowOut.toString());
				System.out.println(innerBorder.toString());
			} else if (index == rows.length - 1) {
				System.out.println(rowOut.toString());
				System.out.println(botBorder.toString());
			} else {
				System.out.println(rowOut.toString());
				System.out.println(innerBorder.toString());
			}
		}
	}

	/**
	 * Validates the users input based on the type of input expected. If
	 * invalid, prompts the user to try again.
	 *
	 * @param   input   the string of the user's input
	 * @param   type    the type of input expected
	 * @return          <code>true</code> if the input is valid;
	 *                  <code>false</code> otherwise
	 * @see             #printMessage(String, Style)
	 * @see             Character
	 */
	public static boolean isInvalid(String input, String type) {
		switch (type) {
			case "name":
				if (input.isEmpty()) {
					printMessage("The name given is blank, please try again" +
							".", Style.Light);
					return true;
				}
				if (input.contains(" ")) {
					printMessage("Spaces are not allowed, please try again" +
							".", Style.Light);
					return true;
				}
				for (int index = 0; index < input.length(); index ++) {
					if(!Character.isLetter(input.charAt(index))) {
						printMessage("The name given contains invalid " +
								"characters, please try again.", Style.Light);
						return true;
					}
				}
				return false;
			case "answer":
				if (input.isEmpty()) {
					printMessage("The answer given is blank, please try again" +
							".", Style.Light);
					return true;
				}
				if (input.contains(" ")) {
					printMessage("Spaces are not allowed, please try again" +
							".", Style.Light);
					return true;
				}
				if (input.contains("-")) {
					printMessage("All answers are POSITIVE integers, please " +
							"try again.", Style.Light);
					return true;
				}

				for (int index = 0; index < input.length(); index ++) {
					if(!Character.isDigit(input.charAt(index))) {
						printMessage("The answer given is not an integer, " +
								"please try again.", Style.Light);
						return true;
					}
				}
				return false;
			default:
				printMessage("Something went wrong, please try again.", Style.Light);
				return true;
		}
	}

	/**
	 * Prints a continue prompt to the console. If the user inputs
	 * <code>y</code>/<code>Y</code>, it returns to the main menu. Otherwise,
	 * the program is terminated.
	 *
	 * @param   name    the user's name
	 * @param   exit    <code>true</code> if the program should terminate at
	 *                  this method; <code>false</code> if the program should
	 *                  continue until the end of {@link #main(String[])}
	 * @see             #printMessage(String, Style)
	 * @see             Scanner
	 */
	public static void cont(String name, boolean exit) {
		Scanner scanner = new Scanner(System.in).useLocale(Locale.getDefault());

		printMessage("Enter [y]/[Y] to continue or any other key to exit.",
				Style.Light);

		String input = scanner.nextLine().toLowerCase();

		if (input.equals("y") && !exit) {
			menu(name);
		} else if (!input.equals("y") && exit) {
			System.exit(0);
		}
	}

	/**
	 * Prints to console a splash screen containing the title of the program and
	 * the author's name.
	 *
	 * @see             #printMessage(String, Style)
	 */
	private static void credits() {
		System.out.print("▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓" +
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
				"                ▓");
		printMessage("By Mark Kozlov", Style.Bold);
	}

	/**
	 * Prints to console a menu containing options for the user to choose from.
	 *
	 * @param   name    the user's name
	 * @see             #menuSelection(String)
	 * @see             #printTable(String[][])
	 * @see             #printMessage(String[], Style)
	 */
	private static void menu(String name) {

		printMessage(new String[] {"Choose an option from the right column " +
				"and enter the", "corresponding character from the left " +
				"column to make your selection."}, Style.Bold);

		String[] row0 = {"1", "Addition Expression"};
		String[] row1 = {"2", "Subtraction Expression"};
		String[] row2 = {"3", "Multiplication Expression"};
		String[] row3 = {"4", "Division Expression"};
		String[] row4 = {"5", "Statistics"};
		String[] row5 = {"q/Q", "Quit"};
		String[][] rows = {row0, row1, row2, row3, row4, row5};

		MathGame.printTable(rows);
		System.out.println();

		menuSelection(name);
	}

	/**
	 * Gets the user's selection for the menu and executes the corresponding
	 * actions.
	 *
	 * @param   name    the user's name
	 * @see             Expression
	 * @see             Scanner
	 */
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
					answer = Expression.genAdd();
					Expression.isCorrect(Expression.getAnswer(), answer, name);
					retry = false;
					break;
				case "2":
					answer = Expression.genSub();
					Expression.isCorrect(Expression.getAnswer(), answer, name);
					retry = false;
					break;
				case "3":
					answer = Expression.genMult();
					Expression.isCorrect(Expression.getAnswer(), answer, name);
					retry = false;
					break;
				case "4":
					answer = Expression.genDiv();
					Expression.isCorrect(Expression.getAnswer(), answer, name);
					retry = false;
					break;
				case "5":
					Stats.print(name);
					retry = false;
					break;
				default:
					printMessage("That selection is invalid, please try again" +
							".", Style.Light);
					retry = true;
					break;
			}
		} while (retry);
	}

	/**
	 * Requests the user to input one's name and returns that input.
	 *
	 * @return          a string of the user's inputted name
	 * @see             #printMessage(String, Style)
	 * @see             #isInvalid(String, String)
	 * @see             Scanner
	 */
	private static String getName() {
		Scanner scanner = new Scanner(System.in).useLocale(Locale.getDefault());
		String inputName;

		printMessage("Enter your name.", Style.Bold);
		do {
			inputName = scanner.nextLine();
		} while (isInvalid(inputName, "name"));

		return inputName;
	}

	/**
	 * Converts a 2-dimensional array of rows into a 2-dimensional array of
	 * columns.
	 *
	 * @param   rows    the 2-dimensional array of rows
	 * @return          a 2-dimensional array of columns
	 * @see             #printTable(String[][])
	 */
	private static String[][] getColumns (String[][] rows) {
		int count = 0;
		String[][] columns = new String[rows[1].length][];

		do {
			String[] col = new String[rows.length];

			for (int index = 0; index < rows.length; index ++) {
				System.arraycopy(rows[index], count, col, index, 1);
			}

			columns[count] = col;
			count ++;
		} while (count < rows[1].length);

		return columns;
	}
}

/**
 * This class is for methods related to tracking and updating the user's
 * statistics.
 *
 * @author      Mark Kozlov
 * @see         MathGame#getName()
 */
class Stats {

	/**
	 * Reads the user's statistics file, corrects any field name errors,
	 * extracts the values from the fields, and returns these statistics values
	 * in a string array.
	 * <p>
	 * If the field name is invalid, the value will also be reset to the
	 * default.
	 *
	 * @param   name        the user's name
	 * @param   fileExists  <code>true</code> if the file already existed at
	 *                      runtime; <code>false</code> if the file was just
	 *                      created.
	 * @return              a string array of the user's statistics
	 * @see                 Scanner
	 */
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

			// No need to print errors if the file was just created.
			if (!errors.isEmpty() && fileExists) {
				MathGame.printErrors(name, errors);
			}

			inputFile.close();

			return new String[]{statName, statCorrect, statWrong, statEarnings};
		} catch (FileNotFoundException e) {
			MathGame.printMessage(new String[]{"The statistics file could " +
					"not be accessed.", "Try restarting the game to recreate " +
					"the file", "or launching the game in another directory."},
					Style.Bold);
			MathGame.cont(name, false);
			// e.printStackTrace();
		}
		return null;
	}

	/**
	 * Returns a string array of the user's statistics with updated values
	 * based on if the user's given answer was correct/wrong.
	 *
	 * @param   name        the user's name
	 * @param   isCorrect   if the user's given answer was correct/wrong
	 * @return              a string array of the user's statistics with updated
	 *                      values
	 * @see                 #get(String, boolean)
	 */
	public static String[] update(String name, boolean isCorrect) {
		String statName = name;
		int statCorrect = 0;
		int statWrong = 0;
		double statEarnings = 0;
		String[] stats = get(name, true);
		List<String> errors = new ArrayList<>();

		DecimalFormat decimalFormat = new DecimalFormat("#0.00");
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		// Would be ideal to leave it up to user's locale but that would
		// over-complicate since earnings are always in USD anyway.
		symbols.setDecimalSeparator('.');
		decimalFormat.setDecimalFormatSymbols(symbols);

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
				errors.add("Correct the value(s) manually");
				errors.add("or delete the file so that it can be remade.");
				MathGame.printErrors(name, errors);
			}
		}

		if (isCorrect) {
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
			.toString(statWrong), decimalFormat.format(statEarnings)};
	}

	/**
	 * Writes the user's statistics to file. If it does not exist, it creates an
	 * empty file named after the user's name.
	 *
	 * @param   name    the user's name
	 * @param   stats   the string array of the user's statistic to be
	 *                  written to file
	 */
	public static void save(String name, String[] stats) {
		String fileName = name + ".txt";

		File statsFile = new File (fileName);
		PrintWriter printWriter;

		try {
			if (!statsFile.exists()) {
				printWriter = new PrintWriter(fileName);
				stats = get(name, false);
			} else {
				if (stats.length == 0) {
					stats = get(name, true);
				}
				FileWriter fileWriter = new FileWriter(fileName);
				printWriter = new PrintWriter(fileWriter);
			}

			if (stats !=null) {
				printWriter.println("NAME=" + stats[0]);
				printWriter.println("CORRECT=" + stats[1]);
				printWriter.println("WRONG=" + stats[2]);
				printWriter.println("EARNINGS=" + stats[3]);
			}
			printWriter.close();
		} catch (FileNotFoundException e) {
			MathGame.printMessage(new String[]{"The statistics file could" +
					" not be accessed.", "Try restarting the game to " +
					"recreate the file", "or launching the game in another" +
					" directory."}, Style.Bold);
			MathGame.cont(name, false);
			// e.printStackTrace();
		} catch (IOException e) {
			MathGame.printMessage(new String[]{"The statistics file could" +
				" not be accessed or created.", "Try launching the game in " +
				"another directory."}, Style.Bold);
			MathGame.cont(name, false);
			// e.printStackTrace();
		}
	}

	/**
	 * Prints the user's statistics to the console.
	 *
	 * @param   name    the user's name
	 * @see             MathGame#printTable(String[][])
	 */
	public static void print(String name) {
		String[] stats = get(name, true);

		if (stats != null) {
			String[] row0 = {"Name", stats[0]};
			String[] row1 = {"Correct", stats[1]};
			String[] row2 = {"Wrong", stats[2]};
			String[] row3 = {"Earnings", "$" + stats[3]};
			String[][] rows = {row0, row1, row2, row3};

			MathGame.printMessage("Statistics", Style.Bold);
			MathGame.printTable(rows);
			MathGame.cont(name, false);
		}
	}
}

/**
 * This class contains methods pertaining to the expressions the user has to
 * solve. Methods for:
 * <ul>
 *     <li>Generation of expressions</li>
 *     <li>Input retrieval</li>
 *     <li>Comparison of answers</li>
 * </ul>
 *
 * @author      Mark Kozlov
 */
class Expression {
	/**
	 * Generates an addition expression with random terms and prints it to
	 * the console.
	 * <p>
	 * The terms are integers ranging from 0-10.
	 *
	 * @return          the answer to the generated expression
	 * @see             Random
	 */
	public static int genAdd() {
		Random rand = new Random();

		int first = rand.nextInt(11);
		int second = rand.nextInt(11);
		String expressionStr = first + " + " + second + " = ?";

		MathGame.printMessage(expressionStr, Style.Bold);

		return first + second;
	}

	/**
	 * Generates a subtraction expression with random terms and prints it to
	 * the console.
	 * <p>
	 * The terms are integers ranging from 0-10. The subtrahend is always
	 * greater than or equal to the minuend so that the expression evaluates
	 * to 0 or a positive integer.
	 *
	 * @return          the answer to the generated expression
	 * @see             Random
	 */
	public static int genSub() {
		Random rand = new Random();

		int first =  rand.nextInt(11);
		int second = rand.nextInt(first + 1);
		String expressionStr = first + " - " + second + " = ?";

		MathGame.printMessage(expressionStr, Style.Bold);

		return first - second;
	}

	/**
	 * Generates a multiplication expression with random terms and prints it
	 * to the console.
	 * <p>
	 * The terms are integers ranging from 0-10.
	 *
	 * @return          the answer to the generated expression
	 * @see             Random
	 */
	public static int genMult() {
		Random rand = new Random();

		int first = rand.nextInt(11);
		int second = rand.nextInt(11);
		String expressionStr = first + " × " + second + " = ?";

		MathGame.printMessage(expressionStr, Style.Bold);

		return first * second;
	}

	/**
	 * Generates a division expression with random terms and prints it to the
	 * console.
	 * <p>
	 * The dividend is an integer ranging from 0-100. The divisor is an
	 * integer ranging from 1-10 and is a factor of the dividend.
	 *
	 * @return          the answer to the generated expression
	 * @see             Random
	 */
	public static int genDiv() {
		Random rand = new Random();

		int first = rand.nextInt(101);
		int second;

		do {
			second = rand.nextInt(10) + 1; // Add 1 to avoid dividing by 0
		} while (first % second != 0);

		String expressionStr = first + " ÷ " + second + " = ?";

		MathGame.printMessage(expressionStr, Style.Bold);

		return first / second;
	}

	/**
	 * Retrieve the user's answer to an expression.
	 *
	 * @return          the user's input as an integer
	 * @see             MathGame#isInvalid(String, String)
	 * @see             Scanner
	 */
	public static int getAnswer() {
		Scanner scanner = new Scanner(System.in).useLocale(Locale.getDefault());
		String input;

		do {
			input = scanner.nextLine();
		} while (MathGame.isInvalid(input, "answer"));

		return Integer.parseInt(input);
	}

	/**
	 * Compares the user's answer to the correct answer. Outputs a message
	 * stating whether the user is correct/wrong and updates the user's stats
	 * accordingly.
	 *
	 * @param   input   the user's inputted answer
	 * @param   answer  the correct answer to the expression
	 * @param   name    the user's name
	 * @see             Stats#update(String, boolean)
	 * @see             Stats#save(String, String[])
	 * @see             MathGame#printMessage(String, Style)
	 */
	public static void isCorrect(int input, int answer, String name) {
		if (input == answer) {
			MathGame.printMessage("Correct!", Style.Bold);
			Stats.save(name, Stats.update(name, true));
		} else {
			MathGame.printMessage("Wrong!", Style.Bold);
			Stats.save(name, Stats.update(name, false));
		}

		MathGame.cont(name, false);
	}
}

/**
 * An enum for the available border styles to use with
 * {@link MathGame#printMessage(String, Style)} and
 * {@link MathGame#printMessage(String[], Style)}
 *
 * @author      Mark Kozlov
 * @see         MathGame#printMessage(String, Style)
 * @see         MathGame#printMessage(String[], Style)
 */
enum Style {
	Bold,
	Light;

	/**
	 * Returns an array of characters corresponding to the style given.
	 *
	 * @param   style   the style to use
	 * @return          an character array with the corresponding blocks
	 */
	public static char[] getBlocks(Style style) {
		char[] blocks = new char[2];
		switch (style) {
			case Bold:
				blocks[0] = '▓';
				blocks[1] = '▒';
				break;
			case Light:
				blocks[0] = '▒';
				blocks[1] = '░';
				break;
		}
		return blocks;
	}
}