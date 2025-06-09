package cose457.drawingtool.command;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Executes commands and manages undo/redo history.
 */
public class CommandManager {
    private final Deque<Command> undoStack = new ArrayDeque<>();
    private final Deque<Command> redoStack = new ArrayDeque<>();

    /**
     * Execute a command and push it onto the undo stack.
     * The redo stack will be cleared.
     */
    public void execute(Command command) {
        command.execute();
        undoStack.push(command);
        redoStack.clear();
    }

    /**
     * Undo the last executed command if available.
     */
    public void undo() {
        if (!undoStack.isEmpty()) {
            Command cmd = undoStack.pop();
            cmd.undo();
            redoStack.push(cmd);
        }
    }

    /**
     * Redo the last undone command if available.
     */
    public void redo() {
        if (!redoStack.isEmpty()) {
            Command cmd = redoStack.pop();
            cmd.execute();
            undoStack.push(cmd);
        }
    }
}
