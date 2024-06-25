package de.ostfalia.gdp.ws23.s1;

import java.util.Arrays;

class Nachricht {
	private static int idCounter = 1;
	private int id;
	private String betreff;
	private String mitteilung;
	private Benutzer autor;

	public Nachricht(Benutzer autor, String betreff, String mitteilung) {
		this.id = idCounter++;
		this.autor = autor;
		this.betreff = betreff;
		this.mitteilung = mitteilung;
	}

	public int getId() {
		return id;
	}

	public String getBetreff() {
		return betreff;
	}

	public String getMitteilung() {
		return mitteilung;
	}

	public Benutzer getAutor() {
		return autor;
	}

}

class Benutzer {
	private static int idCounter = 1;
	private int id;
	private String benutzername;
	private String email;

	public Benutzer(String benutzername, String email) {
		this.id = idCounter++;
		this.benutzername = benutzername;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public String getBenutzername() {
		return benutzername;
	}

	public String getEmail() {
		return email;
	}

}

public class SchwarzesBrett {
	private Benutzer[] benutzer;
	private Nachricht[] nachrichten;
	private int benutzerCount;
	private int nachrichtenCount;

	public SchwarzesBrett() {
		this.benutzer = new Benutzer[100];
		this.nachrichten = new Nachricht[1000];
		this.benutzerCount = 0;
		this.nachrichtenCount = 0;
	}

	public boolean speichereNachricht(Nachricht nachricht) {
		if (nachricht != null && nachricht.getAutor() != null && istBenutzerRegistriert(nachricht.getAutor())) {
			if (nachrichtenCount < nachrichten.length) {
				nachrichten[nachrichtenCount++] = nachricht;
				return true;
			} else {
				System.out.println("Nachrichten sind erfüllt");
			}
		} else {
			System.out.println("Error");
		}
		return false;
	}

	public boolean registriereBenutzer(Benutzer benutzer) {
		if (benutzer != null && !istBenutzernameVorhanden(benutzer.getBenutzername())) {
			if (benutzerCount < 100) {
				this.benutzer[benutzerCount++] = benutzer;
				return true;
			} else {
				System.out.println("User limit reached. Cannot register more users.");
			}
		} else {
			System.out.println("Invalid user or username already exists.");
		}
		return false;
	}

	public Benutzer sucheBenutzer(String benutzername) {
		for (int i = 0; i < benutzerCount; i++) {
			if (benutzer[i].getBenutzername().equalsIgnoreCase(benutzername)) {
				return benutzer[i];
			}
		}
		return null;
	}

	public Nachricht[] sucheNachrichten(String suchbegriff) {
		Nachricht[] result = new Nachricht[1000];
		int resultCount = 0;
		for (int i = 0; i < nachrichtenCount; i++) {
			if (nachrichten[i].getBetreff().toLowerCase().contains(suchbegriff.toLowerCase())
					|| nachrichten[i].getMitteilung().toLowerCase().contains(suchbegriff.toLowerCase())) {
				result[resultCount++] = nachrichten[i];
			}
		}
		return Arrays.copyOf(result, resultCount);
	}

	public int anzahlNachrichten() {
		return nachrichtenCount;
	}

	public int anzahlBenutzer() {
		return benutzerCount;
	}

	private boolean istBenutzerRegistriert(Benutzer benutzer) {
		for (int i = 0; i < benutzerCount; i++) {
			if (this.benutzer[i] != null && this.benutzer[i].equals(benutzer)) {
				return true;
			}
		}
		return false;
	}

	private boolean istBenutzernameVorhanden(String benutzername) {
		for (int i = 0; i < benutzerCount; i++) {
			if (this.benutzer[i].getBenutzername().equalsIgnoreCase(benutzername)) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		SchwarzesBrett schwarzesBrett = new SchwarzesBrett();
		Benutzer q1 = new Benutzer("user", "email@gmai.com");
		Benutzer q2 = new Benutzer("users", "email2@gmai.com");

		schwarzesBrett.registriereBenutzer(q1);
		schwarzesBrett.registriereBenutzer(q2);

		Benutzer such = schwarzesBrett.sucheBenutzer("user");
		if (such != null) {
			System.out.println("gefunden: " + such.getBenutzername());
		} else {
			System.out.println("keine benutzer");
		}

		Nachricht n1 = new Nachricht(q1, "betref1", "GDP");
		Nachricht n2 = new Nachricht(q2, "betreff2", "TGI");

		schwarzesBrett.speichereNachricht(n1);
		schwarzesBrett.speichereNachricht(n2);

		Nachricht[] searchResult = schwarzesBrett.sucheNachrichten("Meeting");
		System.out.println("Search Result:");
		for (Nachricht message : searchResult) {
			if (message != null) {
				System.out.println("ID: " + message.getId() + ", Betreff: " + message.getBetreff());
			}
		}

		System.out.println("Number of Users: " + schwarzesBrett.anzahlBenutzer());
		System.out.println("Number of Messages: " + schwarzesBrett.anzahlNachrichten());
	}
}
