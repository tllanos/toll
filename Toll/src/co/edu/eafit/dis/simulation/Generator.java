package co.edu.eafit.dis.simulation;

public class Generator {
	private String randomNames[] = {"Logan Zak", "Rodger Armand", "Dannie Rackley", "Cesar Weyer", 
		"Arlie Obrian", "Stanford Paulsen", "Brent Mcferrin", "Federico Kruse", "Norberto Friend", 
		"Houston Dufner", "Jessie Dugger", "Brendon Matzke", "Charles Meaux", "Alonso Milum", 
		"Lynn Dustin", "Cole Kamrowski", "Seth Colosimo", "Earle Nellis", "Wendell Barnette", 
		"Edgardo Painter", "Arthur Lirette", "Roger Cappel", "Keenan Weingartner", "Edmundo Wilfong", 
		"Gilberto Caputo", "Marshall Quijas", "Bradley Hulen", "Ned Dease", "Linwood Oswalt", 
		"Loyd Shipp", "Bradly Perin", "Kristopher Teske", "Leroy Kalin", "Minh Crosbie", 
		"Roderick Easterwood", "Nigel Jiles", "Jayson Fulbright", "Matthew Northup", "Eusebio Laning", 
		"Rey Rana", "Milan Rae", "Eloy Maynard", "Maria Hobgood", "Daryl Nordquist", "Elmo Melecio", 
		"James Greb", "Orlando Alvardo", "Hiram Jansson", "Quentin Vanderhorst", "Vincent Bridger"
	};
	
	public String generate(int idArray) {
		return randomNames[idArray];
	}
}