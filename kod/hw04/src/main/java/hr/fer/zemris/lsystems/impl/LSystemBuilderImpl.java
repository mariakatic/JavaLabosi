package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.collections.Dictionary;
import hr.fer.zemris.lsystems.impl.collections.Vector2D;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;

/**
 * Razred sluzi za izgradnju Lindermayerovih sustava. 
 * @author Maria
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder {
	
	private int idx = 0;
	
	private Dictionary<Character, String> productions;
	private Dictionary<Character, Command> actions;
	private double unitLength;
	private double unitLengthDegreeScaler;
	private Vector2D origin;
	private double angle;
	private String axiom;

	/**
	 * Stvara novi LSystemBuilder s pretpostavljenim vrijednostima.
	 */
	public LSystemBuilderImpl() {
		productions = new Dictionary<>();
		actions = new Dictionary<>();
		unitLength = 0.1;
		unitLengthDegreeScaler = 1;
		origin = new Vector2D(0, 0);
		angle = 0;
		axiom = "";
	}
	
	/**
	 * Modelira neki odredeni Lindermayerov sustav.
	 * @author Maria
	 *
	 */
	class LSystemImpl implements LSystem {

		/**
		 * Metoda  uporabom primljenog objekta za crtanje linija koji je tipa Painter crta rezultantni fraktal na dubini arg0. 
		 */
		@Override
		public void draw(int arg0, Painter arg1) {
			
			Context ctx = new Context();
			double shiftLength = unitLength * Math.pow(unitLengthDegreeScaler, arg0);
			TurtleState state = new TurtleState(origin, new Vector2D(Math.cos(angle), Math.sin(angle)) , Color.BLACK, shiftLength);
			ctx.pushState(state);
			
			String entries = this.generate(arg0);
			for (int i = 0; i < entries.length(); i++) {
				Command action = actions.get(entries.charAt(i));
				if (action != null) {
					action.execute(ctx, arg1);
				}
			}
		}

		/**
		 * Metoda generira niz simbola za traženu razinu.
		 */
		@Override
		public String generate(int arg0) {
			if (arg0 == 0)
				return axiom;
			
			String res = this.generate(arg0 - 1);
			
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < res.length(); i++) {
				if (productions.get(res.charAt(i)) != null) {
					sb.append(productions.get(res.charAt(i)));
				} else {
					sb.append(res.charAt(i));
				}
			}
			return sb.toString();
		}
		
	}

	/**
	 * Stvara novi Lindermayerov sustav na temelju zadanih parametara.
	 */
	@Override
	public LSystem build() {
		return new LSystemImpl();
	}
	
	public String createToken (String data) {
		StringBuilder sb = new StringBuilder();
		while (idx < data.length()) {
			if (data.charAt(idx) == ' ') {
				break;
			}
			sb.append(data.charAt(idx));
			idx++;
		}
		
		while(idx < data.length()) {
			if (data.charAt(idx) != ' ') {
				break;
			}
			idx++;
		}
		
		return sb.toString();
	}

	/**
	 * Stvara LSystemBuilder na temelju zadanih linija teksta.
	 * Podrazumijeva da svaka linija sadrzi jednu direktivu (ili je prazna).
	 */
	@Override
	public LSystemBuilder configureFromText(String[] arg0) {
		
		for (int i = 0; i < arg0.length; i++) {
			idx = 0;
			
			if (arg0[i].isEmpty())
				continue;
			
			String method = createToken(arg0[i]);
			
			if (method.equals("origin")) {
				String first = createToken(arg0[i]);
				String second = createToken(arg0[i]);
				this.setOrigin(Double.parseDouble(first), Double.parseDouble(second));
				
			} else if (method.equals("angle")) {
				String first = createToken(arg0[i]);
				this.setAngle(Double.parseDouble(first));
				
			} else if (method.equals("axiom")) {
				String first = createToken(arg0[i]);
				this.setAxiom(first);

			} else if (method.equals("unitLength")) {
				String first = createToken(arg0[i]);
				this.setUnitLength(Double.parseDouble(first));
				
			} else if (method.equals("unitLengthDegreeScaler")) {
				String first = createToken(arg0[i]);
				String second = String.valueOf(arg0[i].charAt(idx));
				idx++;
				while(idx < arg0[i].length()) {
					if (arg0[i].charAt(idx) != ' ') {
						break;
					}
					idx++;
				}
				
				if (!second.equals("/"))
					throw new IllegalArgumentException();
				
				String third = createToken(arg0[i]);
				this.setUnitLengthDegreeScaler(Double.parseDouble(first) / Double.parseDouble(third));
				
			} else if (method.equals("production")) {
				String first = createToken(arg0[i]);
				String second = createToken(arg0[i]);
				
				if (first.length() != 1)
					throw new IllegalArgumentException();
				
				this.registerProduction(first.charAt(0), second);
				
			} else if (method.equals("command")) {
				String first = createToken(arg0[i]);
				String second = createToken(arg0[i]);
				String third = createToken(arg0[i]);

				if (first.length() != 1)
					throw new IllegalArgumentException();
				
				this.registerCommand(first.charAt(0), second + " " + third);
				
			} else {
				throw new IllegalArgumentException("Neispravno zadana naredba.");
			}
		}

		return this;
	}

	/**
	 * Definira akciju koju kornjaca treba napraviti.
	 * @param arg0 simbol pomocu kojeg je zapisana akcija
	 * @param arg1 tekst akcije
	 */
	@Override
	public LSystemBuilder registerCommand(char arg0, String arg1) {
		char[] action = arg1.toCharArray();
		int index = 0;
		StringBuilder sb = new StringBuilder();
		
		while (index < action.length) {
			if (Character.isLetter(action[index])) {
				sb.append(action[index]);
				index++;
			} else if (action[index] == ' ') {
				break;
			} else {
				throw new IllegalArgumentException("Neispravno zadana naredba.");
			}
		}
		index++;
		
		String command = sb.toString();
		Command com;
		
		if (command.equals("pop")) {
			com = new PopCommand();
			
		} else if (command.equals("push")) {
			com = new PushCommand();
			
		} else if (command.equals("color")) {
			StringBuilder sbColor = new StringBuilder();
			while (index < action.length) {
				sbColor.append(action[index]);
				index++;
			}
			
			try {
				Integer i = Integer.parseInt(sbColor.toString(), 16);
				Color color = new Color(i);
				com = new ColorCommand(color);
			} catch (NumberFormatException ex) {
				throw new IllegalArgumentException("Neispravno zadana naredba.");
			}
			
		} else {
			StringBuilder sbArg = new StringBuilder();
			while (index < action.length) {
				sbArg.append(action[index]);
				index++;
			}
			
			try {
				Double d = Double.parseDouble(sbArg.toString());
				if (command.equals("skip")) {
					com = new SkipCommand(d);
				} else if (command.equals("scale")) {
					com = new ScaleCommand(d);
				} else if (command.equals("rotate")) {
					com = new RotateCommand(d);
				} else if (command.equals("draw")) {
					com = new DrawCommand(d);
				} else {
					throw new IllegalArgumentException("Neispravno zadana naredba.");
				}
			} catch (NumberFormatException ex) {
				throw new IllegalArgumentException("Neispravno zadana naredba.");
			}
		}
		
		actions.put(arg0, com);

		return this;
	}

	/**
	 * Za simbol definira niz kojim se isti zamjenjuje.
	 * @param arg0 simbol 
	 * @param arg1 niz kojim se isti zamjenjuje
	 */
	@Override
	public LSystemBuilder registerProduction(char arg0, String arg1) {
		productions.put(arg0, arg1);
		return this;
	}

	/**
	 * Postavlja kut prema pozitivnoj osi-x smjera u kojem kornjača gleda.
	 * @param arg0 kut 
	 */
	@Override
	public LSystemBuilder setAngle(double arg0) {
		this.angle = arg0 * Math.PI / 180.0;
		return this;
	}

	/**
	 * Postavlja početni niz iz kojeg kreće razvoj sustava.
	 * @param arg0 jedan simbol ili niz simbola
	 */
	@Override
	public LSystemBuilder setAxiom(String arg0) {
		this.axiom = arg0;
		return this;
	}

	/**
	 * Zadaje tocku iz koje kornjaca krece.
	 * @param arg0 x koordinata tocke 
	 * @param arg1 y koordinata tocke
	 */
	@Override
	public LSystemBuilder setOrigin(double arg0, double arg1) {
		this.origin = new Vector2D(arg0, arg1);
		return this;
	}

	/**
	 * Postavlja koliko je dugacak jedinicni pomak kornjace.
	 * @param arg0 vrijednost jedinicnog pomaka kornjace
	 */
	@Override
	public LSystemBuilder setUnitLength(double arg0) {
		this.unitLength = arg0;
		return this;
	}

	/**
	 * Sluzi za skaliranje pocetne efektivne duljine koraka kornjace.
	 * @param arg0 vrijednost s kojom se skalira
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double arg0) {
		this.unitLengthDegreeScaler = arg0;
		return this;
	}
	
	

	
}
