package hr.fer.oprpp1.hw05.shell;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import hr.fer.oprpp1.hw05.shell.commands.CatShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.CharsetsShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.CopyShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.ExitShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.HelpShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.HexdumpShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.LsShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.MkdirShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.SymbolShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.TreeShellCommand;

public class SortedMapImpl<K, V> implements SortedMap<String, ShellCommand> {
	
	private TreeMap<String, ShellCommand> commands = new TreeMap<>();
	
	public SortedMapImpl() {
		commands.put("cat", new CatShellCommand());
		commands.put("charsets", new CharsetsShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("exit", new ExitShellCommand());
		commands.put("help", new HelpShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("symbol", new SymbolShellCommand());
		commands.put("tree", new TreeShellCommand());
	}

	@Override
	public int size() {
		return commands.size();
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public boolean containsKey(Object key) {
		return commands.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return commands.containsValue(value);
	}

	@Override
	public ShellCommand get(Object key) {
		return commands.get(key);
	}

	@Override
	public ShellCommand put(String key, ShellCommand value) {
		throw new RuntimeException("Map is unmodifiable.");
	}

	@Override
	public ShellCommand remove(Object key) {
		throw new RuntimeException("Map is unmodifiable.");
	}

	@Override
	public void putAll(Map<? extends String, ? extends ShellCommand> m) {
		throw new RuntimeException("Map is unmodifiable.");
	}

	@Override
	public void clear() {
		throw new RuntimeException("Map is unmodifiable.");
	}

	@Override
	public Comparator<? super String> comparator() {
		return commands.comparator();
	}

	@Override
	public SortedMap<String, ShellCommand> subMap(String fromKey, String toKey) {
		return null;
	}

	@Override
	public SortedMap<String, ShellCommand> headMap(String toKey) {
		return null;
	}

	@Override
	public SortedMap<String, ShellCommand> tailMap(String fromKey) {
		return null;
	}

	@Override
	public String firstKey() {
		return commands.firstKey();
	}

	@Override
	public String lastKey() {
		return commands.lastKey();
	}

	@Override
	public Set<String> keySet() {
		return commands.keySet();
	}

	@Override
	public Collection<ShellCommand> values() {
		return commands.values();
	}

	@Override
	public Set<Entry<String, ShellCommand>> entrySet() {
		return commands.entrySet();
	}

}
