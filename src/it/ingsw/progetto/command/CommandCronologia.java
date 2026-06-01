package it.ingsw.progetto.command;

import java.util.ArrayDeque;
import java.util.Deque;

public class CommandCronologia {
private static final int MAX_HISTORY = 50; // Quanti "Undo" posso fare
	
	private final Deque<MediaCommand> history = new ArrayDeque<>();
	private final Deque<MediaCommand> redoStack = new ArrayDeque<>();
	
	// Esegue il comando e lo salva nella cronologia
	public boolean eseguiCommand(MediaCommand command) {
		boolean result = command.esegui();
		if (result) {
			history.push(command);
			redoStack.clear(); // Se faccio una nuova azione, perdo i vecchi Redo
			
			// Non teniamo in memoria troppe azioni
			while (history.size() > MAX_HISTORY) {
				history.removeLast();
			}
		}
		return result;
	}
	
	// Prende l'ultima azione fatta e la annulla
	public MediaCommand annulla() {
		if (!history.isEmpty()) {
			MediaCommand command = history.pop();
			if (command.annulla()) {
				redoStack.push(command); // Lo metto nella pila dei "Redo"
				return command;
			} else {
				history.push(command); // Se l'undo fallisce, lo rimetto a posto
			}
		}
		return null;
	}
	
	public boolean canUndo() {
		return !history.isEmpty();
	}
}
